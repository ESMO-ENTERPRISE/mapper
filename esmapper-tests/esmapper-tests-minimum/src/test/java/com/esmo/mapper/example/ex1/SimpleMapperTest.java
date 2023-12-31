package com.esmo.mapper.example.ex1;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleMapperTest {
    @Test
    public void testSimpleMapper(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        UserInput input = new UserInput();
        input.setName(name);
        input.setSurname(surname);

        SimpleMapper mapper = MapperUtil.getMapper(SimpleMapper.class);

        UserOutput output = mapper.toOutput(input);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
    }

    @Test
    public void testMappingBackwards(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        UserInput input = new UserInput();
        input.setName(name);
        input.setSurname(surname);

        SimpleMapper mapper = MapperUtil.getMapper(SimpleMapper.class);

        UserOutput output = mapper.toOutput(input);
        UserInput backToInput = mapper.toInput(output);

        assertNotNull(output);
        assertNotNull(backToInput);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());

        assertEquals(name, backToInput.getName());
        assertEquals(surname, backToInput.getSurname());

        assertNotEquals(input, backToInput);
    }
}
