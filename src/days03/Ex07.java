package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author love
 * @date 2024. 9. 4. 오후 4:39:54
 * @content
 */
public class Ex07 {

	public static void main(String[] args) {

		String sql = "{ call UP_selectdept(?) }";

		Connection conn = null;
		CallableStatement cstmt = null;

		conn = DBConn.getConnection();
		ResultSet rs = null;

		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeQuery(); 
			rs = (ResultSet)cstmt.getObject(1);
			
			int deptno;
			String dname, loc;
			
			while (rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n",deptno, dname, loc);
			}//wh
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DBConn.close();

	} // main

} // class

/*
 * 
 * -- dept의 모든 부서 정보 조회하는 저장프로시저 CREATE OR REPLACE PROCEDURE up_selectdept (
 * pdeptcursor OUT SYS_REFCURSOR ) IS
 * 
 * BEGIN OPEN pdeptcursor FOR select * from dept;
 * 
 * end;
 * 
 */