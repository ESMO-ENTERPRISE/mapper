package com.esmo.mapper.example.ex5;

import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

import java.util.List;

@Mapper
@MapperConfig(
        fieldMapping = {
                @FieldMapping(s = "zipCode", d="zip")
        }
)
public interface NestedClassListMapper {
    UserWithAddressOutput toOutput(UserWithAddressInput userWithAddressInput);
    List<? extends String> testWidlCard(List<? extends Number> userWithAddressInput);
}
