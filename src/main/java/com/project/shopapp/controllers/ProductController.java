package com.project.shopapp.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.dtos.ProductDTO;

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

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  // Nếu tham số truyền vào là một đối tượng (object) thì ? => Data Transfer Object = Request Object
  public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO,
        // @RequestPart("file") MultipartFile file,  
        BindingResult result) {
    try {         
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(errorMessages);
      }
      MultipartFile file = productDTO.getFile();
      if(file != null) {
            // Kiểm tra kích thước file và định dạng
          if (file.getSize() > 1024 * 1024 * 10) { // Kích thước tối đa 10MB
            // throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large!, Maximum size is 10MB");
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large!, Maximum size is 10MB");
          }
      
          String contentFile = file.getContentType();
          if (contentFile == null || !contentFile.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be image!!");
          }

          // Lưu file và cập nhậ thumbnail trong DTO
          String filename = storeFile(file);

          // Lưu vào đối tượng Product trong database
      }
      return ResponseEntity.ok("Product created successfully!!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
//   {
//     "name": "Macbook",
//     "price": 821.26,
//     "thumbnail": "",
//     "description": "this is test product",
//     "category_id": 1
// }

  private String storeFile(MultipartFile file) throws IOException {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    // Thêm UUID vào trước tên file để đảm bảo file name là duy nhất
    String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
    // Đường dẫn thư mục muốn lưu file
    java.nio.file.Path uploadDir = Paths.get("uploads");
    if(!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }

    // Lấy đường dẫn đầy đủ đén file
    java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

    // Sao chép file vào thư mục đích
    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

    return uniqueFilename;


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
