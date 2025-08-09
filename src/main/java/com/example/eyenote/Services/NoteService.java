package com.example.eyenote.Services;

import com.example.eyenote.Domains.Note;
import com.example.eyenote.Dto.NoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    public ResponseEntity<List<Note>> findAll(Boolean deleted);
    public ResponseEntity<Note> findById(Integer id);
    public ResponseEntity<NoteDto> save(NoteDto noteDto);
    public ResponseEntity<String> deleteById(Integer id);
    public ResponseEntity<NoteDto> update(NoteDto noteDto, Integer id);



}
