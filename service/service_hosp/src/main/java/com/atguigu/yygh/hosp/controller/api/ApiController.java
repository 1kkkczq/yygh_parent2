package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    19:54
 * @注释:
 */

@Api(tags = "医院系统接口")
@RestController
@CrossOrigin
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    //上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取从医院系统传过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
