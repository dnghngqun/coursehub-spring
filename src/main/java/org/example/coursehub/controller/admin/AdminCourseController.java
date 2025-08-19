package org.example.coursehub.controller.admin;

import org.example.coursehub.entity.Course;
import org.example.coursehub.repository.CategoryRepository;
import org.example.coursehub.repository.InstructorRepository;
import org.example.coursehub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller @RequestMapping("/admin/courses") @RequiredArgsConstructor
public class AdminCourseController {
    private final CourseService service;
    private final CategoryRepository categoryRepo;
    private final InstructorRepository instructorRepo;

    @GetMapping
    public String list(@RequestParam(required=false) String q,
                       @RequestParam(required=false) Long category,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        var data = service.search(q, category, pageable);
        model.addAttribute("courses", data);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        return "admin/course-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("instructors", instructorRepo.findAll());
        return "admin/course-form";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam Long categoryId,
                         @RequestParam Long instructorId,
                         @RequestParam(required=false) String shortDesc,
                         @RequestParam(required=false) String content,
                         @RequestParam(required=false) String price,
                         @RequestParam(required=false) MultipartFile thumbnail) {
        service.create(title, categoryId, instructorId, shortDesc, content, price, thumbnail);
        return "redirect:/admin/courses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", service.get(id));
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("instructors", instructorRepo.findAll());
        return "admin/course-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam Long categoryId,
                         @RequestParam Long instructorId,
                         @RequestParam(required=false) String shortDesc,
                         @RequestParam(required=false) String content,
                         @RequestParam(required=false) String price,
                         @RequestParam(required=false) MultipartFile thumbnail) {
        service.update(id, title, categoryId, instructorId, shortDesc, content, price, thumbnail);
        return "redirect:/admin/courses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/courses";
    }
}