package days04;
/**
 * @author love
 * @date 2024. 9. 6. 오전 9:07:29
 * @content
 */
public class Ex03 {

	public static void main(String[] args) {
		
		/* [ 글 상세보기 ]
		 * - 게시글을 클릭하면 글 내용이 나오도록 출력한다.
		 * DTO 자바파일에 글 상세보기를 선언한다.
		 * 필요한 쿼리를 디벨로퍼로 만든 후, BoardService 파일 String sql = "";에 붙여넣는다.
		 * DAO 에서 글 상세보기를 생성한다. 
		 * system.out.print("> 게시글 번호 선택 ? ");
		 * 글 내용에 필요한 변수들을 선언한다. int seq, String content, String title, 등등 모든것들...
		 * prepareStament를 사용한다. get.으로 변수들을 가져온다.
		 * ResultSet을 사용하여 rs.
		 * 용도에 맞게 executeQuery / Update를 사용하여 쿼리를 내보낸다.
		 * try / catch 사용, 
		 * 
		 * Ex01에 BoardController
		 *        boardStart()
		 *        	ㄴ 메뉴출력()
		 *        	ㄴ 메뉴선택()
		 *        	ㄴ 메뉴처리()
		 *        		ㄴ 상세보기() -> 	BoardService의 viewService(seq 10) -> BoardDAOImlp의 
		 *        											 트랜잭션 처리
		 *                 보려는 글번호 10?                 1) 조회수증가
		 *                                                   2) 게시글정보
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * **/
		
		

	}

}
