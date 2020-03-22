package com.rock.mapper;

import com.rock.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMapper {
    Employee getEmpById (String empId);
}
