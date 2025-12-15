package com.notesapi.Repositories;

import com.notesapi.Document.Notes;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends MongoRepository<Notes,String> {

   List<Notes> findByOwnerId(String ownerId);

}
