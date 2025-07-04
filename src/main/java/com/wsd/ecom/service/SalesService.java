package com.wsd.ecom.service;

import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.repository.SalesRepository;
import com.wsd.ecom.repository.WishlistRepository;
import com.wsd.ecom.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@Service
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;


    @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public SalesTodayDto getTotalSalesAmountOfToday() {
        LocalDate today = LocalDate.now();
        BigDecimal totalSalesAmount = salesRepository.sumSalesAmountBetween(today.atStartOfDay(),
                today.plusDays(1).atStartOfDay());
        return SalesTodayDto.builder()
                .totalSales(totalSalesAmount)
                .build();
    }
}
