package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String postIndex() {
        return "View all posts.";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable long id) {
        return "View individual post.";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreatePost() {
        return "View your created post.";
    }

//    @GetMapping("/posts/create")
    @RequestMapping(path = "/post/create", method = RequestMethod.POST)
    @ResponseBody
    public String createPost() {
        return "Create a new post.";
    }

}
