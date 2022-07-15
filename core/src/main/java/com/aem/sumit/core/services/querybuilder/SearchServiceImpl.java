package com.aem.sumit.core.services.querybuilder;

import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchService.class , immediate = true)
public class SearchServiceImpl implements SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);


    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resolverFactory;

    @Activate
    public void activate() {
        LOG.info("---SEARCH SERVICE ACTIVATED---");
    }

    public Map<String, String> createSearchTextQuery(String searchText,int startResult , int resultPerPage) {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("path","/content/we-retail");
        queryMap.put("type","cq:Page");
        queryMap.put("fulltext",searchText);
        queryMap.put("p.offset",Long.toString(startResult));
        queryMap.put("p.limit",Long.toString(resultPerPage));
        return queryMap;
    }

    @Override
    public JSONObject searchResult(String searchText,int startResult , int resultPerPage) {
        JSONObject finalResult = new JSONObject();
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(createSearchTextQuery(searchText,startResult,resultPerPage)),session);

            SearchResult searchResult =  query.getResult();
            int pagePerResults = searchResult.getHits().size();
            long totalResult = searchResult.getTotalMatches();
            long startingResult = searchResult.getStartIndex();
            double totalPages = Math.ceil((double) totalResult/(double) resultPerPage);

            finalResult.put("perpageresult",pagePerResults);
            finalResult.put("totalresult",totalResult);
            finalResult.put("startingresult",startingResult);
            finalResult.put("pages",totalPages);

            List<Hit> hits = searchResult.getHits();
            JSONArray resultArray = new JSONArray();

            for(Hit hit:hits) {
                Page page = hit.getResource().adaptTo(Page.class);

                if(page!=null){
                    JSONObject resultObject = new JSONObject();
                    resultObject.put("title",page.getTitle());
                    resultObject.put("path",page.getPath());
                    resultArray.put(resultObject);
                    LOG.info("Page {}",page.getPath());
                }
            }
            finalResult.put("results",resultArray);
        } catch (LoginException | RepositoryException | JSONException e) {
            e.printStackTrace();
        }

        return finalResult;
    }
}
