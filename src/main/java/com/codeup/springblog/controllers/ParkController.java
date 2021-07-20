package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Park;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ParkController {
    @GetMapping("/parks")
    public String showParks(Model model) {
        Park gc = new Park("Grand Canyon National Park");
        Park rm = new Park("Rocky Mountains National Park");

        List<Park> parks = new ArrayList<>(); //passing the park list

        parks.add(gc);
        parks.add(rm);

        model.addAttribute("parks", parks);

        return "parks";
    }
}
