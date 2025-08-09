package com.example.eyenote.Repositories;

import com.example.eyenote.Domains.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Integer> {
    List<Note> findByDeletedTrue();
    List<Note> findByDeletedFalse();
}


