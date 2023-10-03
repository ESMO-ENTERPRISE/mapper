package com.esmo.mapper.example.ex2;

import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

@Mapper
@MapperConfig(fieldMapping = {
        @FieldMapping(s="name", d="firstName"),
        @FieldMapping(s="surname", d="lastName"),
        @FieldMapping(s={"id", "id2"}, d={"id2", "id"})
})
public interface CustomFieldMapper {
    UserOutput toOutput(UserInput userInput);
}
