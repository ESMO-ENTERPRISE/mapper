package com.esmo.mapper.example.ex8;

import com.esmo.mapper.common.annotations.Mapper;
import com.esmo.mapper.common.annotations.Return;
import com.esmo.mapper.example.ex1.UserInput;
import com.esmo.mapper.example.ex1.UserOutput;

@Mapper
public interface UpdateDestinationBeanMapper {
    UserOutput toOutput(UserInput input, @Return UserOutput output);
}
