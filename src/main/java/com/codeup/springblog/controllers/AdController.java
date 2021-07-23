package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdController {
    private final AdRepository adDao; // property being set to final ; cannot be changed

    public AdController(AdRepository adDao) { // create method to include the repo and the dao
        this.adDao = adDao;
    }

    @GetMapping("/ads")
    public String index(Model model) {
        model.addAttribute("ads", adDao.findAll()); // gets the findAll from JpaRepository<Ad, Long> in AdRepository
        return "ads/index";
    }

    @GetMapping("/ads/{n}")
    public String viewOne(@PathVariable long n, Model model) {
        Ad ad = adDao.findById(n);
        model.addAttribute("ad", ad); // gets the information
        return "ads/show";
    }

    @GetMapping("/ads/{title}")
    public String viewOne(@PathVariable String title, Model model) {
        Ad ad = adDao.findFirstByTitle(title);
        model.addAttribute("ad", ad); // gets the information
        return "ads/show"
    }
}
