package days04;
/**
 * @author love
 * @date 2024. 9. 5. 오후 2:10:51
 * @content
 */
public class Ex02 {

	public static void main(String[] args) {
		
		int totalRecord = 136; // 총레코드수
		int currentPage = 1; // 현재페이지
		int numberPerPage = 10; // 한페이지에 10개씩
		
		int totalPages = (int) Math.ceil((double)totalRecord / numberPerPage);
		System.out.println(":"+totalPages);
		
		int start = 1;
		int end = 10;
//		21 30
//		11 20 1,10부터 시작, start + 10, end + 10
//		1 10
		for (int i = 1; i <= totalPages; i++) {
			start = (i-1)*numberPerPage+1; // 페이징처리!
			end = (i-1)*numberPerPage+10;
			if (end > totalRecord)  {
				end = totalRecord;
			}
			
			System.out.printf("%d페이지 : start=%d~end=%d\n",i,start,end);
		}
		

	} // main

} // class
