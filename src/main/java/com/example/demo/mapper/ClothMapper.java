package com.example.demo.mapper;

import com.example.demo.model.Cloth;

public interface ClothMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cloth record);

    int insertSelective(Cloth record);

    Cloth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cloth record);

    int updateByPrimaryKey(Cloth record);
}