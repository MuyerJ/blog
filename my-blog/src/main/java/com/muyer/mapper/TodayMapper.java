package com.muyer.mapper;

import com.muyer.model.DailySpeech;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describe:
 */
@Mapper
@Repository
public interface TodayMapper {

    @Insert("insert into daily_speech(content,mood,picsUrl,publishDate) values(#{content}, #{mood}, #{picsUrl}, #{publishDate})")
    void save(DailySpeech dailySpeech);

    @Select("select * from daily_speech order by id desc")
    List<DailySpeech> getTodayInfo();

}
