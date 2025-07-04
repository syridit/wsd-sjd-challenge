package com.wsd.ecom.repository;

import com.wsd.ecom.entity.Sales;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

}
