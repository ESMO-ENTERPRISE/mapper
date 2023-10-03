package com.esmo.mapper.example.ex20;

import com.esmo.mapper.common.annotations.EsMapperVisibility;
import com.esmo.mapper.common.annotations.enums.MapperVisibility;

public class OhterMapper4 {
    @EsMapperVisibility(MapperVisibility.IGNORED)
    public String byteToString(byte i) {
        return i + ":" + this.getClass().getSimpleName();
    }

    @EsMapperVisibility(MapperVisibility.IGNORED)
    public RefType1<Byte> createRefType1a() {
        return new RefType1<>(this.getClass().getSimpleName());
    };
}
