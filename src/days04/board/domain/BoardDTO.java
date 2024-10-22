package days04.board.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 게터세터 줄필요없이 한번에 data로 해결.
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDTO {
	// 컬럼명 가져와서, private 타입명 컬럼명; 으로 변경
	  private long seq;
	  private String writer;  
	  private String pwd;
	  private String email;
	  private String title;
	  private Date writedate;
	  private int readed;
	  private int tag;
	  private String content;
	  
}
