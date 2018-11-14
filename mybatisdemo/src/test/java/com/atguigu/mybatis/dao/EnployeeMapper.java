package com.atguigu.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EnployeeMapper {
	
	//多条记录封装一个map:Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
	@MapKey("id")
	public Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);
	 
	public Map<String,Object> getEmpByIdReturnMap(Integer id);
	
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	
	public Employee getEmpById(Integer id);
	
	public void addEmp(Employee employee);
	
	public void deleteEmpById(Integer id);
	
	public Boolean updateEmp(Employee employee);
}
