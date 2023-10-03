package com.esmo.mapper.example.ex3;

import com.esmo.mapper.common.annotations.Mapper;

@Mapper
public interface CopyMapper {
    BeanToCopy toOutput(BeanToCopy beanInput);
}
