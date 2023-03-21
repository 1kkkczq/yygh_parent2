package com.atguigu.yygh.hosp.mapper;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 医院设置表 Mapper 接口
 * </p>
 *
 * @author KKK
 * @since 2023-03-21
 */

@Repository
@Mapper
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {

}
