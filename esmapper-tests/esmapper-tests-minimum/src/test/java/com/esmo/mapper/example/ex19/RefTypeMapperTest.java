package com.esmo.mapper.example.ex19;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RefTypeMapperTest {

    @Test
    public void test() {
        SubType1aLong in = new SubType1aLong();
        in.setId(123456L);
        in.setName(UUID.randomUUID().toString());

        RefTypeMapper mapper = MapperUtil.getMapper(RefTypeMapper.class);
        mapper.used_test_interceptor1 = false;
        mapper.used_test_interceptor2 = false;
        mapper.used_test_interceptor3 = false;
        RefType1b<String, String> out = mapper.to(in);
        assertNotNull(out);
        assertEquals("123456",out.getId());
        assertEquals(in.getName(), out.getName());
        assertTrue(mapper.used_test_interceptor1);
        assertFalse(mapper.used_test_interceptor2);
        assertTrue(mapper.used_test_interceptor3);
    }
}
