package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author love
 * @date 2024. 9. 5. 오전 9:00:52
 * @content	[jdbc] 게시판 구현 - ( 모델2 방식 중 MVC패턴 )
 */
public class Ex01 {

	public static void main(String[] args) {
		// 1. 패키지 선언
		//    days04에 board 
		// board.controllder - 모든 걸 총괄하는 클래스\
		// service - 주문을 받아 서비스하는 작업 함.
		// persistenct - 디비연동 DAO(데이터액세스) 
		// domin - DTO, VO(값을가지는 객체)
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		controller.boardStart();
		

	} // main

} // class

/*
 * 
2. 더미데이터 만들기(sqld)
CREATE SEQUENCE seq_tblcstVSBoard
NOCACHE;
--
CREATE TABLE tbl_cstVSBoard 
(
  seq NUMBER NOT NULL PRIMARY KEY, -- 글번호
  writer VARCHAR (20) NOT NULL, -- 작성자
  pwd VARCHAR (20) NOT NULL, -- 비밀번호
  email VARCHAR (100) , -- 이메일
  title VARCHAR (200) NOT NULL, -- 글제목
  writedate DATE DEFAULT SYSDATE,  -- 작성일
  readed NUMBER DEFAULT(0), -- 조회수
  tag NUMBER(1) NOT NULL, -- 글의 형식(0 -> text, 1 -> HTML 태그 허용)
  content CLOB -- 글의 내용
);
--
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO tbl_cstVSBoard (seq, writer, pwd, email, title, tag, content)
        VALUES (seq_tblcstVSBoard.NEXTVAL,'홍길동'|| MOD(i,10),'1234','홍길동'||MOD(i,10)||'@sis.co.kr','더미...'|| i,0,'더미~~~'|| i);
    END LOOP;
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
    WHERE MOD(seq,15) IN (3,5,8);
    COMMIT;
END;
--
4. BoardDTO.java 추가 / days04.board.domain 안에 추가

 * 
 * */
