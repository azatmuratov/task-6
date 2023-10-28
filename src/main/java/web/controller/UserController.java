package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


@Controller@RequestMapping("/people")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", userService.getAllUsers());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", userService.getUserById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("person") User user) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/index";

        userService.addUser(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", userService.getUserById(id));
        return "people/new";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";

        userService.updateUser(user);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")  long id) {
        userService.removeUser(id);
        return "redirect:/people";
    }

}