package org.example.coursehub.controller.admin;

import org.example.coursehub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/admin/categories") @RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService service;

    @GetMapping
    public String list(Model model) { model.addAttribute("list", service.findAll()); return "admin/category-list"; }

    @GetMapping("/new")
    public String createForm() { return "admin/category-form"; }

    @PostMapping
    public String create(@RequestParam String name) { service.create(name); return "redirect:/admin/categories"; }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) { model.addAttribute("item", service.get(id)); return "admin/category-form"; }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String name) { service.update(id, name); return "redirect:/admin/categories"; }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/admin/categories"; }
}