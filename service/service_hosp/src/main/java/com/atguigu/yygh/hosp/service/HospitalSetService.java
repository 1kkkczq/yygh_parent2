package com.atguigu.yygh.hosp.service;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 医院设置表 服务类
 * </p>
 *
 * @author KKK
 * @since 2023-03-21
 */

public interface HospitalSetService extends IService<HospitalSet> {
    /**
     * 获取签名key
     *
     * @param hoscode
     * @return
     */
//    String getSignKey(String hoscode);




    //根据传递过来的医院编号去查询医院设置的数据库看签名是否一样
    String getSignKey_(String hoscode);
}
