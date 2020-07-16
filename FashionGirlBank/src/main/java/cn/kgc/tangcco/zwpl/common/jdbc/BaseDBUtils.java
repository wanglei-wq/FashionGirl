package cn.kgc.tangcco.zwpl.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * baseDbutils
 * @author Administrator
 *
 */
public abstract class BaseDBUtils {
	// 创建ThreadLocal实例化对象用于将线程与Connection绑定
	private static ThreadLocal<Connection> t = new ThreadLocal<Connection>();
	// 获取数据源
	private static DataSource ds = new ComboPooledDataSource();

	/**
	 * 获取数据源
	 * 
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * 获取Connection
	 * 
	 * @return 获取数据库连接Connection
	 * @throws SQLException SQl异常
	 */
	public static Connection getConnection() throws SQLException {
		if (t.get() != null) {
			// 如果当前线程中存在Connection则从当前线程中获取Connection并返回当前线程中的Connection
			return t.get();
		}
		// 如果当前线程中不存在Connection通过DriverManager获取连接
		Connection conn = ds.getConnection();
		// 将Connection与当前线程绑定
		t.set(conn);
		// 返回Connection
		return conn;

	}

	/**
	 * 开始事务
	 * 
	 * @throws SQLException SQl异常
	 */
	public static void startTransaction() throws SQLException {
		// 关闭当前连接的自动提交功能
		getConnection().setAutoCommit(false);
	}

	/**
	 * 获取PreparedStatement
	 * 
	 * @param conn Connection
	 * @param sql  SQl语句
	 * @return PreparedStatement
	 * @throws SQLException SQl异常
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
		System.out.println(sql.toString());
		// 在该连接中预编译SQL
		return conn.prepareStatement(sql);
	}

	/**
	 * 获取PreparedStatement
	 * 
	 * @param conn Connection
	 * @param sql  SQl语句
	 * @param pr   分页参数
	 * @return PreparedStatement
	 * @throws SQLException SQl异常
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String sql, PageRang pr) throws SQLException {
		// MySQL分页中每页的起始index
		// int index = (pr.getPageNumber() - 1) * pr.getPageSize();
		// sql += " limit " + index + " , " + pr.getPageSize();
		sql += " limit " + (pr.getPageNumber() - 1) * pr.getPageSize() + " , " + pr.getPageSize();
		System.out.println(sql.toString());
		// 在该连接中预编译SQL
		return conn.prepareStatement(sql);
	}

	/**
	 * 
	 * @param pst   PreparedStatement
	 * @param param 查询参数
	 * @return ResultSet
	 * @throws SQLException SQl异常
	 */
	public static ResultSet executeQuery(PreparedStatement pst, Object... param) throws SQLException {
		if (param != null && param.length > 0) {
			// 如果参数数组不是null并且长度大于零
			for (int i = 0; i < param.length; i++) {
				// 遍历数组元素的值并将该值设置到PreparedStatement对象中
				pst.setObject(i + 1, param[i]);
			}
		}
		// 执行查询返回结果集
		return pst.executeQuery();
	}

	/**
	 * 
	 * @param pst   PreparedStatement
	 * @param pr    分页参数
	 * @param param 查询参数
	 * @return ResultSet
	 * @throws SQLException SQl异常
	 */
	public static ResultSet executeQuery(PreparedStatement pst, PageRang pageRang, Object... param)
			throws SQLException {
		if (param != null && param.length > 0) {
			// 如果参数数组不是null并且长度大于零
			for (int i = 0; i < param.length; i++) {
				// 遍历数组元素的值并将该值设置到PreparedStatement对象中
				pst.setObject(i + 1, param[i]);
			}
		}
		// MySQL分页中每页的起始index
		int pageIndex = ((pageRang.getPageNumber() - 1) * pageRang.getPageSize());
		// 设置最大行数
		pst.setMaxRows(pageIndex + pageRang.getPageSize());
		ResultSet rs = pst.executeQuery();
		// 相对移动n行
		rs.relative(pageIndex);
		return rs;
	}

	public static int executeUpdate(PreparedStatement pst, Object... param) throws SQLException {
		if (param != null && param.length > 0) {
			// 如果参数数组不是null并且长度大于零
			for (int i = 0; i < param.length; i++) {
				// 遍历数组元素的值并将该值设置到PreparedStatement对象中
				pst.setObject(i + 1, param[i]);
			}
		}
		// 执行查询返回结果集
		return pst.executeUpdate();
	}

	/**
	 * 关闭连接
	 * 
	 * @param pst PreparedStatement
	 * @param rs  ResultSet
	 * @throws SQLException
	 */
	public static void closeAll(PreparedStatement pst, ResultSet rs) throws SQLException {
		// 关闭连接
		// 获取当前线程中的Connection
		Connection conn = getConnection();
		if (rs != null) {
			rs.close();
		}
		if (pst != null) {
			pst.close();
		}
		if (conn != null) {
			conn.close();
		}
		// 从当前线程中移除Connection
		t.remove();
	}
	/**
	 * 关闭连接
	 * @throws SQLException
	 */
	public static void closeAll() throws SQLException {
		// 关闭连接
		// 获取当前线程中的Connection
		Connection conn = getConnection();
		if (conn != null) {
			conn.close();
		}
		// 从当前线程中移除Connection
		t.remove();
	}

	/**
	 * 提交事务并关闭连接
	 * 
	 * @param pst PreparedStatement
	 * @param rs  ResultSet
	 * @throws SQLException 
	 */
	public static void commitAndClose(PreparedStatement pst, ResultSet rs) throws SQLException {
		// 关闭连接
		// 获取当前线程中的Connection
		Connection conn = getConnection();
		// 手动提交事务
		conn.commit();
		if (rs != null) {
			rs.close();
		}
		if (pst != null) {
			pst.close();
		}
		if (conn != null) {
			conn.close();
		}
		// 从当前线程中移除Connection
		t.remove();
	}

	/**
	 * 提交事务并关闭连接
	 * 
	 * @throws SQLException
	 */
	public static void commitAndClose() throws SQLException {
		// 关闭连接
		// 手动提交事务
		DbUtils.commitAndClose(getConnection());
		// 从当前线程中移除Connection
		t.remove();
	}

	/**
	 * 事务灰回滚并关闭连接
	 * 
	 * @param pst PreparedStatement
	 * @param rs  ResultSet
	 * @throws SQLException
	 */
	public static void RollbackAndclose(PreparedStatement pst, ResultSet rs) throws SQLException {
		// 关闭连接
		// 获取当前线程中的Connection
		Connection conn = getConnection();
		// 事务回滚
		conn.rollback();
		if (rs != null) {
			rs.close();
		}
		if (pst != null) {
			pst.close();
		}
		if (conn != null) {
			conn.close();
		}
		// 从当前线程中移除Connection
		t.remove();
	}

	/**
	 * 事务灰回滚并关闭连接
	 * 
	 * @param pst PreparedStatement
	 * @param rs  ResultSet
	 * @throws SQLException
	 */
	public static void RollbackAndclose() throws SQLException {
		// 事务回滚关闭连接
		DbUtils.rollbackAndClose(getConnection());
		// 从当前线程中移除Connection
		t.remove();
	}
}
