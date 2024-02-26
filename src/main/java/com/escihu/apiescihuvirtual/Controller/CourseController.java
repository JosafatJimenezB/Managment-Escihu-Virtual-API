package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.service.Course.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Controlador de cursos")
@RestController
@RequestMapping("/api/v1")
public class CourseController {

     private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

}
