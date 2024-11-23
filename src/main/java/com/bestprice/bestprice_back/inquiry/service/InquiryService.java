package com.bestprice.bestprice_back.inquiry.service;

import com.bestprice.bestprice_back.inquiry.dao.InquiryMapper;
import com.bestprice.bestprice_back.inquiry.dto.InquiryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InquiryService {

    @Autowired
    private InquiryMapper inquiryMapper;

    // 문의 목록 가져오기
    public Map<String, Object> getInquiries(int page) {
        int itemsPerPage = 10; // 페이지당 항목 수
        int offset = (page - 1) * itemsPerPage;

        List<InquiryDTO> inquiries = inquiryMapper.getInquiries(offset, itemsPerPage);
        int totalItems = inquiryMapper.getInquiryCount();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        Map<String, Object> result = new HashMap<>();
        result.put("content", inquiries);
        result.put("totalPages", totalPages);

        return result;
    }

    // 특정 문의 상세 정보 가져오기
    public InquiryDTO getInquiryById(int id) {
        return inquiryMapper.getInquiryById(id);
    }

    // 문의 등록
    public void createInquiry(InquiryDTO inquiryDTO) {
        inquiryMapper.createInquiry(inquiryDTO);
    }

    // 특정 문의 삭제
    public void deleteInquiry(int id) {
        inquiryMapper.deleteInquiry(id);
    }
}

