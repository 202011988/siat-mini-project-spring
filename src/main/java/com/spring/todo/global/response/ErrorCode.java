package com.spring.todo.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "요청한 자원을 찾을 수 없습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러가 발생했습니다."),
    
    USER_NOT_FOUND(404, "User를 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(404, "Project를 찾을 수 없습니다."),
	
	INVALID_PROJECT_DATA(400, "Project Data가 유효하지 않습니다."),
    INVALID_TASK_DATA(400, "Task Data가 유효하지 않습니다."),
    
    COOKIE_USER_EMAIL_NOT_FOUND(401, "쿠키에서 사용자 이메일이 존재하지 않습니다."),
    DUPLICATE_USER_EMAIL(409, "이미 존재하는 이메일입니다."),
    INVALID_CREDENTIALS(401, "아이디 및 비밀번호가 일치하지 않습니다.");

    private final int statusCode;
    private final String message;
}