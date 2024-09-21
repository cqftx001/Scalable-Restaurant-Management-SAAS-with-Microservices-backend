
package com.itheima.restkeeper.constant;


/**
 * @Description 静态变量
 */
public class SuperConstant {

	/**
	 * 主键名称
	 */
	public static final String id = "id";

	/**
	 * 常量是
	 */
	public static final String YES = "YES";

	/**
	 * @Description 前端显示布局
	 */
	public static final String COMPONENT_LAYOUT="Layout";

	/**
	 * 常量否
	 */
	public static final String NO = "NO";


	/**
	 * 树形根节点父Id
	 */
	public static final Long ROOT_PARENT_ID = -1L;

	/**
	 * 树形根节点父Id
	 */
	public static final String ROOT_PARENT_NAME = "资源管理";

	/**
	 * 常量菜单
	 */
	public static final String MENU = "MENU";

	/**
	 * jwt请求key
	 */
	public static final String JWT_TOKEN_HEADER = "jwtToken";


	/**
	 * store请求key
	 */
	public static final String STORE_TOKEN_HEADER = "storeToken";


	/***
	 * 当前用户
	 */
	public static final String CURRENT_USER ="current_user";

	/***
	 * 当前门店
	 */
	public static final String CURRENT_STORE ="current_store";


	/***
	 * 商户号ID字段
	 */
	public static final String ENTERPRISE_ID ="enterprise_id";

	/***
	 * 商户号ID字段
	 */
	public static final String STORE_ID ="store_id";

	/***
	 * 桌台空闲
	 */
	public static final String FREE = "FREE";

	/***
	 * 桌台使用
	 */
	public static final String USE = "USE";

	/***
	 * 订单项目移除
	 */
	public static final  String OPERTION_TYPE_REMOVE = "REMOVE";

	/***
	 * 订单项目添加
	 */
	public static final  String OPERTION_TYPE_ADD = "ADD";


	/***
	 * 企业状态:试用
	 */
	public static final String TRIAL = "TRIAL";
	/***
	 * 企业状态:正式
	 */
	public static final String OFFICIAL = "OFFICIAL";
	/***
	 * 企业状态:停用
	 */
    public static final String STOP = "STOP";
	/***
	 * 企业状态:拒绝
	 */
	public static final String REFUSE = "REFUSE";

	/***
	 * 阿里云短信
	 */
	public static final String ALIYUN_SMS = "ALIYUN_SMS";

	/***
	 * 腾讯云短信
	 */
	public static final String TENCENT_SMS = "TENCENT_SMS";

	/***
	 * 腾讯云短信
	 */
	public static final String BAIDU_SMS = "BAIDU_SMS";

	//邮件负载均衡
    public static final String HASH = "HASH";
	public static final String RANDOM = "RANDOM ";
	public static final String ROUND_ROBIN ="ROUND_ROBIN" ;
	public static final String WEIGHT_RANDOM = "WEIGHT_RANDOM";
	public static final String WEIGHT_ROUND_ROBIN = "WEIGHT_ROUND_ROBIN";
	//发送状态
    public static final String SENDING ="SENDING" ;
    //审核状态:审核中
	public static final String STATUS_IN_AUDIT = "IN_AUDIT";
	//审核通过
    public static final String STATUS_PASS_AUDIT = "PASS_AUDIT";
    //审核失败
	public static final String STATUS_FAIL_AUDIT = "FAIL_AUDIT";

	//站点类型
    public static final String WEBSITE = "webSite";
	public static final String APP_WEBSITE ="appWebSite" ;

	//登录类型
	public static final String USERNAME_LOGIN = "usernameLogin";
	public static final String MOBIL_LOGIN = "mobilLogin";
}
