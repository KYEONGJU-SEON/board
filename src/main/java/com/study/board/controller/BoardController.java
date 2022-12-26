package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
        boardService.write(board,file);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null ;
        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSerchList(searchKeyword,pageable);
        }

        int nowPage = list.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage -4,1);
        int endPage = Math.min(nowPage +5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8090/board/view?id=1;
    // 넘겨줄때는 Model
    public String boardView(Model model, Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
        public String boardDelete(Integer id){
            boardService.boardDelete(id);
            return "redirect:/board/list";
        }

    @GetMapping("/board/modify/{id}")
        public String boardModify(@PathVariable("id") Integer id,Model model){
        model.addAttribute("board",boardService.boardView(id));
            return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,Board board, Model model,MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id); // 기존의 글이 담겨져서 옴
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp,file);

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }
    }