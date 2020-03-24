package com.rock.service;

import com.rock.entity.Result;
import com.rock.nodeEntity.Phone;



public interface PhoneService {

    /**根据Phone的number查询Phone
     */
    Phone getPhoneByNumber(String number);

    Phone addPhone(Phone phone);


    /**
     * 检验Phone是否已经存在
     *
     * @return true为存在
     */
    boolean verifyTheOnlyPhone(String number);

  /**根据Phone的number修改其黑白名单状态
   */

    Phone  updatePhoneByNumber(String number, String flag);

    /**根据Phone的number删除Phone
     */
    void  deletePhoneByNumber(String number);
}
