package com.rock.controller;

import com.rock.entity.Result;
import com.rock.nodeEntity.Phone;
import com.rock.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    /**添加Phone
     * */
    @PostMapping("/addPhone")
    public Result addPhone(@RequestParam("phone") Phone phone){
         phoneService.addPhone(phone);
         return Result.ok("添加成功");
    }
    /**更新Phone
     * */
    @PostMapping("/updatePhone")
    public Result addPhone(@RequestParam("number") String number,@RequestParam("flag") String flag){
        phoneService.updatePhoneByNumber(number,flag);
        return Result.ok("修改成功");
    }
    /**删除Phone
     * */
    @GetMapping("/deletePhone/{number}")
    public Result deletePhoneByNumber(@PathVariable("number") String number){
        phoneService.deletePhoneByNumber(number);
        return Result.ok("删除成功");
    }

}
