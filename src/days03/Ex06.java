package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author love
 * @date 2024. 9. 4. 오후 4:16:11
 * @content [jdbc] 로그인! 인증 / 인가 회원 아이디/비밀번호 입력 [로그인]/[회원가입] *** emp / empno(id)
 *          / ename(pw)
 */
public class Ex06 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// emp tab 의 empno가 아이디로 가정.
		System.out.println("> 로그인할 아이디(empno)/pw(ename) 입력!");
		int id = scanner.nextInt();
		String pwd = scanner.next();
		
		String sql = "{ call UP_LOGIN( ?, ?, ?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1;

		conn = DBConn.getConnection();

		try {
			cstmt = conn.prepareCall(sql);
			// in ? , out ?
			cstmt.setInt(1, id);
			cstmt.setString(2, pwd);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			cstmt.executeQuery(); // rs로 받을 필요 없음.
			check = cstmt.getInt(3);
			if (check == 0) {
				System.out.println("> 로그인 성공!");
			} else if (check == 1) {
				System.out.println("> 아이디는 존재하지만 비밀번호 오류!");
			} else if (check == -1) {
				System.out.println("> 존재하지 않는 아이디입니다.");
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

		DBConn.close();

	} // main

} // class

/*
 * CREATE OR REPLACE PROCEDURE up_login ( pid IN emp.empno %TYPE ,ppwd IN
 * emp.ename%TYPE ,pcheck OUT NUMBER -- 0: 로그인성공, 1: id존재하나 pw틀림, -1: id존재X ) IS
 * vpwd emp.ename %TYPE; BEGIN select count(*) INTO pcheck from emp where empno
 * = pid;
 * 
 * IF pcheck = 1 THEN SELECT ename INTO vpwd FROM emp WHERE empno = pid;
 * 
 * IF vpwd = ppwd THEN pcheck := 0; ELSE pcheck := 1; END IF; ELSE pcheck := -1;
 * END IF;
 * 
 * 
 * END; -- DECLARE vcheck NUMBER; BEGIN up_login(7369,'SMITH', vcheck);
 * DBMS_OUTPUT.PUT_LINE(vcheck); END;
 */
