package com.panda.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panda.springboot.common.Constants;
import com.panda.springboot.common.Result;
import com.panda.springboot.controller.dto.UserDto;
import com.panda.springboot.entity.User;
import com.panda.springboot.exception.ServiceException;
import com.panda.springboot.mapper.UserMapper;
import com.panda.springboot.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int save(User user){
        if(user.getId() == null){
            return userMapper.insert(user);
        }else {
            return userMapper.update(user);
        }
    }

    public void saveBatch(List<User> list) {
        for(User user: list){
            this.save(user);
        }
    }

    public UserDto login(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        //System.out.println(userMapper.loginSelect(username, password));
        List<User> users;
        try {
            users = userMapper.loginSelect(username, password);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (users.size() != 0) {
            User user = users.get(0);
            // System.out.println(users);
            userDto.setNickname(user.getNickname());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setAvatarUrl(user.getAvatarUrl());
            // System.out.println(userDto);
            // 设置token
            String token = TokenUtils.getToken(user.getId().toString(), user.getPassword().toString());
            userDto.setToken(token);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户或密码错误");
        }
    }

    public User register(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        //System.out.println(userMapper.loginSelect(username, password));
        List<User> users;
        try {
            users = userMapper.loginSelect(username, password);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (users.size() == 0) {
            User user = new User();
            user.setNickname(userDto.getNickname());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setAvatarUrl(userDto.getAvatarUrl());
            save(user);
            return user;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在！");
        }
    }
}
