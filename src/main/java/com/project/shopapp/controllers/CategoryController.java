package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.CategoryDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
// @Validated
public class CategoryController {
  // Hiển thị all Categories

  @GetMapping("") 
  public ResponseEntity<String> getAllCategories(
    @RequestParam("page") int page,
    @RequestParam("limit")  int limit
  ) {
    
    return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit %d", page, limit));
  }

  @PostMapping("")
  // Nếu tham số truyền vào là một đối tượng (object) thì ? => Data Transfer Object = Request Object
  public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
    if(result.hasErrors()){
      List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

      return ResponseEntity.badRequest().body(errorMessages);
    }

    
    return ResponseEntity.ok("This is insert categories ! " + categoryDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateCategory(@PathVariable Long id) {
    return ResponseEntity.ok("This is update categories with id = " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    return ResponseEntity.ok("This is delete categories with id = " + id);
  }
}
