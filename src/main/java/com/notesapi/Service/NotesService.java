package com.notesapi.Service;

import com.notesapi.DTO.CreateNoteRequestDto;
import com.notesapi.Document.Notes;
import com.notesapi.Document.User;
import com.notesapi.Repositories.NotesRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final NotesRepository notesRepository;


    public Notes createNote(@Valid CreateNoteRequestDto notes, Object principal) {

        User user = (User) principal;

        Notes newNote = Notes.builder()
                .title(notes.getTitle())
                .content(notes.getContent())
                .tags(notes.getTags())
                .color(notes.getColor())
                .ownerId(user.getId())
                .isArchived(false)
                .isPinned(notes.isPinned())
                .isPublic(false)
                .isTrashed(false)
                .createdAt(Instant.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return notesRepository.save(newNote);
    }

    public List<Notes> getNotesByOwnerId(String id) {

        return notesRepository.findByOwnerId(id).stream().filter(x-> !x.isArchived() && !x.isTrashed()).toList();
    }

    public void deleteNote(String noteId) {

        notesRepository.deleteById(noteId);
    }

    public Notes toggleTrashNote(String noteId) {
        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));
        if(note.isTrashed())
            note.setArchived(false);
        note.setTrashed(!note.isTrashed());
        note.setUpdatedAt(LocalDateTime.now());
       return notesRepository.save(note);
    }

    public Notes toggleArchiveNote(String noteId) {
        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));
        if(note.isArchived())
            note.setPinned(false);
        note.setArchived(!note.isArchived());
        note.setUpdatedAt(LocalDateTime.now());
       return notesRepository.save(note);

    }

    public Notes togglePinNote(String noteId) {
        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));
        note.setPinned(!note.isPinned());
        note.setUpdatedAt(LocalDateTime.now());
       return notesRepository.save(note);
    }


    public Notes togglePublicNote(String noteId) {
        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));
        note.setPublic(!note.isPublic());
        note.setUpdatedAt(LocalDateTime.now());
       return notesRepository.save(note);
    }

    public Notes updateNote(String noteId, @Valid CreateNoteRequestDto noteRequestDto) {
        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));
        note.setTitle(noteRequestDto.getTitle());
        note.setContent(noteRequestDto.getContent());
        note.setTags(noteRequestDto.getTags());
        note.setColor(noteRequestDto.getColor());
        note.setUpdatedAt(LocalDateTime.now());
        return notesRepository.save(note);
    }

    public List<Notes> getPublicNoteByOwnerId(String noteId) {
        return notesRepository.findByOwnerId(noteId).stream().filter(Notes::isPublic).toList();
    }

    public List<Notes> getArchivedNotesByOwnerId(String id) {
        return notesRepository.findByOwnerId(id).stream().filter(note-> note.isArchived() && !note.isTrashed()).toList();
    }

    public List<Notes> getTrashedNotesByOwnerId(String id) {
        return notesRepository.findByOwnerId(id).stream().filter(Notes::isTrashed).toList();
    }

    public List<Notes> getPinnedNotesByOwnerId(String ownerId) {
        return notesRepository.findByOwnerId(ownerId)
                .stream()
                .filter(note -> note.isPinned()
                        && !note.isArchived()
                        && !note.isTrashed())
                .sorted((note1, note2) -> note2.getUpdatedAt().compareTo(note1.getUpdatedAt()))
                .toList();
    }


    public Notes getNoteById(String noteId,String ownerId) {

        Notes note = notesRepository.findById(noteId).orElseThrow(()-> new RuntimeException("Note not found"));

        if(note.isPublic() ) {
            return note;
        }

        if(!Objects.equals(note.getOwnerId(), ownerId)){
            throw new RuntimeException("You are not authorized to access this note");
        } else {
            return note;
        }
    }
}
