package me.ixek.hellupdeathmessages.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.ixek.hellupdeathmessages.CustomDeathMessages;
import me.ixek.hellupdeathmessages.enums.VersionEnums;
import net.md_5.bungee.api.ChatColor;

public class HexChat {
  private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

  private static final char COLOR_CHAR = '§';

  public static String translateHexCodes(String message, CustomDeathMessages plugin) {
    return translate(HEX_PATTERN, message, plugin);
  }

  public static String translateHexCodes(String startTag, String endTag, String message, CustomDeathMessages plugin) {
    Pattern hexPattern = Pattern.compile(String.valueOf(startTag) + "([a-f0-9]{6})" + endTag);
    return translate(hexPattern, message, plugin);
  }

  private static String translate(Pattern hex, String message, CustomDeathMessages plugin) {
    if (plugin.getServerVersion().getVersionInt() >= VersionEnums.VERSION_116.getVersionInt()) {
      Matcher matcher = hex.matcher(message);
      StringBuffer buffer = new StringBuffer(message.length() + 32);
      while (matcher.find()) {
        String group = matcher.group(1);
        matcher.appendReplacement(buffer, "§x§" +
            group.charAt(0) + '§' + group.charAt(1) +
            '§' + group.charAt(2) + '§' + group.charAt(3) +
            '§' + group.charAt(4) + '§' + group.charAt(5));
      }
      return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
    return ChatColor.translateAlternateColorCodes('&', message);
  }
}