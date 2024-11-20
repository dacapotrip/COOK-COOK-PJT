package com.bestprice.bestprice_back.user.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.NicknameChangeDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;

@Mapper
public interface UserMapper {
    
    void joinUser(UserRegisterDTO params); // 사용자 등록

    Optional<User> findByUserId(String userId); // 사용자 아이디로 조회

    Optional<User> findByToken(String token); // 토큰으로 사용자 조회

    void updateStateByToken(String token); // 토큰으로 사용자 상태 업데이트

    Optional<User> findByEmail(String email); // 이메일로 사용자 조회

    void updateResetToken(@Param("userId") String userId, @Param("token") String token); // 비밀번호 초기화 토큰 업데이트

    void updatePassword(@Param("userId") String userId, @Param("password") String password); // 비밀번호 업데이트

    void clearResetToken(String userId); // 비밀번호 변경 후 토큰 초기화
    
    void deleteUser(String userId); // 사용자 삭제

    Optional<User> findByResetToken(String token); // 토큰으로 사용자 조회

    void updateNickname(NicknameChangeDTO nicknameChangeDTO); // 닉네임 업데이트

}
