package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    @Autowired
    private HospitalSetService hospitalSetService;

    //上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //1获取从医院系统传过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //2获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey_(hoscode);


        //传输过程中”+“  转换为了 ” “ 因此要转换回来  图片才能显示
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" " , "+");
        paramMap.put("logoData" , logoData);


       // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if(!hospSign.equals(signKeyMd5)){
            throw  new YyghException(ResultCodeEnum.SIGN_ERROR);
        }



//        if(StringUtils.isEmpty(hoscode)) {
//            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
//        }
////签名校验
//        if(!HttpRequestHelper.isSignEquals(paramMap,
//                hospitalSetService.getSignKey(hoscode))) {
//            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
//        }


        //调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
