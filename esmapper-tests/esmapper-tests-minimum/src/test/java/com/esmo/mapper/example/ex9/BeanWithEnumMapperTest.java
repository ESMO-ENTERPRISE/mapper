package com.esmo.mapper.example.ex9;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanWithEnumMapperTest {
    @Test
    public void testNestedClassMapper() {
        final String name = "TesterName";
        final String surname = "TesterSurname";
        final Role role = Role.EXTERNAL;

        UserWithRoleInput input = new UserWithRoleInput();
        input.setName(name);
        input.setSurname(surname);
        input.setRole(role);

        BeanWithEnumMapper mapper = MapperUtil.getMapper(BeanWithEnumMapper.class);

        UserWithRoleOutput output = mapper.toOutput(input);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
        assertEquals(role, output.getRole());
    }
}
