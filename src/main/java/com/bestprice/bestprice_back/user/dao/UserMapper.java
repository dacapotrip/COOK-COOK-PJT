package com.bestprice.bestprice_back.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;

@Mapper
public interface UserMapper {
    
    public void joinUser(UserRegisterDTO params);
}
