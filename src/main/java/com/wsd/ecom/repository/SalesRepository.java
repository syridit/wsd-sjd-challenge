package com.wsd.ecom.repository;

import com.wsd.ecom.dto.TopSellingProductInfo;
import com.wsd.ecom.entity.Sales;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Repository
public interface SalesRepository extends BaseRepository<Sales> {

    @Query("""
                SELECT SUM(p.price * s.quantity)
                FROM Sales s
                INNER JOIN Product p ON s.productId = p.id
                WHERE s.createdAt BETWEEN :start AND :end
            """)
    BigDecimal sumSalesAmountBetween(LocalDateTime start, LocalDateTime end);


    @Query("""
                SELECT DATE(s.createdAt) AS saleDay
                FROM Sales s
                JOIN Product p ON s.productId = p.id
                WHERE s.createdAt BETWEEN :start AND :end
                GROUP BY saleDay
                ORDER BY SUM(p.price * s.quantity) DESC
                LIMIT 1
            """)
    LocalDate findDayWithMaxSales(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT new com.wsd.ecom.dto.TopSellingProductInfo(
                    p.id, p.name, SUM(p.price * s.quantity)
                )
                FROM Sales s
                INNER JOIN Product p ON s.productId = p.id
                GROUP BY p.id, p.name
                ORDER BY SUM(p.price * s.quantity) DESC
            """)
    List<TopSellingProductInfo> findTopSellingProductsByAmount(Pageable pageable);

}
