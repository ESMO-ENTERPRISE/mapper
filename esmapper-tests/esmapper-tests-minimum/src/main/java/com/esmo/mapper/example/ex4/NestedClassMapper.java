package com.esmo.mapper.example.ex4;

import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

@Mapper
public interface NestedClassMapper {
    @MapperConfig(
            fieldMapping = {
                    @FieldMapping(s = "zipCode", d="zip")
            }
    )
    UserWithAddressOutput toOutput(UserWithAddressInput userWithAddressInput);
    @MapperConfig(fieldMapping = {
            @FieldMapping(s = "address.street", d = "street"),
            @FieldMapping(s = "address.number", d = "number"),
            @FieldMapping(s = "address.city", d = "city"),
            @FieldMapping(s = "address.zipCode", d = "zip")
    })
    UserWithFlatAddressOutput toOutputFlatten(UserWithAddressInput userWithAddressInput);
}
