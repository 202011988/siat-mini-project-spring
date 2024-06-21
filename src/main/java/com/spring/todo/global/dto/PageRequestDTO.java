package com.spring.todo.global.dto;

import lombok.Data;

@Data
public class PageRequestDTO {
	private int page;	
	private int size;
	
	public PageRequestDTO() {
		this(0, 10);
	}
	
	public PageRequestDTO(int page, int size) {
		this.page = page;
		this.size = size;
	}
}
