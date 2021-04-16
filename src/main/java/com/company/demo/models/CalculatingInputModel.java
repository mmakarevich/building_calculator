package com.company.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CalculatingInputModel {
    @JsonProperty("tree_id")
    private Long treeId;
    private String prop;
}
