package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/admin")
    public String redirect() {
        return "redirect:/admin/all";
    }

    @GetMapping(value = "/admin/all")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin_allUsers";
    }

    @GetMapping(value = "/admin/add")
    public String addUserForm(Model model) {
        model.addAttribute("listOfRoles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin_addUser";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("newUser") User newUser,
                          @RequestParam("authorities") List<String> listOfRoles) {
        Set<Role> roleSet = userService.ListOfRolesToSet(listOfRoles);
        newUser.setRoles(roleSet);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit/{id}")
    public String editUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("listOfRoles", roleService.getAllRoles());
        model.addAttribute("userForEdit", userService.getUserById(id));
        return "admin_editUser";
    }

    @PostMapping(value = "/admin/edit")
    public String editUser(@ModelAttribute("editedUser") User editedUser,
                           @RequestParam("authorities") List<String> listOfRoles) {
        Set<Role> roleSet = userService.ListOfRolesToSet(listOfRoles);
        editedUser.setRoles(roleSet);
        userService.editUser(editedUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
