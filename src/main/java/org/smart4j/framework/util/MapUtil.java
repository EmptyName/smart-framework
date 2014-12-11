package org.smart4j.framework.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.mvc.bean.Params;
import org.smart4j.framework.orm.EntityHelper;

/**
 * 映射操作工具类
 *
 * @author huangyong
 * @since 1.0
 */
public class MapUtil {

	private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

	/**
	 * 
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public static boolean validFieldMap(Class<?> entityClass, Params params) {
		Map<String, String> fields = EntityHelper.getFieldMap(entityClass);
		Map<String, Object> fieldMap = params.getFieldMap();
		List<String> keys = new ArrayList<String>();
		if (isNotEmpty(fields) && isNotEmpty(fieldMap)) {
			for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
				if (!fields.containsKey(entry.getKey())) {
					keys.add(entry.getKey());
				}
			}
		}
		if (keys.size() > 0) {
			for(String key : keys) {
				fieldMap.remove(key);
				logger.warn("Remove invalid field[" + key + "] of class: " + entityClass.getName());
			}
			return true;
		}
		return false;
	}

    /**
     * 判断 Map 是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }

    /**
     * 判断 Map 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 转置 Map
     */
    public static <K, V> Map<V, K> invert(Map<K, V> source) {
        Map<V, K> target = null;
        if (isNotEmpty(source)) {
            target = new LinkedHashMap<V, K>(source.size());
            for (Map.Entry<K, V> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
        }
        return target;
    }
}
