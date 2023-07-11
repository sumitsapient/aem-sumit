package com.aem.sumit.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIRequest {

    public String type;
    public String count;
    List<String> message;
}
