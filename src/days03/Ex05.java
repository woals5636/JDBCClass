package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

/**
 * @author love
 * @date 2024. 9. 4. 오후 3:32:38
 * @content
 */
public class Ex05 {

	public static void main(String[] args) {
		  // [저장 프로시저]   - 입력받은 ID를 사용 여부 체크하는 프로시저
	      //       ㄴ 회원가입
	      //             아이디 : [   hong     ] <ID중복체크버튼>
	      //             비밀번호      
	      //             이메일
	      //             주소
	      //             연락처
	      //             등등
		
		Scanner scanner = new Scanner(System.in);
		
		// emp tab 의 empno가 아이디로 가정.
		System.out.println("> Joong-bok Check 아이디(empno) 입력!");
		int id = scanner.nextInt();
		
		//UP_IDCHECK 프로시저를 CSTMT 사용해서 처리.
//		String sql = "{ call UP_IDCHECK(?,?) }";
		String sql = "{ call UP_IDCHECK(pid=>?, pcheck=>?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = -1;
		
		conn = DBConn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			// in ? , out ?
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.executeQuery(); // rs로 받을 필요 없음. 
			check = cstmt.getInt(2);
			if (check == 0) {
				System.out.println("> 사용가능한 아이디!");
			} else {
				System.out.println("> 이미 사용중인 아이디!");
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
		/*
		 * CREATE OR REPLACE PROCEDURE up_idcheck
(
    pid IN emp.empno%TYPE
    ,pcheck OUT NUMBER -- 0/1  내맘대루
)
IS

BEGIN
    select count(*) INTO pcheck
    from emp
    where empno = pid;
--EXCEPTION
--    WHEN OTHER THEN
--    RAISE AP_E

END;


DECLARE
    vcheck NUMBER(1);
BEGIN
    up_idcheck(7369,vcheck);
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;

		 * 
		 * */

	} // main

} // class
