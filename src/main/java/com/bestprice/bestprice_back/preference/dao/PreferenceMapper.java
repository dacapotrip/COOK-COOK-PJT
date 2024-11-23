package com.bestprice.bestprice_back.preference.dao;

import com.bestprice.bestprice_back.preference.dto.PreferenceDTO;
import com.bestprice.bestprice_back.preference.domain.Preference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PreferenceMapper {

    /**
     * 선호도 추가
     * 
     * @param preferenceDTO 추가할 선호도 데이터
     */
    void insertPreference(PreferenceDTO preferenceDTO);

    /**
     * 선호도 업데이트
     * 
     * @param preferenceDTO 업데이트할 선호도 데이터
     */
    void updatePreference(PreferenceDTO preferenceDTO);

    /**
     * 사용자 ID로 선호도 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 선호도 데이터
     */
    Preference findPreferenceByUserId(@Param("userId") String userId);

    /**
     * 사용자 ID로 선호도 삭제
     * 
     * @param userId 사용자 ID
     */
    void deletePreference(@Param("userId") String userId);
}
