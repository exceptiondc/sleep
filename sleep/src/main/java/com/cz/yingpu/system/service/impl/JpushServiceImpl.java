package com.cz.yingpu.system.service.impl;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.JPushUtil;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Message;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.IMessageService;
import com.cz.yingpu.system.service.JPushService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by LENOVO on 2017/5/3.
 */
@Service("jpushService")
public class JpushServiceImpl extends BaseSpringrainServiceImpl implements JPushService{

    @Resource
    private IAppUserService appUserService;
    @Resource
    private IMessageService messageService;

    @Override
    public Integer notify(Integer type, Integer id, List userIds,
                          String... extend) throws Exception {
// TODO Auto-generated method stub
        Map<String, String> other = new HashMap<String, String>() ;
        //message的type:推送给的人，1用户2商品商家
        //		  itemId:目标id   0代表固定位置
        //		  pushType：与推送文档的type一致
        Message message = null ;
        Finder finder = null;
        //先定义全局的
        String content = null ;
        String url = null ;
        if (extend.length >0){
            content = extend[0] ;
        }
        if (extend.length > 1){
            url = extend[1] ;
        }
        try {
            List<AppUser> appUsers = null;
            List<Message> messages = new ArrayList<Message>();
            //先判断是否有userId 若是有userId,需要查出用户是否开启推送
            if(null != userIds && userIds.size() > 0){
                finder = new Finder("select * from t_app_user where isPush =:push and id in (:ids)");
                finder.setParam("push", "是");
                finder.setParam("ids", userIds);
                appUsers = appUserService.queryForList(finder,AppUser.class);
            }else {
                finder = new Finder("select * from t_app_user where isPush =:push and isIdCard=:isIdCard");
                finder.setParam("push", "是");
                finder.setParam("isIdCard", "是");
                //先查出来所有的，要保存message的人
                Finder userFinder = Finder.getSelectFinder(AppUser.class,"id") ;
                userIds = appUserService.queryForList(userFinder,Integer.class) ;
                appUsers = appUserService.queryForList(finder,AppUser.class);
            }
            if(null!=appUsers && appUsers.size()>0){
                    switch (type) {
                        case 1://推出新的理财项目发给用户消息
                            content ="尊敬的用户您好，现推出"+extend[0]+"理财，年化收益"+extend[1]+"%,期限"+extend[2]+"个月，如您需要进行理财，请及时前往进行投标哦~";
                             break;
                        case 2://项目满标提醒
                            content ="尊敬的用户您好，您投资的"+extend[0]+"项目已满标，将于"+extend[1]+"起开始计算收益，请及时关注";
                            break;
                        case 3://项目不满标到期提醒
                            content ="尊敬的用户您好，您投资的"+extend[0]+"项目已逾期未满标，项目作废，将于1个工作日后将款项退还至您的账户内。如有疑问，请联系客服：400-*****";
                            break;
                        case 4://项目还款分润提醒
                            content ="尊敬的用户您好，您投资的"+extend[0]+"项目已进行还款第一期，款项已进入进的账户内，请及时进行关注，如有疑问，请联系客服：400-******";
                            break;
                        case 5://项目全部结清提醒
                            content ="尊敬的用户您好，您投资的"+extend[0]+"项目已经全部还清款项，款项已进入您的账户内，请及时进行关注，如有疑问，请联系客服：400-******";
                            break;
                        case 6://天天存吧投资提醒
                            content ="敬的用户您好，您账户内有未使用投资资金，可将项目投入天天存吧中进行随时收益，请及时前往操作哦~";
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10://申请合伙人成功
                            content ="尊敬的用户您好，您申请成为合伙人，已通过申请，请前去查看！";
                            break;
                        case 11://申请合伙人失败
                            content ="尊敬的用户您好，您申请成为合伙人，已被拒绝，请前去查看失败原因，可再次申请！";
                            break;
                        case 12://优惠券发放
                            content = "尊敬的用户您好，你收到新的优惠券，请前去查看！";
                            break;
                        case 13://优惠券到期
                            content = "尊敬的用户您好，你的优惠券到期，请前去查看！";
                            break;
                    }

                messages = createMessage(userIds,content,type,id,url,1, null) ;
                messageService.save(messages);

            /*    for (AppUser user :
                        appUsers) {
                }*/
                    ThreadClazz thread = new ThreadClazz(appUsers,type, id,url,content,1) ;
                    thread.start();
              
            }


        }catch (Exception e) {
            e.printStackTrace();
            return 2 ;
        }
        return 1;
    }

    /**
     * 把userids转化message集合
     * @param userIds  需要插入的用户id集合
     * @param content
     * @param pushType
     * @param ItemId
     * @param url
     * @param type
     * @param name
     * @return
     */
    public List<Message> createMessage(List<Integer> userIds,String content,Integer pushType,Integer ItemId,String url,Integer type,String name){
        List<Message> messages = new ArrayList<>() ;
        Message message = null ;
        for (Integer userId:
                userIds) {
            message = new Message() ;
            message.setUserId(userId);
            message.setPushType(pushType);
            message.setCreateTime( new Date());
            message.setContent(content);
            message.setItemId(ItemId);
            message.setIsRead("0");
            message.setUrl(url);
            message.setName(name);
            message.setType(type);
            messages.add(message) ;
        }

        return  messages ;
    }


    /**
     * 一个多线程的内部类，用来执行大批指定用户的推送
     */ 
    private  class ThreadClazz extends  Thread{
        public ThreadClazz(List<AppUser> appUsers,Integer type,Integer id, String url,String content,Integer userType) {
            super();
            this.content= content ;
            this.id = id;
            this.url = url ;
            this.type = type;
          /*  this.userId = userId ;*/
            this.userType = userType ;
            this.appUser=appUsers;
        }

        public Integer type;
        public Integer id;
        public Integer userId;
        public String url ;
        public String content;
        public Integer userType;
        public List<AppUser> appUser;
        @Override
		public void run(){
            try {
            	for (int i = 0; i <appUser.size() ; i++) {
            		sleep(10);
            		 JPushUtil.sendJPushNotification(content, type+"", id, appUser.get(i).getId(), url);
				}
               
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
