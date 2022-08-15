package com.aem.sumit.core.models;

import java.util.List;

public interface CaapContent {

    public String getCarouselTag();
    public String getTitle();
    public String getSummary();
    public String getHeading();
    public String getFileReference();
    List<CarouselItem> getCarouselActive();
    List<CarouselItem> getCarouselInactive();
}
