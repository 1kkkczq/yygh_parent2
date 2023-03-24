package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/24    20:45
 * @注释:
 */

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule , String> {

    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId) ;


}

