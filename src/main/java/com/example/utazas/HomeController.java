package com.example.utazas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired private HelysegRepo helysegRepo;
    @Autowired private SzallodaRepo szallodaRepo;
    @Autowired private TavaszRepo tavaszRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private MessageRepo messageRepo;

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

    @GetMapping("/contact")
    public String contact( Model model) {
        model.addAttribute("msg", new Message());
        model.addAttribute("contentPage", "contact");
        return "layout";
    }

    @GetMapping("/data")
    public String data(Model model) {
        List<List<String>> data = GetCombinedDataFromTables();
        model.addAttribute("data", data);
        model.addAttribute("contentPage", "data");
        return "layout";
    }

    List<List<String>> GetCombinedDataFromTables() {
        List<List<String>> matrix = new ArrayList<>();

        for (Tavasz tavasz : tavaszRepo.findAll()) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(tavasz.getSorszam()));
            row.add(tavasz.getSzalloda().getNev());
            row.add(tavasz.getSzalloda().getHelyseg().getNev());
            row.add(tavasz.getSzalloda().getHelyseg().getOrszag());
            row.add(String.valueOf(tavasz.getAr()));
            row.add(String.valueOf(tavasz.getSzalloda().getTengerpart_tav()));
            row.add(String.valueOf(tavasz.getSzalloda().getRepter_tav()));
            row.add(tavasz.getIndulas());
            row.add(String.valueOf(tavasz.getIdotartam()));
            matrix.add(row);
        }
        return matrix;
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("contentPage", "user");
        return "layout";
    }

    @GetMapping("/messages")
    public String Uzenetek(Model model) {
        Iterable<Message> messages = messageRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        model.addAttribute("messages", messages);
        model.addAttribute("contentPage", "messages");
        return "layout";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("reg", new User());
        model.addAttribute("contentPage", "register");
        return "layout";
    }

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

    @PostMapping("/contact")
    public String submitMessage( @ModelAttribute Message message,  Model model, Principal principal) {

        if (message.getText() == null || message.getText().isBlank()) {
            model.addAttribute("error", "Az üzenet mező nem lehet üres.");
            model.addAttribute("msg", message);
            model.addAttribute("contentPage", "contact");
            return "layout";
        }

        if (!message.getText().isEmpty() && message.getText().length()<10) {
            model.addAttribute("error", "Túl rövid az üzenet.");
            model.addAttribute("msg", message);
            model.addAttribute("contentPage", "contact");
            return "layout";
        }

        if (message.getEmail() == null || !message.getEmail().matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            model.addAttribute("error", "Érvénytelen email cím.");
            model.addAttribute("msg", message);
            model.addAttribute("contentPage", "contact");
            return "layout";
        }

        if (principal != null) {
            Optional<User> loggedInUser = userRepo.findByEmail(principal.getName());
            loggedInUser.ifPresent(user -> message.setUser_id(user.getId()));
        }
        message.setCreated_at(LocalDateTime.now());
        messageRepo.save(message);

        model.addAttribute("success", "Az üzenetet sikeresen elküldtük!");
        model.addAttribute("msg", new Message()); // Reset the form
        model.addAttribute("contentPage", "contact");
        return "layout";
    }

}

