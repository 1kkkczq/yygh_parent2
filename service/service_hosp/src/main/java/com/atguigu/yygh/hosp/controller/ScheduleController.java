package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/26    15:21
 * @注释:
 */
@Api(tags = "医院排班规则接口")
//@CrossOrigin
@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;



    //根据医院编号和科室编号和工作日期 查询排班详细信息
    @ApiOperation(value = "根据医院编号和科室编号和工作日期 查询排班详细信息")
    @GetMapping("getScheduleDetail//{hoscode}/{depcode}/{workDate}")
    public  Result getScheduleDetail(@PathVariable String hoscode,
                                     @PathVariable String depcode,
                                     @PathVariable String workDate){
    List<Schedule> list =
            scheduleService.getDetailSchedule(hoscode , depcode , workDate);
    return Result.ok(list);

    }




    //根据医院编号和科室编号 查询排班规则数据
    @ApiOperation(value = "根据医院编号和科室编号 查询排班规则数据")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Long page,
                                  @PathVariable Long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode) {
        Map<String, Object> map = scheduleService
                .getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);

    }

}
