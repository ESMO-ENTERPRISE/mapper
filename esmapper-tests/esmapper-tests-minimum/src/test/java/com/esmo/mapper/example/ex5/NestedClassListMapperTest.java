package com.esmo.mapper.example.ex5;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;
import com.esmo.mapper.example.ex4.AddressInput;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NestedClassListMapperTest {
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
        input.setAddresses(new ArrayList<>());

        AddressInput a = new AddressInput();
        a.setStreet(street);
        a.setCity(city);
        a.setZipCode(zip);
        input.getAddresses().add(a);

        NestedClassListMapper mapper = MapperUtil.getMapper(NestedClassListMapper.class);

        UserWithAddressOutput output = mapper.toOutput(input);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
        assertEquals(street, output.getAddresses().get(0).getStreet());
        assertEquals(city, output.getAddresses().get(0).getCity());
        assertEquals(zip, output.getAddresses().get(0).getZip());
    }
}
