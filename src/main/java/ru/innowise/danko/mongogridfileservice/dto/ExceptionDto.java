package ru.innowise.danko.mongogridfileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private LocalDateTime dateTime;

    private String message;

}
