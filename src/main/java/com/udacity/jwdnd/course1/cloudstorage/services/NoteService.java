package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public void addNoteService(String title, String description, String userName) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Note note = new Note(0, title, description, userId);
        noteMapper.insertNote(note);
    }

    public Note[] getNoteListService(Integer userId) {
        return noteMapper.getNotesListUsers(userId);
    }

    public Note getNoteService(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public void deleteNoteService(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNoteService(Integer noteId, String title, String description) {
        noteMapper.updateNote(noteId, title, description);
    }
}
