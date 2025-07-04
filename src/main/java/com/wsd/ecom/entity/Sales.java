package com.wsd.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Table(
        indexes = {
        @Index(name = "idx_sales_product", columnList = "productId"),
        @Index(name = "idx_sales_customer", columnList = "customerId"),
        @Index(name = "idx_sales_created_at", columnList = "createdAt")
})

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sales extends BaseEntity {

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    Long customerId;

    @Column(nullable = false)
    Long quantity;

}
