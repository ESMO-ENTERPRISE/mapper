package com.esmo.mapper.example.ex15;

import com.esmo.mapper.common.annotations.*;
import com.esmo.mapper.common.annotations.enums.IgnoreType;
import com.esmo.mapper.common.annotations.enums.MapperFeature;
import com.esmo.mapper.example.ex15.subpackage.Data1;

@Mapper
@MapperConfig(
	fieldIgnore = {
		@FieldIgnore(value = "b", ignored = IgnoreType.DISABLED),
		@FieldIgnore("c"),													// applied on all fields
		@FieldIgnore(value = "d", ignored = IgnoreType.IGNORE_ALL),			// applied on all fields
		@FieldIgnore(value = "e", ignored = IgnoreType.IGNORE_READ),		// if it is applied on all types, without custom field
		@FieldIgnore(value = "f", ignored = IgnoreType.IGNORE_WRITE),		// applied on all fields, but if READ is not possible, WRITE cannot be generated
		@FieldIgnore(types=Data1.class, value = "g", ignored = IgnoreType.IGNORE_READ),
		@FieldIgnore(types=Data1.class, value = "h", ignored = IgnoreType.IGNORE_WRITE)
	}
)
@DisableMapperFeature({MapperFeature.ALL})
public interface FieldIgnoresDirectionWithTypesMapper {
	public Data2 clone1a2(Data1 in);
	public Data1 clone2a1(Data2 in);


	@MapperConfig(
		fieldMapping = {@FieldMapping(s="e",d="f")}
	)
	public Data2 clone1b2(Data1 in);

	@MapperConfig(
		fieldMapping = {@FieldMapping(s="e",d="f")}
	)
	public Data1 clone2b1(Data2 in);
}
