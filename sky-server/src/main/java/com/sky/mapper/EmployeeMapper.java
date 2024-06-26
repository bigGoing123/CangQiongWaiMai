package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee(username, password, name, phone, sex, id_number,  status, create_time, update_time)" +
            "values(#{username}, #{password}, #{name}, #{phone}, #{sex}, #{idNumber},#{status}, #{createTime}, #{updateTime})")
    @AutoFill(value = OperationType.INSERT)
    void save(Employee employee);


    /**
     * 根据条件分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新员工状态
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);


    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
