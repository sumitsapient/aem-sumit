package com.aem.sumit.core.services.querybuilder;

import org.json.JSONObject;

public interface SearchService {

    public JSONObject searchResult(String searchText , int startResult , int resultPerPage);
}
