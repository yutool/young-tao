/**
 * 已完成
 */
const COMPLETED = 0;

/**
 * 创建中
 */
const CREATING = 1;

/**
 * 待付款
 */
const PAYMENT = 2;

/**
 * 待发货
 */
const DELIVERY = 3;

/**
 * 待收货
 */
const RECEIVING = 4;

/**
 * 待评价
 */
const COMMENT = 5;

/**
 * 退货中
 */
const RETURN = 6;


/**
 * 创建失败
 */
const FAILED = 9;

/**
 * 关闭
 */
const CLOSE = 10;

export default {
	COMPLETED,
	CREATING,
	PAYMENT,
	RECEIVING,
	DELIVERY,
	COMMENT,
	RETURN,
	FAILED,
	CLOSE
}