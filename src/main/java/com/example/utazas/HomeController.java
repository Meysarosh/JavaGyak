package com.example.utazas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("contentPage", "wellcome");
        return "layout";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("contentPage", "about");
        return "layout";
    }
    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("contentPage", "user");
        return "layout";
    }
    @GetMapping("/admin/home")
    public String admin(Model model) {
        model.addAttribute("contentPage", "admin");
        return "layout";
    }
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("reg", new User());
        model.addAttribute("contentPage", "register");
        return "layout";
    }
    @Autowired
    private UserRepository userRepo;
    @PostMapping("/regisztral_feldolgoz")
    public String Register(@ModelAttribute User user, Model model) {
        for(User felhasznalo2: userRepo.findAll())
            if(felhasznalo2.getEmail().equals(user.getEmail())){
                model.addAttribute("uzenet", "A regisztrációs email már foglalt!");
                model.addAttribute("contentPage", "reghiba");
                return "layout";
            }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        model.addAttribute("id", user.getId());
        model.addAttribute("contentPage", "regjo");
        return "layout";
    }
}

