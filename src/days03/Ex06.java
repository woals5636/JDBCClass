package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.internal.OracleTypes;

/**
 * @author jam
 * @date 오후 4:17:07
 * @subject [jdbc] 로그인(인증) / 인가
 * @content 아이디 / 비밀번호 입력
 * 			[로그인][회원가입]
 * 			
 * 			emp   / empno(id) / ename(pwd)
 */
public class Ex06 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);

		System.out.print("> 로그인할 ID(empno)/PWD(ename)를 입력 ? ");
		int id = scanner.nextInt();
		String pwd = scanner.next();
		
		String sql = "{ call UP_LOGIN ( ?,?,? ) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1;
		
		conn = DBConn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			// IN ? , OUT ?
			cstmt.setInt(1, id);
			cstmt.setString(2, pwd);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			cstmt.executeQuery();
			check = cstmt.getInt(3);
			if (check == 0) {
				System.out.println("로그인 성공!!!");
			} else if(check == 1){
				System.out.println("아이디는 존재하지만 비밀번호가 잘못됨");
			} else if(check == -1){
				System.out.println("존재하지 않는 아이디입니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
