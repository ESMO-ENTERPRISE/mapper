package com.esmo.mapper.example.ex10;

import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;
import com.esmo.mapper.example.ex1.UserInput;
import com.esmo.mapper.example.ex4.AddressInput;
import com.esmo.mapper.example.ex4.UserWithFlatAddressOutput;

@Mapper
@MapperConfig(fieldMapping = {
    @FieldMapping(s = "zipCode", d = "zip")
})
public interface AggregationMapper {
    UserWithFlatAddressOutput toOutput(UserInput userInput, AddressInput addressInput);
}
