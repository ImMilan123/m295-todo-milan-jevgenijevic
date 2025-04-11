package ch.jevgenijevic.milan.todo.todo_project.controller;

import ch.jevgenijevic.milan.todo.todo_project.model.Todo;
import ch.jevgenijevic.milan.todo.todo_project.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clearDB() {
        todoRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "user")
    void shouldCreateTodoSuccessfully() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("JUnit Test Task");
        todo.setDescription("A test task created in the unit test");
        todo.setCompleted(false);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("JUnit Test Task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @WithMockUser(roles = "user")
    void shouldFetchTodosSuccessfully() throws Exception {
        // Pre-save a todo
        Todo todo = new Todo();
        todo.setTitle("Saved Task");
        todo.setDescription("Saved directly into repo");
        todo.setCompleted(false);
        todoRepository.save(todo);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Saved Task"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }
}
