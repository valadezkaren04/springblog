package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public PostController(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    // shows the posts (viewing posts)
    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

    // finds a post by the ID
    @GetMapping("/posts/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepo.findById(id));
        return "posts/show";
    }

    // shows the form to create form
    @GetMapping("/posts/create")
    public String showForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    // creates the post
//    @PostMapping("/posts/create")
//    public String createPost(@RequestParam String title, @RequestParam String body) {
//        User user = userRepo.getById(1L);
//        Post post = new Post(title, body, user);
//        postRepo.save(post);
//        return "redirect:/posts";
//    }

    // refactored code with form model binding
    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        post.setUser(userRepo.getById(1L));
        postRepo.save(post);
        return "redirect:/posts";
    }

    // deletes the posts
    @PostMapping("/posts/delete/{id}")
    public String deleteById(@PathVariable long id) {
        postRepo.deleteById(id);
        return "redirect:/posts"; // better practice to return to the individual post page
    }

//    @GetMapping("/posts/{id}/edit")
//    public String editThePost(@PathVariable long id, Model model) {
//        model.addAttribute("post", postRepo.findById(id));
//        return "post/edit";
//    }

    // allows you to edit your post
    @GetMapping("/posts/{id}/edit")
    public String postToEdit(@PathVariable long id, Model model) { // needs model b.c needs id in order to edit
        model.addAttribute("post", postRepo.getById(id));
        return "posts/edit";
    }

    // the post gets updated and displayed again
    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @ModelAttribute Post post) {
       post.setUser(userRepo.getById(1L));
       postRepo.save(post);
       return "redirect:/posts/" + id;
    }
}

//    private List<Post> posts = new ArrayList<>();
//
//    @GetMapping("/posts")
////    @ResponseBody //taken out due to views exercise
//    public String postIndex(Model model) { //references back to the posts
//        posts.add(new Post("This is post1", "This is post1s body"));
//        posts.add(new Post("This is post2", "This is post2s body"));
//        model.addAttribute("posts", posts);
//        return "posts/index";
////        return "View all posts."; //taken out due to views exercise
//    }
//
//    @GetMapping("/posts/{id}")
////    @ResponseBody
//    public String individualPost(@PathVariable long id, Model model) {
//        Post post =  new Post("BT21 are the cutest character", "I want all of the BT21 plushies please!");
//        model.addAttribute("post", post);
//        return "posts/show";
////        return "View individual post.";
//    }
//
//    // When you visit the URL you will see the form to create a post.
//    @GetMapping("/posts/create")
//    @ResponseBody
//    public String viewCreatePost() {
//        return "View your created post.";
//    }
//
//    // When you submit the form on the /posts/create page,
//    // the information will be posted to the same URL
////    @GetMapping("/posts/create")
//    @RequestMapping(path = "/post/create", method = RequestMethod.POST)
//    @ResponseBody
//    public String createPost() {
//        return "Create a new post.";
//    }

