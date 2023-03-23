package com.atguigu.yygh.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static sun.security.util.PolicyUtil.getInputStream;

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
//
//        @Autowired
//        private DictMapper dictMapper;

    //导入数据字典
    @Override
    @CacheEvict(value ="dict" , allEntries = true)  //清空缓存
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class,
                    new DictListener(baseMapper))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //导出数据字典
    @Override
    public void exportDictData(HttpServletResponse response) {


        //设置下载信息
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
            //Dict ---> DictEeVo
            //遍历dictList  把他复制放到 dictEeVoList
            List<DictEeVo> dictEeVoList = new ArrayList<>();
            for (Dict dict : dictList) {
                DictEeVo dictEeVo = new DictEeVo();
                //复制
//                dictEeVo.setId(dict.getId());
                BeanUtils.copyProperties(dict, dictEeVo);
                dictEeVoList.add(dictEeVo);
            }

            //调用方法进行写出操作

            EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                    .sheet("dict").doWrite(dictEeVoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //    根据数据id查询子数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")  //放入缓存 生成key
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
//        select * from dict where parent_id = 传进来的id
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向dictList集合中的每一个dict 对象设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
//        select * from dict where parent_id = id
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        //count>0 1>0 true
        return count > 0;
    }
}
