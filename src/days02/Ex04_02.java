package days02;



	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.doit.domain.EmpDeptSalgradeVo;
import org.doit.domain.SalgradeVO;

import com.util.DBConn;

	public class Ex04_02 {
	   public static void main(String[] args) {

	      String sql = 
	            "SELECT grade, losal, hisal "
	                  + "     , COUNT(*) cnt "
	                  + "FROM salgrade s JOIN emp e ON e.sal BETWEEN s.losal AND s.hisal "
	                  + "GROUP BY grade, losal, hisal "
	                  + "ORDER BY grade ASC";
	      String empSql = "SELECT d.deptno, dname, empno, ename, sal  "
	            + "FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
	            + "            JOIN salgrade s  ON sal BETWEEN losal AND hisal "
	            + "WHERE grade = ?   ";

	      Connection conn = null;
	      PreparedStatement pstmt = null, empPstmt = null;
	      ResultSet rs = null, empRs = null;
	      SalgradeVO vo = null;                         // key
	      ArrayList<EmpDeptSalgradeVo> empList = null;  // value      
	      EmpDeptSalgradeVo empVo = null;

	      LinkedHashMap<SalgradeVO, ArrayList<EmpDeptSalgradeVo>> map = new LinkedHashMap<SalgradeVO, ArrayList<EmpDeptSalgradeVo>>();

	      conn = DBConn.getConnection();

	      try {
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         if (rs.next()) {            
	            do {
	               int grade = rs.getInt("grade");
	               vo = new SalgradeVO( 
	                     grade 
	                     , rs.getInt("losal")
	                     , rs.getInt("hisal")
	                     , rs.getInt("cnt")
	                     );
	               //
	               empPstmt = conn.prepareStatement(empSql);
	               empPstmt.setInt(1, grade);
	               empRs = empPstmt.executeQuery();

	               if (empRs.next()) {
	                  empList = new ArrayList<EmpDeptSalgradeVo>();
	                  do {
	                     //d.deptno, dname, empno, ename, sal
	                     empVo = EmpDeptSalgradeVo
	                           .builder()
	                           .empno(empRs.getInt("empno"))
	                           .dname(empRs.getString("dname"))
	                           .ename(empRs.getString("ename"))
	                           .pay(empRs.getDouble("sal"))
	                           .build();
	                     empList.add(empVo);
	                  } while (empRs.next());
	               } // if

	               map.put(vo, empList);

	               empRs.close();
	               empPstmt.close();

	            } while (rs.next());
	         } // if


	         dispSalgrade(map);

	      } catch (SQLException e) { 
	         e.printStackTrace();
	      } catch (Exception e) { 
	         e.printStackTrace();
	      } finally {
	         try {            
	            rs.close();
	            pstmt.close();
	            DBConn.close();
	         } catch (SQLException e) { 
	            e.printStackTrace();
	         }
	      } 

	   } // main

	   private static void dispSalgrade(
	         LinkedHashMap<SalgradeVO, ArrayList<EmpDeptSalgradeVo>> map) {
	      Set<Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVo>>> set = map.entrySet();
	      Iterator<Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVo>>> ir = set.iterator();
	      while (ir.hasNext()) {
	         Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVo>> entry = 
	               ir.next();
	         SalgradeVO vo =  entry.getKey();
	         ArrayList<EmpDeptSalgradeVo> list = entry.getValue();
	         // 출력
	         System.out.printf("%d등급   ( %d~%d ) - %d명\n"
	               , vo.getGrade()
	               , vo.getLosal()
	               , vo.getHisal()
	               , vo.getCnt());
	         // 사원 출력
	         Iterator<EmpDeptSalgradeVo> ir2 = list.iterator();
	         while (ir2.hasNext()) {
	            EmpDeptSalgradeVo empvo = ir2.next();
	            System.out.printf(
	                  "\t\t%s\t%d\t%s\t%.2f\n"
	                  , empvo.getDname()
	                  , empvo.getEmpno()
	                  , empvo.getEname()
	                  , empvo.getPay());
	         }
	      } // while
	   }

	   private static void dispSalgrade(ArrayList<SalgradeVO> list) {
	      Iterator<SalgradeVO> ir = list.iterator();
	      while (ir.hasNext()) {
	         SalgradeVO vo = ir.next();
	         System.out.printf("%d등급   ( %d~%d ) - %d명\n"
	               , vo.getGrade()
	               , vo.getLosal()
	               , vo.getHisal()
	               , vo.getCnt());


	} // class

	

}
	   
	}
