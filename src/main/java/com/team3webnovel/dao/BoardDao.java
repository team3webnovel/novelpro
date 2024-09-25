package com.team3webnovel.dao;

import com.team3webnovel.vo.BoardVo;
import java.util.List;

public interface BoardDao {
    // 게시글 목록 가져오기
    List<BoardVo> getAllPosts();
    
    // 게시글 ID로 단일 게시글 가져오기
    BoardVo getPostById(int id);

    // 게시글 추가
    void addPost(BoardVo post);
}
