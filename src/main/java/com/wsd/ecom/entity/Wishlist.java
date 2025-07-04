package com.wsd.ecom.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Table(
        indexes = {
                @Index(name = "idx_wishlist_customer", columnList = "customerId"),
                @Index(name = "idx_wishlist_product", columnList = "productId")
        },
        uniqueConstraints = @UniqueConstraint(
                name = "uc_customer_product",
                columnNames = {"customerId", "productId"}
        )
)

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wishlist extends BaseEntity {

    @Column(nullable = false)
    Long customerId;

    @Column(nullable = false)
    Long productId;

}
