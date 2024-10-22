package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

public class Ex07_04 {

	public static void main(String[] args) {
				Scanner scanner = new Scanner(System.in);
				ResultSet rs = null;
				String sql = "{ call up_alterdept(?) }";
				
				Connection conn = null;
				CallableStatement cstmt = null;
		
				int rowCount =scanner.nextInt();
				int pdeptno = scanner.nextInt();
				
				
				conn = DBConn.getConnection();
				System.out.print("> 수정할 부서번호 입력하세요.");
				
				try {
					cstmt = conn.prepareCall(sql);
					cstmt.setInt(1, pdeptno);

					rowCount = cstmt.executeUpdate();

					if (rowCount == 1) {
						System.out.println("수정할 부서가 존재하지 않네요~");
					}  else {
						System.out.println("부서 수정 성공 ~");
						
					} // if
					
					String odname = rs.getString("dname");
					String oloc = rs.getString("loc");

					System.out.println(" Original DNAME : " + odname);
					System.out.println(" Original LOC : " + oloc);

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						cstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				System.out.print("> 수정할 부서명, 지역명 입력하세요.");
				String dname = scanner.next();
				String loc = scanner.next();

				sql = "UPDATE dept SET dname = ?, loc = ? WHERE deptno = ?";

				try {
					cstmt = conn.prepareCall(sql);
					cstmt.setString(1, dname);
					cstmt.setString(2, loc);
					cstmt.setInt(3, deptno);
					int rowCount = cstmt.executeUpdate();
					if (rowCount == 1) {
						System.out.println("부서 수정 성공!!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			

	}

}
