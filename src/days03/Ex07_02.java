package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author love
 * @date 2024. 9. 4. 오후 4:39:54
 * @content
 */
public class Ex07_02 {

	public static void main(String[] args) {
		
		String sql = "{CALL UP_DELETEDEPT(?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;

		int pdeptno = 50;

		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pdeptno); // IN

			rowCount = cstmt.executeUpdate(); // DML문

			if (rowCount == 1) {
				System.out.println("부서 삭제 성공!!");
			} else {
				System.out.println("부서 삭제 실패!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DBConn.close();
		System.out.println(" end ");

	} // main

} // class

/*
 * 
 * CREATE OR REPLACE PROCEDURE up_deletedept ( pdeptno IN dept.deptno%TYPE ) IS
 * BEGIN DELETE FROM dept WHERE deptno = pdeptno; COMMIT; -- EXCEPTION END;
 * 
 */