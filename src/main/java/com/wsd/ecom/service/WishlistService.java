package com.wsd.ecom.service;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.repository.WishlistRepository;
import com.wsd.ecom.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<WishlistDto> getWishlistForCustomer(Long customerId, Pageable pageable) {
        pageable = AppUtil.getSortingWithCreationTime(pageable, Sort.Direction.DESC);
        return wishlistRepository.findWishlistByCustomerId(customerId, pageable);
    }
}
