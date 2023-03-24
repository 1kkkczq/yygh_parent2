package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.ScheduleRepository;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    20:47
 * @注释:
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;


    //删除排班的接口

    @Override
    public void remove(String hoscode, String hosScheduleId) {
        //先根据医院编号和排班编号查询出排班信息
        Schedule schedule = scheduleRepository
                .getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        //判断
        if (schedule != null) {
            //存在排班信息
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    //查询排班的接口
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        //创建pageable对象  设置当前页和每页记录数  0 是第一页
        Pageable pageable = PageRequest.of(page - 1, limit);

//        scheduleQueryVo --> schedule
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);

        //创建example对象  模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING).withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);


        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }


    //添加排班的接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap --> 转换成schedule对象
        //先变成字符串
        String paramMapString = JSONObject.toJSONString(paramMap);
        //再转成对象
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);
        //判断是否存在排班信息
        //根据医院编号和排班编号进行查询
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());

        //判断
        if (scheduleExist != null) { //排班存在
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleExist.setStatus(1);
            scheduleExist.setHosScheduleId(schedule.getHosScheduleId());
            scheduleRepository.save(scheduleExist);
        } else {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
    }
}
