package com.rock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rock.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
   // Employee getEmpById (String empId);
}
