package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    19:30
 * @注释:
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {




    @Autowired
    private DepartmentRepository departmentRepository;

    //删除科室接口

    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号和科室编号查询科室信息
        Department department = departmentRepository
                .getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null){
            departmentRepository.deleteById(department.getId());
        }
    }


    //查询科室接口

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
       //创建pageable对象  设置当前页和每页记录数  0 是第一页
        Pageable pageable = PageRequest.of(page-1, limit );


//        departmentQueryVo --> department
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo , department);
        department.setIsDeleted(0);

        //创建example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                        .withStringMatcher(ExampleMatcher
                                .StringMatcher.CONTAINING).withIgnoreCase(true);
        Example<Department> example = Example.of(department , matcher);


        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }







    //添加科室信息接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap --> 转换成department对象
        //先变成字符串
        String paramMapString = JSONObject.toJSONString(paramMap);
        //再转成对象
        Department department = JSONObject.parseObject(paramMapString, Department.class);
        //判断是否存在科室信息
        //根据医院编号和科室编号进行查询
        Department departmentExist = departmentRepository.
                getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        //判断
        if (departmentExist != null) { //科室存在
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }


    }
}