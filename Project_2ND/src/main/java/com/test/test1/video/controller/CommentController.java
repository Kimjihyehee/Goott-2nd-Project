package com.test.test1.video.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.test.test1.user.service.UserService;
import com.test.test1.video.dto.CommentDto;
import com.test.test1.video.dto.VideoDto;
import com.test.test1.video.service.CommentService;
import com.test.test1.video.service.VideoService;

@Controller
@RequestMapping("comt/**")
public class CommentController {
	 
	@Autowired
	CommentService commentService;	
	@Autowired
	UserService userService;
	@Autowired
	VideoService videoService;
	
//	원댓글 작성하기 02.20 장민실
	@RequestMapping(value="write", method=RequestMethod.POST)
	public ModelAndView insert(VideoDto v_dto, CommentDto c_dto, HttpSession session, ModelAndView mv) {
		String user_id = session.getAttribute("user_id").toString();
		String nickname = session.getAttribute("nickname").toString();
		int id = userService.getid(user_id);
		
//		원댓글 등록할때 pid값 자동증가를 위해 DB의 마지막 pid값 가져오기 start 02.23 장민실
		int pid = commentService.get_pid(c_dto);
		pid+=1;
		c_dto.setPid(pid);
//		원댓글 등록할때 pid값 자동증가를 위해 DB의 마지막 pid값 가져오기 end
		
		c_dto.setUser_id(id);
		c_dto.setNickname(nickname);	
		
//		CommentDto 자체에서 video_id를 받아오지 못해 VideoDto에서 video_id 받아와서 값 넣어주기 start 02.21 장민실
		int video_id = v_dto.getVideo_id();
		c_dto.setVideo_id(video_id);
//		CommentDto 자체에서 video_id를 받아오지 못해 VideoDto에서 video_id 받아와서 값 넣어주기 end
		
		commentService.insert(c_dto);
		mv.addObject("c_dto", c_dto);
		mv.setViewName("redirect:/video/detail?video_id="+video_id);
		return mv;
	}
	
//	대댓글 목록 불러오기 02.23 장민실
	@RequestMapping("cocomList/{pid}")
	@ResponseBody
	public ResponseEntity<List<CommentDto>> cocomList(@PathVariable("pid") int pid) {
	   List<CommentDto> cocom_list = commentService.cocomList(pid);
	   return new ResponseEntity<List<CommentDto>>(cocom_list, HttpStatus.OK);
	}
	
//	대댓글 작성하기 - 미완 주석처리
//	@RequestMapping(value="cocomwrite", method=RequestMethod.POST)
//	public ModelAndView cocomInsert(VideoDto v_dto, CommentDto c_dto, HttpSession session, ModelAndView mv) {
//		String user_id = session.getAttribute("user_id").toString();
//		String nickname = session.getAttribute("nickname").toString();
//		int id = userService.getid(user_id);
//		
//		int depth = 1;
//		c_dto.setDepth(depth);
//		
//		c_dto.setUser_id(id);
//		c_dto.setNickname(nickname);	
//		
//		int video_id = v_dto.getVideo_id();
//		c_dto.setVideo_id(video_id);
//		
//		commentService.cocomInsert(c_dto);
//		mv.addObject("c_dto", c_dto);
//		mv.setViewName("redirect:/video/detail?video_id="+video_id);
//		return mv;
//	}
	
//	댓글 수정 - 미완 주석처리
//	@RequestMapping(value="edit", method=RequestMethod.POST)
//	@ResponseBody
//	public String edit(int comment_id, String commentary, CommentDto dto) {
//		dto.setComment_id(comment_id);
//		dto.setCommentary(commentary);
//		int video_id = dto.getVideo_id();
//		commentService.edit(dto);
//		return "redirect:/video/detail?video_id="+video_id;
//	}
//	public ResponseEntity<List<CommentDto>> edit(@PathVariable("comment_id") int comment_id) {
//		List<CommentDto> edit_list = commentService.edit(comment_id);
//		return new ResponseEntity<List<CommentDto>>(edit_list, HttpStatus.OK);
//	}

	
//	댓글 삭제 02.23 장민실
	@RequestMapping("delete")
	@ResponseBody
	public String delete(int video_id, int comment_id) {
		commentService.delete(comment_id);
//		return null로 준 이유 : detail.js에서 따로 페이지 새로고침 처리해서
		return null;
	}
	
	
	
	
	
	
	
	

}