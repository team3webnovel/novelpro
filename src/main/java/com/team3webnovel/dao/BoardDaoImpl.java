package com.team3webnovel.dao;

import com.team3webnovel.vo.BoardVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardDaoImpl implements BoardDao {

    // 게시글 목록을 저장하는 임시 리스트
    private List<BoardVo> posts = new ArrayList<>();

    public BoardDaoImpl() {
        // 초기 데이터
        posts.add(new BoardVo(1, "첫 번째 게시글", "관리자", "2024-09-24"));
        posts.add(new BoardVo(2, "두 번째 게시글", "사용자1", "2024-09-23"));
    }

    @Override
    public List<BoardVo> getAllPosts() {
        return posts;
    }

    @Override
    public BoardVo getPostById(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addPost(BoardVo post) {
        posts.add(post);
    }
}
