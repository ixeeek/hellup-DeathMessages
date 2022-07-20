package me.ixek.hellupdeathmessages.other;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MsgToJson {
	public static TextComponent translate(String newDeathMessage, String originalDeathMessage) {
		TextComponent message = new TextComponent(TextComponent.fromLegacyText(newDeathMessage));
		TextComponent hoverBase = new TextComponent(TextComponent.fromLegacyText(originalDeathMessage));
		BaseComponent[] hoverComponent = (new ComponentBuilder((BaseComponent)hoverBase)).create();
		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
		message.setHoverEvent(hoverEvent);
		return message;
	}
}

