package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.EmpStatus;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.DepartmentMapper;
import com.atguigu.mybatis.dao.EmployeeMapperDynamicSQL;
import com.atguigu.mybatis.dao.EnployeeMapper;
import com.atguigu.mybatis.dao.EnployeeMapperAnnotation;
import com.atguigu.mybatis.dao.EnployeeMapperPlus;

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
	/**
	 * 1、获取SqlSessionFactory对象：
	 * 		解析每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession
	 * 		注意：MappedStatment代表一个增删改查的详细信息
	 * 2、获取sqlSession对象
	 * 		返回一个DefaultSqlSession对象，包含Executor和Confident;
	 * 3、获取接口创建代理对象MapperProxy
	 * 		getMapper,使用MapperProxyFactory创建一个MapperProxy的代理对象
	 * 		代理对象里面包含了DefaultSqlSession
	 * 4、执行增删改查方法
	 * 
	 * 总结：
	 * 	1、根据配置文件(全局，sql映射)初始化出Configuration对象
	 * 	2、创建一个DefaultSqlSession对象，
	 * 		他里面包含Configuration以及
	 * 		Executor(根据全局配置文件中的defaultExecutorType创建出对应的Executor)
	 *  3、DefaultSqlSession.getMapper():拿到Mapper接口对应的MapperProxy;
	 *  4、MapperProxy里面有(DefaultSqlSession);
	 *  5、执行增删改查方法：
	 *  	1)、调用DefaultSqlSession的增删改查(Executor);
	 *  	2)、会创建一个StatementHandler对象。
	 *  		(同时也会创建出ParameterHandler和ResultSetHandler)
	 *  	3)、调用ParameterHandler预编译参数以及设置参数值;
	 *  		用ParameterHandler给sql语句设置参数
	 *  	4)、调用StatementHandler的增删改查方法；
	 *  	5)、ResultSetHandler封装结果
	 *  注意：
	 *  	四大对象每个创建都有一个interceptorChain.pluginAll()
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		// 1、获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3、获取接口的实现类对象
		try {
			//4、会为接口创建代理对象MapperProxy
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
			//执行增删改查
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
			emps.add(new Employee(null,"smith1","smith1@atguigu.com","1",new Department(1)));
			emps.add(new Employee(null,"allen1","allen1@atguigu.com","0",new Department(1)));
			mapper.addEmps(emps);
			openSession.commit();
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	/**
	 * 两级缓存
	 * 	一级缓存：(本地缓存)：sqlSession级别的缓存，一级缓存是一直开启的，没法关闭；sqlSession的一个Map
	 * 		与数据库同一次会话期间查询到的数据会放到本地缓存中。
	 * 		以后如果需要获取相同的数据，直接从缓存中拿，不去再次查询数据库
	 * 
	 * 		一级缓存失效的情况(没有使用到当前一级缓存的情况，效果就是，还需要向数据库发出查询)
	 * 		1、sqlSession不同
	 * 		2、sqlSession相同，查询条件不同(当前缓存中还没有这个数据)
	 * 		3、sqlSession相同，查询期间执行了增删改操作(车次增删改对当前数据有影响)
	 * 		4、sqlSession相同，手动清除一级缓存
	 * 	二级缓存：(全局缓存):基于namespace级别的缓存：一个namespace对应一个二级缓存：
	 * 		工作机制：
	 * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
	 * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存
	 * 		3、不同namespace查出的数据会放在自己对应的缓存中(map)
	 * 			查出的数据都会默认先放在一级缓存中。
	 * 			只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	 * 	使用：
	 * 		1)、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
	 * 		2)、去mapper.xml中配置二级缓存，只有如下写二级缓存才能生效
	 * 			<cache></cache>
	 * 		3)、我们的POJO需要实现序列化接口
	 * 	和缓存有关的设置/属性：
	 * 		1)、cacheEnabled=true:false:关闭缓存(二级缓存关闭)(一级缓存一直可用)
	 * 		2)、每个select标签都有useCache="true"：
	 * 			false:不使用缓存(一级缓存依然使用，二级缓存不使用)
	 * 		3)、每个增删改标签的：flushCache="true"：(一级二级缓存都会被清空；)
	 * 				增删改执行完之后就会清空缓存 
	 * 		4)、openSession.clearCache();只是清除当前session的一级缓存；
	 * 		5)、setting配置localCacheScope:本地缓存作用域：(一级缓存SESSION)；当前会话的所有数据保存在会话缓存中；
	 * 				STATMENT:可以禁用缓存；
	 *第三方缓存整合：
	 *	1)、导入第三方缓存包即可；
	 *	2)、导入第三方缓存整合的适配；官方有
	 *	3)、mapper.xml中使用自定义缓存<cache type=""></cache>
	 *	
	 */
	@Test
	public void testSecondLevelCache() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		try {
			//会为接口创建代理对象
			EnployeeMapper mapper = openSession.getMapper(EnployeeMapper.class);
			EnployeeMapper mapper2 = openSession2.getMapper(EnployeeMapper.class);
			Employee e01=mapper.getEmpById(1);
			System.out.println(e01);
			openSession.close();
			Employee e02=mapper2.getEmpById(1);
			System.out.println(e02); 
			openSession2.close();
			
		} finally{
			// TODO: handle exception
//			openSession.close();
//			openSession2.close();
		}
	}
	@Test
	public void testFirstLevelCache() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		//第二个Session
