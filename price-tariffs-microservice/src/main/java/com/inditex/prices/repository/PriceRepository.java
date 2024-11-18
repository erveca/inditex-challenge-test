package com.inditex.prices.repository;

import com.inditex.prices.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
