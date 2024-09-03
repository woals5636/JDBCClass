package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

import lombok.ToString;

/**
 * @author User
 * [jdbc] org.doit.domain.DeptVO.java
 * 		 1. SELECT * FROM dept 쿼리실행 ArrayList<DeptVO> deptList
 * 		 2. 부서정보 출력
 * 		 3. 부서를 선택하세요 ? 10 or 20 ..
 * 		 4. Select * FROM emp WHERE deptno = 20;
 * 		 5. ArrayList<EmpVO> empList 저장
 * 		 6. 해당 사원의 정보를 출력
 */
public class Ex02 {

	public static void main(String[] args) {
		String sql = "SELECT * FROM dept";
		ArrayList<DeptVO> deptList = null;
		ArrayList<EmpVO> empList = new ArrayList<EmpVO>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// DeptVO
		int deptno;
		String dname, loc;
		
		// EmpVO
		int empno;
		String ename;
		String job;
		int mgr;
		LocalDateTime hiredate;
		double sal;
		double comm;
		
		EmpVO evo = null;
		DeptVO dvo = null;

		// 1+2
		conn = DBConn.getConnection();
		// 3
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	// "SELECT * FROM dept"
			if(rs.next()) {
				deptList = new ArrayList<DeptVO>();

				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("deptno");
					loc = rs.getString("loc");
					
					// dvo = new DeptVO(deptno, dname, loc);
					
					/*
					dvo = new DeptVO();
					dvo.setDeptno(deptno);
					dvo.setDname(dname);
					*/
					// @Builder
					dvo = new DeptVO().builder()
							.deptno(deptno).dname(dname).loc(loc)
							.build();
					
					deptList.add(dvo);
				} while (rs.next());
				
				//
				deptList.forEach(vo->System.out.println(vo));
				
			} // if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.cancel();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} // try
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 부서번호를 입력하세요 ? ");	 // 10, 20
//		deptno = scanner.next();
		String deptnos = scanner.nextLine();
		
		sql = String.format(" SELECT * "
			+ " FROM emp "
			+ " WHERE deptno IN ( %s ) " , deptnos);
//			+ " WHERE deptno = " + deptno;
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select
			
			while (rs.next()) {
				
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				
				evo = new EmpVO(empno, ename,job,mgr,hiredate,sal,comm,deptno);
				
				empList.add(evo);
				
			} // while
	
			Ex01_04.dispEmp(empList);
			
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
		
		
		
		
		
		
		
		// 4
		DBConn.close();
		
	}//main

}//class
