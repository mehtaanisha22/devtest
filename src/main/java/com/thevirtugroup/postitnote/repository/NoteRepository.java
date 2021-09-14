package com.thevirtugroup.postitnote.repository;


import com.thevirtugroup.postitnote.dto.CreateNoteRequest;
import com.thevirtugroup.postitnote.dto.UpdateNoteRequest;
import com.thevirtugroup.postitnote.model.Note;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NoteRepository {
    @Autowired
    ModelMapper mapper;

    private List<Note> notes = new ArrayList();

    public NoteRepository() {
        populateNotes();
    }

    public Note findById(Long id){
        return notes.stream().filter(note -> id.equals(note.getId())).findAny().orElseThrow(RuntimeException::new);
    }

    public List<Note> findAll(){
        return notes;
    }

    public Note add(CreateNoteRequest noteRequest){
        Note newNote = new Note();
        mapper.map(noteRequest, newNote);
        int size = notes.size();
        newNote.setId((long) (size + 1));
        newNote.setCreatedAt(LocalDate.now().toString());
        notes.add(newNote);
        return newNote;
    }

    public Note update(UpdateNoteRequest request){
        Note existingNote = notes.stream().filter(listNote -> request.getId().equals(listNote.getId())).findAny().orElse(null);
        if (existingNote != null){
            notes.remove(existingNote);
            existingNote.setName(request.getName());
            existingNote.setText(request.getText());
            notes.add(existingNote);
            return existingNote;
        }
        return null;
    }

    public List<Note> findByUserId(Long userId){
        return notes.stream().filter(note -> userId.equals(note.getUserId())).collect(Collectors.toList());
    }

    private void populateNotes() {
        notes.add(new Note(1L, "Note1", "Test1 description text", LocalDate.now().minusDays(1).toString(), 1L));
        notes.add(new Note(2L, "Note2", "Test2 description text", LocalDate.now().minusDays(2).toString(), 1L));
        notes.add(new Note(3L, "Note3", "Test3 description text", LocalDate.now().minusDays(3).toString(), 1L));
        notes.add(new Note(4L, "Note4", "Test4 description text", LocalDate.now().minusDays(4).toString(), 2L));
        notes.add(new Note(5L, "Note5", "Test5 description text", LocalDate.now().minusDays(5).toString(), 2L));
        notes.add(new Note(6L, "Note6", "Test6 description text", LocalDate.now().minusDays(6).toString(), 2L));
        notes.add(new Note(7L, "Note7", "Test7 description text", LocalDate.now().minusDays(7).toString(), 3L));
        notes.add(new Note(8L, "Note8", "Test8 description text", LocalDate.now().minusDays(8).toString(), 3L));
        notes.add(new Note(9L, "Note9", "Test9 description text", LocalDate.now().minusDays(9).toString(), 3L));
        notes.add(new Note(10L, "Note10", "Test10 description text", LocalDate.now().minusDays(10).toString(), 3L));
    }
}
