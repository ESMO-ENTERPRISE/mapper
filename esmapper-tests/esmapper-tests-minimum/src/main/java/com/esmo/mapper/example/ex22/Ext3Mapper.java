package com.esmo.mapper.example.ex22;

import java.math.BigDecimal;

public class Ext3Mapper {
    public String toStr(BigDecimal value) {
        return value + ":" + this.getClass().getSimpleName();
    }
}
