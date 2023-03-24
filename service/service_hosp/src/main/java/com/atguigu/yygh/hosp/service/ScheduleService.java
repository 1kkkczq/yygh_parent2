package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    20:47
 * @注释:
 */
public interface ScheduleService {





    //添加排班的接口
    void save(Map<String, Object> paramMap);
    //查询排班的接口
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);


    //删除排班的接口
    void remove(String hoscode, String hosScheduleId);
}
