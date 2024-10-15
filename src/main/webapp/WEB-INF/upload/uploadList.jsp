<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	border-collapse: collapse;
}

th, td {
	padding: 5px;
}
</style>
</head>
<body>
<form id="uploadListForm">
<table border="1" frame="hsides" rules="rows">
	<tr>
		<th width="100">
			<input type="checkbox" id="all"> 번호 
		</th>
		<th width="100">번호</th>
		<th width="200">이미지</th>
		<th width="200">상품명</th>
	</tr>
	
	<c:forEach var="userUploadDTO" items="${list }">
		<tr>
			<td align="center">
				<input type="checkbox" name="check" id="check" value="${userUploadDTO.seq}"> ${userUploadDTO.seq}
			</td>
			<td align="center">${userUploadDTO.seq }</td>
			<td align="center">
				<!--  
				<img src="http://localhost:8080/spring/storage/${userUploadDTO.imageOriginalFileName }" 
				     alt="userUploadDTO.imageName" width="50" height="50" />-->
				     
				<!-- Object Storage -->
				<a href="/spring/user/uploadView?seq=${userUploadDTO.seq}">
					<img src="https://kr.object.ncloudstorage.com/bitcamp-9th-bucket-144/storage/${userUploadDTO.imageFileName }" 
					     alt="userUploadDTO.imageName" width="50" height="50" />
				</a>
			</td>
			<td align="center">${userUploadDTO.imageName }</td>
		</tr>
	</c:forEach>
</table>
<div style="margin-top : 5px;">
	<input type="button" value="선택삭제"  id="uploadDeleteBtn">
</div>
</form>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="../js/uploadDelete.js"></script>
</body>
</html>











