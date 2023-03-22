package com.atguigu.yygh.cmn.service.impl;


import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author KKK
 * @since 2023-03-22
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //    根据数据id查询子数据列表
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
//        select * from dict where parent_id = 传进来的id
        wrapper.eq("parent_id" , id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向dictList集合中的每一个dict 对象设置hasChildren
        for(Dict dict : dictList){
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
//        select * from dict where parent_id = id
        wrapper.eq("parent_id" , id);
        Integer count = baseMapper.selectCount(wrapper);
        //count>0 1>0 true
        return count>0;
    }
}
