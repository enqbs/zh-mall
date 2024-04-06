package com.enqbs.common.constant;

public class Constants {

    /* 数据库软删除标识 */
    public static final Integer IS_DELETE = 1;

    public static final Integer IS_NOT_DELETE = 0;

    public static final Integer PRODUCT_SHELVES = 1;

    public static final Integer PRODUCT_NOT_SHELVES = 0;

    public static final Integer PRODUCT_CATEGORY_HOME = 1;

    public static final Integer PRODUCT_CATEGORY_NOT_HOME = 0;

    public static final Integer PRODUCT_CATEGORY_NAVI = 1;

    public static final Integer PRODUCT_CATEGORY_NOT_NAVI = 0;

    public static final Integer COUPON_VALID = 1;

    public static final Integer COUPON_INVALID = 0;

    public static final Integer COUPON_USED = 1;

    public static final Integer COUPON_UNUSED = 0;

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

    public static final String USER_REDIS_KEY = "user:info:%s";

    public static final String USER_CART_REDIS_KEY = "user:cart:%s";

    public static final String USER_COUPON_LOCK = "user:coupon.lock:%s";

    public static final String SYS_USER_REDIS_KEY = "sys:user:info:%s";

    public static final String PRODUCT_CATEGORY_LIST = "product:category:list";

    public static final String PRODUCT_CATEGORY_LIST_LOCK = "product:category:list.lock";

    public static final String PRODUCT_CATEGORY_LIST_HOME = "product:category:list.home";

    public static final String PRODUCT_CATEGORY_LIST_HOME_LOCK = "product:category:list.home.lock";

    public static final String PRODUCT_CATEGORY_LIST_NAVI = "product:category:list.navi";

    public static final String PRODUCT_CATEGORY_LIST_NAVI_LOCK = "product:category:list.navi.lock";

    public static final String PRODUCT_CATEGORY_LIST_BANNER = "product:category:list.banner";

    public static final String PRODUCT_CATEGORY_LIST_BANNER_LOCK = "product:category:list.banner.lock";

    public static final String ORDER_TOKEN_REDIS_KEY = "order:token:%s";

    public static final String ORDER_CONFIRM_REDIS_KEY = "order:confirm:%s";

    /* rabbitmq */
    public static final String EXCHANGE_TYPE_DELAYED = "x-delayed-message";

    public static final Integer MESSAGE_SEND_SUCCESS = 1;

    public static final Integer MESSAGE_SEND_ERROR = -1;

}
