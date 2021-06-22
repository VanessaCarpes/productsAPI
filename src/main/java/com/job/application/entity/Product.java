package com.job.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    private Integer id;
    private String name;

    @Column(length = 3000)
    private String description;

    @JsonProperty("price")
    private BigDecimal amount;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    private Boolean status;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    @JsonIgnoreProperties({"products"})
    private List<Category> categories;

    public Product(Integer id, String name, BigDecimal amount, Boolean status) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.status = status;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPrice(String price) {
        setAmount(new BigDecimal(price));
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setDiscount_amount(String discountAmount) {
        setDiscountAmount(new BigDecimal(discountAmount));
    }

}
