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
    //更新医院的上线状态
    void updateStatus(String id, Integer status);
    //医院详情信息
    Map<String, Object>   getHospById(String id);


    //根据医院编号获取医院名称

    String getHospName(String hoscode);


}
