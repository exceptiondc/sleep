package com.cz.yingpu.system.refundUtil;

/**
 * 还款方式的计算，算出每月应还的金额
 * Created by Michael on 2017/4/13.
 */
public class RefundUtil {

    /**
     * 构造函数
     * @param type 分类:1按月付息/到期还本  2等额本息  3到期还本还息
     * @param loan 贷款金额（单位：元）
     * @param periods  借款周期（单位:月）
     */
    public RefundUtil(Integer type,Double loan,Integer periods) {

        if(type == 1){

        }else if (type == 2){

        }else if (type==3){

        }
    }
}
