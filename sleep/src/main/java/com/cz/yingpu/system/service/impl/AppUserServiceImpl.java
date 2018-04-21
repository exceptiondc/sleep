package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.CryptAES;
import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GetEwm;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.SMSUtil;
import com.cz.yingpu.frame.util.SystemConfigConstants;
import com.cz.yingpu.frame.util.TokenGenerateUtil;
import com.cz.yingpu.frame.util.fuyou.ConfigReader;
import com.cz.yingpu.frame.util.pay.WxPayUtil;
import com.cz.yingpu.system.entity.AccessToken;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.entity.Token;
import com.cz.yingpu.system.exception.NoUserException;
import com.cz.yingpu.system.exception.ParameterErrorException;
import com.cz.yingpu.system.exception.PasswordFailException;
import com.cz.yingpu.system.exception.PhoneExistException;
import com.cz.yingpu.system.exception.PhoneNotExistException;
import com.cz.yingpu.system.exception.VerifyCodeFailException;
import com.cz.yingpu.system.fuyoudata.AppTransReqData;
import com.cz.yingpu.system.fuyoudata.AppTransRspData;
import com.cz.yingpu.system.fuyoudata.QueryBalanceReqData;
import com.cz.yingpu.system.fuyoudata.QueryBalanceResultData;
import com.cz.yingpu.system.fuyoudata.QueryBalanceRspData;
import com.cz.yingpu.system.fuyoudata.QueryUserInfs_v2ReqData;
import com.cz.yingpu.system.fuyoudata.QueryUserInfs_v2RspData;
import com.cz.yingpu.system.fuyoudata.QueryUserInfs_v2RspDetailData;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.FuiouService;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.ICardService;
import com.cz.yingpu.system.service.ISmsService;
import com.cz.yingpu.system.service.ISysSysparamService;
import com.cz.yingpu.system.service.IUserCardService;
import com.cz.yingpu.system.service.JPushService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.service.impl.AppUser
 */
@Service("appUserService")
public class AppUserServiceImpl extends BaseSpringrainServiceImpl implements IAppUserService {

