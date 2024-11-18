package com.inditex.prices.repository;

import com.inditex.prices.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT price " +
            "FROM Price price " +
            "WHERE price.product.id = :productId " +
            "AND price.brand.id = :brandId " +
            "AND :date BETWEEN price.startDate AND price.endDate")
    List<Price> findApplicablePricesForDateAndProductIdAndBrandId(
            @Param("date") Instant date,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );

    @Query(value = "SELECT * " +
            "FROM Price price " +
            "WHERE price.product_id = :productId " +
            "AND price.brand_id = :brandId " +
            "AND :date BETWEEN price.start_date AND price.end_date " +
            "ORDER BY price.priority DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Price> findApplicablePriceForDateAndProductIdAndBrandId(
            @Param("date") Instant date,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
}
