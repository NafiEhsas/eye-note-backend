package com.example.eyenote.Services.ServiceImpl;

import com.example.eyenote.Domains.Note;
import com.example.eyenote.Dto.NoteDto;
import com.example.eyenote.Repositories.NoteRepository;
import com.example.eyenote.Services.NoteService;
import com.example.eyenote.configuration.ObjectMapperUtils;
import com.example.eyenote.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;
    private ObjectMapper objectMapper;

    NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public ResponseEntity<List<Note>> findAll(Boolean deleted) {

        if(deleted == null){
        return ResponseEntity.ok(noteRepository.findAll());
        } else if (deleted == true) {
            return ResponseEntity.ok(noteRepository.findByDeletedTrue());
        }else{
            return ResponseEntity.ok(noteRepository.findByDeletedFalse());
        }



    }

    @Override
    public ResponseEntity<Note> findById(Integer id){
        Note note = noteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("the note is not found"));
        return ResponseEntity.ok(note);
    }

    @Override
    public ResponseEntity<NoteDto> save(NoteDto noteDto){
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setColor(noteDto.getColor());
        note.setIsPinned(noteDto.getIsPinned());
        note.setCreatedAt(ZonedDateTime.now());
        note.setUpdatedAt(ZonedDateTime.now());
        note.setImgUrl(noteDto.getImgUrl());
        Note createdNote = noteRepository.save(note);
        NoteDto createdNoteDto = ObjectMapperUtils.map(createdNote, NoteDto.class);
        return ResponseEntity.ok(createdNoteDto);
    }

    @Override
    public ResponseEntity<String> deleteById(Integer id){
        Note note = noteRepository.findById(id).orElse(null);
        if(note != null){
            note.setDeletedAt(ZonedDateTime.now());
            note.setDeleted(true);
            noteRepository.save(note);
            return ResponseEntity.ok("Note deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<NoteDto> update(NoteDto noteDto, Integer id){
        Note note = findById(id).getBody();
        if(note != null){
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setColor(noteDto.getColor());
            note.setImgUrl(noteDto.getImgUrl());
            note.setUpdatedAt(ZonedDateTime.now());
            Note updatedNote = noteRepository.save(note);
            NoteDto updatedNoteDto = ObjectMapperUtils.map(updatedNote, NoteDto.class);
            return ResponseEntity.ok(updatedNoteDto);
        }
        return ResponseEntity.notFound().build();
    }

}
