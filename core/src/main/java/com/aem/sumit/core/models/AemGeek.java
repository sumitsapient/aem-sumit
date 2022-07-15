package com.aem.sumit.core.models;

import java.util.List;

public interface AemGeek {
    public String getFirstName();
    public String getLastName();
    boolean getProfessor();
    public String getPageTitle();
    List<BookDetailNestedMFPOJO> getBookDetails();
}
