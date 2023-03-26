package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    19:52
 * @注释:
 */
@Service

public class HospitalServiceImpl implements HospitalService {


    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DictFeignClient dictFeignClient;




    //医院详情信息
    @Override
    public Map<String , Object> getHospById(String id) {
//       Hospital hospital = hospitalRepository.findById(id).get();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.
                findById(id).get());
      Map<String, Object> resultMap = new HashMap<>();
      //医院基本信息 包含医院等级
        resultMap.put("hospital" , hospital);
        //单独处理预约规则
      resultMap.put("bookingRule" , hospital.getBookingRule());
      //不需要重复返回
        hospital.setBookingRule(null);
        return resultMap;

    }


    //更新医院的上线状态
    @Override
    public void updateStatus(String id, Integer status) {
        //根据id查询医院信息
        Hospital hospital = hospitalRepository.findById(id).get();
        //修改值
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);

    }

    //医院条件查询分页列表 (MongoDB)
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        //创建pageable对象
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING
                ).withIgnoreCase(true);

//        hospitalQueryVo --->  hospital
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        //创建对象
        Example<Hospital> example = Example.of(hospital, matcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        //pages只有医院的基本信息 没有等级信息
        //得到pages的医院基本信息集合 Hospital中并没有医院等级信息
        //把等级信息封装到param里去
//        List<Hospital> content = pages.getContent();
        //遍历集合进行医院等级分装
        pages.getContent().stream().forEach(item ->{
            this.setHospitalHosType(item);
        });


        return pages;
    }

//    遍历集合进行医院等级分装
    private Hospital setHospitalHosType(Hospital hospital) {
        //根据dictCode和 value 获取医院等级名称
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        //查询省 市 地区
        String privinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress" , privinceString+cityString+districtString);
        hospital.getParam().put("hostypeString" , hostypeString);
        return hospital;
    }

//    根据医院编号查询

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }


    //上传医院接口

    //数据加到mongodb
    @Override
    public void save(Map<String, Object> paramMap) {
        //把参数map集合转换对象 Hospital
        String mapString = JSONObject.toJSONString(paramMap); //map变成字符串
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        //判断是否存在相同数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospitalExist != null) {//存在--> 修改
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);

        } else {//不存在 --> 添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);

        }


    }
}
