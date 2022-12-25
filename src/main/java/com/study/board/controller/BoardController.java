package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    //@는 어노테이션! 컨트롤러라는것을 알 수 있게 어노테이션을 적어줌!

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8090/board/write
    // GetMapiing :  어떤 url로 접근할거야?
    public String boardwriteForm(){
        return "boardwrite";
    }


    @PostMapping("/board/writepro") //form 태그 url과 동일해야한다
    public String boardWritePro(Board board){
        boardService.write(board);
        return "";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model) {
        model.addAttribute("list",boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(){
        return "boardview";
    }
    }