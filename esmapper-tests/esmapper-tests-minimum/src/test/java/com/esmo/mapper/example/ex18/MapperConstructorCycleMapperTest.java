package com.esmo.mapper.example.ex18;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapperConstructorCycleMapperTest {
	@Test
	public void testMapper() {
		MapperConstructorCycleMapper mapper = MapperUtil.getMapper(MapperConstructorCycleMapper.class);
		Assertions.assertNotNull(mapper);
		Assertions.assertNotNull(mapper.mapper);
		Assertions.assertEquals(mapper, mapper.mapper);
	}
}
