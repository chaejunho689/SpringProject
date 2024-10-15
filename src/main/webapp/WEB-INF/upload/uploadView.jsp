<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr>
		<td rowspan="3">
			<img 
			src="https://kr.object.ncloudstorage.com/bitcamp-9th-bucket-144/storage/${userUploadDTO.imageFileName}"
			alt="${userUploadDTO.imageName }" />
		</td>
		<td> 번호 : ${userUploadDTO.seq} </td>
	</tr>

	<tr>
		<td style="text-indent : 10px;">
			${userUploadDTO.imageName}	
		</td>
	</tr>
	<tr>
	</tr>
		
	<tr style="text-indent : 10px;">
		<td>
			${userUploadDTO.imageOriginalFileName}
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<pre> ${userUploadDTO.imageContent }</pre>
		</td>
	</tr>
	
</table>
<div style="margin-top: 5px;">
	<input type="button" value="목록" onclick="location.href='/spring/user/uploadList'" />
	<input type="button" value="수정" onclick="location.href='/spring/user/uploadUpdateForm?seq=${userUploadDTO.seq}'" />
	<input type="button" value="삭제" onclick="location.href='/spring/user/uploadDeleteForm?seq=${userUploadDTO.seq}'" />
</div>
</body>
</html>