package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardDAO {
	//글등록
	public int boardInsert(BoardDTO dto);
	
	//총 레코드 수
	public int totalRecord(PagingVO vo);
	
	//해당페이지 선택
	public List<BoardDTO> pageSelect(PagingVO vo);
	
	//글선택(no)
	public BoardDTO boardSelect(int no);
	
	//글선택(수정)
	public BoardDTO boardEditSelect(int no);
	
	//글수정(DB)
	public int boardUpdate(BoardDTO dto);
	
	//조회수 증가
	public void boardHitCount(int no);
	
	//글삭제
	public int boardDelete(BoardDTO dto);
}