	@Resource
	private ISmsService smsService;
	@Resource
	private ISysSysparamService sysSysparamService;
	@Resource
	private ICardService cardService;
	@Resource
	private IUserCardService userCardService;
	@Resource
	private JPushService jPushService;
	
/*	@Resource
	private IOrderService orderService;
	@Resource
	private IUserAccountHistoryService userAccountHistoryService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private IUserProjectService userProjectService;

	@Resource
	private IBankService bankService;
	@Resource
	private IUserYebHistoryService userYebHistoryService;
	@Resource
	private IPartnerUserService partnerUserService;


	@Resource 
	private IFYStatusCodeService fYStatusCodeService;
*/
	@Override
	public String  save(Object entity ) throws Exception{
	      AppUser appUser=(AppUser) entity;
	       return super.save(appUser).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      AppUser appUser=(AppUser) entity;
		 return super.saveorupdate(appUser).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 AppUser appUser=(AppUser) entity;
	return super.update(appUser);
    }
	@Override
	public AppUser findAppUserByphone(Object phone) throws Exception{
		if(null == phone) throw  new ParameterErrorException();
		Finder fin = new Finder("select id from t_app_user where phone=:phone");
		fin.setParam("phone", phone);
		Map<String,Object> user = queryForObject(fin);
		AppUser a=new AppUser();
		if(user!=null&&user.size()>0){
			a.setId(Integer.parseInt(user.get("id").toString()));
		}
		return a;
	}
    @Override
	public AppUser findAppUserById(Object id) throws Exception{
		if(null == id) throw  new ParameterErrorException();
		AppUser appUser = super.findById(id,AppUser.class);
		//最后要更新的数据
		AppUser updateUser = new AppUser( ) ;
		updateUser.setId(appUser.getId());
		BigDecimal totalAmount = new BigDecimal("00");
//		if (null != appUser.getTotalInvestAmount()){
//			totalAmount = totalAmount.add(new BigDecimal(appUser.getTotalInvestAmount())) ;
//		}
		DecimalFormat df = new DecimalFormat("#.00");
		updateUser.setTotalAmount(Double.valueOf(df.format(totalAmount.doubleValue())));
		appUser = super.findById(id,AppUser.class);
		appUser.setTotalAmount(updateUser.getTotalAmount());
		appUser.setPaid(updateUser.getPaid());
		appUser.setIncome(updateUser.getIncome());
		appUser.setBankName(updateUser.getBankName());
		appUser.setBankLog(updateUser.getBankLog());
	 	return appUser;
	}

/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}


	/**
	 *  用户注册
	 * regType: sms => 短信注册 pwd => 密码注册
	 * 	（当包含 wx(微信)、qq(QQ)、wb(微博) 字符串，此时注册变为账号绑定，即使用注册信息与前面任意账号进行绑定。）
	 */
	@Override
	public AppUser register(AppUser appUser,Page page, String regType) throws Exception {
		
		if(null == appUser.getPhone() || null == appUser.getCode()
				|| (regType.contains("pwd") && null == appUser.getPassword()))
			throw  new ParameterErrorException();
		
		//验证手机号码是否注册
		AppUser user = new AppUser();
		if(StringUtils.isNotBlank(appUser.getPhone())){
			user.setPhone(appUser.getPhone());
		}
		List<AppUser> datas = findListDataByFinder(null,page,AppUser.class,user);
		if(null != datas && datas.size() > 0) throw  new PhoneExistException();
		//验证验证码是否正确
		Sms sms=new Sms();
		sms.setPhone(appUser.getPhone());
		sms.setContent(appUser.getCode());
		sms.setType(1);
		List<Sms> smss=smsService.findListDataByFinder(null, page, Sms.class, sms);
		if(null == smss || smss.size() ==0) throw  new VerifyCodeFailException();

		//开始注册流程
		user.setPassword(CryptAES.AES_Encrypt(appUser.getPassword()));
		user.setIsFirst("是");
		/*user.setDateline(new Date());
		user.setIsIdCard("否");
		user.setIsPush("是");
		user.setIsPartner("0");
		SysParamBean sysParamBean = sysSysparamService.findParamBean();
		user.setIsFirstProject("是");
		user.setInviteCode(appUser.getPhone());
		//检查富有系统是否存在该账户，
//		Integer id = save(user);
//		user = findAppUserById(Integer.parseInt(save(user)));
		user = super.findById(Integer.parseInt(save(user)),AppUser.class);
		user.setInviteCodeUrl(GetEwm.ewm(user.getId(),user.getInviteCode()));*/
		//是否有输入邀请码
		/*if(null != appUser.getInviteCode() && !"".equals(appUser.getInviteCode())){
			AppUser inviteUser = new AppUser();
			inviteUser.setInviteCode(appUser.getInviteCode());
			List<AppUser> inviteUsers = findListDataByFinder(null,page,AppUser.class,inviteUser);
			if (null != inviteUsers && inviteUsers.size() >0) {
				inviteUser = inviteUsers.get(0);
				if (null != inviteUser.getInviteNum()) {
					inviteUser.setInviteNum(inviteUser.getInviteNum() + 1);
				} else {
					inviteUser.setInviteNum(1);
				}
				saveorupdate(inviteUser);
			
				user.setParentId(inviteUser.getId());
			}else {
				throw  new NoUserException();
			}
		}*/

		final String[] accountTypes = {"wx", "qq", "wb"};
		String bindType = "";
		boolean isAccountBind = false;
		for (String at : accountTypes) {
			if (regType.endsWith(at)) {
				isAccountBind = true;
				bindType = at;
				break;
			}
		}

		if (isAccountBind) {
			String accountBindIDs = "",
					field = "";
			Finder finder = new Finder("SELECT * FROM t_app_user WHERE ");
			switch(bindType) {
				case "wx":
					accountBindIDs = appUser.getWxOpenId();
					field = "wxopenid";
					break;

				case "qq":
					accountBindIDs = appUser.getQqCode();
					field = "qqcode";
					break;

				case "wb":
					accountBindIDs = appUser.getWxCode();
					field = "wbcode";
					break;
			}

			finder.append(field + " = :" + field).setParam(field, accountBindIDs);
			AppUser au = userCardService.queryForObject(finder, AppUser.class);
			if (au == null) {
				throw new Exception("该账户不存在！");
			}

			user.setId(au.getId());
			updateVaildValue(user);
		}
		else {
			user.setBalance(0.00);
			user.setCashPledge(0.00);
			user.setIncome(0.00);
			user.setMoney(0.00);
			user.setTotalInvestAmount(0.00);
			user.setTotalConsume(0.00);
			save(user);
		}
		//开关是开启的状态则为赠送卡券
		/*Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","isOpenRegisterGift");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
		if("1".equals(sysparam.getValue())){
			Finder find= new Finder("SELECT * FROM t_card WHERE cardType=1");
			List<Card> cards = cardService.queryForList(find,Card.class);
			if(null!=cards&&cards.size()>0){
				for (Card card:cards){
					UserCard userCard = new UserCard();
					userCard.setUserId(user.getId());
					userCard.setStatus(1);
					userCard.setCreateTime(new Date());
					userCard.setName(card.getName());
					userCard.setType(card.getType());
					userCard.setHotelId(card.getHotelId());
					userCard.setBrandId(card.getBrandId());
					userCard.setStartTime(card.getStartTime());
					userCard.setEndTime(card.getEndTime());
					userCard.setLimitMoney(card.getLimitMoney());
					if(1==card.getType()){
						userCard.setMoney(card.getMoney());
					}
					userCard.setCardType(2);
					userCardService.save(userCard);
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(user.getId());
					jPushService.notify(12,null,userIds);
				}
			}
		}*/
		return user;
	}

	public  String getRand() throws Exception {
		String x = "";
		Finder find = new Finder("select count(*) total from t_app_user ");
		Map<String,Object> object = queryForObject(find);
		String t = object.get("total").toString();
		String a = "9";
		String b = "1";
		for (int i=0;i<t.length()+2;i++){
			a=a+"0";
			b=b+"0";
		}
		x = (Math.random()*Integer.parseInt(a)+Integer.parseInt(b))+"";
		Finder finder = new Finder("select * from t_app_user where inviteCode=:inviteCode ");
		finder.setParam("inviteCode",x);
		while (queryForList(finder).size()!=0){
			x = (Math.random()*Integer.parseInt(a)+Integer.parseInt(b))+"";
			finder.setParam("inviteCode",x);
		}
		return x.substring(0,x.indexOf("."));
	}

	@Override
	public AppUser login(AppUser appUser,Page page) throws Exception {
		if(null == appUser.getType() ) throw  new ParameterErrorException();
		AppUser user = new AppUser();
		if("1".equals(appUser.getType())){//手机登陆
			if(null == appUser.getPhone() ||  null == appUser.getPassword()) throw  new ParameterErrorException();
			user.setPhone(appUser.getPhone());
			user.setPassword(CryptAES.AES_Encrypt(appUser.getPassword()));
		}else if("3".equals(appUser.getType())){//QQ登陆
			if( null == appUser.getQqCode()) throw  new ParameterErrorException();
			user.setQqCode(appUser.getQqCode());
		}else if("2".equals(appUser.getType())){//微信登陆
			if( null == appUser.getWxCode()) throw  new ParameterErrorException();
			user.setWxCode(appUser.getWxCode());
		}
		List<AppUser> datas = findListDataByFinder(null,page,AppUser.class,user);
		if ("1".equals(appUser.getType())  && datas.size() == 0) throw  new PhoneNotExistException();
		
		user = datas.get(0);
		/*if(null == datas || datas.size() == 0){//若是微信或QQ查出为空的话，需要新注册
			user.setDateline(new Date());
			user.setIsIdCard("否");
			user.setIsPush("是");
			user.setIsFirst("是");
			user.setIsPartner("0");
			SysParamBean sysParamBean = sysSysparamService.findParamBean();
			user = findAppUserById(save(user));
			user.setInviteCodeUrl(GetEwm.ewm(user.getId(),user.getInviteCode()));
			update(user);
			//开关是开启的状态则为赠送卡券
			Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
			finder.setParam("code","isOpenRegisterGift");
			List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
			SysSysparam sysparam = (SysSysparam) sysparams.get(0);
			if("1".equals(sysparam.getValue())){
				Finder find= new Finder("SELECT * FROM t_card WHERE cardType=1");
				List<Card> cards = cardService.queryForList(find,Card.class);
				if(null!=cards&&cards.size()>0){
					for (Card card:cards){
						UserCard userCard = new UserCard();
						userCard.setUserId(user.getId());
						userCard.setStatus(1);
						userCard.setCreateTime(new Date());
						userCard.setName(card.getName());
						userCard.setType(card.getType());
						userCard.setHotelId(card.getHotelId());
						userCard.setBrandId(card.getBrandId());
						userCard.setStartTime(card.getStartTime());
						userCard.setEndTime(card.getEndTime());
						userCard.setLimitMoney(card.getLimitMoney());
						if(1==card.getType()){
							userCard.setMoney(card.getMoney());
						}
						userCard.setCardType(2);
						userCardService.save(userCard);
						List<Integer> userIds = new ArrayList<Integer>();
						userIds.add(user.getId());
						jPushService.notify(12,null,userIds);
					}
				}
			}
		}else{
			user = datas.get(0);
			user.setInviteCode(user.getPhone());
			user.setInviteCodeUrl(GetEwm.ewm(user.getId(),user.getInviteCode()));
			updateValidValue(user);
		}*/
		
		return  user;
	}

	@Override
	@Cacheable(value = GlobalStatic.tokenCacheKey , key = "'token_'+#userId")
	public Token getToken(Integer userId) throws Exception {
		Token token = new Token();
		token.setUserId(userId.toString());
		token.setToken(TokenGenerateUtil.getToken());
		token.setLastOperate(new Date());
		token.setExpired(new Date(token.getLastOperate().getTime() + SystemConfigConstants.getInt("TOKENTIMEOUT") * 60 * 1000));
		return  token;
	}

	@Override
	public AppUser modify(AppUser appUser) throws Exception {
		Finder finder = null;
		AppUser aUser = findAppUserById(appUser.getId());
		if(null == aUser)  throw  new PhoneNotExistException();
		if(StringUtils.isNotBlank(appUser.getPassword()) && StringUtils.isNotBlank(appUser.getOldpassword())){
			if(aUser.getPassword().equals(CryptAES.AES_Encrypt(appUser.getOldpassword()))){
				appUser.setPassword(CryptAES.AES_Encrypt(appUser.getPassword()));
			}else {
				throw  new PasswordFailException();
			}

		}
		//判断该用户是否绑定qq
		if(StringUtils.isNotBlank(appUser.getQqCode())){
			//判断QQ号是否注册过了
			finder = new Finder("select * from t_app_user where  qqCode=:qqCode ");
			finder.setParam("qqCode",appUser.getQqCode());
			List<AppUser> users = queryForList(finder,AppUser.class);
			if(null != users && users.size() > 0){
				throw  new PhoneExistException();
			}
		}

		//判断该用户是否绑定微信
		if(StringUtils.isNotBlank(appUser.getWxCode())){
			//判断微信号是否注册过了
			finder = new Finder("select * from t_app_user where  wxCode=:wxCode ");
			finder.setParam("wxCode",appUser.getWxCode());
			List<AppUser> users = queryForList(finder,AppUser.class);
			if(null != users && users.size() > 0){
				throw  new PhoneExistException();
			}
		}

		if(StringUtils.isNotBlank(appUser.getPhone())){
			//判断手机号是否注册过了
			finder = new Finder("select * from t_app_user where  phone=:phone ");
			finder.setParam("phone",appUser.getPhone());
			List<AppUser> users = queryForList(finder,AppUser.class);
			if(null != users && users.size() > 0){
				throw  new PhoneExistException();
			}else {
				//判断验证码
				if(StringUtils.isNotBlank(appUser.getCode())){
					 finder = new Finder("select * from t_sms where type=3 and phone=:phone and content=:content");
					 finder.setParam("phone",appUser.getPhone());
					 finder.setParam("content",appUser.getCode());
					List<Sms> smss=smsService.queryForList(finder,Sms.class);
					if(null == smss || smss.size() ==0) throw  new VerifyCodeFailException();
					if(null!=appUser.getPassword() && !"".equals(appUser.getPassword())){
						appUser.setPassword(CryptAES.AES_Encrypt(appUser.getPassword()));
					}else{
						throw  new ParameterErrorException();
					}
					if(null != appUser.getInviteCode() && !"".equals(appUser.getInviteCode())){
						finder = new Finder("select * from t_app_user where inviteCode=:inviteCode");
						finder.setParam("inviteCode",appUser.getInviteCode());
						List<AppUser> inviteUsers =queryForList(finder,AppUser.class);
						if (null != inviteUsers && inviteUsers.size() >0) {
							AppUser inviteUser = inviteUsers.get(0);
							if (null != inviteUser.getInviteNum()) {
								inviteUser.setInviteNum(inviteUser.getInviteNum() + 1);
							} else {
								inviteUser.setInviteNum(1);
							}
							saveorupdate(inviteUser);
						
							appUser.setInviteCode(null);

							Finder fin = new Finder("select  * from t_partner_user where levelId=:levelId ");
							fin.setParam("levelId",inviteUser.getId());
						

							appUser.setParentId(inviteUser.getId());
						}else {
							throw  new NoUserException();
						}
					}
				}else{
					throw  new ParameterErrorException();
				}

			}

		}
		appUser.setInviteCode(null);
//		appUser.set
		update(appUser,true);
		return findAppUserById(appUser.getId());
	}

