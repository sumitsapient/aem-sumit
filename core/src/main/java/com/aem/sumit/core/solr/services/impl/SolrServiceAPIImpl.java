package com.aem.sumit.core.solr.services.impl;

import com.aem.sumit.core.solr.constants.SolrConstants;
import com.aem.sumit.core.solr.helper.PageDetail;
import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.util.SolrUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Component(service = SolrServiceAPI.class , immediate = true)
public class SolrServiceAPIImpl implements SolrServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(SolrServiceAPIImpl.class);

    @Override
    public String addDocuments(List<PageDetail> pageDetails, SolrSearchHelper solrSearchHelper) {
        try{
            final String solrServer = solrSearchHelper.getSolrOSGIConfigService().getSolrServerEndpoint();
            final SolrClient client =SolrUtils.getSolrClient(solrServer);
            for(PageDetail page: pageDetails) {
                final SolrInputDocument document = new SolrInputDocument();
                document.addField(SolrConstants.PAGE_ID,SolrUtils.getPageName(page.getPath()));
                document.addField(SolrConstants.PAGE_TITLE,page.getTitle());
                document.addField(SolrConstants.PAGE_NAME,page.getName());
                document.addField(SolrConstants.PAGE_DESCRIPTION,page.getDescription());
                final UpdateResponse updateResponse = client.add(solrSearchHelper.getSolrCAConfig().coreName(),document);
            }
            //Indexed document must be committed
            client.commit(solrSearchHelper.getSolrCAConfig().coreName());
            LOG.info("\n Total Documents Added - {}",pageDetails.size());
            return pageDetails.size() + " Documents Added!!";

        }
        catch (Exception e) {
           LOG.error("Error while indexing -{}",e.getMessage());
        }

        return "Documents were failed to index.";
    }

    @Override
    public JSONArray getSearchResult(String searchText, SolrSearchHelper solrSearchHelper) {
        JSONArray searchResult = new JSONArray();
        try {
            final String solrServer = solrSearchHelper.getSolrOSGIConfigService().getSolrServerEndpoint();
            final SolrClient client =SolrUtils.getSolrClient(solrServer);
            // *:* - returns all the result
            String query = StringUtils.isNotBlank(searchText) ? searchText : "*:*";
            final SolrQuery solrQuery = new SolrQuery(query);
            final QueryResponse queryResponse = client.query(solrSearchHelper.getSolrCAConfig().coreName(),solrQuery);
            final SolrDocumentList documentList = queryResponse.getResults();
            LOG.info("\n Found {} documents with {}",documentList.getNumFound(),searchText);
            for(SolrDocument doc: documentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(SolrConstants.PAGE_ID,doc.getFirstValue(SolrConstants.PAGE_ID));
                jsonObject.put(SolrConstants.PAGE_NAME,doc.getFirstValue(SolrConstants.PAGE_NAME));
                jsonObject.put(SolrConstants.PAGE_TITLE,doc.getFirstValue(SolrConstants.PAGE_TITLE));
                jsonObject.put(SolrConstants.PAGE_DESCRIPTION,doc.getFirstValue(SolrConstants.PAGE_DESCRIPTION));
                searchResult.put(jsonObject);
                LOG.info("\n -------Document Added To Search Result------ {}",searchResult);

            }
        }catch (Exception e) {
            LOG.error("\n Search Failed {}", e.getMessage());
        }
        return searchResult;
    }

    @Override
    public void indexPage(SolrSearchHelper solrSearchHelper, PageDetail pageDetail) {

        try {
            LOG.info("\n --Indexing Single Page Start--");
            final String solrServer = solrSearchHelper.getSolrOSGIConfigService().getSolrServerEndpoint();
            final SolrClient client =SolrUtils.getSolrClient(solrServer);
            final SolrInputDocument doc = new SolrInputDocument();
            doc.addField(SolrConstants.PAGE_ID, SolrUtils.getPageName(pageDetail.getPath()));
            doc.addField(SolrConstants.PAGE_NAME, pageDetail.getName());
            doc.addField(SolrConstants.PAGE_TITLE, pageDetail.getTitle());
            doc.addField(SolrConstants.PAGE_DESCRIPTION, pageDetail.getDescription());
            final UpdateResponse updateResponse = client.add(solrSearchHelper.getSolrCAConfig().coreName(),doc);
            client.commit(solrSearchHelper.getSolrCAConfig().coreName());
            LOG.info("\n --Indexing Single Page End-- {}",updateResponse.getQTime());
        }
        catch (SolrServerException | IOException e) {
            LOG.error("Error while indexing single page - {}",e.getMessage());
        }
    }

    @Override
    public void deletePage(SolrSearchHelper solrSearchHelper, PageDetail pageDetail) {
        try {
            LOG.info("\n --Deleting Single Page Start--");
            final String solrServer = solrSearchHelper.getSolrOSGIConfigService().getSolrServerEndpoint();
            final SolrClient client =SolrUtils.getSolrClient(solrServer);
            String pageId = SolrUtils.getPageName(pageDetail.getPath());
            LOG.info("\n --Deleting Page With ID-- {}",pageId);
            client.deleteById(solrSearchHelper.getSolrCAConfig().coreName(),pageId);
            final UpdateResponse updateResponse = client.commit(solrSearchHelper.getSolrCAConfig().coreName());
            LOG.info("\n --Deleting Single Page End-- {}",updateResponse.getQTime());
        }
        catch (SolrServerException | IOException e) {
            LOG.error("Error while Indexing/Deleting single page - {}",e.getMessage());
        }
    }
}
