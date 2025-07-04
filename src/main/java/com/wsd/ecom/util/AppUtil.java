package com.wsd.ecom.util;

import com.wsd.ecom.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Slf4j
public class AppUtil {


    public static Pageable getSortingWithCreationTime(Pageable pageable, Sort.Direction direction) {

        if (direction == null) {
            direction = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(direction, BaseEntity.Fields.createdAt);

        if (!pageable.getSort().isEmpty()
                && pageable.getSort().isSorted()) {
            sort = pageable.getSort().and(sort);
        }

        log.info("Updated pageable with direction: {} and property: {}", direction.name(), BaseEntity.Fields.createdAt);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);
    }

}
