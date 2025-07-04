package com.wsd.ecom.service;

import com.wsd.ecom.dto.MaxSaleDayDto;
import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.repository.SalesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Cacheable(value = "dailySale")
    public SalesTodayDto getTotalSalesAmountOfToday() {
        log.info("Getting total sales amount of today...");
        LocalDate today = LocalDate.now();
        BigDecimal totalSalesAmount = salesRepository.sumSalesAmountBetween(today.atStartOfDay(),
                today.plusDays(1).atStartOfDay());
        return SalesTodayDto.builder()
                .totalSales(totalSalesAmount)
                .build();
    }

    @Cacheable(value = "maxSaleDay", key = "{#from.toString(), #to.toString()}")
    public MaxSaleDayDto getMaxSalesDay(LocalDateTime from, LocalDateTime to) {
        log.info("Getting the day with max sales amount... within {} to {}", from.toString(), to.toString());
        LocalDate maxSalesDay = salesRepository.findDayWithMaxSales(from, to);
        return MaxSaleDayDto.builder()
                .from(from)
                .to(to)
                .maxSalesDay(maxSalesDay)
                .build();
    }

    @Cacheable(value = "topProductsAllTime")
    public List<TopSellingProductByAmountDto> getAllTimeTopFiveProductsBySalesAmount() {
        log.info("Getting top 5 selling products of all time based on sales amount.");
        return salesRepository.findTopSellingProductsByAmount(Pageable.ofSize(5));
    }

    @Cacheable(value = "topProductsAllTime")
    public List<TopSellingProductByQuantityDto> getLastMonthTopFiveProductsByQuantity() {
        log.info("Getting top 5 selling products of last month based on sales quantity.");
        LocalDateTime from = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime to = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        log.info("Querying from {} to {}", from.toString(), to.toString());
        return salesRepository.findTopSellingProductsByQuantity(from, to,
                Pageable.ofSize(5));
    }

}
