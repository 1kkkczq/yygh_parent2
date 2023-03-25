package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/25    16:41
 * @注释:
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //医院条件查询分页列表
    @ApiOperation(value = "医院条件查询分页列表")
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable  Integer limit,
                           HospitalQueryVo hospitalQueryVo){
     Page<Hospital> pageModel = hospitalService.selectHospPage(page , limit , hospitalQueryVo);
     return Result.ok(pageModel);
    }
}
