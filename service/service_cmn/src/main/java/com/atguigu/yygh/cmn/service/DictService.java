package com.atguigu.yygh.cmn.service;


import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author KKK
 * @since 2023-03-22
 */
public interface DictService extends IService<Dict> {

//    根据数据id查询子数据列表
    List<Dict> findChildData(Long id);
}
