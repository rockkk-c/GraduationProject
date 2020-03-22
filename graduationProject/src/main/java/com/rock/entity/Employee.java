package com.rock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("Employee")
public class Employee {
    @TableId(value = "emp_id", type = IdType.AUTO)
    private String id;
    @TableField(value = "emp_name")
    private String name;
    @TableField(value = "emp_pwd")
    private String pwd;
    @TableField(value = "emp_role")
    private String role;
}
