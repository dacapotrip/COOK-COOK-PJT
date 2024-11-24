package com.bestprice.bestprice_back.inquiry.ctrl;

import com.bestprice.bestprice_back.inquiry.dto.InquiryDTO;
import com.bestprice.bestprice_back.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    // 문의 목록 가져오기 (페이지네이션 지원)
    @GetMapping
    public Map<String, Object> getInquiries(@RequestParam int page) {
        return inquiryService.getInquiries(page);
    }

    // 특정 문의 상세 정보 가져오기
    @GetMapping("/{id}")
    public InquiryDTO getInquiryById(@PathVariable int id) {
        return inquiryService.getInquiryById(id);
    }

    // 새로운 문의 등록
    @PostMapping
    public void createInquiry(@RequestBody InquiryDTO inquiryDTO) {
        inquiryService.createInquiry(inquiryDTO);
    }

    // 특정 문의 삭제
    @DeleteMapping("/{id}")
    public void deleteInquiry(@PathVariable int id) {
        inquiryService.deleteInquiry(id);
    }

    // 문의 답변 등록
    @PostMapping("/{id}/answer")
    public void submitAnswer(@PathVariable int id, @RequestBody Map<String, String> request) {
        String answer = request.get("answer");
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("답변 내용이 비어 있습니다.");
        }
        inquiryService.submitAnswer(id, answer);
    }
}
