package com.bestprice.bestprice_back.recipe;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface BookmarkMapper {
    BookmarkDto findByUserIdAndRcpSno(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    void insertBookmark(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    void deleteBookmark(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    List<Long> findRecipesByUserId(@Param("userId") String userId);

    void incrementWeeklyFavoritesCount(@Param("rcp_sno") Long rcp_sno);

    void decrementWeeklyFavoritesCount(@Param("rcp_sno") Long rcp_sno);

}
