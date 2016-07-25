/*
 * 文件名： PageUtils.java
 * 
 * 创建日期： 2010-2-25
 *
 * Copyright(C) 2010, by neo.
 *
 * 原始作者: wangmeng
 *
 */
package com.shopin.core.framework.base.vo;

import java.util.List;

/**
 * Page分页工具类
 *
 * 
 *
 * @version 
 *
 * @since 2010-2-25
 */
public class PageUtils {

	/**
	 * 
	 * 功能描述：根据页码(pageNumber)和页面大小(fetchSize)，得到当前页的第一条记录序号
	 *
	 * @param pageNumber 当前页码
	 * 
	 * @param fetchSize 页面大小（每页行数）
	 * 
	 * @return 当前页的第一条记录序号
	 *
	 * 
	 *
	 * @since 2010-2-25
	 *
	 * 
	 */
	public static int getFirstResult(int pageNumber,int fetchSize) {
		
		if(fetchSize <= 0) throw new IllegalArgumentException("[fetchSize] must great than zero");
		return (pageNumber - 1) * fetchSize;
	}
	
	/**
	 * 
	 * 功能描述：计算以currentPageNumber为中心点的前后count个页码
	 *
	 * @param currentPageNumber 当前页码
	 * 
	 * @param lastPageNumber 最后一页的页码数
	 * 
	 * @param count 需要计算的页码总个数
	 * 
	 * @return 连接页码的List，List的size可能为0
	 *
	 * 
	 *
	 * @since 2010-2-25
	 *
	 * 
	 */
	public static List<Integer> generateLinkPageNumbers(int currentPageNumber, int lastPageNumber,int count) {
		
		int mod = count % 2;
		
		int startPageNumber = 0;
		
		int endPageNumber = 0;
		
		if(mod == 1) {
			
			int avg = count / 2;
			
			startPageNumber = currentPageNumber - avg;
			if(startPageNumber <= 0) {
				startPageNumber = 1;
			}
			
			endPageNumber = currentPageNumber + avg;
			if(endPageNumber > lastPageNumber) {
				endPageNumber = lastPageNumber;
			}
		}
		
		if(endPageNumber - startPageNumber < count) {
			startPageNumber = endPageNumber - count;
			if(startPageNumber <= 0 ) {
				startPageNumber = 1;
			}
		}
		
		java.util.List<Integer> result = new java.util.ArrayList<Integer>();
		for(int i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Integer(i));
		}
		return result;
	}
	
	/**
	 * 
	 * 功能描述：计算记录的最后一页页码数值
	 *
	 * @param totalElements 总行数
	 * 
	 * @param fetchSize 页面大小（每页行数）
	 * 
	 * @return 最后一页的页码数值
	 *
	 * 
	 *
	 * @since 2010-2-25
	 *
	 * 
	 */
	public static int computeLastPageNumber(long totalElements,int fetchSize) {
		
		long result = totalElements % fetchSize == 0 ? 
				totalElements / fetchSize 
				: totalElements / fetchSize + 1;
		if(result <= 1)
			result = 1;
		return (int)result;
	}
	
	/**
	 * 
	 * 功能描述：计算页码；将不合法的页码值转换为合法
	 * 
	 * <br>如果pageNumber < 1 返回 1
	 * 
	 * <br>如果pageNumber > 最大页码值 返回最大页码值
	 *
	 * @param pageNumber 当前页码
	 * 
	 * @param fetchSize 页面大小（每页行数）
	 * 
	 * @param totalElements 总行数
	 * 
	 * @return 正确页码数
	 *
	 * 
	 *
	 * @since 2010-2-25
	 *
	 * 
	 */
	public static int computePageNumber(int pageNumber, int fetchSize,long totalElements) {
		
		if(pageNumber <= 1) {
			return 1;
		}
    	if (Integer.MAX_VALUE == pageNumber
				|| pageNumber > computeLastPageNumber(totalElements,fetchSize)) { //last page
			return computeLastPageNumber(totalElements,fetchSize);
		}
		return pageNumber;
    }
	
	
	// public static void main(String args[]) {
	// for(Integer integer : PageUtils.generateLinkPageNumbers(10, 100, 6)){
	// System.out.println(integer);
	// }
	// }
}
