package com.multi.campus.controller;



import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;
import com.multi.campus.service.BoardService;

// Controller : 뷰단언어를 사용할 수 없다. 스크립트가 필요하면  jsp파일을 생성하여 구현한다.
// RestController : 프론트 언어를 백엔드에서 기술할 수 있는 기능을 제공한다.
//                       반환형을 String으로 하면 뷰페이지 파일명이 아닌 컨텐츠 내용으로 처리한다.
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;
	//게시판목록
	@GetMapping("/boardList") //   /board/boardList
	public ModelAndView boardList(PagingVO vo) {
		
		ModelAndView mav = new ModelAndView();
		
		//총레코드 수를 구하여
		vo.setTotalRecord(service.totalRecord(vo));
		
		System.out.println(vo.toString());
		//DB조회
		//해당페이지 레코드 선택하기
		mav.addObject("list",service.pageSelect(vo));
		
		mav.addObject("vo",vo);//뷰페이지로 페이지 정보 셋팅
		mav.setViewName("board/boardList");
		return mav;
	}
	//글쓰기
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		
		return mav;
	}
	//글쓰기(DB등록)
	@PostMapping("/boardWriteOk")
	//							  form              ip(request), 글쓴이(session)
	public ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		dto.setIp(request.getRemoteAddr());// ip
		dto.setUserid((String)request.getSession().getAttribute("logId"));//로그인한 아이디 구하기
		//글등록 시 예외(실패)가 발생
		String htmlTag ="<script>";
		try {
			int result = service.boardInsert(dto);
			htmlTag += "location.href='boardList';";
		}catch(Exception e) {
			htmlTag += "alert('글이 등록되지 않았습니다.');";
			htmlTag += "history.back();";
			e.printStackTrace();
		}
		htmlTag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		//										내용
		return new ResponseEntity<String>(htmlTag, headers, HttpStatus.OK);
	}
	//글내용 보기
	@GetMapping("/boardView")    //dto
	public ModelAndView boardView(int no, PagingVO vo ) {
		
		//조회수 증가
		service.boardHitCount(no);
		
		BoardDTO dto = service.boardSelect(no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto",dto); //선택한 레코드
		mav.addObject("vo", vo); //페이지번호, 검색어, 검색키
		
		mav.setViewName("board/boardView");
		
		return mav;
	}
	//수정폼
	@GetMapping("/boardEdit")
	public ModelAndView boardEdit(int no,PagingVO vo) {
		BoardDTO dto =service.boardEditSelect(no);
		
		// "  "     '   '  제목에 따옴표가 들어갈때 추가필요
		String subject = dto.getSubject().replaceAll("\"","&quot;"); // "   
		dto.getSubject().replaceAll("'","&#39;");  // '
		dto.setSubject(subject);
				
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto",dto);
		mav.addObject("vo",vo);
		
		mav.setViewName("board/boardEdit");
		
		return mav;
	}
	//수정등록
	@PostMapping("/boardEditOk")
	public ResponseEntity<String> boardEditOk(BoardDTO dto, PagingVO vo, HttpSession session) {
		// no레코드 번호, 로그인 아이디가 같을 때 업데이트
		dto.setUserid((String)session.getAttribute("logId"));
		String bodyTag = "<script>";
		try {
			service.boardUpdate(dto);
			// location.href='no=12&nowPage=2';
			// location.href='no=12&nowPage=2&searchKey=subject&searchWord=폭스';
			bodyTag += "location.href='boardView?no="+dto.getNo()+"&nowPage="+vo.getNowPage();
			if(vo.getSearchWord()!=null) {//검색어가 있을때
				bodyTag += "&searchKey="+vo.getSearchKey()+"&searchWord="+vo.getSearchWord();
			}//검색어가 없을때
			bodyTag +="';";	
			
			
		}catch(Exception e) {
			e.printStackTrace();
			//수정 실패했을때
			bodyTag += "alert('게시판 글수정 실패하였습니다.');";
			bodyTag += "history.back();";
		}
		bodyTag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","html",Charset.forName("UTF-8")));
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		ResponseEntity<String> entity = new ResponseEntity<String>(bodyTag, headers, HttpStatus.OK);
	
		return entity;
	}
	//삭제
	@GetMapping("/boardDel")
	public ModelAndView boardDel(BoardDTO dto, PagingVO vo, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		int result = service.boardDelete(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("nowPage", vo.getNowPage());
		if(vo.getSearchWord()!=null) {
			mav.addObject("searchKey",vo.getSearchKey());
			mav.addObject("searchWord",vo.getSearchWord());
		}
		if(result>0) {//삭제시 리스트로 이동
			mav.setViewName("redirect:boardList");
		}else {//삭제 실패시 글내용보기로 이동
			mav.addObject("no",dto.getNo());
			mav.setViewName("redirect:boardView");
		}
		
		return mav;
	}
}
