package com.common.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Evan
 * @since 2015年9月21日
 */

public final class CollectionsUtil {

	/**
	 * 返回不同对象的HashMap
	 * 
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 返回不同对象的ArrayList
	 * 
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

}
