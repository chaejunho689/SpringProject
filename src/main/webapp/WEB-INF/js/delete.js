$(function() {
	$('#deleteBtn').click(function() {
		$('#pwdDiv').empty();

		if ($('#pwd').val() == '') { // ID 셀렉터 수정
			alert('먼저 비밀번호를 입력하세요.');
		} else {
			$.ajax({
				type: 'post',
				url: '/spring/user/getExisPwd',
				data: 'id=' + $('#id').val(),
				dataType: 'json',
				success: function(data) { // success 콜백에서 data를 인자로 받아야 함
					alert(JSON.stringify(data)); // stringfy -> stringify 수정

					if (data.pwd == $('#pwd').val()) { // 값을 비교할 때 .val() 사용
						$.ajax({
							type: 'post',
							url: '/spring/user/delete',
							data: 'id=' + $('#id').val(),
							dataType: 'json',
							success: function() {
								// 성공 시 추가 작업이 있다면 여기 작성
							},
							error: function(e) {
								console.log(e);
							}
						});

					} else {
						$('#pwdDiv').html('비밀번호를 잘못 입력했습니다.');
					}

					location.href = '/spring/user/list?pg=1'; // 페이지 이동 코드
				},
				error: function(e) {
					console.log(e);
				}
			});
		}
	});
});
