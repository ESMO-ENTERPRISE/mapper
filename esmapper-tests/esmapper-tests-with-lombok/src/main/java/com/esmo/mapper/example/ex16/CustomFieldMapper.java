package com.esmo.mapper.example.ex16;

import com.esmo.mapper.common.annotations.FieldMapping;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;
import com.esmo.mapper.example.ex15.Data2;
import com.esmo.mapper.example.ex15.IData;

@Mapper
@MapperConfig(
	fieldMapping = {
		@FieldMapping(s = "a", d = "b", ignoreDirectionD2S = false, ignoreDirectionS2D = false),
		@FieldMapping(s = "c", d = "d", ignoreDirectionD2S = true, ignoreDirectionS2D = false),
		@FieldMapping(s = "e", d = "f", ignoreDirectionD2S = false, ignoreDirectionS2D = true),
		@FieldMapping(s = "g", d = "h", ignoreDirectionD2S = true, ignoreDirectionS2D = true),

		@FieldMapping(
			sTypes = Data2.class, s = "d1.a", ignoreDirectionS2D = false,
			dTypes = Data2b.class, d = "d1_a", ignoreDirectionD2S = false
		),

		@FieldMapping(
			sTypes = IData.class, s = "d1.b", ignoreDirectionS2D = false,
			dTypes = Data2b.class, d = "d1_b", ignoreDirectionD2S = true),

		@FieldMapping(
			sTypes = Data2.class, s = "d1.c", ignoreDirectionS2D = true,
			dTypes = Data2b.class, d = "d1_c", ignoreDirectionD2S = false)
	}
)
public interface CustomFieldMapper {

	public Data2 transf1(Data2b i);
	public Data2b transf2(Data2 i);
}
