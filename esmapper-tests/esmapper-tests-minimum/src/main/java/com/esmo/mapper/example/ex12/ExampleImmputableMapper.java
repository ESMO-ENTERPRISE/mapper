package com.esmo.mapper.example.ex12;

import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;

@Mapper
@MapperConfig(immutable = {Obj1.class})
public interface ExampleImmputableMapper {
	public Obj to2(Obj obj);
}
