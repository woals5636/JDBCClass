package days02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

public class Ex05 {
	
	public static String [] menu = { "추가", "수정", "삭제", "조회", "검색", "종료" };
	public static int selectedNumber ;
	public static Connection conn;
	public static Statement stmt = null;
	public static Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		
		conn = DBConn.getConnection();
		
		do {
	         메뉴출력();
	         메뉴선택();
	         메뉴처리();         
	      } while (true);
		

	}

	private static void 메뉴처리() {
		switch (selectedNumber) {
	      case 1:  // 추가
	         부서추가();
	         break;
	      case 2:  // 수정
	         부서수정();
	         break;
	      case 3:  // 삭제
	         부서삭제();
	         break;
	      case 4: // 조회
	         부서조회();
	         break;
	      case 5:// 검색
	         부서검색();
	         break;
	      case 6: // 종료
	         프로그램종료();
	         break;
	      default:
	         break;
	      } // switch

	      일시정지();
		
	}

	private static void 일시정지() {
		System.out.print("엔터치면 계속합니다.");
	      try {
	         System.in.read();
	         System.in.skip( System.in.available() );
	      } catch (IOException e) { 
	         e.printStackTrace();
	      }      
		
	}

	private static void 프로그램종료() {
		// 1. DB 닫기
	      DBConn.close();
	      // 1-2. 스캐너 닫기
	      scanner.close();
	      // 2. 종료 메시지 출력
	      System.out.println("프로그램 종료!!!");
	      // 3. 
	      System.exit(-1);
		
	}

	private static void 부서검색() {
		// 검색조건 입력?	1 'b' (부서번호)/2 'n' (부서명)/3 'l' (지역명)  nl -> 부서명과지역명
		// 검색어 입력? 
		String searchCondition; // 검색조건(b,n,l)
		String searchWord; // 검색어
		System.out.print("> 검색조건, 검색어 입력?");
		searchCondition = scanner.nextLine();
		searchWord = scanner.nextLine();
		
		
		ArrayList<DeptVO> list = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sql = "SELECT * "
				+ " FROM dept "
				+ " WHERE "; 
		int deptno;
		String dname, loc;
		
		DeptVO vo = null;
		
		// 
		if (searchCondition.equals("b")) { // 부서번호
			sql += "deptno =" + searchWord;
		} else if (searchCondition.equals("n")){ // 부서명
			sql += " REGEXP_LIKE ( dname , ' "+searchWord+" ' , 'i') ";
		} else if (searchCondition.equals("l")){ // 지역명
			sql += " REGEXP_LIKE ( loc , ' "+searchWord+" ' , 'i') ";
		} else if (searchCondition.equals("nl")){ // 지역명
			sql += " REGEXP_LIKE ( loc , ' "+searchWord+" ' , 'i') OR REGEXP_LIKE ( loc, '"+searchWord+"','i' )";
		}
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc, 0);
					list.add(vo);
				} while (rs.next());
				
			}//if
			
			부서출력(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
	}
		
		
		
		
		
		
		
	}

	private static void 부서조회() {
		ArrayList<DeptVO> list = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sql = "SELECT * "
				+ " FROM dept "
				+ " WHERE deptno > 0 "; // 인덱스 걸어 튜닝 (실행속도 up)
		int deptno;
		String dname, loc;
		
		DeptVO vo = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc,0);
					list.add(vo);
				} while (rs.next());
				
			}//if
			
			부서출력(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void 부서출력(ArrayList<DeptVO> list) {
		Iterator<DeptVO> ir = list.iterator();
	      System.out.println("-".repeat(30));
	      System.out.printf("deptno\tdname\tloc\n");
	      System.out.println("-".repeat(30));
	      while (ir.hasNext()) {
	         DeptVO vo =  ir.next();
	         System.out.printf("%d\t%s\t%s\n"
	               , vo.getDeptno(), vo.getDname(), vo.getLoc());
	      }
	      System.out.println("-".repeat(30));
		
	}

	private static void 부서삭제() {
		// 삭제할 부서번호 입력
		// 없으면 존재하지않는 부서다 출력
		// 잇으면 삭제, 삭제햇습니다 출력
		// 수정이랑 똑같은데 몇개빼면 됨
		
		ResultSet rs = null;
		DeptVO vo = null;
		System.out.print("> 삭제할 부서번호 입력하세요.");
		int deptno = scanner.nextInt();
		String sql = "SELECT *"
				+ " FROM dept "
				+ " WHERE deptno =" + deptno;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if ( ! rs.next()) {
				System.out.println("삭제할 부서가 존재하지 않네요~");
				
				return;
			}//if
			String odname = rs.getString("dname");
			
			System.out.println(" Original DNAME : "+odname);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
			System.out.print("> 삭제할 부서번호 입력하세요.");
			String dname = scanner.next();
			
			
			sql = String.format( 
					"DELETE FROM dept "
					+ " WHERE deptno =" + deptno);
//			System.out.println(sql);
			 try {
		         stmt = conn.createStatement();
		         int rowCount = stmt.executeUpdate(sql);
		         if (rowCount >= 1) {
		            System.out.println("부서 삭제 성공!!");
		         } else {
		        	 System.out.println("삭제할 부서가 존재하지 않네용~");
				}
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		   
		      
		   }
			


	private static void 부서수정() {
		// 1. 수정할 부서번호를 입력
		// 2. 실제 부서정보 읽어와서 출력
		// 3. 수정할 부서명, 지역명 입력받아서 업데이트
		ResultSet rs = null;
		DeptVO vo = null;
		System.out.print("> 수정할 부서번호 입력하세요.");
		int deptno = scanner.nextInt();
		String sql = "SELECT *"
				+ " FROM dept "
				+ " WHERE deptno =" + deptno;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if ( ! rs.next()) {
				System.out.println("수정할 부서가 존재하지 않네요~");
				
				return;
			}//if
			String odname = rs.getString("dname");
			String oloc = rs.getString("loc");
			
			System.out.println(" Original DNAME : "+odname);
			System.out.println(" Original LOC : "+oloc);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
			System.out.print("> 수정할 부서명, 지역명 입력하세요.");
			String dname = scanner.next();
			String loc = scanner.next();
			
			sql = String.format(
					"UPDATE dept "
					+ " SET dname='%s', loc = '%s'"
					+ " WHERE deptno =%d"
					, dname, loc, deptno);
			
			 try {
		         stmt = conn.createStatement();
		         int rowCount = stmt.executeUpdate(sql);
		         if (rowCount == 1) {
		            System.out.println("부서 수정 성공!!");
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      }
		   
		      
		   }
			
			
		
		
	

	private static void 부서추가() {
	      System.out.println("> 부서번호 부서명 지역명 입력 ?");
	      
	      int deptno = scanner.nextInt();
	      String dname = scanner.next();
	      String loc = scanner.next();
	      //String sql = " INSERT INTO dept ( deptno, dname, loc ) VALUES ( 60, '','' ) " ;
	      
	      
	      String sql = String.format(
	            " INSERT INTO dept ( deptno, dname, loc ) "
	            + " VALUES ( %d , '%s' , '%s' ) ", deptno, dname, loc );
	      
	      try {
	         stmt = conn.createStatement();
	         int rowCount = stmt.executeUpdate(sql);
	         if (rowCount == 1) {
	            System.out.println("부서 추가 성공");
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   
	      
	   }

	private static void 메뉴선택() {
		try{
	         System.out.print("> 메뉴 선택하세요 ? ");
	         selectedNumber = scanner.nextInt();
	         scanner.nextLine(); // \r\n 제거 작업
	      } catch (Exception e) {      }
		
	}

	private static void 메뉴출력() {
		System.out.printf("[메뉴]\n");
	      for (int i = 0; i < menu.length; i++) {
	         System.out.printf("%d. %s\n", i+1, menu[i]);
	      }
		
	}

}
