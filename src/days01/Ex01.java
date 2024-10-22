package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

public class Ex01 {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		// JDBCClass  폴더 생성
		// 이클립스 실행하여 경로 이 폴더로 잡고, 설정 잡기.
		// java 프로젝트 생성 : jdbcPro
		// days01.Ex01.java생성 : Hello World 출력.
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // jdbc:oracle:thin : 4번 드라이브 연결
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn); // oracle.jdbc.driver.T4CConnection@3b2da18f : 테스트 성공

			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
//		OracleDriver

	}

}
