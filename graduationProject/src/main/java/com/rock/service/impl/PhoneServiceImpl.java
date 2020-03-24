//package com.rock.service.impl;
//
//import com.rock.entity.Result;
//import com.rock.nodeEntity.Phone;
//import com.rock.repository.PhoneRepository;
//import com.rock.service.PhoneService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//@Service
//@Slf4j
//public class PhoneServiceImpl implements PhoneService {
//    @Autowired
//    private PhoneRepository phoneRepository;
//
//    /**
//     * 检验Phone是否存在
//     *
//     * @return
//     */
//
//    @Override
//    public boolean verifyTheOnlyPhone(String number) {
//        if (Objects.isNull(phoneRepository.getPhoneByNumber(number))) {
//            return false;
//        }
//        return true;
//    }
//
//
//    @Override
//    public Phone getPhoneByNumber(String number) {
//        return phoneRepository.getPhoneByNumber(number);
//    }
//
//    @Override
//    public Phone addPhone(Phone phone) {
//        try{
//            Phone phone1=phoneRepository.save(phone);
//            return phone1;
//        }catch(Exception e){
//            log.error("PhoneService 发生的异常为：Phone新增失败! {} 参数为：{}", e, phone);
//            throw new RuntimeException(e.getMessage(), e);
//        }
//
//    }
//
//
//
//    @Override
//    public Phone updatePhoneByNumber(String number, String flag) {
//        return phoneRepository.updatePhoneByNumber(number,flag);
//       // return Result.ok("号码:"+number+",修改号码状态成功！");
//    }
//
//    @Override
//    public void deletePhoneByNumber(String number) {
//       phoneRepository.deletePhoneByNumber(number);
//        //return Result.ok("号码:"+number+",删除号码成功！");
//    }
//}
