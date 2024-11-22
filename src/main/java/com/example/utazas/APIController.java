package com.example.utazas;

import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {
   private final UserRepository userRepository;
   public APIController(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   @GetMapping("/api/users")
    Iterable<User> getUsers() {
       return userRepository.findAll();
   }

   @GetMapping("/api/users/{id}")
    User getUser(@PathVariable int id) {
       return userRepository.findById(id)
               .orElseThrow(() -> new UserNotFoundException(id));
   }

   @PostMapping("api/users")
    User addUser(@RequestBody User user) {
       if(user.getEmail()==null || user.getName()==null || user.getPassword()==null)
           throw new UserInsufficientDataException();
       if(userRepository.findByEmail(user.getEmail()).isPresent())
           throw new UserAlreadyExistsException(user.getEmail());
       return userRepository.save(user);
   }

   @PutMapping("/api/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User newUser) {
       if(newUser.getEmail()==null || newUser.getName()==null || newUser.getPassword()==null)
           throw new UserInsufficientDataException();
       return userRepository.findById(id)
               .map(u->{
                   u.setName(newUser.getName());
                   u.setEmail(newUser.getEmail());
                   u.setPassword(newUser.getPassword());
                   return userRepository.save(u);
               })
               .orElseGet(() -> {
                   newUser.setId(id);
                   return userRepository.save(newUser);
               });
   }

   @DeleteMapping("/api/users/{id}")
    void deleteUser(@PathVariable int id) {
       User user = userRepository.findById(id)
               .orElseThrow(() -> new UserNotFoundException(id));
       userRepository.deleteById(id);
   }

}
