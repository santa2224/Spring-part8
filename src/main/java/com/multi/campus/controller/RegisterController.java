package com.multi.campus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;
import com.multi.campus.service.RegisterService;

@Controller
public class RegisterController {
	
	@Autowired
	RegisterService service;
	//로그인폼
	@GetMapping("/loginForm")
	public String login() {
		return "register/loginForm"; //		WEB-INF/views/register/loginForm.jsp
	}
	//로그인(DB)
	@PostMapping("/loginOk")
	public ModelAndView loginOk(String userid, String userpwd, HttpServletRequest request, HttpSession session) {
		// Session 객체 얻어오기
		// 매개변수로 HttpServletRequest reqeust -> Session 구하기
		// 매개변수로 HttpSession session
		
		//System.out.println("userid->"+userid);
		//->post방식 한글 변환 확인
		RegisterDTO dto = service.loginOk(userid, userpwd);
		// dto -> null인 경우 선택레코드가 없다.   --- 로그인실패
		//           null이 아닌경우 선택레코드가 있다. ---- 로그인성공
		
		ModelAndView mav = new ModelAndView();
		
		if(dto!=null) {//로그인성공
			session.setAttribute("logId", dto.getUserid());
			session.setAttribute("logName", dto.getUsername());
			session.setAttribute("logStatus", "Y");
			mav.setViewName("redirect:/");
		}else {//로그인 실패
			mav.setViewName("redirect:loginForm");
		}
		return mav;
	}
	//로그아웃 - 세션제거
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
	//회원가입 폼
	@GetMapping("/join")
	public String join() {
		return "register/join";
	}
	//아이디 중복검사
	@GetMapping("/idCheck")
	public String idCheck(String userid, Model model) {
		//조회
		//아이디의 개수 구하기  0, 1
		int result = service.idCheckCount(userid);
		
		//뷰에서 사용히기 위해 모델에 셋팅
		model.addAttribute("userid", userid);
		model.addAttribute("result", result);
		
		return "register/idCheck";
	}
	//우편번호 검색
	@RequestMapping(value="/zipcodeSearch",method=RequestMethod.GET)
	public ModelAndView zipCodeSearch(String doroname) {
		ModelAndView mav = new ModelAndView();
		
		// 선택한 주소가 없으면 리턴은 null
		List<ZipcodeDTO> zipList = null;
		
		if(doroname!=null) {
			zipList = service.zipSearch(doroname);
		}
		
		mav.addObject("zipList", zipList);
		mav.setViewName("register/zipcodeSearch");
		
		return mav;
	}
	@RequestMapping(value="/joinOk", method=RequestMethod.POST)
	public ModelAndView joinOk(RegisterDTO dto) {
		System.out.println(dto.toString());
		
		ModelAndView mav = new ModelAndView();
		//회원가입
		int result = service.registerInsert(dto);
		
		if(result>0) {//회원가입 성공시 - 로그인폼으로 이동
			mav.setViewName("redirect:loginForm");
		}else { //회원가입 실패시
			mav.addObject("msg","회원등록 실패하였습니다.");
			mav.setViewName("register/joinOkResult");
		}
		return mav;
	}
	//회원정보 수정폼 - session 로그인 아이디에 해당하는 회원정보 select하여 뷰페이지로 이동
	@GetMapping("/joinEdit")
	public ModelAndView joinEdit(HttpSession session) {// request-> session, session
		RegisterDTO dto = service.registerEdit((String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto",dto);
		mav.setViewName("register/joinEdit");
		
		return mav;
	}
	//회원정보 수정(DB) - form의 내용과 session의 로그인 아이디를 회원정보를 수정한다.
	@PostMapping("/joinEditOk")
	public ModelAndView joinEditOk(RegisterDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		int cnt = service.registerEditOk(dto);
		
		ModelAndView mav = new ModelAndView();
		if(cnt>0){// 수정 성공 시 -> db에서 수정된 내용을 보여주고
			mav.setViewName("redirect:joinEdit");
		}else{// 수정 실패 시 -> 이전페이지 (알림)
			mav.addObject("msg","회원정보 수정에 실패하였습니다.");
			mav.setViewName("register/joinOkResult");
		}
		return mav;
	}
}