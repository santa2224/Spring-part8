package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterDAO {
	//추상메소드
	public RegisterDTO loginOk(String userid, String userpwd);
	
	//아이디중복검사 - 아이디의 개수를 구한다.
	public int idCheckCount(String userid);
	
	//도로명검색
	public List<ZipcodeDTO> zipSearch(String doroname);
	
	//회원가입
	public int registerInsert(RegisterDTO dto);
	
	//회원정보 수정 폼
	public RegisterDTO registerEdit(String userid);
	
	//회원정보 수정 DB update
	public int registerEditOk(RegisterDTO dto);
}
