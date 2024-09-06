package days04.board.vo;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

/**
 * @author jam
 * @date 오전 11:25:10
 * @subject [jdbc] 
 * @content 
 */
public class PagingVO {

	public int currentPage = 1;
	public int start;
	public int end;
	public boolean prev;
	public boolean next;

	public PagingVO(int currentPage, int numberPerPage, int numberOfPageBlock) {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		try {
			int totalPages = dao.getTotalPages(numberPerPage);

			start = (currentPage-1)/numberOfPageBlock*numberOfPageBlock+1;
			end = start + numberOfPageBlock - 1;
			if( end > totalPages) end = totalPages;

			if( start != 1) this.prev = true;
			if( end != totalPages) this.next = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
