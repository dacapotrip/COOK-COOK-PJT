package com.bestprice.bestprice_back.preference.ctrl;

import com.bestprice.bestprice_back.preference.domain.Preference;
import com.bestprice.bestprice_back.preference.dto.PreferenceDTO;
import com.bestprice.bestprice_back.preference.service.PreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    // 사용자 선호도 확인 API
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkPreference(@RequestParam("userId") String userId) {
        boolean exists = preferenceService.isPreferenceExist(userId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<Void> createPreference(@RequestBody PreferenceDTO preferenceDTO) {
        preferenceService.insertPreference(preferenceDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePreference(@RequestBody PreferenceDTO preferenceDTO) {
        preferenceService.updatePreference(preferenceDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Preference> getPreference(@PathVariable String userId) {
        Preference preference = preferenceService.findPreferenceByUserId(userId);
        return ResponseEntity.ok(preference);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePreference(@PathVariable String userId) {
        preferenceService.deletePreference(userId);
        return ResponseEntity.ok().build();
    }
}
