package org.doit.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDeptSalgradeVo {
	
//	private DeptVO dvo;
//	private EmpVO evo;
	
	
	
	private int empno;
	private String ename;
	private LocalDateTime hiredate;
	private double pay;
	private String dname;
	private int grade;

} // class
