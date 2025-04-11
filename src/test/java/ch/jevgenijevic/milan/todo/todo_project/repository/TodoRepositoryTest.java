package ch.jevgenijevic.milan.todo.todo_project.repository;

import ch.jevgenijevic.milan.todo.todo_project.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldSaveAndFindTodos() {
        Todo todo = new Todo();
        todo.setTitle("Repo Test");
        todo.setDescription("Testing JPA layer");
        todo.setCompleted(false);

        todoRepository.save(todo);

        List<Todo> all = todoRepository.findAll();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("Repo Test");
    }
}