/*	@Override
	public void saveDetail(Integer type,AppUser appUser,AppTransReqData appTransReqData) throws Exception {
		Order order = new Order();
		order.setUserId(appUser.getId());
		order.setTradeNo(appTransReqData.getMchnt_txn_ssn());
		order.setCreateTime(new Date());
		order.setMoney(appUser.getMoney());
		order.setType(type);
		order.setStatus(1);
		order = orderService.findOrderById(Integer.parseInt(orderService.save(order).toString()));
		UserAccountHistory userAccountHistory = new UserAccountHistory();
		userAccountHistory.setUserId(appUser.getId());
		userAccountHistory.setMoney(appUser.getMoney());
		userAccountHistory.setType(type);
		userAccountHistory.setTradeNo(appTransReqData.getMchnt_txn_ssn());
		userAccountHistory.setStatus(1);
		userAccountHistory.setCreatetime(new Date());
		userAccountHistory.setOrderId(order.getId());
		userAccountHistoryService.save(userAccountHistory);
	}*/

	
/*	
 * @Override
 * public  void saveDetailChange(AppTransRspData appTransRspData)  throws Exception {
		FYStatusCode fyCode = new FYStatusCode();
    	fyCode.setCode(appTransRspData.getResp_code());
    	List<FYStatusCode> fyCodes = fYStatusCodeService.findListDataByFinder(null, null, FYStatusCode.class, fyCode);
    	String respInfo = fyCodes != null && fyCodes.size() != 0 ? fyCodes.get(0).getInfo() : appTransRspData.getResp_desc();
		Finder finder = new Finder("SELECT * FROM t_order WHERE tradeNo=:tradeNo");
		finder.setParam("tradeNo", appTransRspData.getMchnt_txn_ssn());
		Order order = orderService.queryForObject(finder,Order.class);
		order.setStatus(2);
		order.setPs(respInfo.trim().length() == 0 ? appTransRspData.getResp_desc() : respInfo);
		orderService.update(order);
		finder = new Finder("SELECT * FROM t_user_account_history WHERE tradeNo=:tradeNo");
		finder.setParam("tradeNo", appTransRspData.getMchnt_txn_ssn());
		UserAccountHistory userAccountHistory = userAccountHistoryService.queryForObject(finder,UserAccountHistory.class);
		userAccountHistory.setStatus(2);
		userAccountHistory.setRemarkers(appTransRspData.getResp_desc());
		userAccountHistoryService.update(userAccountHistory);
		AppUser user = findAppUserById(order.getUserId());
		if(1==order.getType()){
			if(null != user.getBalance()){
				user.setBalance(user.getBalance() + Double.parseDouble(appTransRspData.getAmt()) / 100);
			}else{
				user.setBalance( Double.parseDouble(appTransRspData.getAmt()) / 100);
			}
			if(null != user.getCaBalance()){
				user.setCaBalance(user.getCaBalance() + Double.parseDouble(appTransRspData.getAmt()) / 100);
			}else{
				user.setCaBalance( Double.parseDouble(appTransRspData.getAmt()) / 100);
			}
		}else {
			user.setBalance(user.getBalance() - Double.parseDouble(appTransRspData.getAmt()) / 100);
			user.setCaBalance(user.getCaBalance() - Double.parseDouble(appTransRspData.getAmt()) / 100);
		}
		update(user);
	}*/

	@Override
	public QueryBalanceResultData  queryUserBalance(String phone) throws Exception{
		QueryBalanceReqData queryBalanceReqData = new QueryBalanceReqData();
		queryBalanceReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
		queryBalanceReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
		queryBalanceReqData.setCust_no(phone);
		queryBalanceReqData.setMchnt_txn_dt(DateUtils.setDateFormat(new Date(),"yyyyMMdd"));
		QueryBalanceRspData queryBalanceRspData = FuiouService.balanceAction(queryBalanceReqData);
		List<QueryBalanceResultData> queryBalanceResultDatas = queryBalanceRspData.getResults();
		QueryBalanceResultData queryBalanceResultData = null;
		if(null!=queryBalanceResultDatas&&queryBalanceResultDatas.size()>0){
			queryBalanceResultData = queryBalanceResultDatas.get(0);
		}
		return queryBalanceResultData;

	}

	@Override
	public AppUser isFirst(Integer id) throws Exception{
		AppUser appUser = super.findById(id,AppUser.class);
		if(null!=appUser && "是".equals(appUser.getIsFirst())){
			appUser.setIsFirst("否");
			appUser = findAppUserById(update(appUser));
		}

		return appUser;
	}

	public String findPassword(AppUser appUser)throws Exception{

		return null;
	}

	@Override
	public List<QueryUserInfs_v2RspDetailData> queryUserInfs(String phone)throws Exception{
		QueryUserInfs_v2ReqData queryUserInfs_v2ReqData = new QueryUserInfs_v2ReqData();
		queryUserInfs_v2ReqData.setVer(ConfigReader.getConfig("ver"));
		queryUserInfs_v2ReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
		queryUserInfs_v2ReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
		queryUserInfs_v2ReqData.setMchnt_txn_dt(DateUtils.setDateFormat(new Date(),"yyyyMMdd"));
		queryUserInfs_v2ReqData.setUser_ids(phone);
		QueryUserInfs_v2RspData QueryUserInfs_v2RspData = FuiouService.queryUserInfs_v2(queryUserInfs_v2ReqData);
		List<QueryUserInfs_v2RspDetailData> queryUserInfs_v2RspDetailDatas = QueryUserInfs_v2RspData.getResults();
		return queryUserInfs_v2RspDetailDatas;
	}

	@Override
	public void changeEWM()throws Exception{
		Finder finder = new Finder("select * from  t_app_user");
		List<AppUser> appUsers = queryForList(finder,AppUser.class);
		for (int i=0;i<appUsers.size(); i++){
			AppUser appUser = appUsers.get(i);
			appUser.setInviteCode(getRand());
			appUser.setInviteCodeUrl(GetEwm.ewm(appUser.getId(),appUser.getInviteCode()));
			update(appUser,true);
		}
	}

	@Override
	public AppUser getTotalBalance(AppUser appUser)throws Exception{
		AppUser au = new AppUser();
		Finder finder = new Finder("SELECT SUM(balance) totalBalance,SUM(yebBalance) totalYebBalance,SUM(freezeYebBalance) totalFreezeYebBalance  FROM `t_app_user` WHERE 1=1");
		if(StringUtils.isNotBlank(appUser.getPhone())){
			finder.append(" and phone=:phone ");
			finder.setParam("phone",appUser.getPhone());
		}
		if(StringUtils.isNotBlank(appUser.getIdCard())){
			finder.append(" and idCard=:idCard ");
			finder.setParam("idCard",appUser.getIdCard());
		}
		if(StringUtils.isNotBlank(appUser.getRealName())){
			finder.append(" and realName=:realName ");
			finder.setParam("realName",appUser.getRealName());
		}
		if(null!=appUser.getCityId() && 0!=appUser.getCityId()){
			finder.append(" and cityId=:cityId ");
			finder.setParam("cityId",appUser.getCityId());
		}
		if(null!=appUser.getId() && 0!=appUser.getId()){
			finder.append(" and id=:id ");
			finder.setParam("id",appUser.getId());
		}
		Map<String,Object> object = queryForObject(finder);
		if (object.containsKey("totalBalance") && null!=object.get("totalBalance")){
			au.setTotalBalance(Double.valueOf(object.get("totalBalance").toString()));
		}else{
			au.setTotalBalance(0d);
		}
		if (object.containsKey("totalYebBalance") && null!=object.get("totalYebBalance")){
			au.setTotalYebBalance(Double.valueOf(object.get("totalYebBalance").toString()));
		}else{
			au.setTotalYebBalance(0d);
		}
		if (object.containsKey("totalFreezeYebBalance") && null!=object.get("totalFreezeYebBalance")){
			au.setTotalFreezeYebBalance(Double.valueOf(object.get("totalFreezeYebBalance").toString()));
		}else{
			au.setTotalFreezeYebBalance(0d);
		}
		return au;
	}

	@Override
	public void sendMessage()throws Exception{
		Finder finder = new Finder("select * from  t_app_user");
		List<AppUser> appUsers = queryForList(finder,AppUser.class);
		for (int i=0;i<appUsers.size(); i++){
			AppUser appUser = appUsers.get(i);
			String content = "喜讯：\n" +
					"“感恩父亲节 营普来送礼”活动\n" +
					"在活动期间线上APP营普金服客户\n" +
					"投多少送多少，投一万送一万，投十万送十万！\n" +
					"让您收益翻倍！\n" +
					"理财让生活变得更美好！\n" +
					"活动截止时间2017年6月17至19日\n" +
					"详情请关注营普金服公告或咨询客服。电话：400-969-1811" +
					"【营普金服】";
			//SMSUtil.SendSMS("178","yingpucaifu","yingpucaifu",appUser.getPhone(), content);
			Sms sms = new Sms();
			sms.setPhone(appUser.getPhone());
			sms.setCreateTime(new Date());
			sms.setCreateTime(new Date());
			sms.setType(6);
			smsService.save(sms);
}
	}

	public static void main(String[] args) throws Exception {
//		AppUserServiceImpl appUserService = new AppUserServiceImpl();
//		System.out.println(appUserService.getRand());
		Double ma = Math.random()*900000+100000 ;
		System.out.println(ma);

	}


	@Override
	public Integer isPartner(Integer id, String type) throws Exception {
		Integer result = null;
		AppUser appUser = super.findById(id,AppUser.class);
		if ("1".equals(type)) {//合伙人
			appUser.setIsPartner("5");
			appUser.setPartnerCancelTime(null);
			appUser.setPartnerStartTime(new Date());
			result=super.update(appUser);
		}else if ("2".equals(type)){//删除合伙人
			appUser.setIsPartner("3");
			appUser.setPartnerStartTime(null);
			appUser.setPartnerCancelTime(new Date());
			result=super.update(appUser);;
		}else if ("3".equals(type)){//删除合伙人
			appUser.setIsPartner("7");
//			appUser.setPartnerStartTime(null);
//			appUser.setPartnerCancelTime(new Date());
			result=super.update(appUser);;
		}
		return result;
	}

	@Override
	public List partnerList(Page page,Map<String, Object> map)throws Exception {
		Finder finder = new Finder("SELECT\n" +
				"\ttau.*,IF(o.money IS NULL ,0,o.money) as money,o.id as orderId\n" +
				"FROM\n" +
				"\t(\n" +
				"\t\tSELECT\n" +
				"\t\t\tid,realName,phone,inviteNum\n" +
				"\t\tFROM\n" +
				"\t\t\tt_app_user\n" +
				"\t\tWHERE\n" +
				"\t\t\tisPartner = 3 or isPartner = 7\n");
		if(null != map){
			if(null != map.get("userName") &&StringUtils.isNotBlank(map.get("userName").toString())){
				finder.append(" and realName Like :userName ");
				finder.setParam("userName","%"+map.get("userName").toString()+"%");
			}
			if(null != map.get("phone") &&StringUtils.isNotBlank(map.get("phone").toString())){
				finder.append(" and phone =:phone ");
				finder.setParam("phone",map.get("phone").toString());
			}
		}
		finder.append("\t) tau\n" +
				"LEFT JOIN t_order o ON tau.id = o.userId\n" +
				"AND o.type = 14 AND o.`status` = 1 GROUP BY tau.id");
		List list = queryForList(finder,AppUser.class,page);

		return list;
	}

	@Override
	public List partnerFenxiaoList(Integer userId,String month)throws Exception {
		/**
		 * SELECT aa.*,IF(SUM(up.money) IS NULL ,0,SUM(up.money)) as money  FROM
		 (SELECT
		 tau.realName,
		 tau.header,
		 IF(tau.inviteNum IS NULL ,0,tau.inviteNum) inviteNum,
		 tpu.`level`,
		 tau.id
		 FROM
		 t_partner_user tpu
		 LEFT JOIN t_app_user tau ON tau.id=tpu.levelId
		 WHERE
		 1 = 1
		 AND tpu.lvParentId = 111) aa  LEFT JOIN t_user_project up ON aa.id = up.userId
		 AND up.createTime LIKE "%2017-05%"
		 GROUP BY aa.id
		 */
		Finder finder = new Finder("SELECT aa.*,IF(SUM(up.money) IS NULL ,0,SUM(up.money)) as money  FROM\n" +
				"(SELECT\n" +
				"\ttau.realName,\n" +
				"\ttau.header,\n" +
				"\tIF(tau.inviteNum IS NULL ,0,tau.inviteNum) inviteNum,\n" +
				"\ttpu.`level`,\n" +
				"\ttau.id\n" +
				"FROM\n" +
				"\tt_partner_user tpu\n" +
				"LEFT JOIN t_app_user tau ON tau.id=tpu.levelId \n" +
				"WHERE\n" +
				"\t1 = 1\n" +
				"AND tpu.lvParentId =:userId) aa  LEFT JOIN t_user_project up ON aa.id = up.userId   \n" +
				"AND up.createTime LIKE :date" +
				" GROUP BY aa.id ");
		finder.setParam("userId",userId);
		finder.setParam("date","%"+month+"%");
		List list = queryForList(finder,AppUser.class);
		return list;
	}

	@Override
	public Map<String,Object> fenxiaoTotalList(Integer userId,String month)throws Exception {
		/**
		 * SELECT
		 IF(SUM(tup.money) IS NULL ,0,SUM(tup.money)) as money,
		 FORMAT(IF(SUM(tup.money*tpd.partnerPercent) IS NULL ,0,SUM(tup.money*tpd.partnerPercent)),1) as percent,
		 tpu.userId
		 FROM
		 t_partner_user tpu
		 LEFT JOIN t_user_project tup ON tpu.levelId = tup.userId
		 LEFT JOIN t_project_deadline tpd ON tpd.deadline = tup.deadLine
		 WHERE
		 tpu.userId = 111
		 AND tup.createTime LIKE "%2017-05%"
		 */
		Finder finder = new Finder("SELECT\n" +
				"\tIF(SUM(tup.money) IS NULL ,0,SUM(tup.money)) as money,\n" +
				"\tFORMAT(IF(SUM(tup.money*tpd.partnerPercent) IS NULL ,0,SUM(tup.money*tpd.partnerPercent)),1) as percent\n"+
				"FROM\n" +
				"\tt_partner_user tpu\n" +
				"LEFT JOIN t_user_project tup ON tpu.levelId = tup.userId\n" +
				"LEFT JOIN t_project_deadline tpd ON tpd.deadline = tup.deadLine\n" +
				"WHERE\n" +
				"\ttpu.level <= 6 And tpu.userId =:userId\n" +
				"AND tup.createTime LIKE :date");
		finder.setParam("userId",userId);
		finder.setParam("date","%"+month+"%");
		Map<String,Object> list = queryForObject(finder);
		double d1 = new DecimalFormat().parse(list.get("percent").toString()).doubleValue();
		list.put("percent",String.valueOf(d1));
		Finder fin = new Finder("select IF(SUM(money) IS NULL ,0,SUM(money)) as percentMoney from t_user_yeb_history where type = 9 and userId=:userId ");
		fin.setParam("userId",userId);
		Map<String,Object> objectMap = queryForObject(fin);
		list.putAll(objectMap);
		Finder find = new Finder("select IF(money IS NULL ,0,money) as monthMoney from t_user_yeb_history where type = 9 and userId=:userId AND createTime LIKE :date ");
		find.setParam("userId",userId);
		String monthDate = DateUtils.convertDate2String("yyyy-MM", org.apache.commons.lang.time.DateUtils.addMonths(DateUtils.convertString2Date("yyyy-MM",month),1));
		find.setParam("date","%"+monthDate +"%");
		Map<String,Object> object = queryForObject(find);
		if(null!=object){
			list.putAll(object);
		}else {
			list.put("monthMoney",0);
		}

		return  list;
	}

