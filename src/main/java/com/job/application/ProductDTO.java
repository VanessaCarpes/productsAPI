package com.job.application;

import java.math.BigDecimal;

public interface ProductDTO {

    Integer getId();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    BigDecimal getDiscountAmount();
    Boolean getStatus();

}
