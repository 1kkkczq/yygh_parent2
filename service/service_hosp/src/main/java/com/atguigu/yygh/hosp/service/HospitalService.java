package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    19:51
 * @注释:
 */
public interface HospitalService {


    //上传医院接口

    void save(Map<String, Object> paramMap);

    //根据医院编号查询
    Hospital getByHoscode(String hoscode);

    //医院条件查询分页列表
    Page<Hospital> selectHospPage( Integer page,  Integer limit, HospitalQueryVo hospitalQueryVo);
}
