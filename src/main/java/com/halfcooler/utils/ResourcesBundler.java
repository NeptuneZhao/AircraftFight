package com.halfcooler.utils;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class ResourcesBundler
{
	private static Method cachedGetBundleMethod = null;

	public String GetMessage(String key)
	{
		ResourceBundle bundle;
		try
		{
			Class<?> thisClass = this.getClass();
			if (cachedGetBundleMethod == null)
			{
				Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
				cachedGetBundleMethod = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
			}
			bundle = (ResourceBundle) cachedGetBundleMethod.invoke(null, "language", thisClass);
		} catch (Exception e)
		{
			bundle = ResourceBundle.getBundle("language");
		}
		return bundle.getString(key);
	}
}
