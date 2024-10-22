package days04;

public class Ex04 {

	public static void main(String[] args) {
		// 페이징블럭 만들기!! : [1] 2 3 4 5 6 7 8 9 10 NEXT
		//					   <                         >
		
		//int currentPage = 1; // 현재 페이지
		int numberPageBlock = 10; // 아래에 뜨는 숫자출력.
		int totalRecords = 151; // 총 게시글 수
		int numberPerPage = 10; // 한 페이지에 몇개?
		int totalPages = (int) Math.ceil((double)151/10); // 16page!
		
		int start = 1, end = 10;
		
		for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
			start = (currentPage-1)/numberPageBlock*numberPageBlock+1;
			end = start + numberPageBlock - 1;
			if (end > totalPages ) {
				end = totalPages;
			} 
			System.out.printf("%d \t", currentPage);
			if (start != 1) {
				System.out.print("prev(<) ");
			}
			for (int i = start; i <= end; i++) {
				System.out.printf(i==currentPage?"[%1$d]" : "%1$d", i );
			}
			if (end != totalPages) {
				System.out.print(" next(>) ");
			}
			System.out.println();
		}
		
		

	} // main

} // class
