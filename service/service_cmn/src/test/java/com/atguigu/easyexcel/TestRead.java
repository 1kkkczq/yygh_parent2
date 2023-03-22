package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    0:41
 * @注释:  easyExcel 读操作
 */
public class TestRead {
    public static void main(String[] args) {

        //设置要读取的excel文件路径和文件名称
        String fileName = "F:\\excel\\01.xlsx";
        //调用方法读取
        EasyExcel.read(fileName , UserData.class , new ExcelListener())
                .sheet().doRead();
    }
}
