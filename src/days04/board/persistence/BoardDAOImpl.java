package days04.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// db연동해주는 클래스
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.doit.domain.DeptVO;

import days04.board.domain.BoardDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class BoardDAOImpl implements BoardDAO {

	private Connection conn = null; // = DBConn.getConnection(); -> 결합력 높아져서 안좋은 코딩이 됨!! 밖에서 주입하자.
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자 통해 의존성주입(DI) 가능
	public BoardDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	// Setter DI 가능
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() {
		return conn;
	}

	@Override
	public ArrayList<BoardDTO> select() throws SQLException {
		// BoardDAOImpl을 싱글톤으로 만들어서 다른사람과 같이 써보자.
		long seq;
		String writer, email, title;
		Date writedate;
		int readed;

		ArrayList<BoardDTO> list = null;

		String sql = "select seq, writer, email, title, writedate, readed " + "from tbl_cstVSBoard "
				+ "order by seq DESC";

		// 부서조회 시작
		BoardDTO dto = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder() // @NoArgsConstructor
							.seq(seq).writedate(writedate).email(email).title(title).writedate(writedate).readed(readed)
							.build();
					list.add(dto);
				} while (rs.next());

			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 부서조회 끝

		return list;
	}

	@Override
	public ArrayList<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException {

		// BoardDAOImpl을 싱글톤으로 만들어서 다른사람과 같이 써보자.
		long seq;
		String writer, email, title;
		Date writedate;
		int readed;

		ArrayList<BoardDTO> list = null;

		String sql = "select b.* " + "from ( " + "    select rownum no, t.* " + "        from( "
				+ "        select seq, writer, email, title, writedate, readed " + "        from tbl_cstVSBoard "
				+ "        order by seq DESC " + "    ) t " + ") b " + "where no BETWEEN ? AND ?";
		// 컨트롤+F = \r\n 입력후 아래칸에 공백 쓰면 전체 없어짐!

		// 부서조회 시작
		BoardDTO dto = null;

		int start = (currentPage - 1) * numberPerPage + 1;
		int end = start + numberPerPage - 1;
		int totalRecords = getTotalRecords();
		if (end > totalRecords) end = totalRecords;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder() // @NoArgsConstructor
									.seq(seq)
									.writer(writer)
									.email(email)
									.title(title)
									.writedate(writedate)
									.readed(readed)
									.build();
					list.add(dto);
				} while (rs.next());

			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 부서조회 끝

		return list;
	}

	@Override
	public int getTotalRecords() throws SQLException {
		int totalRecords = 0;
		String sql = "SELECT COUNT(*) " + "FROM tbl_cstvsboard";
		this.pstmt = this.conn.prepareStatement(sql);
		this.rs = this.pstmt.executeQuery();
		if (this.rs.next())
			totalRecords = rs.getInt(1);
		this.rs.close();
		this.pstmt.close();
		return totalRecords;
	}

	@Override
	public int getTotalPages(int numberPerPage) throws SQLException {
		int totalPages = 0;
		String sql = "SELECT CEIL(COUNT(*)/?) " + "FROM tbl_cstvsboard";
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, numberPerPage);
		this.rs = this.pstmt.executeQuery();
		if (this.rs.next())
			totalPages = rs.getInt(1);
		
		this.rs.close();
		this.pstmt.close();
		return totalPages;
	}

	@Override
	public int insert(BoardDTO dto) throws SQLException {
		String sql = "INSERT INTO tbl_cstvsboard "
	               + " (seq, writer, pwd, email, title, tag, content ) "
	               + "VALUES "
	               + " (seq_tblcstvsboard.NEXTVAL, ?, ?, ?, ?, ?, ? ) ";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, dto.getWriter());
		this.pstmt.setString(2, dto.getPwd());
		this.pstmt.setString(3, dto.getEmail());
		this.pstmt.setString(4, dto.getTitle());
		this.pstmt.setInt(5, dto.getTag());
		this.pstmt.setString(6, dto.getContent());
		rowCount = this.pstmt.executeUpdate();
		return rowCount;
	}
	
	//
	

	@Override
	public int increasReaded(long seq) throws SQLException {
		String sql = "update tbl_cstvsboard "
	               + " set readed = readed +1 "
	               + "where seq = ? ";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();
		return rowCount;
	}

	
	// 상세보기
	@Override
	public BoardDTO view(long seq) throws SQLException {
		String writer, email, title;
		Date writedate;
		int readed;
		String content;

		String sql = "select seq, writer, email, title, writedate, readed, content " 
				+ "from tbl_cstVSBoard "
				+ "where seq = ?";

		// 부서조회 시작
		BoardDTO dto = null;

		try {
			pstmt = conn.prepareStatement(sql);
			this.pstmt.setLong(1, seq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					content = rs.getString("content");
					dto = new BoardDTO().builder() // @NoArgsConstructor
							.seq(seq)
							.writer(writer)
							.email(email)
							.title(title)
							.writedate(writedate)
							.readed(readed)
							.content(content)
							.build();
				
				

			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 부서조회 끝

		return dto;
	}
	
	// 글 삭제
	@Override
	public int delete(long seq) throws SQLException {
		String sql = "delete from tbl_cstVSBoard "
				+ "where seq = ?";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();
		return rowCount;
	}

	// 글 수정
	@Override
	public int alter(BoardDTO dto) throws SQLException {
			String sql = "update tbl_cstvsboard "
					+ "	set title = ?, content =?, email = ? "
					+ "where seq = ?";
			int rowCount = 0;
			this.pstmt = this.conn.prepareStatement(sql);
				this.pstmt.setString(1, dto.getTitle());
				this.pstmt.setString(2, dto.getContent());
				this.pstmt.setString(3, dto.getEmail());
				this.pstmt.setLong(4, dto.getSeq());
				rowCount = this.pstmt.executeUpdate();
		
			// 부서조회 끝

			return rowCount;
		}

	@Override
	public ArrayList<BoardDTO> search(String searchCondition, String searchWord) throws SQLException {
		long seq;
		String writer, email, title;
		Date writedate;
		int readed;

		ArrayList<BoardDTO> list = null;

		String sql = "select seq, writer, email, title, writedate, readed " 
				+ "from tbl_cstVSBoard ";
			// 검색조건에 맞는 where절 추가하는 코딩 St
			switch (searchCondition) {
			case "t":
				sql += " WHERE REGEXP_LIKE(title, ?, 'i')";
				break;
			case "w":
				sql += " WHERE REGEXP_LIKE(writer, ?, 'i')";
				break;
			case "c":
				sql += " WHERE REGEXP_LIKE(content, ?, 'i')";
				break;
			case "tc":
				sql += " WHERE REGEXP_LIKE(title, ?, 'i') OR  WHERE REGEXP_LIKE(content, ?, 'i')";
				break;

			} 
			// 검색조건에 맞는 where절 추가하는 코딩 End
				sql += " order by seq DESC";
				System.out.println(sql);
		// 부서조회 시작
		BoardDTO dto = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			// tc
			if (searchWord.equals("tc")) {
				pstmt.setString(2, searchWord);
				
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder() // @NoArgsConstructor
							.seq(seq).writer(writer).email(email).title(title).writedate(writedate).readed(readed)
							.build();
					list.add(dto);
				} while (rs.next());

			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 부서조회 끝

		return list;
	}
	
	@Override
	public ArrayList<BoardDTO> search (String searchCondition, String searchWord, int currentPage, int numberPerPage)
			throws SQLException {

		// BoardDAOImpl을 싱글톤으로 만들어서 다른사람과 같이 써보자.
		long seq;
		String writer, email, title;
		Date writedate;
		int readed;

		ArrayList<BoardDTO> list = null;

		String sql = "select b.* " + "from ( " + "    select rownum no, t.* " + "        from( "
				+ "        select seq, writer, email, title, writedate, readed " + "        from tbl_cstVSBoard ";
					// 검색조건에 맞는 where절 추가하는 코딩 St
					switch (searchCondition) {
					case "t":
						sql += " WHERE REGEXP_LIKE(title, ?, 'i')";
						break;
					case "w":
						sql += " WHERE REGEXP_LIKE(writer, ?, 'i')";
						break;
					case "c":
						sql += " WHERE REGEXP_LIKE(content, ?, 'i')";
						break;
					case "tc":
						sql += " WHERE REGEXP_LIKE(title, ?, 'i') OR  WHERE REGEXP_LIKE(content, ?, 'i')";
						break;

					} 
					// 검색조건에 맞는 where절 추가하는 코딩 End
				
						sql += " order by seq DESC " + "    ) t " 
						+ ") b " 
								+ "where no BETWEEN ? AND ?";
		// 컨트롤+F = \r\n 입력후 아래칸에 공백 쓰면 전체 없어짐!

		// 부서조회 시작
		BoardDTO dto = null;

		int start = (currentPage - 1) * numberPerPage + 1;
		int end = start + numberPerPage - 1;
		int totalRecords = getTotalRecords();
		if (end > totalRecords) end = totalRecords;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			//if
			if (searchWord.equals("tc")) {
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, start);
				pstmt.setInt(4, end);
				
			}else {
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					dto = new BoardDTO().builder() // @NoArgsConstructor
									.seq(seq)
									.writer(writer)
									.email(email)
									.title(title)
									.writedate(writedate)
									.readed(readed)
									.build();
					list.add(dto);
				} while (rs.next());

			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 부서조회 끝

		return list;
	}

	@Override
	public int getTotalPages(int numberPerPage, String searchCondition, String searchWord) throws SQLException {
		int totalPages = 0;
		String sql = "SELECT CEIL(COUNT(*)/?) " 
				   + "FROM tbl_cstvsboard";
		// 검색조건에 맞는 where절 추가하는 코딩 St
		switch (searchCondition) {
		case "t":
			sql += " WHERE REGEXP_LIKE(title, ?, 'i')";
			break;
		case "w":
			sql += " WHERE REGEXP_LIKE(writer, ?, 'i')";
			break;
		case "c":
			sql += " WHERE REGEXP_LIKE(content, ?, 'i')";
			break;
		case "tc":
			sql += " WHERE REGEXP_LIKE(title, ?, 'i') OR  WHERE REGEXP_LIKE(content, ?, 'i')";
			break;

		} 
		// 검색조건에 맞는 where절 추가하는 코딩 End
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, numberPerPage);
		this.pstmt.setString(2, searchWord);
		if (searchWord == "tc") {
			this.pstmt.setString(3, searchWord);
		}
		
		this.rs = this.pstmt.executeQuery();
		if (this.rs.next())
			totalPages = rs.getInt(1);
		
		this.rs.close();
		this.pstmt.close();
		return totalPages;
	}



}
