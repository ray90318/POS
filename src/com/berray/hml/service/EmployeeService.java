package com.berray.hml.service;

import com.berray.hml.dao.EmployeeDAO;
import com.berray.hml.domain.Employee;

//emp業務層
public class EmployeeService {
    //定義一個employeeDAO屬性
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    //方法,根據empID,pwd返回一個employee對象
    public Employee getEmployeeIdAndPwd(String empID,String pwd){
        String sql = "select * from employee where empID = ? and pwd = md5(?)";
        return employeeDAO.quertSingle(sql,Employee.class,empID,pwd);
    }
}
