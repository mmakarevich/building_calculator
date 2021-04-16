package com.company.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CalculatedValuesModel {
    @JsonIgnore
    private Long parentId;
    private Long id;
    private String name;
    private String prop;
    private BigDecimal value;
    private List<CalculatedValuesModel> items;
}
