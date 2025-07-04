package com.wsd.ecom.repository;

import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.entity.Sales;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    BigDecimal sumSalesAmountBetween(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);


    @Query("""
                SELECT DATE(s.createdAt) AS saleDay
                FROM Sales s
                JOIN Product p ON s.productId = p.id
                WHERE s.createdAt BETWEEN :start AND :end
                GROUP BY saleDay
                ORDER BY SUM(p.price * s.quantity) DESC
                LIMIT 1
            """)
    LocalDate findDayWithMaxSales(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end);

    @Query("""
                SELECT new com.wsd.ecom.dto.TopSellingProductByAmountDto(
                    p.id, p.name, SUM(p.price * s.quantity)
                )
                FROM Sales s
                INNER JOIN Product p ON s.productId = p.id
                GROUP BY p.id, p.name
                ORDER BY SUM(p.price * s.quantity) DESC
            """)
    List<TopSellingProductByAmountDto> findTopSellingProductsByAmount(Pageable pageable);

    @Query("""
                SELECT new com.wsd.ecom.dto.TopSellingProductByQuantityDto(
                    p.id, p.name, SUM(s.quantity)
                )
                FROM Sales s
                INNER JOIN Product p ON s.productId = p.id
                WHERE s.createdAt BETWEEN :start AND :end
                GROUP BY p.id, p.name
                ORDER BY SUM(s.quantity) DESC
            """)
    List<TopSellingProductByQuantityDto> findTopSellingProductsByQuantity(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable);
}
