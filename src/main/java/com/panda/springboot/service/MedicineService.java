package com.panda.springboot.service;

import com.panda.springboot.common.Constants;
import com.panda.springboot.controller.dto.UserDto;
import com.panda.springboot.entity.Medicine;
import com.panda.springboot.entity.User;
import com.panda.springboot.exception.ServiceException;
import com.panda.springboot.mapper.MedicineMapper;
import com.panda.springboot.mapper.UserMapper;
import com.panda.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    @Autowired
    private MedicineMapper medicineMapper;

    public int save(Medicine medicine){
        if(medicine.getId() == null){
            return medicineMapper.insert(medicine);
        }else {
            return medicineMapper.update(medicine);
        }
    }

    public void saveBatch(List<Medicine> list) {
        for(Medicine medicine: list){
            this.save(medicine);
        }
    }
}
