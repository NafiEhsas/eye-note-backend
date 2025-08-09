package com.example.eyenote.Controllers;

import com.example.eyenote.Domains.Note;
import com.example.eyenote.Dto.NoteDto;
import com.example.eyenote.Services.ServiceImpl.NoteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {


    private NoteServiceImpl noteService;

    NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;

    }

    @GetMapping
    private ResponseEntity<List<Note>> getAll(
            @RequestParam(name = "deleted",required = false) Boolean deleted
    ) {
        return noteService.findAll(deleted);
    }

    @PostMapping
    private ResponseEntity<NoteDto> save(@RequestBody NoteDto noteDto) {
        return noteService.save(noteDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<NoteDto> update(@PathVariable Integer id, @RequestBody NoteDto noteDto) {
        return noteService.update(noteDto, id);
    }

    @GetMapping("/findById/{id}")
    private ResponseEntity<Note> findById(@PathVariable Integer id) {
        return noteService.findById(id);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        return noteService.deleteById(id);
    }
}
