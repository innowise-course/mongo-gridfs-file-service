package ru.innowise.danko.mongogridfileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {

    /*@Id
    private ObjectId id;*/

    private String name;

    private InputStream stream;

}
