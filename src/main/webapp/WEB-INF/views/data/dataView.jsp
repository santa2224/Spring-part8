<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.dataView a:link, .dataView a:visited, .dataView a:hover{
		color:#000;
	}
	.dataView li{
		padding: 5px;
		border-bottom:1px solid #ddd;
	}
</style>
<script>
	function dataDelCheck(){
		if(confirm("자료실글을 삭제하시겠습니까?")){
			location.href="/campus/data/dataDelete?no=${dto.no}";
		}
	}
</script>
<div class="container">
	<h1>자료실 글내용보기</h1>
	 <ul class="dataView">
	 	<li>번호 : ${dto.no}</li>
	 	<li>작성자 : ${dto.userid}</li>
	 	<li>조회수 : ${dto.hit }</li>
	 	<li>작성일 : ${dto.writedate }</li>
	 	<li>첨부파일 : 
	 		<c:forEach var="fileDto" items="${fileList }">
	 			<a href="/campus/uploadfile/${fileDto.filename }" download>${fileDto.filename}</a>
	 		</c:forEach>
	 	</li>
	 	<li>제목</li>
	 	<li>${dto.subject }</li>
	 	<li>글내용</li>
	 	<li>${dto.content }</li>
	 </ul>
	 <div style="background:olive">
	 		<!-- 로그인 아이디와 글쓴이가 같을때 수정이 가능하다. -->
	 		<c:if test="${logId==dto.userid }">
			 	<a href="/campus/data/dataEdit/${dto.no}">수정</a>
			 	<a href="javascript:dataDelCheck()">삭제</a>
		 	</c:if>
	 </div>
</div>