package com.panda.springboot.mapper;

import com.panda.springboot.controller.dto.UserDto;
import com.panda.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //查看所有数据
    @Select("Select * from user")
    List<User> findAll();

    // 插入数据
    @Insert("INSERT into user(username, password, nickname, email, phone, address) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address})")
    int insert(User user);

    // 更新数据
    @Update("<script>" +
            "UPDATE user " +
            "<set>" +
            "<if test='username != null'>username = #{username},</if>" +
            "<if test='password != null'>password = #{password},</if>" +
            "<if test='nickname != null'>nickname = #{nickname},</if>" +
            "<if test='email != null'>email = #{email},</if>" +
            "<if test='phone != null'>phone = #{phone},</if>" +
            "<if test='address != null'>address = #{address},</if>" +
            "</set>" +
            " WHERE id = #{id}" +
            "</script>")
    int update(User user);

    // 删除数据
    @Delete("DELETE from user where id = #{id}")
    int deleteById(@Param("id") Integer id);

    // 分页查找数据
    @Select("select * from user limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize);

    // 数据总数
    @Select("select COUNT(*) from user")
    Integer selectTotal();

    // 模糊查询
    @Select("SELECT COUNT(*) FROM user WHERE username LIKE #{searchInput} " +
            "OR nickname LIKE #{searchInput} " +
            "OR email LIKE #{searchInput}")
    Integer selectSearchTotal(String searchInput);
    @Select("SELECT * FROM user WHERE username LIKE #{searchInput} " +
            "OR nickname LIKE #{searchInput} " +
            "OR email LIKE #{searchInput}")
    List<User> selectUsersByKeyword(String searchInput);

    // 登陆
    @Select("SELECT * FROM user WHERE username = #{userName} AND password = #{password}")
    List<User> loginSelect(String userName, String password);

    // 个人信息
    @Select("SELECT * FROM user WHERE username = #{userName}")
    User personSelect(String userName);
}
