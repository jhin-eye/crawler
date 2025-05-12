package com.yanoos.member.controller.post;

import com.yanoos.global.util.AuthUtil;
import com.yanoos.member.service.business_service.post.PostBusinessService;
import com.yanoos.member.controller.dto.AsideMenu;
import com.yanoos.member.controller.dto.GetPostsOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/view/posts")
public class PostViewController {
    private final PostBusinessService postBusinessService;
    private final AuthUtil authUtil;
    private final String TITLE = "Posts";
    @GetMapping()
    public String getPosts(Model model){
        GetPostsOut mapMemberPosts = postBusinessService.getMapMemberPostsByMemberId(authUtil.getMemberId());

        model.addAttribute("asideMenus",getAsideMenus());
        model.addAttribute("title",TITLE);
        model.addAttribute("mapMemberPosts",mapMemberPosts.getMapMemberPostOuts());
        return "post/posts";
    }

    @PostMapping("/{mapMemberPostId}/checked")
    public String putPost(@PathVariable("mapMemberPostId") Long mapMemberPostId){
        postBusinessService.updateCheckedByMapMemberPostId(authUtil.getMemberId(),mapMemberPostId);
        return "redirect:/api/view/posts";

    }

    private List<AsideMenu> getAsideMenus(){
        return new ArrayList<>(List.of(new AsideMenu("posts","/api/view/settings/keyword")));
    }
}
