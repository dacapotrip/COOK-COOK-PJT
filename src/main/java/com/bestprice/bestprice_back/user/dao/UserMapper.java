package com.bestprice.bestprice_back.user.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;

@Mapper
public interface UserMapper {
    
    public void joinUser(UserRegisterDTO params);

    Optional<User> findByUserId(String userId);

    Optional<User> findByToken(String token);

    public void updateStateByToken(String token);
}
