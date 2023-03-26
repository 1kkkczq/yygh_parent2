package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
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
    //根据医院编号和科室编号 查询排班规则数据
    Map<String, Object> getRuleSchedule(Long page, Long limit, String hoscode, String depcode);
    //根据医院编号和科室编号和工作日期 查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
