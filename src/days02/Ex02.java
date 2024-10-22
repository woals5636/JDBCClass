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

/**
 * @author User
 * org.doit.domain.DeptVO.java 패키지 추가
 * 1. SELECT * FROM dept; 쿼리 실행 -> 부서정보 ArrayList<DeptVO> deptList 생성
 * 2. 부서정보 출력
 * 3. 부서번호를 선택하세요? 20 선택
 * 4. SELECT * FROM emp WHERE deptno = 20;
 * 5. ArrayList<EmpVO> empList 저장
 * 6. 해당사원의 정보를 출력
 *
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
		String dname;
		String loc;
		
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
		
		conn = DBConn.getConnection();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				deptList = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					
					// dvo = new DeptVO(deptno, dname, loc);
					dvo = new DeptVO().builder()
							 .deptno(deptno)
							 .dname(dname)
							 .build();
					
					deptList.add(dvo);
				} while (rs.next());
				
				deptList.forEach(vo->System.out.println(vo));
				
			} //if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 부서번호를 입력하세용"); // 10
//		deptno = scanner.nextInt();
//		String deptnos = scanner.next();
		String deptnos = scanner.nextLine(); // 공백 있어도 상관 x
		
		sql = String.format(" SELECT * "
				+ " FROM emp "
				+ " Where deptno IN ( %s )",deptnos);
		
		//
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			while (rs.next()) {
				
				empno = rs.getInt("empno");
				deptno = rs.getInt("deptno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime(); // import java.util.Date;
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				
				evo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno); // 여기서 변수를 활용하는게 좋음.
				
				empList.add(evo);
				
				/*
				
				*/
			} // while
			
			Ex01_04.dispEmp(empList); // 이 함수 있어야 출력됨.
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 4. Connection 객체 닫기 - close()
				rs.close();
				stmt.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		

		DBConn.close();
	} // main

	
	
} // class














