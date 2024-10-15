$(function(){

	$('#all').click(function(){
		if($(this).prop('checked'))
			$('input[name="check"]').prop('checked', true);
		else
			$('input[name="check"]').prop('checked', false);
	});
	
	$('input[name="check"]').click(function(){
		
		console.log('개수 = ' + $('input[name="check"]').length); //체크 박스의 갯수
		console.log('개수 = ' + $('input[name="check"]:checked').length); //체크된 체크 박스의 갯수
	
		$('#all').prop('checked', $('input[name="check"]:checked').length);
	});
	
	$('#uploadDeleteBtn').click(function(){
		$.ajax({
			type: 'post',
			url : '/spring/user/uploadDelete',
			data : $('#uploadListForm').serialize(), // 체크된 항목만 서버로 전송
			success:function(){
				alert('이미지 삭제 완료');
				// location.href='/spring/user/uploadList';
			},
			error : function(e) {
				console.log(e);
			}
		});
	});
});