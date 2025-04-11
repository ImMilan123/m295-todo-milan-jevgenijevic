package ch.jevgenijevic.milan.todo.todo_project.controller;

import ch.jevgenijevic.milan.todo.todo_project.model.Todo;
import ch.jevgenijevic.milan.todo.todo_project.repository.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todos", description = "CRUD endpoints for Todo items")
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Get all todos", description = "Returns a list of all todos.")
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new todo", description = "Creates and returns a new todo.")
    @ApiResponse(responseCode = "200", description = "Todo created successfully")
    public Todo create(@Valid @RequestBody Todo todo) {
        return repository.save(todo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a todo", description = "Updates an existing todo by ID.")
    public Todo update(
            @Parameter(description = "ID of the todo to update") @PathVariable Long id,
            @Valid @RequestBody Todo todo) {

        return repository.findById(id).map(existing -> {
            existing.setTitle(todo.getTitle());
            existing.setDescription(todo.getDescription());
            existing.setCompleted(todo.isCompleted());
            return repository.save(existing);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo", description = "Deletes a todo by ID.")
    public void delete(@Parameter(description = "ID of the todo to delete") @PathVariable Long id) {
        repository.deleteById(id);
    }
}
