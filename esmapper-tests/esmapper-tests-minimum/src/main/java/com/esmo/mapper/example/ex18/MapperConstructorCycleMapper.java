package com.esmo.mapper.example.ex18;

import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.utils.MapperUtil;

@Mapper
abstract public class MapperConstructorCycleMapper  {
	public MapperConstructorCycleMapper mapper = MapperUtil.getMapper(MapperConstructorCycleMapper.class, this);

	public abstract int toInt(String i);
	public abstract String toInt(int i);
}
