package com.markswell.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty(message = "The name must not be null or empty")
    private String name;

    @NotNull(message = "The age must not be null")
    private Integer age;

}
