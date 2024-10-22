package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author love
 * @date 2024. 9. 4. 오전 10:48:30
 * @content [jdbc] 트랜잭션을 자바에서 어떻게?!
 * 				   논리적인 작업 단위				모두 완료 or 모두 취소
 * 				   예) 계좌 이체는 둘 다에게 업데이트가 일어남!    -> 모두 성공 : commit, 하나라도 실패 : rollback
 * 			begin
 *              update
 *              update
 *              
 *              commit;
 *          exepction
 *          	when then
 *          end
 */
public class Ex02 {

	public static void main(String[] args) {
		String sql = "INSERT INTO dept VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		try {
			// 1, 2
			conn = DBConn.getConnection();
			conn.setAutoCommit(false); // 자동커밋 막아줌
			pstmt = conn.prepareStatement(sql);
			
			// ㄱ. 인설트
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");
			
			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("첫 번째 부서 추가 성공!");
			}
			
			// ㄴ. 에러발생시켜보기
			pstmt.setInt(1, 50); // 부서번호는 하나밖에 있을 수 없다. -> 오류나서 롤백됐다. -> 50번 추가도 롤백됐다.
			pstmt.setString(2, "QC2");
			pstmt.setString(3, "SEOUL2");
			
			rowCount = pstmt.executeUpdate();
			if (rowCount == 1) {
				System.out.println("두 번째 부서 추가 성공!");
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
		
		

	} // main

} // class
