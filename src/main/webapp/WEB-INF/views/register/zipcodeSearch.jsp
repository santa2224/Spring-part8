<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	header,footer{
		display:none
	}
	#zipcodeList>li:hover{
		cursor:pointer;
		background-color:#ddd;
	}
</style>
<script>
	$(function(){
		//사용할 주소를 클릭하면 메인 회원페이지에 표시하기
		$(document).on('click','#zipcodeList>li',function(){
			var address = $(this).text();
			//alert(address);
			var zip = address.substring(0, 5);
			var addr = address.substring(6);
			
			opener.document.getElementById("zipcode").value=zip;
			//opener.document.getElementById("zipcode").value = zip;
			opener.document.getElementById("addr").value=addr;
			
			self.close(); //window.close();
		});
	});
</script>
<div class="container">
	<div>
		도로명을 입력 후 우편번호를 검색하세요<br>
		(예:백범로22길)<br>
		<form method="get" action="zipcodeSearch" id="zipSearchForm">
			도로명 : <input type="text" name="doroname" id="doroname"/>
			<input type="submit" value="우편번호검색하기">
		</form>
	</div>
	<hr>
	<div>
		<ul id="zipcodeList">
			<!-- 검색한 주소가 있을때 -->
			<c:if test="${zipList!=null }">
				<c:forEach var="zipDTO" items="${zipList }">
					<li>${zipDTO.zipcode } ${zipDTO.sido } ${zipDTO.doroname } ${zipDTO.bungi1}<c:if test="${zipDTO.bungi2>0}">-${zipDTO.bungi2}</c:if>,(${zipDTO.dong }<c:if test="${zipDTO.buildname!=null}">, ${zipDTO.buildname}</c:if>)</li>
				</c:forEach>
			</c:if>
			<!-- 검색한 주소가 없을때 -->
			<c:if test="${zipList==null }">
				<li>
					검색한 주소가 없습니다.
				</li>
			</c:if>
		</ul>
	</div>
</div>