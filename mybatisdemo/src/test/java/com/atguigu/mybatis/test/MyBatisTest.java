package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.DepartmentMapper;
import com.atguigu.mybatis.dao.EmployeeMapperDynamicSQL;
import com.atguigu.mybatis.dao.EnployeeMapper;
import com.atguigu.mybatis.dao.EnployeeMapperAnnotation;
import com.atguigu.mybatis.dao.EnployeeMapperPlus;
import com.mysql.fabric.xmlrpc.base.Array;

public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource.trim());
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	/**
	 * 创建SqlSessionFactory 文件
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// 从文件中获取SqlSessionFactory
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource.trim());
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 获取sqlSession实例，能执行已经映射的语句
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Employee employee = (Employee) session.selectOne("com.atguigu.mybatis.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		} finally {
			session.close();
		}
	}

	@Test
	public void test01() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			EnployeeMapper mapper = openSession.getMapper(EnployeeMapper.class);
			
//			Employee employee = mapper.getEmpById(1);
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("id", 1);
//			Employee employee = mapper.getEmpByMap(map);
			
//			List<Employee> list = mapper.getEmpsByLastNameLike("%e%");
//			for (Employee e : list) {
//				System.out.println(e);
//			}
			
//			Map map = mapper.getEmpByIdReturnMap(1);
//			System.out.println(map);
			
			Map<Integer, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
			System.out.println(map);
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	
	@Test
	public void test02() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			EnployeeMapperAnnotation mapper = openSession.getMapper(EnployeeMapperAnnotation.class);
			
			Employee employee = mapper.getEmpById(1);
			
			System.out.println(employee);
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	/**
	 * 增删该查测试
	 * 1、mybatis允许增删改直接定义以下类型返回值
	 * 		Integer、Long、Boolean
	 * 2、我们需要手动提交数据
	 * 		sqlSessionFactory.openSession();====>>手动提交
	 * 		sqlSessionFactory.openSession(true);======>>自动提交
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			EnployeeMapper mapper = openSession.getMapper(EnployeeMapper.class);
			
			//测试添加
			Employee emp=new Employee(null, "jerry", "jerry@asd.com", "0",null);
			mapper.addEmp(emp);
			System.out.println(emp.getId());
			
			//测试修改
//			Employee emp=new Employee(2, "tom", "jerry@asd.com", "0");
//			Boolean updateEmp=mapper.updateEmp(emp);
//			System.out.println(updateEmp);
			
			//测试删除
//			mapper.deleteEmpById(2);
			
//			Employee employee = mapper.getEmpById(1);
			
//			System.out.println(employee);
			//手动提交
			openSession.commit();
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	
	@Test
	public void test05() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			EnployeeMapperPlus mapper = openSession.getMapper(EnployeeMapperPlus.class);
			
//			Employee employee = mapper.getEmpById(1);
//			System.out.println(employee);
			
//			Employee e=mapper.getEmpAndDept(1);
			
			Employee e=mapper.getEmpByIdStep(3);
			System.out.println(e);
			System.out.println(e.getDept());
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}

	
	@Test
	public void test06() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
			
//			Department dep=mapper.getDeptByIdPlus(1);
//			System.out.println(dep);
//			System.out.println(dep.getEmps());
			
			Department dep=mapper.getDeptByIdStep(1);
			System.out.println(dep);
			System.out.println(dep.getEmps());
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	@Test
	public void testDynamicSQL() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			//测试if和where
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			
			Employee e=new Employee(1,"qwertyui",null,null,null);
//			List<Employee> dep=mapper.getEmpsByConditionIf(e);
//			for (Employee emp : dep) {
//				System.out.println(emp);
//			}
//解决if下第一个没有and的情况	 1.where 前面加1=1 
//			2，用where标签
			
			//测试Trim
//			List<Employee> trim=mapper.getEmpsByConditionTrim(e);
//			for (Employee emp : trim) {
//				System.out.println(emp);
//			}
			//测试choose
//			List<Employee> cho=mapper.getEmpsByConditionChoose(e);
//			for (Employee emp : cho) {
//				System.out.println(emp);
//			}
			//测试set
//			mapper.updateEmp(e);
//			openSession.commit();
			//测试foreach
//			List<Employee> list=mapper.getEmpsByConditionForeach(Arrays.asList(1,3,4));
//			for (Employee emp : list) {
//				System.out.println(emp);
//			}
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	
	@Test
	public void testBatchSave() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			//测试if和where
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			
			List<Employee> emps=new ArrayList<Employee>();
			emps.add(new Employee(null,"smith","smith@atguigu.com","1",new Department(1)));
			emps.add(new Employee(null,"allen","allen@atguigu.com","0",new Department(1)));
			mapper.addEmps(emps);
			openSession.commit();
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	
}
