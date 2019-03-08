package com.example.demo.lgh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.lgh.pojo.Bos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BosMapper  extends BaseMapper<Bos> {
    @Select("select * from bos ${ew.customSqlSegment} order by id desc limit 0,1")
    Bos getOne(@Param(Constants.WRAPPER) Wrapper wrapper);
}
