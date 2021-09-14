package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.dto.CreateNoteRequest;
import com.thevirtugroup.postitnote.dto.UpdateNoteRequest;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 */
@RestController
@RequestMapping("/notes")
public class NoteController
{
    @Autowired
    NoteRepository noteRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{userId}")
    public List<Note> getNoteByUserId(@PathVariable Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Note createNote(@Valid @RequestBody CreateNoteRequest request) {
        return noteRepository.add(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Note updateNote(@Valid @RequestBody UpdateNoteRequest request) {
        return noteRepository.update(request);
    }
}
