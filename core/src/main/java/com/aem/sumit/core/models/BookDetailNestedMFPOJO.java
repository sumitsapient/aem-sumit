package com.aem.sumit.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.inject.Inject;
import java.util.List;

@Getter
@Setter
@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BookDetailNestedMFPOJO {

    @Inject
    String bookname;

    @Inject
    String booksubject;

    @ChildResource(name="bookeditons")
    List<BookEdition> bookeditons;

}
