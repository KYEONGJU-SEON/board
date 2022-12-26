package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.FileNameMap;
import java.util.List;
import java.util.UUID;

@Service // service는 controller에서 다시 이용
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;



    //글 작성 처리
    public void write(Board board,MultipartFile file) throws Exception{
        // 저장할 경로 지정
        String projectPath = System.getProperty("user.dir") + "/src/main/webapp/";
        // 파일 클래스 이용해서 빈 껍데기 생성 (경로, 파일이름 지정)
        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤 이름 식별자
        String fileName = uuid + "_" + file.getOriginalFilename(); // 저장될 파일 이름 생성
        File saveFile = new File(projectPath,fileName);
        file.transferTo(saveFile);
        board.setFilename(fileName);
        board.setFilepath("/webapp/" + fileName);
        boardRepository.save(board);
    }


    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSerchList(String searchKeyword,Pageable pageable){
        return  boardRepository.findByTitleContaining(searchKeyword,pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        //optional 받을때는 get
        return boardRepository.findById(id).get();
    }

    // 특정 게시물 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }



}