/*	@Override
	public Integer sendMoney(Integer id,Integer orderId) throws Exception {
		AppUser appUser = super.findById(id,AppUser.class);
		Order order = orderService.findById(orderId,Order.class);
		UserYebHistory userYebHistory = new UserYebHistory();
		userYebHistory.setUserId(id);
		userYebHistory.setMoney(order.getMoney());
		userYebHistory.setCreateTime(new Date());
		userYebHistory.setType(9);
		userYebHistory.setOrderId(order.getId());
		if(null!=appUser.getYebBalance()){
			userYebHistory.setNowBalance(appUser.getYebBalance() + order.getMoney());
		}else {
			userYebHistory.setNowBalance(order.getMoney());
		}
		userYebHistoryService.save(userYebHistory);
		if(null!=appUser.getYebBalance()){
			appUser.setYebBalance(appUser.getYebBalance() + order.getMoney());
		}else {
			appUser.setYebBalance(order.getMoney());
		}
		order.setStatus(2);
		orderService.update(order,true);
		return update(appUser,true);
	}*/


	@Override
	public List fenxiaoList(Integer userId,Map<String,Object> map)throws Exception {
		Finder finder = new Finder("SELECT\n" +
				"\t\t tau.realName,\n" +
				"\t\t tau.header,\n" +
				"\t\t tau.phone,\n" +
				"\t\t IF(tau.inviteNum IS NULL ,0,tau.inviteNum) inviteNum,\n" +
				"\t\t tpu.`level`,\n" +
				"\t\t tau.id\n" +
				"\t\t FROM\n" +
				"\t\t t_partner_user tpu\n" +
				"\t\t LEFT JOIN t_app_user tau ON tau.id=tpu.levelId\n" +
				"\t\t WHERE\n" +
				"\t\t 1 = 1\n" +
				"\t\tAND tpu.level <= 6 AND tpu.userId =:userId");
		finder.setParam("userId",userId);
		if(null!=map&&map.size()!= 0){
			if(null != map.get("userName") &&StringUtils.isNotBlank(map.get("userName").toString())){
				finder.append(" and tau.realName Like :userName ");
				finder.setParam("userName","%"+map.get("userName").toString()+"%");
			}
			if(null != map.get("level") &&StringUtils.isNotBlank(map.get("level").toString())){
				finder.append(" and tpu.`level` =:level ");
				finder.setParam("level",Integer.parseInt(map.get("level").toString()));
			}
			if(null != map.get("phone") &&StringUtils.isNotBlank(map.get("phone").toString())){
				finder.append(" and tau.phone =:phone ");
				finder.setParam("phone",map.get("phone").toString());
			}
		}

		List list = queryForList(finder,AppUser.class);
		return list;
	}


