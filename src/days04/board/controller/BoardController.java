package days04.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.service.BoardService;
import days04.board.vo.PagingVO;

public class BoardController {

	private int selectedNumber ;
	private Scanner scanner = null;
	private BoardService service;

	// 페이징 처리 위한 변수(필드) 선언
	private int currentPage = 1;
	private int numberPerPage = 10; // 한 페이지에 몇개 출력?
	private int numberPageBlock = 10;
	private int totalRecords;


	public BoardController() {
		super();
		this.scanner = new Scanner(System.in);
	}

	// 1. 생성자를 통한 DI
	public BoardController(BoardService service) {
		this();
		this.service = service;
	}

	// 게시판 기능을 사용...
	public void boardStart() {
		while (true) {
			메뉴출력();
			메뉴선택();
			메뉴처리();
		} // while
	}


	private void 메뉴출력() {
		String [] menu = {"새글","목록","보기","수정","삭제","검색","종료"};
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\t", i+1, menu[i]);
		}
		System.out.println();
	}

	private void 메뉴선택() {
		System.out.print("> 메뉴 선택하세요 ? ");
		this.selectedNumber = this.scanner.nextInt();      
		this.scanner.nextLine(); //   \r\n 제거
	}

	private void 메뉴처리() {
		switch (this.selectedNumber) {
		case 1:// 새글   
			새글쓰기();
			break;
		case 2:// 목록
			목록보기();
			break;
		case 3:// 보기
			상세보기();
			break;
		case 4:// 수정
			수정하기();
			break;
		case 5:// 삭제
			삭제하기();
			break;
		case 6:// 검색
			검색하기();
			break;
		case 7:// 종료   
			exit();
			break; 
		} // switch

		일시정지();
	}

	private void 검색하기() {
		System.out.print(
	            "> 검색 조건 : 제목(t) , 내용(c), 작성자(w), 제목+내용(tc) 선택  ? ");
	      String searchCondition = this.scanner.next();
	      System.out.print("> 검색어 입력 ? ");
	      String searchWord = this.scanner.next();
		
		System.out.print("> 현재 페이지번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();

		ArrayList<BoardDTO> list = this.service.searchService(searchCondition, searchWord, this.currentPage,this.numberPerPage);

		// 출력담당객체(View) + list
		System.out.println("\t\t\t  게시판");
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%s\t%-20s\t%s\t%-10s\t%s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-------------------------------------------------------------------------");
		if (list == null) {
			System.out.println("\t\t> 게시글 존재 X");   
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto =  ir.next();
				System.out.printf("%d\t%-40s  %s\t%-10s\t%d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWritedate(),
						dto.getReaded());   
			} // while
		}

		System.out.println("-------------------------------------------------------------------------");      
		// 검색된 내역에 맞게 검색블록 나오게.
		PagingVO paging = new PagingVO(currentPage, numberPerPage, numberPageBlock, searchCondition
				, searchWord);
		System.out.print("\t\t");
		if (paging.prev) System.out.printf(" %s ", "<");
		for (int i = paging.start; i <= paging.end; i++) {
			System.out.printf(i==currentPage?" [%1$d] " : " %1$d ", i );
		}
//		for (int i = paging.start; totalRecords/numberPerPage <= paging.end; i++) {
//			System.out.printf(i==currentPage?"[%1$d]" : "%1$d", i );
//		}
		if (paging.next) System.out.printf(" %s ", ">");
		
		System.out.println("\n-------------------------------------------------------------------------");

	}

	private void 삭제하기() {
		System.out.println("> 삭제할 게시글 번호 입력 ? ");
		long seq = this.scanner.nextLong();
		int rowCount = this.service.deleteService(seq);
		if (rowCount == 1) {
			System.out.println("> 삭제 완료!");
			목록보기();
		}

	}

	private void 수정하기() {
		System.out.println("> 수정할 게시글 번호 입력 ? ");
		int seq = this.scanner.nextInt();

		System.out.print("> 1. 제목 입력 ? ");
		String title = scanner.next();
		System.out.print("> 2. 내용 입력 ? "); 
		String content = scanner.next();
		System.out.print("> 3. 이메일 입력 ? ");
		String email = scanner.next();


		BoardDTO dto = BoardDTO
				.builder()
				.seq(seq)
				.title(title)
				.content(content)
				.email(email)
				.build();


		int rowCount = this.service.alterService(dto);
		if (rowCount == 1) {
			System.out.println("> 수정 완료!");
		}



	}

	private void 상세보기() {
		System.out.print("> 게시글 번호를 입력 ? ");
		int seq = this.currentPage = this.scanner.nextInt();
		// 게시글 가져와서 출력.

		BoardDTO dto = this.service.viewService(seq);


		if (dto == null) {
			System.out.println("> 게시글이 존재하지 않습니다.");
			return;
		}
		// 뷰(View) : 출력 담당 객체
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter() );
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded() );
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle() );
		System.out.println("\tㅁ. 글내용 : " + dto.getContent() );
		System.out.println("\tㅂ. 작성일 : " + dto.getWritedate() );

		System.out.println("\t\n [수정] [삭제] [목록(home)]");

	}

	private void 목록보기() {

		System.out.print("> 현재 페이지번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();

		ArrayList<BoardDTO> list = this.service.selectService(this.currentPage,this.numberPerPage);

		// 출력담당객체(View) + list
		System.out.println("\t\t\t  게시판");
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%s\t%-20s\t%s\t%-10s\t%s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-------------------------------------------------------------------------");
		if (list == null) {
			System.out.println("\t\t> 게시글 존재 X");   
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto =  ir.next();
				System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWritedate(),
						dto.getReaded());   
			} // while
		}

		System.out.println("-------------------------------------------------------------------------");      
		// System.out.println("\t\t[1] 2 3 4 5 6 7 8 9 10 NEXT");
		PagingVO paging = new PagingVO(currentPage, numberPerPage, numberPageBlock);
		
		System.out.print("\t\t");
		if (paging.prev) System.out.printf(" %s ", "<");
		// System.out.println( paging.start + " / " + paging.end);
		for (int i = paging.start; i <= paging.end; i++) {
			System.out.printf(i==currentPage?" [%1$d] " : " %1$d ", i );
		}
		if (paging.next) System.out.printf(" %s ", ">");
		
		System.out.println("\n-------------------------------------------------------------------------");
	}




	private void 새글쓰기() {
		System.out.print("> writer, pwd, email. title, tag, content 입력?");
		String [] datas = this.scanner.nextLine().split("\\s*,\\s*");
		String writer = datas[0];
		String pwd = datas[1];
		String email = datas[2];
		String title = datas[3];
		int tag = Integer.parseInt(datas[4]);
		String content = datas[5];

		BoardDTO dto = new BoardDTO()
				.builder()
				.writer(writer)
				.pwd(pwd)
				.email(email)
				.title(title)
				.tag(tag)
				.content(content)
				.build();
		int rowCount = this.service.insertService(dto);
		if (rowCount == 1) {
			System.out.println("> 새 글 작성 완료!!");
		}



	}

	private void 일시정지() {
		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	// 프로그램 종료
	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	}

}

/*
게시판 테이블 , 시퀀스 생성
BoardDTO
BoardDAO 인터페이스
  ㄴ ArrayList<BoardDTO> select() throws SQLException
BoardDAOImpl 구현클래스
  ㄴ ArrayList<BoardDTO> selectService()
      트랜잭션
       return    this.dao.select();
       문자/메시지 전송
       로그 기록 작업
       커밋

       롤백

단위테스트
  BoardDAOImpTest.java       this.dao.select()
  BoardServiceTest.java      this.service.selectService(); 
 * */

/*
 *    ojdbc6.jar
  1. 
     Class.forName();
     Connection conn = DriverManager.getConnection(url, user, password);
  1-1.Connection conn =  DBConn.getConnection();

  3. CRUD 작업
  String sql = "";
  stmt;
  pstmt;
  cstmt;
  I/U/D   int rowCount =  executeUpdate()
  S       rs =  executeQuery()
  while(rs.next()){

  }      
  4. 
  con.close();    
 * */