package com.wsd.ecom.repository;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Repository
public interface WishlistRepository extends BaseRepository<Wishlist> {

    @Query("""
                        select new com.wsd.ecom.dto.WishlistDto(w.customerId, p.name, p.price, p.id, w.createdAt) from Wishlist w
                        inner join Product p on w.productId = p.id
                        where w.customerId = :customerId
            """)
    Page<WishlistDto> findWishlistByCustomerId(Long customerId, Pageable pageable);
}
