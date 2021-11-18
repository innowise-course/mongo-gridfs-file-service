package ru.innowise.danko.mongogridfileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {

    private String name;

    private String type;

    private byte[] file;

}
