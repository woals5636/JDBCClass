package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author jam
 * @date 오전 9:01:20
 * @subject [jdbc] 게시판 구현 ( 모델 2방식 중 MVC 패터 ) 
 * @content 
 */
public class Ex01 {
	public static void main(String[] args) {
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		controller.boardStart();
		
		/*
		 * 1. 패키지 선언
		 *	days04.board
		 *  days04.board.controller
		 *	days04.board.service
		 *	days04.board.persistence - DAO
		 *	days04.board.domain - DTO, VO
		 *
		 * 2. http://taeyo.net/ 참조 
		 * 
		 * 3. 테이블 생성
		 * 
		 * -- ORACLE
CREATE SEQUENCE seq_tblcstVSBoard
NOCACHE;

CREATE TABLE tbl_cstVSBoard (
  seq NUMBER NOT NULL PRIMARY KEY,
  writer VARCHAR2 (20) NOT NULL,
  pwd VARCHAR2 (20) NOT NULL,
  email VARCHAR2 (100),
  title VARCHAR2 (200) NOT NULL,
  writedate DATE DEFAULT SYSDATE,
  readed NUMBER DEFAULT 0,
  tag NUMBER (1) NOT NULL,
  content CLOB
);

--
BEGIN
   FOR i IN 1..150 LOOP
       INSERT INTO tbl_cstVSBoard ( seq,  writer, pwd, email, title, tag,  content) 
       VALUES ( SEQ_TBLCSTVSBOARD.NEXTVAL, '홍길동' || MOD(i,10), '1234'
       , '홍길동' || MOD(i,10) || '@sist.co.kr', '더미...'  || i, 0, '더미...' || i );
   END LOOP;
   COMMIT;
END;
--
BEGIN
    UPDATE tbl_cstVSBoard
    SET writer = '이시훈'
    WHERE MOD(seq,15) = 2;
    COMMIT;
END;
--
BEGIN
    UPDATE tbl_cstVSBoard
    SET writer = '박준용'
    WHERE MOD(seq,15) = 4;
    COMMIT;
END;
--
BEGIN
    UPDATE tbl_cstVSBoard
    SET title = '게시판 구현'
    WHERE MOD(seq,15) IN(3,5,8);
    COMMIT;
END;
--
SELECT * 
FROM tbl_cstVSBoard;
		 * 
		 * 4. days04.board.domain.BoardDTO.java
		 * 
		 * */
	}
}
