package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.doit.domain.EmpDeptSalgradeVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author User
 * [jdbc] emp + dept + sal + grade => empDeptSalGradeVO
 */
public class Ex03 {
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = " SELECT empno, ename, dname, hiredate , sal+NVL(comm,0) pay, grade "
				+ " FROM emp e JOIN dept d ON e.deptno = d.deptno "
				+ "           JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal ";
		
		int empno;
		String ename;
		LocalDateTime hiredate;
		double pay;
		String dname;
		int grade;
		
		ArrayList<EmpDeptSalgradeVO> list = new ArrayList<>();
		EmpDeptSalgradeVO vo = null;
		
		try {
			// 1. + 2. == com.util.DBConn.getConnection
			conn = DBConn.getConnection();
			// 3. CRUD 작업 - Statement 객체
			stmt = conn.createStatement();
			// int stmt.executeUpdate(sql);	// insert, update, delete
			rs = stmt.executeQuery(sql); // select
			
			while (rs.next()) {
				
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				pay = rs.getDouble("pay");
				dname = rs.getString("dname");
				grade = rs.getInt("grade");
				
				vo = new EmpDeptSalgradeVO(empno, ename, hiredate, pay, dname, grade);
				
				list.add(vo);
				
			} // while
	
			list.forEach(evo->System.out.println(evo));
			
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			// 4. Connection 객체 닫기 - close()
			try {
				// 닫는 순서 지켜야함
				rs.close();
				stmt.close();
				// conn.close();
				DBConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // main
} // class
