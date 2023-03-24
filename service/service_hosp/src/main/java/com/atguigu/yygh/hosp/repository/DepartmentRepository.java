package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    19:29
 * @注释:
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department , String> {
   //查询科室信息
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
