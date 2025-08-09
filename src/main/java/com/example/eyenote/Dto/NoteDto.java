package com.example.eyenote.Dto;

import com.example.eyenote._base.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoteDto extends AuditableEntityDto {
    private Integer id;


    private String title;
    private String content;
    private String color;
    private Boolean isPinned;
    private Boolean isCompleted;
    private String imgUrl;
}
