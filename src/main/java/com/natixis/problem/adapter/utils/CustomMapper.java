package com.natixis.problem.adapter.utils;

import org.modelmapper.ModelMapper;

public class CustomMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <X, Y> Y fromXtoY(X source, Class<Y> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}
