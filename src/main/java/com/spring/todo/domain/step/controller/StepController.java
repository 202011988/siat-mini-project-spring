package com.spring.todo.domain.step.controller;


import com.spring.todo.domain.step.dto.StepDTO;
import com.spring.todo.domain.step.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/steps")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    // create
    @PostMapping("/{taskId}")
    public ResponseEntity<StepDTO> createTask(@RequestBody StepDTO stepDTO, @PathVariable Long taskId) {
        StepDTO createdStep = stepService.createTask(stepDTO, taskId);
        return new ResponseEntity<>(createdStep, HttpStatus.CREATED);
    }

    // delete
    @DeleteMapping("/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long stepId) {
        return null;
    }

}
