package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    19:49
 * @注释:
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);

}
