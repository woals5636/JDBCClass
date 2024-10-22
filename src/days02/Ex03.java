package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.doit.domain.EmpDeptSalgradeVo;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
emp + dept + salgrade => EmpDeptSalGradeVO

**/



/**
 * @author love
 * @2024. 9. 3. 오후 12:36:26
 * @content
 */
public class Ex03 {

	public static void main(String[] args) {
				
				Connection conn = null;
				Statement stmt = null; // 배달기사
				ResultSet rs = null; // 결과담아놓는 집합.
				String sql = "SELECT empno, ename, dname, hiredate, sal+NVL(comm,0) pay, grade "
						+ " FROM emp e JOIN dept d ON e.deptno = d.deptno "
						+ "           JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal";
				int empno;
				String ename;
				LocalDateTime hiredate;
				double pay;
				String dname;
				int grade;
				
				ArrayList<EmpDeptSalgradeVo> list = new ArrayList<>();
				EmpDeptSalgradeVo vo = null;
				
				try {
					conn = DBConn.getConnection();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql); // select
					
					while (rs.next()) {
						empno = rs.getInt("empno");
						ename = rs.getString("ename");
						dname = rs.getString("dname");
						hiredate = rs.getTimestamp("hiredate").toLocalDateTime(); 
						pay = rs.getDouble("pay");
						grade = rs.getInt("grade");
						
						vo = new EmpDeptSalgradeVo(empno, ename, hiredate, pay, dname, grade);
						
						list.add(vo);
						

					} // while
					
					list.forEach(evo -> System.out.println(evo));
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						// 4. Connection 객체 닫기 - close()
						rs.close();
						stmt.close(); // 일꾼도 클로즈작업.
//						conn.close();
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
				

	} //main

} //class
