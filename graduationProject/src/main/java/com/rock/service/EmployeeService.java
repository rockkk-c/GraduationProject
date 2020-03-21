package com.rock.service;

import com.rock.mapper.EmployeeMapper;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 检验Employee是否存在
     *
     * @return
     */
    @GraphQLQuery(name = "verifyTheOnlyFeatureData", description = "检验featureData是否已经存在")
    public boolean verifyTheOnlyEmp(@GraphQLArgument(name = "empId", description = "empId") String empId) {
        if (Objects.isNull(employeeMapper.getEmpById(empId))) {
            return false;
        }
        return true;
    }

}
