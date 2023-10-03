package com.esmo.mapper.example.ex6;

import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

@Mapper
@MapperConfig(withCustom = {CustomBeanMapperImpl.class})
public interface CustomBeanMapper1 {
    CustomBeanOutput toOutput(CustomBeanInput input);
}
