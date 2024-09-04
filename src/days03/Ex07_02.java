package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

public class Ex07_02 {
	public static void main(String[] args) {
		String sql = "{CALL UP_DELETEDEPT(?)}"; 
	      
	      Connection conn = null;
	      CallableStatement cstmt = null;
	      int rowCount = 0;
	      
	      int pdeptno = 50;
	      
	      conn =  DBConn.getConnection();
	      try {
	         cstmt = conn.prepareCall(sql);    
	         cstmt.setInt(1, pdeptno);    // IN    
	         
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

-- DEPT 테이블의 모든 부서 정보를 조회하는 저장프로시저
CREATE OR REPLACE PROCEDURE UP_DELETEDEPT
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS 
BEGIN
    OPEN pdeptcursor FOR
        SELECT *
        From dept;
--EXCEPTION
--  WHEN OTHERS THEN
--    RAISE AP_E)
END;

*/