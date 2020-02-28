package com.sky.base.jdbc.spring.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

/**
 * @author allinpay
 * @author lizp
 */
public class BeanTools {

	public static boolean hasBit(int flag, int bit) {
		return (flag & bit) == bit;
	}

	public static Object readProperty(Object o, Method m) {
		try {
			return m.invoke(o);
		} catch (Exception e) {
			return null;
		}
	}

	public static PropertyDescriptor findPropertyDescriptor(PropertyDescriptor[] pda, String name) {
		int i;
		for (i = 0; i < pda.length; ++i) {
			PropertyDescriptor pd = pda[i];
			if (pd.getName().equals(name)) {
				return pd;
			}
		}
		return null;
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
		return BeanUtils.getPropertyDescriptors(clazz);
	}

	public static boolean isDigit(Class<?> clz) {
		if (clz.isAssignableFrom(Character.class)) {
			return true;
		} else if (clz.isAssignableFrom(Byte.class)) {
			return true;
		} else if (clz.isAssignableFrom(Short.class)) {
			return true;
		} else if (clz.isAssignableFrom(Integer.class)) {
			return true;
		} else if (clz.isAssignableFrom(Long.class)) {
			return true;
		} else if (clz.isAssignableFrom(Float.class)) {
			return true;
		} else if (clz.isAssignableFrom(Double.class)) {
			return true;
		}
		return false;
	}

	public static Object toObject(Class<?> clz, long val) {
		if (clz.isAssignableFrom(Character.class)) {
			return new Character((char) val);
		} else if (clz.isAssignableFrom(Byte.class)) {
			return new Byte((byte) val);
		} else if (clz.isAssignableFrom(Short.class)) {
			return new Short((short) val);
		} else if (clz.isAssignableFrom(Integer.class)) {
			return new Integer((int) val);
		} else if (clz.isAssignableFrom(Long.class)) {
			return new Long(val);
		} else if (clz.isAssignableFrom(Float.class)) {
			return new Float(val);
		} else if (clz.isAssignableFrom(Double.class)) {
			return new Double(val);
		}
		return null;
	}

	public static Object getMinValue(Class<?> clz) {
		if (clz.isAssignableFrom(Character.class)) {
			return new Character(Character.MIN_VALUE);
		} else if (clz.isAssignableFrom(Byte.class)) {
			return new Byte(Byte.MIN_VALUE);
		} else if (clz.isAssignableFrom(Short.class)) {
			return new Short(Short.MIN_VALUE);
		} else if (clz.isAssignableFrom(Integer.class)) {
			return new Integer(Integer.MIN_VALUE);
		} else if (clz.isAssignableFrom(Long.class)) {
			return new Long(Long.MIN_VALUE);
		} else if (clz.isAssignableFrom(Float.class)) {
			return new Float(Float.MIN_VALUE);
		} else if (clz.isAssignableFrom(Double.class)) {
			return new Double(Double.MIN_VALUE);
		}
		return null;
	}

	public static void iteratProperty(Object bean, BeanPropertyHandler propertyHandler) {
		PropertyDescriptor[] pda = getPropertyDescriptors(bean.getClass());
		for (PropertyDescriptor pd : pda) {
			if ("class".equals(pd.getName())) {
				continue;
			}
			Object val = readProperty(bean, pd.getReadMethod());
			propertyHandler.handle(bean, pd.getName(), val);
		}
	}

	public static boolean ignore(Object o) {
		if (o == null) {
			return true;
		}
		if (o.equals(getMinValue(o.getClass()))) {
			return true;
		}
		return false;
	}

	public void initObj(Object o) {
		int i;
		PropertyDescriptor[] pds = getPropertyDescriptors(o.getClass());
		PropertyDescriptor pd;
		for (i = 0; i < pds.length; ++i) {
			pd = pds[i];
			Method wm = pd.getWriteMethod();
			if (wm == null) {
				continue;
			}
			try {
				Class<?> clz = pd.getPropertyType();
				Object[] val = new Object[1];
				if (clz.isAssignableFrom(Boolean.class)) {
					val[0] = new Boolean(false);
				} else {
					val[0] = getMinValue(clz);
				}
				wm.invoke(o, val);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals(s.toUpperCase()) && Character.isLetter(s.charAt(0))) {
					result.append("_");
					result.append(s.toLowerCase());
				} else {
					result.append(s);
				}
			}

		}
		return result.toString();
	}

	public static int indexOfIg(String[] strs, String toFind) {
		if (strs == null) {
			return -1;
		}
		for (int i = 0; i < strs.length; ++i) {
			if (toFind == null && strs[i] == null || toFind.equalsIgnoreCase(strs[i])) {
				return i;
			}
		}
		return -1;
	}
}
