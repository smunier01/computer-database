package com.excilys.cdb.mapper;

import static com.excilys.cdb.model.PageParameters.Order;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.PageParametersDTO;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.model.PageParameters.Direction;

/**
 * Implements different mapping methods to create or convert a PageParameters
 * objects.
 *
 * @author simon
 */
@Component
public class PageParametersMapper {

    public PageParameters fromDTO(PageParametersDTO dto) {

        PageParameters.Builder builder = new PageParameters.Builder();

        if (dto.getSearch() != null && !dto.getSearch().isEmpty()) {
            builder.search(dto.getSearch());
        }

        builder.size(dto.getSize());

        builder.pageNumber(dto.getPageNumber());

        if (dto.getDirection() != null && !dto.getDirection().isEmpty()) {
            builder.direction(Direction.valueOf(dto.getDirection().toUpperCase()));
        }

        if (dto.getOrder() != null && !dto.getOrder().isEmpty()) {
            builder.order(Order.valueOf(dto.getOrder().toUpperCase()));
        }

        return builder.build();
    }
}
