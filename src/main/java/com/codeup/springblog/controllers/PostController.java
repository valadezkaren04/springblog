package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
//    @ResponseBody //taken out due to views exercise
    public String postIndex(Model model) { //references back to the posts
        posts.add(new Post("This is post1", "This is post1s body"));
        posts.add(new Post("This is post2", "This is post2s body"));
        model.addAttribute("posts", posts);
        return "posts/index";
//        return "View all posts."; //taken out due to views exercise
    }

    @GetMapping("/posts/{id}")
//    @ResponseBody
    public String individualPost(@PathVariable long id, Model model) {
        Post post =  new Post("BT21 are the cutest character", "I want all of the BT21 plushies please!");
        model.addAttribute("post", post);
        return "posts/show";
//        return "View individual post.";
    }

    // When you visit the URL you will see the form to create a post.
    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreatePost() {
        return "View your created post.";
    }

    // When you submit the form on the /posts/create page,
    // the information will be posted to the same URL
//    @GetMapping("/posts/create")
    @RequestMapping(path = "/post/create", method = RequestMethod.POST)
    @ResponseBody
    public String createPost() {
        return "Create a new post.";
    }

}
