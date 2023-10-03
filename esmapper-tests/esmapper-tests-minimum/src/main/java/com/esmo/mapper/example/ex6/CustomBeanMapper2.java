package com.esmo.mapper.example.ex6;

import com.esmo.mapper.common.annotations.Mapper;

@Mapper
public abstract class CustomBeanMapper2 {
    protected CustomBeanMapperImpl otherMapper;

    abstract CustomBeanOutput toOutput(CustomBeanInput input);
}
