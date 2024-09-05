package days04.board.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.board.domain.BoardDTO;

public interface BoardDAO {
	
	
	// 1. 페이징 처리 X + 게시글 목록
	ArrayList<BoardDTO> select() throws SQLException;
	
	// 1-2. 페이징 처리 O + 게시글 목록
	ArrayList<BoardDTO> select(int currentPage, int numberPerPage ) throws SQLException;
	
	// 1-3. 총 레코드수
	int getTotalRecords() throws SQLException;
	
	// 1-4. 총 페이지수
	int getTotalPages( int numberPerPage ) throws SQLException;
	
	// 2. 게시글 쓰기(새글)
	int insert(BoardDTO dto) throws SQLException;

	// 3. 조회수 증가
	int increaseReaded(long seq) throws SQLException;
	// 3-2. 상세보기
	BoardDTO view(long seq) throws SQLException;

	// 4. 게시글 삭제
	int delete(long seq) throws SQLException;

	// 5. 게시글 수정 - 제목, 내용, 이메일
	int update(BoardDTO dto) throws SQLException;
	
	
} // interface
