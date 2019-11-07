package com.example.demo.service.impl;

import com.example.demo.mapper.ClothMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Cloth;
import com.example.demo.model.User;
import com.example.demo.service.ClothService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//import com.github.pagehelper.PageHelper;

@Service(value = "clothService")
public class ClothServiceImpl implements ClothService {

    @Autowired
    private ClothMapper clothMapper;

    @Override
    public int addCloth(Cloth cloth) {
        return clothMapper.insertSelective(cloth);
    }

}
