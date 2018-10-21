package cn.zgc.mall.order;

import cn.zgc.mall.bean.UserAddress;
import cn.zgc.mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderServiceImpl {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    UserService userService;

    public List<UserAddress> initOrder(String userId){
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        for (UserAddress userAddress : userAddressList) {
            logger.info(userAddress.getUserAddress());
        }
        return userAddressList;
    }

}
