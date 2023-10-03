package com.esmo.mapper.example.ex20;


import com.esmo.mapper.common.annotations.DisableMapperFeature;
import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.MapperConfig;
import com.esmo.mapper.common.annotations.enums.MapperFeature;

@Mapper
@MapperConfig(withCustom = {OhterMapper1.class, OhterMapper4.class} /*Method in mapper is annotation IgnoredByMapper*/)
@DisableMapperFeature(MapperFeature.ALL)
public abstract class UseOtherMapper {
    protected OhterMapper2 otherMapper2;
    public void setOtherMapper2(OhterMapper2 otherMapper2) {
        this.otherMapper2 = otherMapper2;
    }

    // private mapper is ignored
    private OhterMapper3 otherMapper3;
    public void setOtherMapper3(OhterMapper3 otherMapper3) {
        this.otherMapper3 = otherMapper3;
    }

    abstract public String intToStr(Integer i0);
    abstract public RefType1<Integer> c1(RefType1<Integer> i0);

    abstract public String longToStr(Long i0);
    abstract public RefType1<Long> c2(RefType1<Long> i0);

    abstract public String byteToString(byte i0);
    abstract public RefType1<Byte> c3(RefType1<Byte> i0);
}