//		SqlSession openSession2 = sqlSessionFactory.openSession();
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			EnployeeMapper mapper = openSession.getMapper(EnployeeMapper.class);
			Employee e01=mapper.getEmpById(1);
			System.out.println(e01);
//			mapper.addEmp(new Employee(null,"testCache","cache","1",new Department(1)));
//			EnployeeMapper mapper2 = openSession2.getMapper(EnployeeMapper.class);
//			openSession.clearCache();
//			Employee e02=mapper.getEmpById(1);
//			System.out.println(e02);
//			System.out.println(e01==e02);
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	/**
	 * 在四大对象创建的时候
	 * 1、每个创建出来的对象不是直接返回的，而是
	 * 		interceptorChain.pluginAll(parameterHandler)
	 * 2、获取到所有的Interceptor(拦截器) (插件需要实现的接口)
	 * 		调用Interceptor.plugin(target);返回target包装后的对象
	 * 3、插件机制，我们可以使用插件为目标创建一个代理对象；AOP(面向切面)
	 * 		我们的插件可以为四大对象创建出代理对象；
	 * 		代理对象可以拦截到四大对象的每一个执行；
	 * 
	 * @throws IOException
	 */
	/**
	 * 插件编写
	 * 1、编写Interceptor的实现类
	 * 2、使用@Intercepts注解完成插件签名
	 * 3、将写好的插件注册到全局配置文件中
	 * @throws IOException
	 */
	@Test
	public void testPlugin() throws IOException {
		// 1、获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3、获取接口的实现类对象
		try {
			//4、会为接口创建代理对象MapperProxy
			EnployeeMapper mapper = openSession.getMapper(EnployeeMapper.class);
			
			//执行增删改查
			Map<Integer, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
			System.out.println(map);
			
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	/**
	 * 批量操作
	 * @throws IOException
	 */
	@Test
	public void testBatch() throws IOException {
		// 获取SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 获取可以执行批量操作的sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		// 获取接口的实现类对象
		try {
			//会为接口创建代理对象
			//测试if和where
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			System.out.println("UUID:"+UUID.randomUUID().toString());
			List<Employee> emps=new ArrayList<Employee>();
			emps.add(new Employee(null,"smith1","smith1@atguigu.com","1",new Department(1)));
			emps.add(new Employee(null,"allen1","allen1@atguigu.com","0",new Department(1)));
			mapper.addEmps(emps);
			openSession.commit();
		} finally{
			// TODO: handle exception
			openSession.close();
		}
	}
	@Test
	public void testEnumUse() throws IOException {
		EmpStatus login=EmpStatus.LOGIN;
		System.out.println("枚举的索引:"+login.ordinal());
		System.out.println("枚举的名字:"+login.name());
		System.out.println("枚举的状态码:"+login.getCode());
		System.out.println("枚举的消息:"+login.getMsg());
		
	}
	
}
