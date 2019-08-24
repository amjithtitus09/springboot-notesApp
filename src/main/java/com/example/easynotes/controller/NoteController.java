package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
	NoteRepository repo;
	
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return repo.findAll();
	}
	
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return repo.save(note);
		
	}
	
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId	) {
		return repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note newNote) {
		
		Note note = repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		note.setTitle(newNote.getTitle());
		note.setContent(newNote.getContent());
		
		return repo.save(note);
		
	}
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId	) {
		Note note = repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		
		repo.delete(note);
		
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
