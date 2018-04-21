package com.cz.yingpu.frame.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LotteryUtil {
	/**
	 * 抽奖
	 *
	 * @param orignalRates 原始的概率列表，保证顺序和实际物品对应
	 * @return 物品的索引
	 */
	public static int lottery(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
	
	public static int getJD(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
	
	public static void main(String[] args) {
		/*List<Prize> plist=new ArrayList<>();
		plist.add(new Prize(1,"iphonex", "0.8"));
		plist.add(new Prize(2,"100元", "0.1"));
		plist.add(new Prize(3,"皮肤", "0.05"));
		plist.add(new Prize(4,"红包", "0.1"));
		plist.add(new Prize(5,"大红包", "0.05"));
		plist.add(new Prize(6,"优惠券", "0.01"));
		plist.add(new Prize(7,"优惠券", "0.02"));
		plist.add(new Prize(8,"优惠券", "0.02"));
		plist.add(new Prize(9,"优惠券", "0.00"));
		List<Double> orignalRates = new ArrayList<Double>(plist.size());
		for (Prize prize : plist) {
			double probability = Double.parseDouble(prize.getProbability());
			if (probability < 0) {
				probability = 0;
			}
			orignalRates.add(probability);
		}
			*/
		/*int count=0;
		for (int i = 0; i < 1000000; i++) {
			int index=LotteryUtil.lottery(orignalRates);
			if(plist.get(index).getId()==9){
				count++;
			}
			System.out.println("奖品名称"+plist.get(index).getPrizename()+"概率"+plist.get(index).getProbability());
		}*/
//		System.out.println("次数"+count);
	}
	
}
