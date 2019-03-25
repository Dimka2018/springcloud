package com.epam.springcloud.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class Mapper extends ModelMapper {

    public <T, D> List<D> mapAll(Collection<T> objects,
            Class<D> parseToClass) {
        return objects.stream().map(entity -> map(entity, parseToClass))
                .collect(Collectors.toList());
    }

}
