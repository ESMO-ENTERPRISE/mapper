package com.esmo.mapper.example.ex4;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NestedClassMapperTest {
    @Test
    public void testNestedClassMapper(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        final String street = "TestStreet";
        final String city = "TestCity";
        final String zip = "TestZip";

        UserWithAddressInput input = new UserWithAddressInput();
        input.setName(name);
        input.setSurname(surname);

        input.setAddress(new AddressInput());
        input.getAddress().setStreet(street);
        input.getAddress().setCity(city);
        input.getAddress().setZipCode(zip);

        NestedClassMapper mapper = MapperUtil.getMapper(NestedClassMapper.class);

        UserWithAddressOutput output = mapper.toOutput(input);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
        assertEquals(street, output.getAddress().getStreet());
        assertEquals(city, output.getAddress().getCity());
        assertEquals(zip, output.getAddress().getZip());
    }

    @Test
    public void flattenMapperTest(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        final String street = "TestStreet";
        final String city = "TestCity";
        final String zip = "TestZip";

        UserWithAddressInput input = new UserWithAddressInput();
        input.setName(name);
        input.setSurname(surname);

        input.setAddress(new AddressInput());
        input.getAddress().setStreet(street);
        input.getAddress().setCity(city);
        input.getAddress().setZipCode(zip);

        NestedClassMapper mapper = MapperUtil.getMapper(NestedClassMapper.class);

        UserWithFlatAddressOutput output = mapper.toOutputFlatten(input);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
        assertEquals(street, output.getStreet());
        assertEquals(city, output.getCity());
        assertEquals(zip, output.getZip());
    }
}
