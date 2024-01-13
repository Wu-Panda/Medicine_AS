package com.panda.springboot.controller.dto;

import lombok.Data;

/**
 * 接受前端传输的登陆参数
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
