<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	#logFrm input{margin-bottom:50px 0; width:90%}
</style>
<div class="container">
	<h1>로그인</h1>
	<form method="post" action="loginOk" id="logFrm">
		<ul>
			<li>아이디</li>
			<li><input type="text" name="userid" id="userid"/></li>
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd"/></li>
			<li><input type="submit" value="LOGIN"/></li>
		</ul>
	</form>
	<div style="background:gray; margin:50px;padding:50px;text-align:center;">
		<a href="join">회원가입</a>
		
		<!-- 숙제 -->
		<a href="">아이디찾기</a>
		<a href="">비밀번호찾기</a>
	</div>
</div>