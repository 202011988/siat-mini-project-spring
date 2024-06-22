package com.spring.todo.global.utill;

import java.util.List;
import java.util.stream.Collectors;

import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;

public abstract class EntityDtoMapper<DTO, Entity, RelatedEntity> {

    public abstract DTO entityToDTO(Entity entity);
    public abstract Entity dtoToEntity(DTO dto, RelatedEntity relatedEntity);
    
    public List<DTO> entityListToDTOList(List<Entity> entities) {
        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public static TaskDTO entityToTaskDTO(Task task) {
        String dueDate = DateUtils.formatLocalDate(task.getDueDate());

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(dueDate)
                .status(task.getStatus())
                .build();
    }
}