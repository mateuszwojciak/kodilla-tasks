package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.mapper.TaskMapper;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task1", "Description for task 1"));
        tasks.add(new Task(2L, "Task2", "Description for task 2"));

        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(new TaskDto(1L, "Task1", "Description for task 1"));
        tasksDto.add(new TaskDto(2L, "Task2", "Description for task 2"));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Description for task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Task2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("Description for task 2")));
    }

    @Test
    void shouldGetTask() throws Exception {
        //Given
        Task task1 = new Task(1L, "Task1", "Description for task 1");
        TaskDto taskDto1 = new TaskDto(1L, "Task1", "Description for task 1");

        when(dbService.getTask(task1.getId())).thenReturn(java.util.Optional.of(task1));
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Description for task 1")));

    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task1 = new Task(1L, "Task1", "Description for task 1");
        TaskDto taskDto1 = new TaskDto(1L, "Task1", "Description for task 1");

        when(dbService.saveTask(any(Task.class))).thenReturn(task1);
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "Task1", "Description for task 1");
        TaskDto taskDto = new TaskDto(1L, "Updated_Task1", "Updated_Description for task 1");

        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated_Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated_Description for task 1")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        long taskId = 1;

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/task/deleteTask")
                .param("taskId", String.valueOf(taskId)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}