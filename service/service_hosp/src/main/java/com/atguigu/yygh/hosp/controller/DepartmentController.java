package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/26    14:23
 * @注释:
 */
@Api(tags = "医院科室信息接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    //根据医院编号 查询所有科室列表
    @ApiOperation(value =  "根据医院编号 查询所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){
     List<DepartmentVo>  list =    departmentService.findDeptTree(hoscode);
     return Result.ok(list);
    }
}
