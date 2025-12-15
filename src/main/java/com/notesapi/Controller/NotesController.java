package com.notesapi.Controller;

import com.notesapi.DTO.CreateNoteRequestDto;
import com.notesapi.Document.Notes;
import com.notesapi.Document.User;
import com.notesapi.Service.NotesService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notes")
public class NotesController {

    private  final NotesService notesService;

    @PostMapping("/create-note")
    public ResponseEntity<?> createNote(@Valid @RequestBody CreateNoteRequestDto noteRequestDto, Authentication authentication){

      Notes note =  notesService.createNote(noteRequestDto,authentication.getPrincipal());
        return ResponseEntity.ok(note);
    }

    @GetMapping("/get-notes")
    public ResponseEntity<?> getNotes(Authentication authentication){

        User user = (User) authentication.getPrincipal();
        String id = user.getId();
        List<Notes> notesList = notesService.getNotesByOwnerId(id);
        return ResponseEntity.ok(notesList);
    }

    @GetMapping("/get-note-by-id/{noteId}")
    public ResponseEntity<?>  getNoteById(@PathVariable String noteId,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String id = user.getId();
        Notes note = notesService.getNoteById(noteId,id);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/delete-note/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable String noteId) {
        notesService.deleteNote(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }

    @PatchMapping("/toggle-trash-note/{noteId}")
    public ResponseEntity<?> trashNote(@PathVariable String noteId) {
       Notes note = notesService.toggleTrashNote(noteId);
        return ResponseEntity.ok(note);

    }

    @PatchMapping("/toggle-archive-note/{noteId}")
    public ResponseEntity<?> archiveNote(@PathVariable String noteId) {
        Notes note = notesService.toggleArchiveNote(noteId);
        return ResponseEntity.ok(note);
    }
    @PatchMapping("/toggle-pin-note/{noteId}")
    public ResponseEntity<?> pinNote(@PathVariable String noteId) {
      Notes note =  notesService.togglePinNote(noteId);
        return ResponseEntity.ok(note);
    }

   @PatchMapping("/toggle-public-note/{noteId}")
    public ResponseEntity<?> makeNotePublic(@PathVariable String noteId) {
       Notes note = notesService.togglePublicNote(noteId);
        return ResponseEntity.ok(note);
   }

   @PutMapping("/update-note/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable String noteId, @Valid @RequestBody CreateNoteRequestDto noteRequestDto) {
        Notes updatedNote = notesService.updateNote(noteId, noteRequestDto);
        return ResponseEntity.ok(updatedNote);
   }

   @GetMapping("/public-notes")
    public ResponseEntity<?> getPublicNote(Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       String id = user.getId();
        List<Notes> publicNote = notesService.getPublicNoteByOwnerId(id);
        return ResponseEntity.ok(publicNote);
   }

   @GetMapping("/archive-notes")
    public ResponseEntity<?> getArchivedNotes(Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       String id = user.getId();
       List<Notes> archivedNotes = notesService.getArchivedNotesByOwnerId(id);
       return ResponseEntity.ok(archivedNotes);
   }

   @GetMapping("/trash-notes")
    public ResponseEntity<?> getTrashedNotes(Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       String id = user.getId();
       List<Notes> trashedNotes = notesService.getTrashedNotesByOwnerId(id);
       return ResponseEntity.ok(trashedNotes);
   }
   @GetMapping("/pinned-notes")
    public ResponseEntity<?> getPinnedNotes(Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       String id = user.getId();
       List<Notes> pinnedNotes = notesService.getPinnedNotesByOwnerId(id);
       return ResponseEntity.ok(pinnedNotes);
   }






}
