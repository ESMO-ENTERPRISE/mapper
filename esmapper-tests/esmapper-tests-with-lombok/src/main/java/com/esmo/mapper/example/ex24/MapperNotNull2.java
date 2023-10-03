package com.esmo.mapper.example.ex24;

import com.esmo.mapper.common.annotations.DisableMapperFeature;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;
import com.esmo.mapper.common.annotations.Return;
import com.esmo.mapper.common.annotations.enums.ApplyFieldStrategy;
import com.esmo.mapper.common.annotations.enums.MapperFeature;

@Mapper
@DisableMapperFeature(MapperFeature.ALL)
abstract public class MapperNotNull2 {
    @MapperConfig(applyWhen = ApplyFieldStrategy.NEWVALUE_IS_NOT_NULL)
    abstract public ObjOut toObjIfNotNull(ObjIn in, @Return ObjOut out);
}
