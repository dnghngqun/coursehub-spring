package org.example.coursehub.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.service.CourseService;
import org.example.coursehub.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCourses", courseService.getTotalCourses());
        model.addAttribute("totalEnrollments", enrollmentService.getTotalEnrollments());
        model.addAttribute("todayEnrollments", enrollmentService.getTodayEnrollments());
        return "admin/dashboard";
    }
}