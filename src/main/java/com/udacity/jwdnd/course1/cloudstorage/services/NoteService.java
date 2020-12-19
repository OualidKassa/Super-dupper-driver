package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.apache.ibatis.exceptions.PersistenceException;
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
        try {

        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
        Integer userId = userMapper.getUser(userName).getUserId();
        Note note = new Note(0, title, description, userId);
        noteMapper.insertNote(note);
    }

    public Note[] getNoteListService(Integer userId) {
        try {
            return noteMapper.getNotesListUsers(userId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    public Note getNoteService(Integer noteId) {
        try {
            return noteMapper.getNote(noteId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteNoteService(Integer noteId) {
        try {
            noteMapper.deleteNote(noteId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }

    public void updateNoteService(Integer noteId, String title, String description) {
        try {
            noteMapper.updateNote(noteId, title, description);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }
}
