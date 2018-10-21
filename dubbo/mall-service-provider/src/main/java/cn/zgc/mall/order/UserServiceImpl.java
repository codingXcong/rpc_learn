package cn.zgc.mall.order;

import cn.zgc.mall.bean.UserAddress;
import cn.zgc.mall.service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {

    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress(1, "上海市浦东新区航头镇航昌路xx号", "1", "张三", "020-98753953", "Y");
        UserAddress address2 = new UserAddress(2, "江西省南昌市经开区志敏大道xx号", "2", "李四", "0790-08532485", "N");
        return Arrays.asList(address1,address2);
    }
}
