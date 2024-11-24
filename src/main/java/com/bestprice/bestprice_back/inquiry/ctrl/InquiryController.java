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

    @GetMapping
    public Map<String, Object> getInquiries(
            @RequestParam int page,
            @RequestParam(required = false) String userId // userId를 String으로 변경
    ) {
        if (userId != null) {
            return inquiryService.getInquiriesByUserId(userId, page); // userId가 있는 경우
        }
        return inquiryService.getInquiries(page); // userId가 없는 경우
    }

    @GetMapping("/{id}")
    public InquiryDTO getInquiryById(@PathVariable int id) {
        return inquiryService.getInquiryById(id);
    }

    @PostMapping
    public void createInquiry(@RequestBody InquiryDTO inquiryDTO) {
        inquiryService.createInquiry(inquiryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInquiry(@PathVariable int id) {
        inquiryService.deleteInquiry(id);
    }

    @PostMapping("/{id}/answer")
    public void submitAnswer(@PathVariable int id, @RequestBody Map<String, String> request) {
        String answer = request.get("answer");
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("답변 내용이 비어 있습니다.");
        }
        inquiryService.submitAnswer(id, answer);
    }
}
