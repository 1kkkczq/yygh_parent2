package com.atguigu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/23    0:17
 * @注释:
 */
@Data
public class UserData {

    @ExcelProperty(value ="用户编号" , index = 0)
    private int uid;
    @ExcelProperty(value ="用户名称" , index = 1)
    private String username;
}