/*	@Override
	public Integer addYebBalance(int userId,Double money)throws Exception {
		AppUser appUser = findById(userId,AppUser.class);
		if(null!=appUser){
			if(null !=appUser.getYebBalance()){
				appUser.setYebBalance(appUser.getYebBalance()+money);
			}else {
				appUser.setYebBalance(money);
			}
			UserYebHistory userYebHistory = new UserYebHistory();
			userYebHistory.setUserId(userId);
			userYebHistory.setMoney(money);
			userYebHistory.setCreateTime(new Date());
			userYebHistory.setType(11);
			if(null!=appUser.getYebBalance()){
				userYebHistory.setNowBalance(appUser.getYebBalance() +money);
			}else {
				userYebHistory.setNowBalance(money);
			}
			userYebHistoryService.save(userYebHistory);
			return update(appUser,true);
		}
		return null;
	}
*/
	//type 1 直接邀请人 2间接邀请人 3.已投资用户  4.为实名用户
	@Override
	public List<AppUser> findUserRecommend(AppUser appUser,int type,Page page)  throws Exception{
		// TODO Auto-generated method stub
		List<AppUser>  alist= new ArrayList<AppUser>();
		try {
			if(type==1){
				Finder finder=new Finder("select * from t_app_user where parentId ="+appUser.getId());
				alist =	super.queryForList(finder, AppUser.class, page);
			}else if(type==2){
				Finder finder=new Finder("select * from t_app_user where parentId ="+appUser.getId());
				alist =	super.queryForList(finder, AppUser.class, page);
				if(alist!=null&&alist.size()>0){
				List<AppUser>  alist2= new ArrayList<AppUser>();
				List<AppUser>  alist3= new ArrayList<AppUser>();
				for (AppUser appUser2 : alist) {
					finder=new Finder("select * from t_app_user where parentId ="+appUser2.getId());
					alist3=	super.queryForList(finder, AppUser.class, page);
					if(alist3!=null)alist2.addAll(alist3);
				}
				alist=alist2;
				}
			}else if(type==3){
				Finder finder=new Finder("select * from t_app_user  as au where au.parentId = :parentId and  (select count(1) from t_user_project where userid=au.id)");
				finder.setParam("parentId", appUser.getId());
				finder.setParam("isIdCard", "是");
				alist =	super.queryForList(finder, AppUser.class, page);
				if(alist!=null&&alist.size()>0){
					List<AppUser>  alist2= new ArrayList<AppUser>();
					List<AppUser>  alist3= new ArrayList<AppUser>();
					for (AppUser appUser2 : alist) {
						finder=new Finder("select * from t_app_user  as au where au.parentId = :parentId and  (select count(1) from t_user_project where userid=au.id)");
						finder.setParam("parentId", appUser2.getId());
						/*finder.setParam("isIdCard", "是");*/
						alist3=	super.queryForList(finder, AppUser.class, page);
						if(alist3!=null)	alist2.addAll(alist3);
					}
					alist.addAll(alist2);
				}
			}else if(type==4){
				Finder finder=new Finder("select * from t_app_user where  parentId = :parentId and isIdCard=:isIdCard");
				finder.setParam("parentId", appUser.getId());
				finder.setParam("isIdCard", "否");
				alist =	super.queryForList(finder, AppUser.class, page);
				if(alist!=null&&alist.size()>0){
				List<AppUser>  alist2= new ArrayList<AppUser>();
				List<AppUser>  alist3= new ArrayList<AppUser>();
				for (AppUser appUser2 : alist) {
					finder=new Finder("select * from t_app_user where parentId = :parentId and isIdCard=:isIdCard");
					finder.setParam("parentId", appUser2.getId());
					finder.setParam("isIdCard", "否");
					alist3=	super.queryForList(finder, AppUser.class, page);
					if(alist3!=null)	alist2.addAll(alist3);
				}
				alist.addAll(alist2);
				}
			}
			/*for (int i = 0; i < array.length; i++) {
				
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return alist;
	}

	@Override
	public void saveDetail(Integer type, AppUser appUser,
			AppTransReqData appTransReqData) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDetailChange(AppTransRspData appTransRspData)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer sendMoney(Integer id, Integer orderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addYebBalance(int userId, Double money) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser wxLogin(String code) throws Exception {
		 ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("wechatpay");
		// TODO Auto-generated method stub
		//AccessToken accessToken=WxPayUtil.getAccessToken(code);
	JSONObject jsonObject = WxPayUtil.httpRequest(
				"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + RESOURCE_BUNDLE.getString("AppId") + "&secret="
						+  RESOURCE_BUNDLE.getString("AppSecre") + "&code=" + code + "&grant_type=authorization_code",
				"POST", null);
	JSONObject jo=WxPayUtil.httpRequest(
			"https://api.weixin.qq.com/sns/userinfo?access_token=" + jsonObject.getString("access_token") + "&openid="
					+  jsonObject.getString("openid"),
			"GET", null);
		AppUser appUser=new AppUser();
		appUser.setWxOpenId(jo.getString("openid"));
		appUser=  super.queryForObject(appUser);
		if(appUser==null){
			appUser=new AppUser();
			appUser.setWxOpenId(jo.getString("openid"));
			appUser.setName(com.cz.yingpu.frame.util.EmojiFilter.filterEmoji(new String(jo.getString("nickname"))));
			appUser.setSex( jo.getString("sex"));
			appUser.setHeader( jo.getString("headimgurl"));
			appUser.setId(Integer.parseInt(super.save(appUser)+""));
			return appUser;
		}
		return appUser;
	}
}
