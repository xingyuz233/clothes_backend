package com.example.demo.mapper;

import com.example.demo.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectByUserName(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}