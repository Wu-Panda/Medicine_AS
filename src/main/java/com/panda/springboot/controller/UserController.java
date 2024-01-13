package com.panda.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.panda.springboot.common.Constants;
import com.panda.springboot.common.Result;
import com.panda.springboot.controller.dto.UserDto;
import com.panda.springboot.entity.User;
import com.panda.springboot.mapper.UserMapper;
import com.panda.springboot.service.UserService;
import com.panda.springboot.utils.TokenUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public Integer save(@RequestBody User user){
        // 新增或更新
        return userService.save(user);
    }

    @GetMapping
    public List<User> findAll(){
        return userMapper.findAll();
    }

    // 删除
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids){
        Integer count = 0;
        for(Integer id : ids){
            count = userMapper.deleteById(id);
        }
        return count;
    }

    // 分页查询
    // 接口路径:/user/page/?pageNum=1&pageSize=10
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        pageNum = (pageNum-1)*pageSize;
        Integer total = userMapper.selectTotal();
        List<User> data = userMapper.selectPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    // 模糊查询
    @GetMapping("/search")
    public Map<String, Object> searchUsers(@RequestParam String searchInput) {
        searchInput = "%" + searchInput + "%";
        Integer total = userMapper.selectSearchTotal(searchInput);
        List<User> data = userMapper.selectUsersByKeyword(searchInput);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    // 导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<User> list = userMapper.findAll();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写入
        writer.write(list, true);

        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheettml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    // 导入
    @PostMapping("/import")
    public void importInfo(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        // 注意excel格式有严格要求, 如果用下面的语句的话（已被注释）
        // List<User> list = excelReader.read(0,1,User.class);
        List<List<Object>> list = excelReader.read(1);
        List<User> users = CollUtil.newArrayList();
        for(List<Object> row : list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }
        userService.saveBatch(users);
    }

    // 登陆
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDto userDto1 = userService.login(userDto);
        return Result.success(userDto1);
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        User user = userService.register(userDto);
        return Result.success(user);
    }

    // 个人信息
    @GetMapping("/username/{username}")
    public Result personInfo(@PathVariable String username){
        return Result.success(userMapper.personSelect(username));
    }
}
