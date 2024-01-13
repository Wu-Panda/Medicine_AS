package com.panda.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.panda.springboot.common.Constants;
import com.panda.springboot.common.Result;
import com.panda.springboot.controller.dto.UserDto;
import com.panda.springboot.entity.Medicine;
import com.panda.springboot.entity.User;
import com.panda.springboot.mapper.MedicineMapper;
import com.panda.springboot.mapper.UserMapper;
import com.panda.springboot.service.MedicineService;
import com.panda.springboot.service.UserService;
import jakarta.servlet.ServletOutputStream;
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
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public Integer save(@RequestBody Medicine medicine){
        // 新增或更新
        return medicineService.save(medicine);
    }

    @GetMapping
    public List<Medicine> findAll(){
        return medicineMapper.findAll();
    }

    // 删除
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return medicineMapper.deleteById(id);
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids){
        Integer count = 0;
        for(Integer id : ids){
            count = medicineMapper.deleteById(id);
        }
        return count;
    }

    // 分页查询
    // 接口路径:/user/page/?pageNum=1&pageSize=10
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        pageNum = (pageNum-1)*pageSize;
        Integer total = medicineMapper.selectTotal();
        List<Medicine> data = medicineMapper.selectPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    // 模糊查询
    @GetMapping("/search")
    public Map<String, Object> searchUsers(@RequestParam String searchInput) {
        searchInput = "%" + searchInput + "%";
        Integer total = medicineMapper.selectSearchTotal(searchInput);
        List<Medicine> data = medicineMapper.selectUsersByKeyword(searchInput);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    // 导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<Medicine> list = medicineMapper.findAll();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题
        writer.addHeaderAlias("medicineName", "药品名称");
        writer.addHeaderAlias("medicineSpec", "规格");
        writer.addHeaderAlias("manufacturer", "生产厂家");
        writer.addHeaderAlias("expiryDate", "有效期");
        writer.addHeaderAlias("price", "价格");
        writer.addHeaderAlias("indications", "适应症");
        writer.addHeaderAlias("contraindications", "禁忌症");
        writer.addHeaderAlias("createTime", "创建时间");

        // 一次性写入
        writer.write(list, true);

        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheettml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("药物信息", "UTF-8");
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
        List<Medicine> medicines = CollUtil.newArrayList();
        for(List<Object> row : list){
            Medicine medicine = new Medicine();
            medicine.setMedicineName(row.get(0).toString());
            medicine.setMedicineSpec(row.get(1).toString());
            medicine.setManufacturer(row.get(2).toString());
            medicine.setExpiryDate(row.get(3).toString());
            medicine.setPrice(row.get(4).toString());
            medicine.setIndications(row.get(5).toString());
            medicine.setContraindications(row.get(6).toString());
            medicines.add(medicine);
        }
        medicineService.saveBatch(medicines);
    }
}
