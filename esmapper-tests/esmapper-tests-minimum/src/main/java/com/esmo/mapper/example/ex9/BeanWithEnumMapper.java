package com.esmo.mapper.example.ex9;

import com.esmo.mapper.common.annotations.Mapper;

@Mapper
public interface BeanWithEnumMapper {
    UserWithRoleOutput toOutput(UserWithRoleInput input);
}
