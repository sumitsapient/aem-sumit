package com.aem.sumit.core.models;

import com.aem.sumit.core.services.AemGeekServiceA;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = AemGeek.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = AemGeekImpl.RESOURCE_TYPE)
@Exporters({
        @Exporter(name = "jackson", extensions = "json", selector = "geek", options = {
                @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value = "true"),
                @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
        }),
        @Exporter(name = "geekxml", extensions = "xml")}
)

@XmlRootElement(name = "rootTag")
public class AemGeekImpl implements AemGeek {

    private static final Logger LOG = LoggerFactory.getLogger(AemGeekImpl.class);

    protected static final String RESOURCE_TYPE = "sumit/components/aemgeek";

    @ValueMapValue
    private String firstName;

    @ValueMapValue
    private String lastName;

    @ValueMapValue
    private boolean professor;

    @ChildResource(name = "bookdetailswithnestedmultifield")
    List<BookDetailNestedMFPOJO> bookdetailswithnestedmultifield;

    @ScriptVariable
    Page currentPage;

    @JsonProperty(value = "scholName")
    @XmlElement(name="scholName")
    public String getSchool() {
        return "SMC";
    }

    @PostConstruct
    private void init() {
        LOG.trace("Model Initiated Successfully");
        LOG.debug("Model Initiated Successfully");
        LOG.info("Model Initiated Successfully");
        LOG.warn("Model Initiated Successfully");
        LOG.error("Model Initiated Successfully");
        String name = professor?"sumit":"yadav";
    }

    @Override
    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    @Override
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    @Override
    @JsonProperty(value = "teacher")
    @XmlElement(name="teacher")
    public boolean getProfessor() {
        return professor;
    }

    @Override
    @XmlElement
    public String getPageTitle() {
        return currentPage != null ? currentPage.getPageTitle() : "hardCoded Title";
    }

    @Override
    @XmlElementWrapper(name="allbook")
    @XmlElement
    public List<BookDetailNestedMFPOJO> getBookDetails() {
        return bookdetailswithnestedmultifield;
    }


}
