package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Ex01_04 {

	public static void main(String[] args) {
		//emp 테이블의 모든 사원 정보 조회
		// org.doit.domain 패키지
		//  ㄴ EmpVO.java	( VO == Value Object )
		// 1. JDBC 드라이버 로딩 - Class.forName()
		// ArrayList<EmpVO> list
		// dispEmp() // 출력하는 함수 선언
		
		// com.util 패키지
		// 		ㄴ DBConn.java
		//			ㄴ Connection getConnection() 메서드 구현
		//			ㄴ Connection getConnection() 메서드 구현
		//			ㄴ Connection getConnection() 메서드 구현
		
		
		Connection conn = null;
		Statement stmt = null; // 배달기사
		ResultSet rs = null; // 결과담아놓는 집합.
		String sql = "SELECT *" // empno, ename, job, mgr, hiredate, sal, comm, deptno
					+ "FROM emp";
		int empno;
		String ename;
		String job;
		int mgr;
//		String hiredate;
//		Date hiredate; // 소숫점이 짤림.
		LocalDateTime hiredate; // 이게 제일 낫다.
		double sal;
		double comm;
		int deptno;
		
		ArrayList<EmpVO> list = new ArrayList<>();
		EmpVO vo = null; // 여기서 선언하고
		
		try {
			 conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select
			
			while (rs.next()) {
				empno = rs.getInt("empno");
				deptno = rs.getInt("deptno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime(); // import java.util.Date;
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				
				vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno); // 여기서 변수를 활용하는게 좋음.
				
				list.add(vo);
				
				/*
				
				*/
			} // while
			
			dispEmp(list); // 이 함수 있어야 출력됨.
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 4. Connection 객체 닫기 - close()
				rs.close();
				stmt.close(); // 일꾼도 클로즈작업.
//				conn.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void dispEmp (ArrayList<EmpVO> list) {
		if (list.size() == 0) {
			System.out.println("사원이 존재하지 않음!");
			return ;
		} 
		// 사원정보 출력
		// ㄴ. 이게 더 짧고 간편하다.
		list.forEach(vo-> {
			System.out.printf("%d\t%d\t%s\t%s\t%d\t%tF\t%f\t%f\n"
					,vo.getEmpno(), vo.getDeptno(), vo.getEname(), vo.getJob()
					, vo.getMgr(), vo.getHiredate(), vo.getSal(), vo.getComm());
			
		}); 
		
		
		
		
		/*
		// ㄱ.
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.printf("%d\t%d\t%s\t%s\t%d\t%tF\t%f\t%f\n"
					,vo.getEmpno(), vo.getDeptno(), vo.getEname(), vo.getJob()
					, vo.getMgr(), vo.getHiredate(), vo.getSal(), vo.getComm()); // 아까 출력한대로 출력됨.
//			System.out.println(vo.toString()); // toString으로 출력되는 양식.
		}
		*/
		
		
	}

} // class









