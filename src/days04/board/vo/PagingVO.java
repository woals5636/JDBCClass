package days04.board.vo;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

public class PagingVO {

	public int currentPage = 1;
	public int start ;
	public int end;
	public boolean prev;
	public boolean next;
	public int numberPageBlock;
	public int totalRecords;

	// private int totalPages = (int) Math.ceil((double)151/10);

	public PagingVO(int currentPage, int numberPerPage, int numberPageBlock) {
		Connection conn= DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);

		try {
			int totalPages = dao.getTotalPages(numberPerPage);
			start = (currentPage-1)/numberPerPage*numberPageBlock+1;
			end = start + numberPageBlock - 1;
			if (end > totalPages ) {
				end = totalPages;
			} 
			//System.out.printf("%d \t", currentPage);
			if (start != 1) this.prev = true;		
			if (end != totalPages) this.next = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	public PagingVO(int currentPage, int numberPerPage, int numberPageBlock, String searchCondition,
			String searchWord) {
		Connection conn= DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);

		try {
			int totalPages = dao.getTotalPages(numberPerPage,searchCondition,searchWord);
			start = (currentPage-1)/numberPerPage*numberPageBlock+1;
			end = start + numberPageBlock - 1;
			if (end > totalPages ) {
				end = totalPages;
			} 
			//System.out.printf("%d \t", currentPage);
			if (start != 1) this.prev = true;		
			if (end != totalPages) this.next = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

} // main
