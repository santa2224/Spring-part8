<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 

<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="inc/style.css" type="text/css"/>
	<script type="text/javascript" src="inc/script.js"></script>
</head>
<body>

	<h1>
		Hello world!  
	</h1>

	<div class="desc">
		java 외부파일 script.js<br/>
		css 외부파일 style.css<br/>
		이미지 파일 
	</div>
	<img src="img/iu.jpg" onclick="test()"/>
	
	<div>
		번호 : ${num }<br/>
		이름 : ${name }
	</div>
</body>
</html>

 -->
 
<div class="container"> 
 	<h1>
		Hello world!  
	</h1>

	<div class="desc">
		java 외부파일 script.js<br/>
		css 외부파일 style.css<br/>
		이미지 파일 
	</div>
	<img src="img/iu.jpg" onclick="test()"/>
	
	<div>
		번호 : ${num }<br/>
		이름 : ${name }
	</div>
	<div>
		<h2>사진업로드 구현</h2>
		<pre>
			pom.xml에 프레임워크 추가
			1. commons-fileupload
			2. commons-io
			
			환경설정파일(mvc-context.xml)에 MultipartResolver객체 추가
			
			파일업로드 위치를 생성
			1. webapp하위에 uploadfile폴더를 생성
			2. servlet-context.xml에 폴더를 등록한다.
		</pre>
	</div>
</div>