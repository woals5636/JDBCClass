package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;

public class Ex01_03 {

	public static void main(String[] args) {
		//emp 테이블의 모든 사원 정보 조회
		// 1. JDBC 드라이버 로딩 - Class.forName()
		
		String className = "oracle.jdbc.driver.OracleDriver"; // oracleDriver 컨트롤엔터하면 나오는것 복붙
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null; // 배달기사
		ResultSet rs = null; // 결과담아놓는 집합.
		String sql = "SELECT *" // empno, ename, job, mgr, hiredate, sal, comm, deptno
					+ "FROM emp";
		int empno;
		String ename;
		String job;
		int mgr;
//		String hiredate;
//		Date hiredate; // 소숫점이 짤림.
		LocalDateTime hiredate; // 이게 제일 낫다.
		double sal;
		double comm;
		int deptno;
		
		try {
			 Class.forName(className);
			// 2. DriverManager 통해 Connection 객체 얻기
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD 작업 - statement(배달기사) 필요
			stmt = conn.createStatement();
//			int stmt.executeUpdate(sql); = insert, update, delete
			rs = stmt.executeQuery(sql); // select
			
			while (rs.next()) {
				empno = rs.getInt("empno");
				deptno = rs.getInt("deptno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime(); // import java.util.Date;
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				System.out.printf("%d\t%d\t%s\t%s\t%d\t%tF\t%f\t%f\n"
						,empno, deptno, ename, job, mgr, hiredate, sal, comm);
				
			} // while
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 4. Connection 객체 닫기 - close()
				rs.close();
				stmt.close(); // 일꾼도 클로즈작업.
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

	}

}
