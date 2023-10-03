package com.esmo.mapper.example.ex24;

import com.esmo.mapper.common.annotations.DisableMapperFeature;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;
import com.esmo.mapper.common.annotations.enums.ApplyFieldStrategy;
import com.esmo.mapper.common.annotations.enums.MapperFeature;

@Mapper
@DisableMapperFeature(MapperFeature.ALL)
abstract public class MapperNotNull1 {
    abstract public ObjOut toObj(ObjIn in);

    abstract public ObjOut toObjIfNotNull1b(ObjIn in);
    abstract public ObjOut toObjIfNotNull1b(ObjIn in, ObjOut out);

    @MapperConfig(applyWhen = ApplyFieldStrategy.ALWAYS)
    abstract public ObjOut toObjIfNotNull1(ObjIn in);
    @MapperConfig(applyWhen = ApplyFieldStrategy.ALWAYS)
    abstract public ObjOut toObjIfNotNull1(ObjIn in, ObjOut out);
    @MapperConfig(applyWhen = ApplyFieldStrategy.OLDVALUE_IS_NULL)
    abstract public ObjOut toObjIfNotNull2(ObjIn in);
    @MapperConfig(applyWhen = ApplyFieldStrategy.OLDVALUE_IS_NULL)
    abstract public ObjOut toObjIfNotNull2(ObjIn in, ObjOut out);
    @MapperConfig(applyWhen = ApplyFieldStrategy.NEWVALUE_IS_NOT_NULL)
    abstract public ObjOut toObjIfNotNull3(ObjIn in);
    @MapperConfig(applyWhen = ApplyFieldStrategy.NEWVALUE_IS_NOT_NULL)
    abstract public ObjOut toObjIfNotNull3(ObjIn in, ObjOut out);

    @MapperConfig(applyWhen = ApplyFieldStrategy.OLDVALUE_IS_NULL)
    abstract public ObjOut toObjIfOldValueIsNull(ObjIn in);
}
