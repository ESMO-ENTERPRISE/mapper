package com.esmo.mapper.example.ex1;

import com.esmo.mapper.common.annotations.Mapper;

@Mapper
//@EnableCDI
public interface SimpleMapper {
    UserOutput toOutput(UserInput userInput);
    UserInput toInput(UserOutput userOutput);
}
