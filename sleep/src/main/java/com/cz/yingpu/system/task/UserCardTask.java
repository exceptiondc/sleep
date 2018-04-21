package com.cz.yingpu.system.task;

import com.cz.yingpu.frame.common.BaseLogger;
import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.IUserCardService;
import com.cz.yingpu.system.service.JPushService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
@Component
public class UserCardTask extends BaseLogger {

    @Resource
    private IAppUserService appUserService;
    @Resource
    private IUserCardService userCardService;
    @Resource
    private JPushService jPushService;

    /**
     *优惠券过期
     */
    //@Scheduled(cron = "00 00 0/1 * * ?")
    @Scheduled(cron = "00 */1 * * * ?")
    public void setUserCardExpired() throws  Exception{
        logger.info("**************优惠券失效定时任务***************");
        Finder finder = new Finder("select * from t_user_card where status = 1041  AND endTime < NOW()");
       // finder.setParam("date","%"+ DateUtils.convertDate2String("yyyy-MM-dd",org.apache.commons.lang.time.DateUtils.addDays(new Date(), -1))+"%");
        List<UserCard> userCards = userCardService.queryForList(finder,UserCard.class);
        if(null!=userCards && userCards.size()>0){
            for (UserCard userCard:userCards){
                if(new Date().after(userCard.getEndTime())){
                    userCard.setStatus(1043);
                    userCardService.update(userCard,true);
                    List<Integer> userIds = new ArrayList<Integer>();
                    userIds.add(userCard.getUserId());
                    jPushService.notify(13,null,userIds);
                }
            }

        }
    }
}
