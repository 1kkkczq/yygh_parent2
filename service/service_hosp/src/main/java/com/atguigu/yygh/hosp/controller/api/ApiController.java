package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;

import com.atguigu.yygh.vo.hosp.DepartmentVo;

import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
//@CrossOrigin
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班的接口
    @ApiOperation(value = "删除排班的接口")
    @PostMapping("schedule/remove")
    public  Result remove(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取排班编号
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode , hosScheduleId);
        return Result.ok();

    }





    //查询排班的接口
    @ApiOperation(value = "查询排班的接口")
    @PostMapping("schedule/list")
    public  Result findSchedule(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取科室编号
        String depcode = (String) paramMap.get("depcode");

        //当前页
        int page = StringUtils.isEmpty(paramMap.get("page"))
                ? 1 : Integer.parseInt((String) paramMap.get("page"));
        //每页显示记录数
        int limit = StringUtils.isEmpty(paramMap.get("limit"))
                ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel =
                scheduleService.findPageSchedule(page, limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }






    //添加排班的接口
    @ApiOperation(value = "上传排班的接口")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.save(paramMap);
        return Result.ok();
    }





    //删除科室接口
    @ApiOperation(value = "删除科室接口")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取科室编号
        String depcode = (String) paramMap.get("depcode");


        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode , depcode);
        return Result.ok();
    }








    //查询科室接口
    @ApiOperation(value = "查询科室接口")
    @PostMapping("department/list")

    public Result findDepartment(HttpServletRequest request) {
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //当前页
        int page = StringUtils.isEmpty(paramMap.get("page"))
                ? 1 : Integer.parseInt((String) paramMap.get("page"));
        //每页显示记录数
        int limit = StringUtils.isEmpty(paramMap.get("limit"))
                ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Page<Department> pageModel =
                departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);

    }


    //添加科室信息接口
    @ApiOperation(value = "添加科室信息接口")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.save(paramMap);
        return Result.ok();
    }


    //查询医院接口
    @ApiOperation(value = "查询医院接口")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //获取医院传递过来的信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取医院系中传递过来的签名
        String hospSign = (String) paramMap.get("sign");  //签名已经进行MD5加密
        //3根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
        String signKey = hospitalSetService.getSignKey_(hoscode);

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }


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

        // 4再把医院设置数据库查出来的签名进行md5加密  看跟医院那边 也就是上面获得的签名是否一致
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断是否一致  签名校验
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

//传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) paramMap.get("logoData");
        if (!StringUtils.isEmpty(logoDataString)) {
            String logoData = logoDataString.replaceAll(" ", "+");
            paramMap.put("logoData", logoData);
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
