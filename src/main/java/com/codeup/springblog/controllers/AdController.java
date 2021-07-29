package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.repositories.AdRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdController {
    private final AdRepository adDao; // property being set to final ; cannot be changed
    private final UserRepository userDao;

    public AdController(AdRepository adDao, UserRepository userDao) { // create method to include the repo and the dao
        this.adDao = adDao;
        this.userDao = userDao;
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
        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String createForm(Model model) {
        model.addAttribute("ad", new Ad()); // passing it to the form ; when suer hits submit, the fields will be saved tot eh database
        return "ads/create";
    }

    @PostMapping("ads/create")
    public String createAd(@ModelAttribute Ad ad) {
        ad.setUser(userDao.getById(1L));
        adDao.save(ad);
        return "redirect:/ads";
    }

    @GetMapping("ads/{id}/edit")
    public String editAdForm(@PathVariable long id, Model model){
        Ad adToEdit = adDao.getById(id);
        model.addAttribute("ad", adToEdit);
        return "ads/create";
    }

    @PostMapping("/ads/{id}/edit")
    public String editAd(@PathVariable long id, @ModelAttribute Ad ad) {
        return createAd(ad);
    }
}
