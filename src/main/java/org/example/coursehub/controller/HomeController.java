package org.example.coursehub.controller;

import org.example.coursehub.repository.CategoryRepository;
import org.example.coursehub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller @RequiredArgsConstructor
public class HomeController {
    private final CourseService courseService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) Long category,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "createdAt,desc") String sort,
                       Model model) {
        String[] s = sort.split(",");
        Sort sortSpec = Sort.by(Sort.Direction.fromString(s.length>1? s[1] : "desc"), s[0]);
        Pageable pageable = PageRequest.of(page, 9, sortSpec);
        var pageData = courseService.search(q, category, pageable);
        model.addAttribute("courses", pageData);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        return "home";
    }
}