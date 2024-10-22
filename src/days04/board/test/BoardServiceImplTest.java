package days04.board.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

class BoardServiceImplTest {
	
	Connection conn = null;
	BoardDAO dao = null;
	BoardService service = null;
	
	public BoardServiceImplTest() {
		this.conn = DBConn.getConnection();
		this.dao = new BoardDAOImpl(this.conn);
		this.service = new BoardService(this.dao);
	}
	
	@Test
	void testSelectService() {

			ArrayList<BoardDTO> list = this.service.selectService(1,10);
			list.forEach(dto->{
				System.out.println(dto);
			});
		
	
		
		
	}

}
