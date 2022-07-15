package com.aem.sumit.core.models;


import com.day.cq.wcm.api.Page;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Location list model.
 */
@Model(adapters = {LocationListModel.class},
        adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Slf4j
public final class LocationListModelImpl implements LocationListModel {
    /** Feature City Page Template. */
    public static final String FEATURED_CITY_TEMPLATE = "/conf/sumit/settings/wcm/templates/article-page";

    /** City Page Template. */
    public static final String CITY_TEMPLATE = "/conf/eweb/settings/wcm/templates/city-page";

    public static final String BRANCH_PAGE_TEMPLATE = "/conf/sumit/settings/wcm/templates/article-page";

    @ScriptVariable
    private Page currentPage;

    @Getter
    @ValueMapValue
    @Default(values = "standard")
    private String layoutType;

    @Getter
    private List<Page> topCitiesLocations = new ArrayList<>();

    @Getter
    private Map<String, String> topLocationMap = new TreeMap<>();
    @Getter
    private List<BranchPageModel> airportLocations = new ArrayList<>();
    @Getter
    private List<BranchPageModel> nonAirportLocations = new ArrayList<>();
    @Getter
    private List<BranchPageModel> exoticsLocations = new ArrayList<>();



    /**
     * Init component.
     */
    @PostConstruct
    public void init() {
        Page parentPage = currentPage;
        if (StringUtils.equals(currentPage.getProperties().get("cq:template", ""), FEATURED_CITY_TEMPLATE)
        || StringUtils.equals(currentPage.getProperties().get("cq:template", ""), CITY_TEMPLATE)) {
            parentPage = currentPage.getParent();
        } else {
            this.topCitiesLocations = getLocationsByTemplate(parentPage, FEATURED_CITY_TEMPLATE);
            this.topCitiesLocations.addAll(getLocationsByTemplate(parentPage, CITY_TEMPLATE));

        }
        final List<BranchPageModel> locations =
                getLocationsByTemplate(parentPage, BRANCH_PAGE_TEMPLATE)
                        .stream()
                        .map(page -> page.getContentResource().adaptTo(BranchPageModel.class))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

        int size = locations.size();
//        locations.forEach(branchPage -> {
//            if (branchPage.isExotic()) {
//                this.exoticsLocations.add(branchPage);
//            } else if (StringUtils.equals(branchPage.getLocationType(), "AIRPORT")) {
//                this.airportLocations.add(branchPage);
//            } else {
//                this.nonAirportLocations.add(branchPage);
//            }
//        });
        //Map<String,String> linkedMap = new TreeMap<>();
        this.topCitiesLocations.forEach(m->{
            String priorityTitle= getPriorityTitle(m);
            topLocationMap.put(priorityTitle,m.getPath());
        });
       // this.topLocationMap.putAll(linkedMap);
        sortLocations();
    }

    private String getPriorityTitle(Page page) {
        if(page == null){
            return StringUtils.EMPTY;
        }
        final String[] priorityOrderForTitle = new String[]{
                page.getNavigationTitle(),
                page.getPageTitle(),
                page.getTitle(),
                page.getName()};

        return Arrays.stream(priorityOrderForTitle).filter( Objects::nonNull).findFirst().orElse(StringUtils.EMPTY);
    }

    private void sortLocations() {
        //this.topCitiesLocations.
        this.topCitiesLocations.sort((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getTitle(),o2.getTitle()));
    }

    private List<Page> getLocationsByTemplate(final Page inputPage, final String template) {
        return Optional.ofNullable(inputPage).map(page -> Lists.newArrayList(
                inputPage.listChildren(
                        page1 -> StringUtils.equals(page1.getProperties().get("cq:template", ""),
                                template),
                        true))).orElse(new ArrayList<>());
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(topCitiesLocations);
    }

}
