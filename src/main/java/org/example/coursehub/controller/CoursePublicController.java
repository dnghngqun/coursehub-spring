package org.example.coursehub.controller;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.service.CourseService;
import org.example.coursehub.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CoursePublicController {
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/courses/{slug}")
    public String detail(@PathVariable String slug, Model model) {
        var course = courseService.findBySlug(slug);
        model.addAttribute("course", course);
        return "course-detail";
    }

    @PostMapping("/courses/{id}/enroll")
    public String enroll(@PathVariable Long id,
                        @RequestParam String studentName,
                        @RequestParam String studentEmail,
                        @RequestParam String studentPhone,
                        Model model) {
        try {
            enrollmentService.enroll(studentEmail, studentName, studentPhone, id);
            model.addAttribute("success", true);
            model.addAttribute("message", "Đăng ký thành công! Chúng tôi sẽ liên hệ với bạn sớm.");
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        }
        return "enroll-success";
    }
}