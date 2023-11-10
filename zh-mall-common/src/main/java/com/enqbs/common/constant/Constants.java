package com.enqbs.common.constant;

public class Constants {

    /* 数据库软删除标识 */
    public static final Integer IS_DELETE = 1;

    public static final Integer IS_NOT_DELETE = 0;

    public static final Integer PRODUCT_SHELVES = 1;

    public static final Integer PRODUCT_NOT_SHELVES = 0;

    /* web */
    public static final String SUCCESS = "success";

    public static final String SERVER_ERROR = "server_error";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CHAR_ENCODE_UTF8 = "UTF-8";

    public static final String PRESENT = "null";

    /* jwt 标识 */
    public static final String USER_TOKEN = "user_token";

    public static final String SYS_USER_TOKEN = "sys_user_token";

    /* redis key、script */
    public static final String REDIS_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public static final String USER_REDIS_KEY = "user:token:%s";

    public static final String USER_CART_REDIS_KEY = "user:cart:%s";

    public static final String SYS_USER_REDIS_KEY = "sys:user:token:%s";

    public static final String PRODUCT_CATEGORY_LIST = "product:category:list";

    public static final String PRODUCT_CATEGORY_LIST_LOCK = "product:category:list.lock";

    public static final String ORDER_TOKEN_REDIS_KEY = "order:token:%s";

    /* rabbitmq */
    public static final String EXCHANGE_TYPE_DELAYED = "x-delayed-message";

    public static final String EXCHANGE_TYPE_DIRECT = "direct";

    public static final Integer MESSAGE_SEND_SUCCESS = 1;

    public static final Integer MESSAGE_SEND_ERROR = -1;

    /* 登录方式标识 */
    public static final String LOGIN_TYPE_USERNAME = "username";

    public static final String LOGIN_TYPE_PHONE = "phone";

    public static final String LOGIN_TYPE_EMAIL = "Email";

    public static final String LOGIN_TYPE_ALIPAY_PC = "alipay_pc";

}
