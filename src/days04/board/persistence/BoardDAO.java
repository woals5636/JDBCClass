package days04.board.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.board.domain.BoardDTO;

public interface BoardDAO {
	
	// 1. 페이징 처리 안된 게시글 목록(최신것 10개만)
	ArrayList<BoardDTO> select() throws SQLException; // 주방장을 호출해 데이터를 떠넘긴다. (이 안에서 처리 x)
	
	// 1-2. 페이징 처리 O 게시글 목록(최신것 10개만)
	ArrayList<BoardDTO> select(int currentPage,int numberPerPage) throws SQLException; // 주방장을 호출해 데이터를 떠넘긴다. (이 안에서 처리 x)
	
	// 1-3. 총레코드수
	int getTotalRecords() throws SQLException;
	
	// 1-4. 총페이지수
	int getTotalPages(int numberPerPage) throws SQLException;
	
	// 2. 게시글쓰기(새글)
	int insert(BoardDTO dto) throws SQLException; // 왜 dto인가?! 
	
	// 3. 조회수 증가
	int increasReaded(long seq) throws SQLException;

	// 3-2. 게시글 상세보기
	BoardDTO view(long seq) throws SQLException;
	
	// 4. 게시글 삭제
	int delete(long seq) throws SQLException;
	
	// 5. 게시글 수정
	int alter(BoardDTO dto) throws SQLException;
	
	// 6. 게시물 검색 - 페이징 X
	ArrayList<BoardDTO> search(String searchCondition, String searchWord) throws SQLException; 
	
	// 6-2 게시물 검색 - 페이징 O
	ArrayList<BoardDTO> search (String searchCondition, String searchWord, int currentPage, int numberPerPage) 
			throws SQLException;
	
	// 검색할 때의 총 페이지 수
	int getTotalPages(int numberPerPage, String searchCondition, String searchWord) throws SQLException;
	
	

}
