package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpDeptSalgradeVo;
import org.doit.domain.EmpVO;
import org.doit.domain.SalgradeVO;

import com.util.DBConn;

/**
 * @author love
 * @date 2024. 9. 3. 오후 12:37:18
 * @content
 */
public class Ex04 {

	public static void main(String[] args) {
		
		String sql = "SELECT grade, s.losal, s.hisal"
				+ " , count(*) cnt"
				+ " FROM salgrade s "
				+ " JOIN emp e ON sal BETWEEN losal AND hisal "
				+ " group by grade, s.losal, s.hisal "
				+ " ORDER BY grade ";
		String sql2 = "select d.deptno, dname, empno, ename, sal "
				+ "from dept d "
				+ "right join emp e on d.deptno = e.deptno "
				+ "join salgrade s on sal between losal and hisal "
				+ "where grade = ";
		
		int deptno;
		int empno;
		String ename;
		String dname;
		double sal;
		
		Connection conn = null;
		Statement stmt = null, stmt2 = null;
		ResultSet rs = null, rs2 = null;
		
		
		
		// gradeVO
		int grade;
		int hisal;
		int losal;
		int cnt;
		
		ArrayList<SalgradeVO> list = new ArrayList<>();
		
		SalgradeVO vo = null;
		
		
		// grade, deptno, empno, job, ename, sal
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {

	            // rs.getInt(1);
	            grade = rs.getInt("grade");
	            losal = rs.getInt("losal");
	            hisal = rs.getInt("hisal");
	            cnt = rs.getInt("cnt"); 

	            vo = new SalgradeVO().builder()
	                   .grade(grade)
	                   .losal(losal)
	                   .hisal(hisal)
	                   .cnt(cnt)
	                   .build();

	            //list.add(vo);
	            
	            // START
	            System.out.printf("%d등급   ( %d~%d ) - %d명\n"
		                  , vo.getGrade(), vo.getLosal()
		                  , vo.getHisal(), vo.getCnt() );
		            String sql3 = sql2 +  vo.getGrade();
		            
		            //System.out.println( sql3 );
		            
		            stmt2 = conn.createStatement();
		            rs2 = stmt2.executeQuery(sql3);
		            
		            if (rs2.next()) {
						
		            	do {
//		            		grade = rs.getInt("grade");
							deptno = rs2.getInt("deptno");
							empno = rs2.getInt("empno");
							dname = rs2.getString("dname");
							ename = rs2.getString("ename");
							sal = rs2.getDouble("sal");
							
							System.out.printf(" \t\t%d\t%d\t%s\t%s\t%.2f \n", deptno, empno, dname, ename, sal);
		            	}while (rs2.next());
		            	
					}else {
						System.out.println("/t 사원 존재하지 않음");
					}
			 //if
					rs2.close();
			        stmt2.close(); 
		
	            // END
	            


	         } // while

	           
	
			} catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         // 4. Connnection 객체 닫기 - close()
	         try {
	            rs.close();
	            stmt.close();
	            // conn.close();
	            DBConn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }


	   } // main


}
