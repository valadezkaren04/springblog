package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    public String guessInput(@PathVariable int n, Model model) {
        int randomNum = (int) Math.floor((Math.random() * 6) + 1); // adding + 1 equals a random num from 1-6
        boolean checkingGuess = randomNum == n;
        model.addAttribute("n", n);
        model.addAttribute("randomNum", randomNum);
        model.addAttribute("checkingGuess", checkingGuess);

        return "roll-dice-check";
    }

}
