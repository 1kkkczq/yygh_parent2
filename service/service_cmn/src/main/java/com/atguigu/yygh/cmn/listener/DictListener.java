package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    10:27
 * @注释:
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {
    //一行一行读取 存到数据库
    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) { //有参构造
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加到数据库
        //DictEeVo -->  Dict
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);

        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
