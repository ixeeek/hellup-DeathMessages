package me.ixek.hellupdeathmessages.other;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {
	private static String versionString;

	private static Map<String, Class<?>> loadedNMSClasses = new HashMap<>();

	private static Map<String, Class<?>> loadedOBCClasses = new HashMap<>();

	private static Map<Class<?>, Map<String, Method>> loadedMethods = new HashMap<>();

	private static Map<Class<?>, Map<String, Field>> loadedFields = new HashMap<>();

	public static String getVersion() {
		if (versionString == null) {
			String name = Bukkit.getServer().getClass().getPackage().getName();
			versionString = String.valueOf(name.substring(name.lastIndexOf('.') + 1)) + ".";
		}
		return versionString;
	}

	public static Class<?> getNMSClass(String nmsClassName) {
		Class<?> clazz;
		if (loadedNMSClasses.containsKey(nmsClassName))
			return loadedNMSClasses.get(nmsClassName);
		String clazzName = "net.minecraft.server." + getVersion() + nmsClassName;
		try {
			clazz = Class.forName(clazzName);
		} catch (Throwable t) {
			t.printStackTrace();
			return loadedNMSClasses.put(nmsClassName, null);
		}
		loadedNMSClasses.put(nmsClassName, clazz);
		return clazz;
	}

	public static synchronized Class<?> getOBCClass(String obcClassName) {
		Class<?> clazz;
		if (loadedOBCClasses.containsKey(obcClassName))
			return loadedOBCClasses.get(obcClassName);
		String clazzName = "org.bukkit.craftbukkit." + getVersion() + obcClassName;
		try {
			clazz = Class.forName(clazzName);
		} catch (Throwable t) {
			t.printStackTrace();
			loadedOBCClasses.put(obcClassName, null);
			return null;
		}
		loadedOBCClasses.put(obcClassName, clazz);
		return clazz;
	}

	public static Object getConnection(Player player) {
		Method getHandleMethod = getMethod(player.getClass(), "getHandle", new Class[0]);
		if (getHandleMethod != null)
			try {
				Object nmsPlayer = getHandleMethod.invoke(player, new Object[0]);
				Field playerConField = getField(nmsPlayer.getClass(), "playerConnection");
				return playerConField.get(nmsPlayer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	public static Constructor<?> getConstructor(Class<?> clazz, Class... params) {
		try {
			return clazz.getConstructor(params);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class... params) {
		if (!loadedMethods.containsKey(clazz))
			loadedMethods.put(clazz, new HashMap<>());
		Map<String, Method> methods = loadedMethods.get(clazz);
		if (methods.containsKey(methodName))
			return methods.get(methodName);
		try {
			Method method = clazz.getMethod(methodName, params);
			methods.put(methodName, method);
			loadedMethods.put(clazz, methods);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			methods.put(methodName, null);
			loadedMethods.put(clazz, methods);
			return null;
		}
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		if (!loadedFields.containsKey(clazz))
			loadedFields.put(clazz, new HashMap<>());
		Map<String, Field> fields = loadedFields.get(clazz);
		if (fields.containsKey(fieldName))
			return fields.get(fieldName);
		try {
			Field field = clazz.getField(fieldName);
			fields.put(fieldName, field);
			loadedFields.put(clazz, fields);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			fields.put(fieldName, null);
			loadedFields.put(clazz, fields);
			return null;
		}
	}
}