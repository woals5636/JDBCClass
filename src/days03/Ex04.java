package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;
// emp, dept테이블의 부서당 사람수 카운트
public class Ex04 {

	public static void main(String[] args) {
		String deptSql = "select d.deptno, dname,count(empno) cnt "
						+ " from emp e "
						+ " full join  dept d on e.deptno = d.deptno "
						+ " group by dname, d.deptno "
						+ " order by d.deptno";
			
		String empSql =	" select empno, ename, hiredate, NVL(sal,comm) pay"
						+ " from emp"
						+ " where deptno = ?"; // 부서없으면 deptno IS NULL 처리!
		
		
		
		Connection conn = null;
		PreparedStatement deptPstmt = null, empPstmt = null;
		ResultSet deptRs, empRs = null;
		DeptVO dvo = null;
		EmpVO evo = null;
		ArrayList<EmpVO> empList = null;
		LinkedHashMap<DeptVO,ArrayList<EmpVO>> lhMap = new LinkedHashMap<>();
		
		// 1, 2
		conn = DBConn.getConnection();
		int deptno, cnt;
		String dname;
		
		int empno;
		String ename;
		LocalDateTime hiredate;
		double pay;
		
		try {
			deptPstmt = conn.prepareStatement(deptSql);
			deptRs = deptPstmt.executeQuery();
			while (deptRs.next()) {
				empList = null; // null로 초기화해놓자!
				deptno = deptRs.getInt("deptno");
				dname = deptRs.getString("dname");
				cnt = deptRs.getInt("cnt");
				dvo = new DeptVO(deptno, dname, null, cnt);
				
//				System.out.printf("%s(%d명) \n", dvo.getDname(), dvo.getCnt());
				// 해당 부서사원 정보 조회 시작
				if (deptno == 0) { //
					empSql =	" select empno, ename, hiredate, NVL(sal,comm) pay"
							+ " from emp"
							+ " where deptno is null";
				}
				
				empPstmt = conn.prepareStatement(empSql);
				if (deptno !=0) empPstmt.setInt(1, deptno); //
				empRs = empPstmt.executeQuery();
				
				if (empRs.next()) {
					empList = new ArrayList<EmpVO>();
					do {
						empno = empRs.getInt("empno");
						ename = empRs.getString("ename");
						hiredate = empRs.getTimestamp("hiredate").toLocalDateTime();
						pay = empRs.getDouble("pay");
						
						evo = new EmpVO().builder()
								.empno(empno)
								.ename(ename)
								.hiredate(hiredate)
								.sal(pay)
								.build();
						empList.add(evo);
					} while (empRs.next());
					
				} //if
				// 사원 정보 조회 끝
				// Map  에 k, v 엔트리 추가
				lhMap.put(dvo, empList); // 맵 안에 추가가 됨.
				
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				deptPstmt.close();
				deptPstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		// 4.
		DBConn.close();
		
		dispLHMap(lhMap);

	} // main

	private static void dispLHMap(LinkedHashMap<DeptVO, ArrayList<EmpVO>> lhMap) {
		
		Set<Entry<DeptVO, ArrayList<EmpVO>>> eset = lhMap.entrySet();
		Iterator<Entry<DeptVO, ArrayList<EmpVO>>> eir = eset.iterator();
		while (eir.hasNext()) {
			Entry<DeptVO, ArrayList<EmpVO>> entry = eir.next();
			DeptVO dvo = entry.getKey();
			System.out.printf("%s(%d명) \n", dvo.getDname(), dvo.getCnt());
			
			ArrayList<EmpVO> empList = entry.getValue();
			if (empList == null) {
					System.out.println("\t 해당 부서원은 존재하지 않음!");
					continue;
					}
			Iterator<EmpVO> ir = empList.iterator();
			while (ir.hasNext()) {
				EmpVO evo = ir.next();
				System.out.printf("\s\s%d\t%s\t%tF\t%.2f\n",evo.getEmpno(),evo.getEname(), evo.getHiredate(), evo.getSal());
			}
			
		} // while
		
		
	}

} // class
