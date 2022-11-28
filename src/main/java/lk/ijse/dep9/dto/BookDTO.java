package lk.ijse.dep9.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Serializable {

    @NotBlank(message = "Isbn can't be empty")
    @Pattern(regexp = "([0-9][0-9\\\\-]*[0-9])",message = "Invalid isbn")
    private String isbn;

    @NotBlank(message = "Title can't be empty")
    @Pattern(regexp = ".+",message = "Invalid title")
    private String title;

    @NotBlank(message = "Author can't be empty")
    @Pattern(regexp = "[A-Za-z ]+",message = "Invalid author")
    private String author;

    @NotNull(message = "Copies can't be empty")
    @Min(value = 1,message = "Copies can't be empty or negative")
    private Integer copies;

   // hash equal method overrided
}
