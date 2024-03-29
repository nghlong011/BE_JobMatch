package com.example.jobmatch.domain.category;

import com.example.jobmatch.domain.category.dto.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId,categoryRequest));
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity get(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
