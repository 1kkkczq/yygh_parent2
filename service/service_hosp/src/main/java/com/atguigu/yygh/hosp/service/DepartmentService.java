package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;


import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    19:32
 * @注释:
 */
public interface DepartmentService {

    //上传科室信息接口
    void save(Map<String, Object> paramMap);


    //查询科室接口
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //删除科室接口
    void remove(String hoscode, String depcode);
}
