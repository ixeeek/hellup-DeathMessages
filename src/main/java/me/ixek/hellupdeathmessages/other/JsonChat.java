package me.ixek.hellupdeathmessages.other;


import java.lang.reflect.Method;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;

public class JsonChat {
	private static String convertItemStackToJson(ItemStack itemStack) {
		Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
		Method asNMSCopyMethod = ReflectionUtil.getMethod(craftItemStackClazz, "asNMSCopy", new Class[] { ItemStack.class });
		Class<?> nmsItemStackClazz = ReflectionUtil.getNMSClass("ItemStack");
		Class<?> nbtTagCompoundClazz = ReflectionUtil.getNMSClass("NBTTagCompound");
		Method saveNmsItemStackMethod = ReflectionUtil.getMethod(nmsItemStackClazz, "save", new Class[] { nbtTagCompoundClazz });
		Object itemAsJsonObject = null;
		try {
			Object nmsNbtTagCompoundObj = nbtTagCompoundClazz.getConstructor(new Class[0]).newInstance(new Object[0]);
			Object nmsItemStackObj = asNMSCopyMethod.invoke(null, new Object[] { itemStack });
			itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, new Object[] { nmsNbtTagCompoundObj });
		} catch (Throwable throwable) {}
		return itemAsJsonObject.toString();
	}

	public TextComponent getTextComponent(String message, ItemStack item, String replace) {
		String itemJson = convertItemStackToJson(item);
		BaseComponent[] hoverEventComponents = { (BaseComponent)new TextComponent(itemJson) };
		HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);
		TextComponent component = new TextComponent(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : WordUtils.capitalize(item.getType().name().replaceAll("_", " ").toLowerCase()));
		component.setHoverEvent(event);
		TextComponent returnValue = new TextComponent();
		byte b;
		int i;
		String[] arrayOfString;
		for (i = (arrayOfString = message.split("%")).length, b = 0; b < i; ) {
			String split = arrayOfString[b];
			if (split.equalsIgnoreCase(replace)) {
				returnValue.addExtra((BaseComponent)component);
			} else {
				returnValue.addExtra((BaseComponent)new TextComponent(split));
			}
			b++;
		}
		return returnValue;
	}
}

