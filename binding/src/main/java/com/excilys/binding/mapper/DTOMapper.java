package com.excilys.binding.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface DTOMapper<T, V> {

    /**
     * convert an object to a DTO.
     *
     * @param object instance of the object to convert
     * @return DTO created
     */
    T toDTO(V object);

    /**
     * convert a DTO into an object.
     *
     * @param dto instance of the DTO to convert
     * @return object created
     */
    V fromDTO (T dto);

    /**
     * convert a list of object to a list of DTO.
     *
     * @param objects list of objects to convert
     * @return list of DTOs created
     */
    default List<T> toDTO(List<V> objects) {
        return objects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * convert a list of DTOs into a list of objects.
     *
     * @param dtos list of dto to convert
     * @return list of object created
     */
    default List<V> fromDTO(List<T> dtos) {
        return dtos.stream().map(this::fromDTO).collect(Collectors.toList());
    }
}
