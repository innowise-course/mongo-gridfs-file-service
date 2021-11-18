package ru.innowise.danko.mongogridfileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {

    private String name;

    private String type;

    private byte[] file;

}
