$(function() {
    // 아이디 중복 체크
    if ($('#id').val() == '') {
        $('#id').focusout(function() {
            $('#idDiv').empty();
            if ($('#id').val() == '') {
                $('#idDiv').html('먼저 아이디를 입력하세요.');
            }
        });
    } else {
        $.ajax({
            type: 'post',
            url: '/spring/user/getExistId',
            data: 'id=' + $('#id').val(),
            dataType: 'text',
            success: function(data) {
                alert(data);
                
                if (data == 'exist') {
                    $('#idDiv').html('사용 불가능');
                    $('#idDiv').css('color', 'red');
                } else {
                    $('#idDiv').html('사용 가능');
                    $('#idDiv').css('color', 'blue');
                }
            },
            error: function(e) {
                console.log(e);
            }
        });
    }
    
    $('#writeBtn').click(function(){
    	$('#nameDiv').empty();
    	$('#iDiv').empty();
    	$('#pwdDiv').empty();
    	
    	if($('#name').val() == '') {
    		$('#nameDiv').html('이름 입력');
    	}
    	else if($('#id').val() == '') {
    		$('#idDiv').html('아이디 입력');
    	}
    	else if($('#pwd').val() == '') {
    		$('#pwdDiv').html('비밀번호 입력');
    	} else 
    		$.ajax({
	    		type: 'post',
	            url: '/spring/user/write',
	            data: $('#userWriteForm').serialize(),
	            success: function(data) {
	                    $('#idDiv').css('color', 'blue');
	                	alert('회원가입 완료');
						location.href = '/spring/user/list';
	            },
	            error: function(e) {
	                console.log(e);
	            }
    		});
    	
    });
});
