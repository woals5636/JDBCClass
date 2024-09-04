package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author jam
 * @date 오전 10:50:32
 * @subject [jdbc] 트랜잭션 처리 
 * @content 
 */

public class Ex02 {
	public static void main(String[] args) {
		String sql = "INSERT INTO dept VALUES (? , ? , ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		// 1,2
		
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			// ㄱ.
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");
			
			rowCount = pstmt.executeUpdate();
			if( rowCount == 1) {
				System.out.println("첫 번째 부서 추가 성공!!!");
			}
			// ㄴ. 에러발생
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC2");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			if( rowCount == 1) {
				System.out.println("두 번째 부서 추가 성공!!!");
			}
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 4
		DBConn.close();
	}
}



