package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;

import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
//根据科室编号 医院编号查询科室名称

    @Override
    public Object getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null){
            return department.getDepname();
        }
        return null;
    }


    //删除科室接口

    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号和科室编号查询科室信息
        Department department = departmentRepository
                .getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }


    //查询科室接口

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建pageable对象  设置当前页和每页记录数  0 是第一页
        Pageable pageable = PageRequest.of(page - 1, limit);


//        departmentQueryVo --> department
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        //创建example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING).withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);


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

    //根据医院编号 查询所有科室列表
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {

        //创建 List集合 用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();
        //根据医院编号 查询所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example<Department> example = Example.of(departmentQuery);
        //所有科室的列表信息
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据大科室编号  bigcode 分组    获取每个大科室里面下级子科室
        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.
                groupingBy(Department::getBigcode));
        //departmentMap有大科室编号 和科室信息  遍历map
        for (Map.Entry<String, List<Department>> entry : departmentMap.entrySet()) {
            String bigcode = entry.getKey();  //大科室编号 bigcode
            List<Department> department1List = entry.getValue(); //大科室编号对应的全部数据

            //封装大科室
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(department1List.get(0).getDepname());
            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department department : department1List) {
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                children.add(departmentVo2);
            }
            //把小科室的list集合放到大科室Children去
            departmentVo1.setChildren(children);
            //放到最终的result去
            result.add(departmentVo1);

        }
        return result;

    }
}
