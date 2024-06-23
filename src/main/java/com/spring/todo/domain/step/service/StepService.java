package com.spring.todo.domain.step.service;

import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.step.dto.StepDTO;
import com.spring.todo.domain.step.entity.Step;
import com.spring.todo.domain.step.repository.StepRepository;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.task.exception.InvalidTaskDataException;
import com.spring.todo.domain.task.exception.TaskNotFoundException;
import com.spring.todo.domain.task.repository.TaskRepository;
import com.spring.todo.global.utill.EntityDtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;
    private final TaskRepository taskRepository;

    public StepDTO createTask(StepDTO stepDTO, Long taskId) {
        log.info("새로운 Step를 생성합니다: {}", stepDTO);
        validateStepDTO(stepDTO);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ProjectNotFoundException("해당 Task가 존재하지 않습니다"));
        Step step = dtoToEntity(stepDTO, task);
        Step savedStep = stepRepository.save(step);
        return entityToDto(savedStep);
    }

    private StepDTO entityToDto(Step step) {
        return EntityDtoMapper.entityToStepDTO(step);
    }

    public Step dtoToEntity(StepDTO stepDTO, Task task) {
        return Step.builder().title(stepDTO.getTitle()).description(stepDTO.getDescription()).task(task).build();
    }

    private void validateStepDTO(StepDTO stepDTO) {
        if (stepDTO.getTitle() == null || stepDTO.getTitle().isEmpty()) {
            log.error("Step 제목이 유효하지 않습니다: {}", stepDTO.getTitle());
            throw new InvalidTaskDataException("Step 제목은 필수입니다.");
        }
        if (stepDTO.getDescription() == null || stepDTO.getDescription().isEmpty()) {
            log.error("Step 설명이 유효하지 않습니다: {}", stepDTO.getDescription());
            throw new InvalidTaskDataException("Step 설명은 필수입니다.");
        }
    }

    public void deleteStep(Long stepId) {
        log.info("Task ID {}를 삭제합니다.", stepId);

        Step step = stepRepository.findById(stepId)
                .orElseThrow(() -> new TaskNotFoundException("해당 Step가 존재하지 않습니다."));
        stepRepository.delete(step);
    }
}
