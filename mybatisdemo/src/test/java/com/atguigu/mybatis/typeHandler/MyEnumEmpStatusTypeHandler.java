package com.atguigu.mybatis.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.atguigu.mybatis.bean.EmpStatus;

/**
 * 1、实现TypeHandler接口。或者继承BaseTypeHandler
 * @author Administrator
 *
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus>{
	/**
	 * 定义当前数据如何保存到数据库中
	 */
	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, parameter.getCode().toString());
	}

	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		//需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
		int code=rs.getInt(columnName);
		System.out.println("从数据库中获取状态码:"+code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	public EmpStatus getResult(ResultSet cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		int code=cs.getInt(columnIndex);
		System.out.println("从数据库中获取状态码:"+code);
		return null;
	}

	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
