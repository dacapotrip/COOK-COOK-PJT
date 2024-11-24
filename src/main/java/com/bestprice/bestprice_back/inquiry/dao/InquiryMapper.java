package com.bestprice.bestprice_back.inquiry.dao;

import com.bestprice.bestprice_back.inquiry.dto.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryMapper {

// 전체 문의 가져오기
List<InquiryDTO> getInquiries(@Param("offset") int offset, @Param("limit") int limit);

// 전체 문의 수 가져오기
int getInquiryCount();

// 특정 사용자 문의 가져오기
List<InquiryDTO> getInquiriesByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("limit") int limit);

// 특정 사용자 문의 수 가져오기
int getInquiryCountByUserId(@Param("userId") String userId);

// 특정 문의 상세 정보 가져오기
InquiryDTO getInquiryById(@Param("id") int id);

// 문의 등록
void createInquiry(InquiryDTO inquiryDTO);

// 특정 문의 삭제
void deleteInquiry(@Param("id") int id);

// 특정 문의 답변 업데이트
void updateInquiryAnswer(@Param("id") int inquiryId, @Param("answer") String answer);
}
