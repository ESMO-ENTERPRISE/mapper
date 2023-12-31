package com.esmo.mapper.example.ex5;

import com.esmo.mapper.example.ex4.AddressInput;

import java.util.List;

public class UserWithAddressInput {
    private String name;
    private String surname;

    private List<AddressInput> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<AddressInput> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressInput> addresses) {
        this.addresses = addresses;
    }
}
