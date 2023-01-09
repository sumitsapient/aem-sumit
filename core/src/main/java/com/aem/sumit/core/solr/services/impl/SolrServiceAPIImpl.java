package com.aem.sumit.core.solr.services.impl;

import com.aem.sumit.core.solr.constants.SolrConstants;
import com.aem.sumit.core.solr.helper.PageDetail;
import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.util.SolrUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
