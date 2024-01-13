package com.panda.springboot.mapper;

import com.panda.springboot.entity.Medicine;
import com.panda.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedicineMapper {
    //查看所有数据
    @Select("Select * from medicine")
    List<Medicine> findAll();

    // 插入数据
    @Insert("INSERT into medicine(medicineName, medicineSpec, manufacturer, expiryDate, " +
            "price, indications,contraindications) " +
            "VALUES(#{medicineName}, #{medicineSpec}, #{manufacturer}, #{expiryDate}, " +
            "#{price}, #{indications}, #{contraindications})")
    int insert(Medicine medicine);

    // 更新数据
    @Update("<script>" +
            "UPDATE medicine " +
            "<set>" +
            "<if test='medicineName != null'>medicineName = #{medicineName},</if>" +
            "<if test='medicineSpec != null'>medicineSpec = #{medicineSpec},</if>" +
            "<if test='manufacturer != null'>manufacturer = #{manufacturer},</if>" +
            "<if test='expiryDate != null'>expiryDate = #{expiryDate},</if>" +
            "<if test='price != null'>price = #{price},</if>" +
            "<if test='indications != null'>indications = #{indications},</if>" +
            "<if test='contraindications != null'>contraindications = #{contraindications},</if>" +
            "</set>" +
            " WHERE id = #{id}" +
            "</script>")
    int update(Medicine medicine);

    // 删除数据
    @Delete("DELETE from medicine where id = #{id}")
    int deleteById(@Param("id") Integer id);

    // 分页查找数据
    @Select("select * from medicine limit #{pageNum}, #{pageSize}")
    List<Medicine> selectPage(Integer pageNum, Integer pageSize);

    // 数据总数
    @Select("select COUNT(*) from medicine")
    Integer selectTotal();

    // 模糊查询
    @Select("SELECT COUNT(*) FROM medicine WHERE medicineName LIKE #{searchInput} " +
            "OR medicineSpec LIKE #{searchInput} " + "OR expiryDate LIKE #{searchInput}" +
            "OR price LIKE #{searchInput}" + "OR expiryDate LIKE #{searchInput}" +
            "OR indications LIKE #{searchInput}" + "OR contraindications LIKE #{searchInput}"
    )
    Integer selectSearchTotal(String searchInput);
    @Select("SELECT COUNT(*) FROM medicine WHERE medicineName LIKE #{searchInput} " +
            "OR medicineSpec LIKE #{searchInput} " + "OR expiryDate LIKE #{searchInput}" +
            "OR price LIKE #{searchInput}" + "OR expiryDate LIKE #{searchInput}" +
            "OR indications LIKE #{searchInput}" + "OR contraindications LIKE #{searchInput}"
    )
    List<Medicine> selectUsersByKeyword(String searchInput);

}
