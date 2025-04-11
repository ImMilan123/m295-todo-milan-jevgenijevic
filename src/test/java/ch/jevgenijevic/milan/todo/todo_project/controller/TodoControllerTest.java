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

    private String token;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        // 🔐 Paste your current valid user token below (must include "user" role)
        token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGMGpheE82dWRvdnB6eXFXU3FPT3BnMG5jRGJLU0hxdFVod2pUdUJVUkpVIn0.eyJleHAiOjE3NDQzNDk3MDMsImlhdCI6MTc0NDM0OTQwMywianRpIjoiYTliMTA4YjQtOTJiOC00NDkzLWE3NTUtMjJjZWU1OTQyZTU0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy90b2RvLWFwcCIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIwMWU5ZTMxMi1mMzYxLTQzNzctODc1OC01YTEwNzJiYjFlMGMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0b2RvLWJhY2tlbmQiLCJzZXNzaW9uX3N0YXRlIjoiMjM4N2ZiODktOTgzZS00MTMzLWFiN2ItN2Q2NzY0ZTFiNDMwIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiLCJkZWZhdWx0LXJvbGVzLXRvZG8tYXBwIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiMjM4N2ZiODktOTgzZS00MTMzLWFiN2ItN2Q2NzY0ZTFiNDMwIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoidXNlciB1c2VyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlciIsImdpdmVuX25hbWUiOiJ1c2VyIiwiZmFtaWx5X25hbWUiOiJ1c2VyIiwiZW1haWwiOiJ1c2VyQGdtYWlsLmNvbSJ9.GIUpTLQpSLe29h7BbUtqPIJPdB_68PYCzBqWy7ViRoGUmhHogrPivT72Hr_w58n7eGnnjPRkWAh9gjEOgQLlUBskJpgxn9eXC6PgNwloKzGBs1LouBeJMYc8Em961WbAT7VlvLm1dJUHeV2Wa4trMYVYrnvu3RSwjzXxLV3YfcnAAX6hhrMau4RFeIf3hgj5xz7FDdK97c32mkuhavrr1bOp6-BiLQzdY3enBEyafiQ2c2307-T0sLsYKDQnoZCWVlfaiI6C_YTg7Ib3iXJlJC1ZM_TEI-XQsl3VcB41hd4MW-c-gL05b3Oto9fZkkoKfq2Gd10dW3DSWx1qWq3-cw"; // ⬅️ Replace this with a real token
    }

    @Test
    void shouldCreateTodoSuccessfully() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Test Task");
        todo.setDescription("A test task for unit testing");
        todo.setCompleted(false);

        mockMvc.perform(post("/api/todos")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldFetchTodosSuccessfully() throws Exception {
        // Insert a todo directly into the DB
        Todo todo = new Todo("Read Book", "Read a software engineering book", false);
        todoRepository.save(todo);

        mockMvc.perform(get("/api/todos")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Read Book"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }
}
