package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

public class Ex07_03 {
	public static void main(String[] args) {
		String sql = "{CALL up_insertdept(? , ? , ?)}"; 
	      
	      Connection conn = null;
	      CallableStatement cstmt = null;
	      int rowCount = 0;
	      
	      int pdeptno = 50;
	      String  pdname = "MKT";
	      String ploc = "SUWON";
	      
	      conn =  DBConn.getConnection();
	      try {
	         cstmt = conn.prepareCall(sql);    
	         cstmt.setInt(1, pdeptno);    // IN     
	         cstmt.setString(2, pdname);    // IN        
	         cstmt.setString(3, ploc);    // IN       
	         
	         rowCount = cstmt.executeUpdate(); // DML문 
	         
	         if (rowCount == 1) {
	            System.out.println("부서 추가 성공!!");
	         } else {
	            System.out.println("부서 추가 실패!!");
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

CREATE OR REPLACE PROCEDURE up_insertdept
( 
     pdeptno IN dept.deptno%TYPE,
     pdname IN dept.dname%TYPE,
     ploc IN dept.loc%TYPE
)
IS   
BEGIN
    INSERT INTO dept 
    VALUES (pdeptno,pdname,ploc);
    COMMIT;
-- EXCEPTION    
END; 

*/