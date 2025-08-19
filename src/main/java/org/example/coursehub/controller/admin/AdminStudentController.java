package org.example.coursehub.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.entity.Student;
import org.example.coursehub.service.StudentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/students")
@RequiredArgsConstructor
public class AdminStudentController {
    private final StudentService studentService;

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                      @RequestParam(defaultValue = "0") int page,
                      Model model) {
        var pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
        var students = studentService.findAll(q, pageable);

        model.addAttribute("students", students);
        model.addAttribute("q", q);
        return "admin/student-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "admin/student-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "admin/student-form";
    }

    @PostMapping
    public String create(Student student) {
        studentService.save(student);
        return "redirect:/admin/students";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, Student student) {
        student.setId(id);
        studentService.save(student);
        return "redirect:/admin/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/admin/students";
    }
}
