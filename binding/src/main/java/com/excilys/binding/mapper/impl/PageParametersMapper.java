package com.excilys.binding.mapper.impl;

import com.excilys.binding.mapper.IPageParametersMapper;
import com.excilys.core.dto.PageParametersDTO;
import com.excilys.core.model.PageParameters;
import com.excilys.core.model.PageParameters.Direction;
import com.excilys.core.model.PageParameters.Order;
import org.springframework.stereotype.Component;

/**
 * Implements different mapping methods to create or convert a PageParameters
 * objects.
 *
 * @author simon
 */
@Component
public class PageParametersMapper implements IPageParametersMapper {

    @Override
    public PageParameters fromDTO(PageParametersDTO dto) {

        PageParameters.Builder builder = new PageParameters.Builder();

        if (dto.getSearch() != null && !dto.getSearch().isEmpty()) {
            builder.search(dto.getSearch());
        }

        if (dto.getSearchType() != null && !dto.getSearchType().isEmpty()) {
            builder.searchType(dto.getSearchType());
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

    @Override
    public PageParametersDTO toDTO(PageParameters param) {
        return new PageParametersDTO(param);
    }
}
