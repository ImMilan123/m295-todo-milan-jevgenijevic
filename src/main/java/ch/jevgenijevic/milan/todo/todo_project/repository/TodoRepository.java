package ch.jevgenijevic.milan.todo.todo_project.repository;

import ch.jevgenijevic.milan.todo.todo_project.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
