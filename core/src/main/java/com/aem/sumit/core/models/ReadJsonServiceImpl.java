package com.aem.sumit.core.models;

import com.aem.sumit.core.constants.AppConstants;
import com.aem.sumit.core.constants.Network;
import com.aem.sumit.core.models.ReadJsonService;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;

@Component(immediate = true,service = ReadJsonService.class)
public class ReadJsonServiceImpl implements ReadJsonService{
    @Override
    public String getData() throws IOException {
        String response = Network.readJson(AppConstants.URL);
        return response;
    }
}
