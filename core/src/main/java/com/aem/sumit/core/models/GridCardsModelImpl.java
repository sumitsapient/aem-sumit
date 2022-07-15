package com.aem.sumit.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class, adapters = GridCardsModel.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GridCardsModelImpl implements GridCardsModel {

@Inject
private List<GridItem> gridItems;

    @Override
    public List<GridItem> getGridItems() {
        return gridItems;
    }

    public void setGridItems(List<GridItem> gridItems) {
        this.gridItems = gridItems;
    }
}
