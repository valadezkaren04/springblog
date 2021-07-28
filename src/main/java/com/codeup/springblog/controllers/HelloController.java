package com.codeup.springblog.controllers;

import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // similar to a servlet
public class HelloController {
    private final EmailService emailService;

    public HelloController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "<h1>Hello from Spring!</h1>"; // this will be our do get for our controller
    }

    @GetMapping("/hello/{name}")
//    @ResponseBody
    public String sayHello(@PathVariable String name, Model model) { // parameter has to match with GetMapping
        model.addAttribute("name", name);
        return "hello";
//        return "Hello " + name + "! n.n";
    }

    // Shows the form for join (allows us to see the form)
    @GetMapping("/join")
    public String showJoinForm() {
        return "join";
    }

    // Allows you to post
    @PostMapping("/join")
    //name is connected to the join.html ; Model passes it into the html
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model) {
        model.addAttribute("cohort", "Welcome to " + cohort + "! n.n");
//        emailService.prepareAndSend("valadez14k@gmail.com", "Testing email service", "Hello! Welcome to " + cohort + " have an amazing day! n.n");
        return "join";
    }

    @GetMapping("number/{num}")
    @ResponseBody
    public int displayNumber(@PathVariable int num) {
        return num;
    }

//    @GetMapping("/hello/in/{color}")
    @RequestMapping(path = "hello/in/{color}", method = RequestMethod.GET)
    @ResponseBody
    public String helloInColor (@PathVariable String color) {
        return "<h1 style=\"color: " + color + "\">Hello in " + color + "!</h1>";
    }

}
