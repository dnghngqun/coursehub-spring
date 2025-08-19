package org.example.coursehub.controller.admin;

import org.example.coursehub.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller @RequestMapping("/admin/instructors") @RequiredArgsConstructor
public class AdminInstructorController {
    private final InstructorService service;

    @GetMapping
    public String list(Model model) { model.addAttribute("list", service.findAll()); return "admin/instructor-list"; }

    @GetMapping("/new")
    public String createForm() { return "admin/instructor-form"; }

    @PostMapping
    public String create(@RequestParam String name, @RequestParam String email, @RequestParam(required=false) String bio,
                         @RequestParam(required=false) MultipartFile avatar) {
        service.create(name, email, bio, avatar); return "redirect:/admin/instructors"; }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) { model.addAttribute("item", service.get(id)); return "admin/instructor-form"; }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String name, @RequestParam String email, @RequestParam(required=false) String bio,
                         @RequestParam(required=false) MultipartFile avatar) {
        service.update(id, name, email, bio, avatar); return "redirect:/admin/instructors"; }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/admin/instructors"; }
}