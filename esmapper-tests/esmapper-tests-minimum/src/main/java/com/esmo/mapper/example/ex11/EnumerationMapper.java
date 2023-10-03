package com.esmo.mapper.example.ex11;

import com.esmo.mapper.common.annotations.DisableMapperFeature;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.enums.MapperFeature;

@Mapper
@DisableMapperFeature({MapperFeature.ALL})
public interface EnumerationMapper {
	public Enum2 to2(Enum1 obj);
	public Enum1 to1(Enum2 obj);
}
