package com.esmo.mapper.example.ex10;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;
import com.esmo.mapper.example.ex1.UserInput;
import com.esmo.mapper.example.ex4.AddressInput;
import com.esmo.mapper.example.ex4.UserWithFlatAddressOutput;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AggregationMapperTest {
    @Test
    public void testNestedClassMapper(){
        final String name = "TesterName";
        final String surname = "TesterSurname";

        final String street = "TestStreet";
        final String city = "TestCity";
        final String zip = "TestZip";

        UserInput userInput = new UserInput();
        userInput.setName(name);
        userInput.setSurname(surname);

        AddressInput addressInput = new AddressInput();
        addressInput.setStreet(street);
        addressInput.setCity(city);
        addressInput.setZipCode(zip);

        AggregationMapper mapper = MapperUtil.getMapper(AggregationMapper.class);

        UserWithFlatAddressOutput output = mapper.toOutput(userInput, addressInput);

        assertNotNull(output);

        assertEquals(name, output.getName());
        assertEquals(surname, output.getSurname());
        assertEquals(street, output.getStreet());
        assertEquals(city, output.getCity());
        assertEquals(zip, output.getZip());
    }
}
