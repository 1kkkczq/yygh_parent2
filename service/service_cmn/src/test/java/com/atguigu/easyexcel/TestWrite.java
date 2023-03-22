package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    0:20
 * @注释:  easyExcel 写操作
 */
public class TestWrite {
    public static void main(String[] args) {
        //构建数据List集合
        List<UserData> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("KKK"+ i);
            list.add(data);
        }
        //设置excel文件路径和文件名称
        String fileName = "F:\\excel\\01.xlsx";
        //调用方法实现写操作
        EasyExcel.write(fileName , UserData.class).sheet("用户信息")
                .doWrite(list);

    }
}
