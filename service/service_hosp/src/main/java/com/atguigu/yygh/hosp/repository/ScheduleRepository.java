package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    20:45
 * @注释:
 */

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule , String> {

    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId) ;
//根据医院编号和科室编号和工作日期 查询排班详细信息
    List<Schedule> getScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}

