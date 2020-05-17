package com.rahn.springsearch.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final UserSearch userSearch;

    public UserController(UserRepository userRepository, UserSearch userSearch) {
        this.userRepository = userRepository;
        this.userSearch = userSearch;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void findById(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public List<User> search(@RequestParam("text") String text){
        return userSearch.search(text);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public User save(@RequestBody User user){
        return userRepository.save(user);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User update(@RequestBody User user){
        return userRepository.save(user);
    }

}
