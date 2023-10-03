package com.esmo.mapper.example.ex7;

import com.esmo.mapper.common.annotations.Mapper;

@Mapper
public interface CyclicBeanMapper {
    TreeNodeOutput toOutput(TreeNodeInput treeNodeInput);
}
