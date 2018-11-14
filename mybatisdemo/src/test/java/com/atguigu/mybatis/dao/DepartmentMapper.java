package com.atguigu.mybatis.dao;

import java.util.List;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;

public interface DepartmentMapper {
	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id); 
	
	public Department getDeptByIdStep(Integer id);
	
	public List<Employee> getEmpsByDeptId(Integer deptId);
	
}	
