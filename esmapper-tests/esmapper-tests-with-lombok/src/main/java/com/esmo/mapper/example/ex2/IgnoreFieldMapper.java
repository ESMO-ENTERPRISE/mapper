package com.esmo.mapper.example.ex2;

import com.esmo.mapper.common.annotations.FieldIgnore;
import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

@Mapper
@MapperConfig(
    fieldMapping = {
        @FieldMapping(s="name", d="firstName"),
        @FieldMapping(s="surname", d="lastName")
    }
)
public interface IgnoreFieldMapper {
    @MapperConfig(
            fieldIgnore = {
				@FieldIgnore(value={"id","id2"}, types={Object.class}),
				@FieldIgnore({"id","id2"})
            }
    )
    UserOutput toOutput(UserInput userInput);

    UserOutput toOutputWithId(UserInput userInput);

    UserDetailOutput toOutputDetail(UserDetailInput detailInput);
}
