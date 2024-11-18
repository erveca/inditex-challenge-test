package com.inditex.prices.model;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@ToString
@Entity
public class Price {
    /**
     * The price tariff ID, which would be equivalent to the PRICE_LIST column from test description.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * The brand associated to the price tariff.
     */
    @ManyToOne
    private Brand brand;

    /**
     * The start date for which the price tariff applies.
     */
    private Instant startDate;

    /**
     * The end date for which the price tariff applies.
     */
    private Instant endDate;

    /**
     * The product associated to the price tariff.
     */
    @ManyToOne
    private Product product;

    /**
     * The price tariff priority used for resolving conflicts between different price tariffs.
     * The higher the value, the more priority has the price tariffs over others applicable price tariffs.
     */
    private int priority;

    /**
     * The final price (PVP).
     */
    private Double price;

    /**
     * The currency ISO code.
     */
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
