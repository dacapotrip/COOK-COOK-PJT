package com.bestprice.bestprice_back.preference.service;

import com.bestprice.bestprice_back.preference.dao.PreferenceMapper;
import com.bestprice.bestprice_back.preference.domain.Preference;
import com.bestprice.bestprice_back.preference.dto.PreferenceDTO;
import org.springframework.stereotype.Service;

@Service
public class PreferenceService {

    private final PreferenceMapper preferenceMapper;

    public PreferenceService(PreferenceMapper preferenceMapper) {
        this.preferenceMapper = preferenceMapper;
    }

    // 사용자 선호도 존재 여부 확인
    public boolean isPreferenceExist(String userId) {
        return preferenceMapper.findPreferenceByUserId(userId) != null;
    }

    public void insertPreference(PreferenceDTO preferenceDTO) {
        preferenceMapper.insertPreference(preferenceDTO);
    }

    public void updatePreference(PreferenceDTO preferenceDTO) {
        preferenceMapper.updatePreference(preferenceDTO);
    }

    public Preference findPreferenceByUserId(String userId) {
        return preferenceMapper.findPreferenceByUserId(userId);
    }

    public void deletePreference(String userId) {
        preferenceMapper.deletePreference(userId);
    }
}
