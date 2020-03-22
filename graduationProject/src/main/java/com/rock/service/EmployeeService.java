package com.rock.service;

<<<<<<< HEAD
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rock.entity.Employee;
import com.rock.mapper.EmployeeMapper;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@GraphQLApi
=======
import com.rock.mapper.EmployeeMapper;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

>>>>>>> origin/master
@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 检验Employee是否存在
     *
     * @return
     */
<<<<<<< HEAD
    @GraphQLQuery(name = "verifyTheOnlyEmp", description = "检验Employee是否存在")
    public boolean verifyTheOnlyEmp(@GraphQLArgument(name = "empId", description = "empId") String empId) {

      // Employee employee=employeeMapper.getEmpById(Integer.parseInt(empId));
      // Employee employee1=employeeMapper.selectById(empId);
      // String s=employee1.getName();
          if (Objects.isNull(employeeMapper.selectById(empId))) {
=======
    @GraphQLQuery(name = "verifyTheOnlyFeatureData", description = "检验featureData是否已经存在")
    public boolean verifyTheOnlyEmp(@GraphQLArgument(name = "empId", description = "empId") String empId) {
        if (Objects.isNull(employeeMapper.getEmpById(empId))) {
>>>>>>> origin/master
            return false;
        }
        return true;
    }
<<<<<<< HEAD
    @GraphQLQuery(name = "login", description = "登录")
    public String login(@GraphQLArgument(name = "empId", description = "empId") String empId,
                         @GraphQLArgument(name = "empPwd", description = "empPwd") String empPwd,
                         @GraphQLArgument(name = "empRole", description = "empRole") String empRole) {
        if(!this.verifyTheOnlyEmp(empId)){
            //return employeeMapper.getEmpById(empId).getId()+employeeMapper.getEmpById(empId).getRole();
            return "此用户不存在！";
        }else{
            Employee employee=employeeMapper.selectById(empId);
            if(employee.getPwd().equals(empPwd) && employee.getRole().equals(empRole)){
                return "登录成功";

            }else{
                return "密码错误";
            }

        }
    }

    @GraphQLMutation(name="deleteById",description = "根据id删除员工")
    public String deleteByIdn(@GraphQLArgument(name = "empId", description = "empId") String empId){
        if (Objects.isNull(empId)&& "".equals(empId)) {
            return "请选择要删除的员工ID";
        }else{
            employeeMapper.deleteById(empId);
            return "删除成功！";
        }
    }
    @GraphQLMutation(name="updateEmployee",description = "根据id修改员工信息")
    public String updateEmployee(@GraphQLArgument(name = "employee", description = "employee") Employee employee){
        if (Objects.isNull(employee)) {
            return "请选择要修改的员工";
        }else{
            employeeMapper.updateById(employee);
            return "修改成功！";
        }
    }
    @GraphQLMutation(name="addEmployee",description = "添加员工")
    public String addEmployee(@GraphQLArgument(name = "employee", description = "employee") Employee employee){
        if (Objects.isNull(employee)) {
            return "信息不全，请重新输入";
        }else{
            employeeMapper.insert(employee);
            return "添加成功！";
        }
    }

    @GraphQLQuery(name = "selectAllEmployee", description = "查看所有员工")
    public List<Employee> selectAllEmployee() {
        List<Employee> list=employeeMapper.selectAllEmployee();
        return employeeMapper.selectAllEmployee();
    }

    @GraphQLQuery(name = "selectEmployeeByWrapper", description = "根据条件查询员工")
    public List<Employee> selectEmployeeByWrapper(@GraphQLArgument(name = "empName", description = "empName")String empName,
                                                  @GraphQLArgument(name = "empRole", description = "empRole")String empRole ) {
        QueryWrapper<Employee> queryWrapper=new QueryWrapper<>();
        if((Optional.ofNullable(empName)).isPresent()){
            queryWrapper.like("emp_name",empName);
        }
        if((Optional.ofNullable(empRole)).isPresent()){
            queryWrapper.eq("emp_role",empRole);
        }
        return employeeMapper.selectList(queryWrapper);
    }
=======

>>>>>>> origin/master
}
