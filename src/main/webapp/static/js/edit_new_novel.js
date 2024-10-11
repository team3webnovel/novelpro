function deleteNovel(novelId) {
    if (confirm('정말 삭제하시겠습니까?')) {
        fetch(`${contextPath}/novel/delete-novel/${novelId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('삭제되었습니다.');
                window.location.href = '/team3webnovel/storage';  // 삭제 후 목록 페이지로 리다이렉트
            } else {
                alert('삭제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('삭제 중 오류 발생:', error);
            alert('삭제 중 오류가 발생했습니다.');
        });
    }
}
