package com.rock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rock.entity.Employee;
import com.rock.entity.Result;
import com.rock.mapper.EmployeeMapper;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
@GraphQLApi
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 检验Employee是否存在
     *
     * @return
     */
    @GraphQLQuery(name = "verifyTheOnlyEmp", description = "检验featureData是否已经存在")
    public boolean verifyTheOnlyEmp(@GraphQLArgument(name = "empId", description = "empId") String empId) {
        if (Objects.isNull(employeeMapper.selectById(empId))) {
            return false;
        }
        return true;
    }

    @GraphQLQuery(name = "login", description = "登录")
    public Result login(@GraphQLArgument(name = "empId", description = "empId") String empId,
                        @GraphQLArgument(name = "empPwd", description = "empPwd") String empPwd,
                        @GraphQLArgument(name = "empRole", description = "empRole") String empRole) {
        if (!this.verifyTheOnlyEmp(empId)) {
            return Result.error("此用户不存在");
        }
        Employee employee = employeeMapper.selectById(empId);
        if (employee.getPwd().equals(empPwd) && employee.getRole().equals(empRole)) {
            return Result.ok("登录成功");
        }
        return Result.error("密码错误");

    }

    @GraphQLMutation(name = "addEmployee", description = "添加工作人员")
    public Result addEmployee(@GraphQLArgument(name = "employee", description = "employee") Employee employee) {
        employeeMapper.insert(employee);
        return Result.ok("添加成功");

    }

    @GraphQLQuery(name = "loadListOfEmployee", description = "按条件查询Employee")
    public List<Employee> loadListOfEmployee(@GraphQLArgument(name = "employee", description = "employee") Employee employee) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();

        if (null != employee.getId() && !"".equals(employee.getId())) {
            queryWrapper.eq("emp_id", employee.getId());
        }else if(null != employee.getName() && !"".equals(employee.getName())){
            queryWrapper.eq("emp_name", employee.getName());
        }else if(null != employee.getRole() && !"".equals(employee.getRole())){
            queryWrapper.eq("emp_role", employee.getRole());
        }
        return employeeMapper.selectList(queryWrapper);
    }
    @GraphQLMutation(name="deleteEmp",description = "通过id删除employee")
    public Result deleteEmp(@GraphQLArgument(name="employee",description = "employee")Employee employee){
        if (!this.verifyTheOnlyEmp(employee.getId())) {
            return Result.error("此用户不存在");
        }
        if(Objects.isNull(employee)){
            return Result.error("请选择要删除的员工");
        }
        employeeMapper.deleteById(employee.getId());
        return Result.ok("删除成功");

    }
    @GraphQLMutation(name="updateEmp",description = "修改employee信息")
    public Result updateEmp(@GraphQLArgument(name="employee",description = "employee")Employee employee){
        employeeMapper.updateById(employee);
        return Result.ok("修改信息成功");
    }

    @GraphQLQuery(name="selectAllEmployee",description = "查询所有员工")
    public List<Employee>selectAllEmployee(){
        return employeeMapper.selectAllEmployee();
    }
}

