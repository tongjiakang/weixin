package com.vanke.mhj.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Evan Yang 2016年2月4日
 */
public final class DataConvert
{

	public static final Logger LOG = LoggerFactory.getLogger(DataConvert.class);
	
	public static List<Long> convertFromString(String str) 
	{
		List<Long> datas = Lists.newArrayList();
		try
		{
			String[] numbers = str.split(",");

			for (int i = 0; i < numbers.length; i++)
			{
				if(isNumber(numbers[i]))
				{
					datas.add(new Long(numbers[i]));
				}
				else
				{
					throw new Exception("不能讲字符串: " + numbers[i] + " 转换为数字。");
				}
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		return datas;
	}

	private static boolean isNumber(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}

}
