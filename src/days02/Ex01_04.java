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

/**
 * @author User
 * org.doit.domain 패키지
 * 			ㄴ EmpVO.java ( Value Object )
 * ArrayList<EmpVO> list
 * dispEmp() 출력함수
 * 
 * com.util 패키지
 * 		ㄴ DBConn.java
 * 			ㄴ Connection getConnection() 메서드 구현
 * 			ㄴ Connection getConnection() 메서드 구현
 * 			ㄴ Connection getConnection() 메서드 구현
 * 			ㄴ close() 메서드 구현
 * 			
 */
public class Ex01_04 {
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * "
				+ "FROM emp";
		
		int empno;
		String ename;
		String job;
		int mgr;
//		String hiredate;
//		Date hiredate;
		LocalDateTime hiredate;
		double sal;
		double comm;
		int deptno;
		
		ArrayList<EmpVO> list = new ArrayList<>();
		EmpVO vo = null;
		
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
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				
				vo = new EmpVO(empno, ename,job,mgr,hiredate,sal,comm,deptno);
				
				list.add(vo);
				
			} // while
	
			dispEmp(list);
			
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
	
	public static void dispEmp(ArrayList<EmpVO> list) {
		if(list.size() == 0) {
			System.out.println("사원이 존재하지 않습니다.");
			return ;
		}
		
		// 사원 정보 출력
		// ㄴ.
		list.forEach(vo->{
			System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n",
					vo.getEmpno(), vo.getEname(),vo.getJob(),vo.getMgr(),
					vo.getHiredate(),vo.getSal(),vo.getComm(),vo.getDeptno());
		});
		/*
		 * ㄱ.
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = (EmpVO) ir.next();
			System.out.println(vo.toString());
			System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n",
					vo.getEmpno(), vo.getEname(),vo.getJob(),vo.getMgr(),
					vo.getHiredate(),vo.getSal(),vo.getComm(),vo.getDeptno());
		} // while
		*/
	}
} // class
