package com.spring.todo.domain.step.controller;


import com.spring.todo.domain.step.dto.StepDTO;
import com.spring.todo.domain.step.service.StepService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    // get steps
    @GetMapping("/")
    public ResponseEntity<List<StepDTO>> getStepsByTaskId(Long taskId) {
        List<StepDTO> stepDTOS = stepService.getSteps(taskId);
        return new ResponseEntity<>(stepDTOS, HttpStatus.OK);
    }

    // create
    @PostMapping("/{taskId}")
    public ResponseEntity<StepDTO> createStep(@RequestBody StepDTO stepDTO, @PathVariable Long taskId) {
        StepDTO createdStep = stepService.createTask(stepDTO, taskId);
        return new ResponseEntity<>(createdStep, HttpStatus.CREATED);
    }

    // delete
    @DeleteMapping("/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long stepId) {
        stepService.deleteStep(stepId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
