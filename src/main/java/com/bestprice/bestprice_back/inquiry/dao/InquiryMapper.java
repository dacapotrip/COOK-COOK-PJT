package com.bestprice.bestprice_back.inquiry.dao;

import com.bestprice.bestprice_back.inquiry.dto.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryMapper {

    // 문의 목록 가져오기
    List<InquiryDTO> getInquiries(@Param("offset") int offset, @Param("limit") int limit);

    // 총 문의 수 가져오기
    int getInquiryCount();

    // 특정 문의 가져오기
    InquiryDTO getInquiryById(@Param("id") int id);

    // 새로운 문의 등록
    void createInquiry(InquiryDTO inquiryDTO);

    // 특정 문의 삭제
    void deleteInquiry(@Param("id") int id);
}
