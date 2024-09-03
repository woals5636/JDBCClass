package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author User
 *
 */
public class Ex01_03 {
	public static void main(String[] args) {
		

		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * "
				+ "FROM emp";
		
		int empno;
		String ename;
		String job;
		int mgr;
//		String hiredate;
//		Date hiredate;
		LocalDateTime hiredate;
		double sal;
		double comm;
		int deptno;
		
		try {
			// 1. JDBC 드라이버 로딩 - Class.forName()
			Class.forName(className);
			// 2. Connection 객체 - DriverManager
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD 작업 - Statement 객체
			stmt = conn.createStatement();
			// int stmt.executeUpdate(sql);	// insert, update, delete
			rs = stmt.executeQuery(sql); // select
			
			while (rs.next()) {
				
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				
				System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n",
						empno, ename,job,mgr,hiredate,sal,comm,deptno);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			// 4. Connection 객체 닫기 - close()
			try {
				// 닫는 순서 지켜야함
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		
	} // main
} // class
