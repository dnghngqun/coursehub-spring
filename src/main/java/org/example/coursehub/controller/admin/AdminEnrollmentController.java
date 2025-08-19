package org.example.coursehub.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.entity.Enrollment;
import org.example.coursehub.entity.EnrollmentStatus;
import org.example.coursehub.service.EnrollmentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/enrollments")
@RequiredArgsConstructor
public class AdminEnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping
    public String list(@RequestParam(required = false) EnrollmentStatus status,
                      @RequestParam(required = false) String q,
                      @RequestParam(defaultValue = "0") int page,
                      Model model) {
        var pageable = PageRequest.of(page, 20, Sort.by("enrolledAt").descending());
        var enrollments = enrollmentService.findAll(status, q, pageable);

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("status", status);
        model.addAttribute("q", q);
        model.addAttribute("statuses", EnrollmentStatus.values());
        return "admin/enrollment-list";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id, @RequestParam(required = false) String notes) {
        enrollmentService.updateStatus(id, EnrollmentStatus.APPROVED, notes);
        return "redirect:/admin/enrollments";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id, @RequestParam(required = false) String notes) {
        enrollmentService.updateStatus(id, EnrollmentStatus.CANCELLED, notes);
        return "redirect:/admin/enrollments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        enrollmentService.deleteById(id);
        return "redirect:/admin/enrollments";
    }
}
