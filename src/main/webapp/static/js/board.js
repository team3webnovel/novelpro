function goToPage(boardId, currentPage) {
    const url = `/team3webnovel/board/view/${boardId}?page=${currentPage}`;
    window.location.href = url;
}