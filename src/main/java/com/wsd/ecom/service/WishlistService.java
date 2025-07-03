package com.wsd.ecom.service;

import com.wsd.ecom.entity.Wishlist;
import com.wsd.ecom.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@Service
@Slf4j
public class WishlistService {

    private final WishlistRepository wishlistRepository;


    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Page<Wishlist> getWishlistForCustomer(Long customerId, Pageable pageable) {
        //ToDo implement after unit test
        return new PageImpl<>(new ArrayList<>());
    }
}
