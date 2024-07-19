package com.project.shopapp.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class CategoryDTO {
  @NotEmpty(message = "Category's Name cannot be empty!!")
  private String name;

}
