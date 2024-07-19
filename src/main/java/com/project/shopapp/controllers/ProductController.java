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


import jakarta.validation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
  // Hiển thị all Products

  @GetMapping("") 
  public ResponseEntity<String> getAllProducts(
    @RequestParam("page") int page,
    @RequestParam("limit")  int limit
  ) {
    
    return ResponseEntity.ok(String.format("getAllProducts, page = %d, limit %d", page, limit));
  }

  @GetMapping("/{id}") 
  public ResponseEntity<String> getProductById(@PathVariable("id") String productId
  ) {
    
    return ResponseEntity.ok("Product with id = " + productId);
  }

  @PostMapping("")
  // Nếu tham số truyền vào là một đối tượng (object) thì ? => Data Transfer Object = Request Object
  public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductController productController,  BindingResult result) {
    try {         
      List<String> errorMessages1= result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(errorMessages);
      }
      return ResponseEntity.ok("Product created successfully!! " + errorMessages1);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable Long id) {
    return ResponseEntity.ok("This is update product with id = " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    return ResponseEntity.ok("This is delete product with id = " + id);
  }
}
