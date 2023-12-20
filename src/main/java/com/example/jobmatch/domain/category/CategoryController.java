package com.example.jobmatch.domain.category;

import com.example.jobmatch.domain.job.JobsService;
import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity delete(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity update(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId,categoryRequest));
    }

    @GetMapping("/get")
    public ResponseEntity get() {
        return ResponseEntity.ok(categoryService.getCategory());
    }
}
