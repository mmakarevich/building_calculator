package com.company.demo.domain;

import java.math.BigDecimal;

public interface AttributeProjection {
    Long getElementId();
    Long getParentId();
    String getTitle();
    BigDecimal getAttrValue();
    Integer getLevel();
}
