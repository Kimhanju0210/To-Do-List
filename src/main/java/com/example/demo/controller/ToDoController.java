package com.example.demo.controller;


import com.example.demo.entity.ToDo;
import com.example.demo.repository.ToDoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/todos")
public class ToDoController {

    ToDo toDo = new ToDo();

    private final ToDoRepository toDoRepository;
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/view")
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable long id) {
        Optional<ToDo> toDo = toDoRepository.findById(id);
        if (toDo.isPresent()) {
            return ResponseEntity.ok(toDo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ToDo createToDo(@RequestBody ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable long id, @RequestBody ToDo toDoDetails) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            ToDo toDo = toDoOptional.get();
            toDo.setTitle(toDoDetails.getTitle());
            toDo.setDescription(toDoDetails.getDescription());
            toDo.setCompleted(toDoDetails.isCompleted());
            return ResponseEntity.ok(toDoRepository.save(toDo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable long id) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            toDoRepository.delete(toDoOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
