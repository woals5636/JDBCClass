package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex01 {
	
	public static void main(String[] args) {
		
		System.out.println("Hello World");
		// JDBCClass 폴더 생성
		// 이클립스 실행 설정
		// java Project 생성 : jdbcPro
		// days01.Ex01.java : Hello World 출력.
		
		// oracle.jdbc.driver.OracleDriver
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD 작업
			System.out.println( conn );
			if(conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			
		}
	}
}
