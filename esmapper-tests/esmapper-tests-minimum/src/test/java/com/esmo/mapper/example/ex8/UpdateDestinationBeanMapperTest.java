package com.esmo.mapper.example.ex8;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;
import com.esmo.mapper.example.ex1.UserInput;
import com.esmo.mapper.example.ex1.UserOutput;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UpdateDestinationBeanMapperTest {
    @Test
    public void testNestedClassMapper(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        UserInput input = new UserInput();
        input.setName(name);
        input.setSurname(surname);

        UpdateDestinationBeanMapper mapper = MapperUtil.getMapper(UpdateDestinationBeanMapper.class);

        UserOutput output = new UserOutput();

        UserOutput returnedOutput = mapper.toOutput(input, output);

        assertNotNull(output);

        assertEquals(output, returnedOutput);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
    }
}
