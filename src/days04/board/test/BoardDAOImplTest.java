package days04.board.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {
   
   Connection conn = null;
   BoardDAO dao  = null;
   
   public BoardDAOImplTest() {
      this.conn = DBConn.getConnection();
      this.dao = new BoardDAOImpl(this.conn);
   }

   @Test
   void testSelect() {
      
      try {
         ArrayList<BoardDTO> list = this.dao.select(1,10);
         list.forEach(dto->{
            System.out.println(dto);
         });
      } catch (SQLException e) {
         e.printStackTrace();
      }
     
   }
   
   @Test
   void testInsert() {
      // writer, pwd, email, title, tag, content
	   BoardDTO dto = new BoardDTO().builder()
			   .writer("홍길동")
			   .pwd("1234")
			   .email("hong@naver.com")
			   .title("단위 테스트")
			   .tag(0)
			   .content("단위 테스트")
			   .build();
	   
	   
      try {
    	  int rowCount = this.dao.insert(dto);
    	  if(rowCount == 1) {
            System.out.println("새글 쓰기 성공!!!");
         };
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
   }

   @Test
   void testSearch() {
      
      try {
    	 // ArrayList<BoardDTO> list = this.dao.search();
         // ArrayList<BoardDTO> list = this.dao.search("w","홍길동1");
    	 ArrayList<BoardDTO> list = this.dao.search("w","홍길동1", 2, 5);
         list.forEach(dto->{
            System.out.println(dto);
         });
      } catch (SQLException e) {
         e.printStackTrace();
      }
     
   }
}











