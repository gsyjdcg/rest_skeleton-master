package com.codechallenge.adapter.persistence.repository;

import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.adapter.persistence.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query(value = "SELECT p "+
            "FROM Price p "+
            "INNER JOIN p.product pr "+
            "INNER JOIN p.brand b "+
            "WHERE b.id = :brandId "+
            "AND pr.id = :productId "+
            "AND p.startDate <= :appDate "+
            "AND p.endDate >= :appDate " +
            "ORDER BY p.priority DESC"
    )
    Optional<List<Price>> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc(@Param("appDate")LocalDateTime appDate,
                                                                                                                            @Param("productId") String productId,
                                                                                                                            @Param("brandId") Long brandId);

    Optional<List<Price>> findByProductOrderByStartDateAsc(Product product);
}
