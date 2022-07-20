package me.ixek.hellupdeathmessages.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import me.ixek.hellupdeathmessages.CustomDeathMessages;

public class CustomDeathMessagesCommand implements CommandExecutor, TabCompleter 
{

	private CustomDeathMessages plugin;

	public CustomDeathMessagesCommand (CustomDeathMessages plugin) 
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender.hasPermission("helldm.reload"))
		{
			if (args.length == 1) 
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					plugin.reloadConfig();
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Przeładowano konfiguracje.");
				} 
				else 
				{
					sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Poprawne użycie:");
					sender.sendMessage(ChatColor.RED + "Reload: /cdm reload");
					sender.sendMessage(ChatColor.RED + "Dodawanie wiadomości do configu: /cdm add [path] [message]");
					sender.sendMessage(ChatColor.RED + "Wyświetlanie wiadomości: /cdm list [path]");
					sender.sendMessage(ChatColor.RED + "Usuwanie wiaqomości z configu: /cdm remove [path] [number]");
				}
			}
			else if (args.length >= 3)
			{
				if (sender.hasPermission("helldm.add.message"))
				{
					if (args[0].equalsIgnoreCase("add"))
					{
						String path = args[1];

						String[] deathMessage = String.join(" ", args).split(" ", 3);

						String message = deathMessage[2];

						if (plugin.getConfig().getString(path) != null)
						{
							List<String> messages = plugin.getConfig().getStringList(path);
							messages.add(message);
							plugin.getConfig().set(path, messages);
							plugin.saveConfig();
							plugin.reloadConfig();
						}
						else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawna ściezka!"));


						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTwoja wiadomość: \"" + message + "\" &została dodana do: &4" + path + "."));
						return true;
					}
					else if (args[0].equalsIgnoreCase("remove"))
					{
						if (sender.hasPermission("helldm.remove.message"))
						{
							String path = args[1];

							try 
							{
								int listNumber = Integer.valueOf(args[2]) - 1;

								if (plugin.getConfig().getString(path) != null)
								{
									List<Integer> list = new ArrayList<Integer>();

									for (int i = 0; i < plugin.getConfig().getStringList(path).size(); i++)
									{
										list.add(i + 1);
									}

									try
									{
										List<String> messages = plugin.getConfig().getStringList(path);

										String removed = messages.get(listNumber);

										if (messages.size() > 1)
										{
											messages.remove(listNumber);
											plugin.getConfig().set(path, messages);
											plugin.saveConfig();
											plugin.reloadConfig();
											sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTwoja wiadomość: \"" + removed + "\"" + " &c została usunięta &4" + path));
										}
										else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMusisz mięc conajmniej jedną wiadomość!"));
									}
									catch (IndexOutOfBoundsException e)
									{
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawny numer! Opcje: " + list.toString()));
									}
								}
								else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawna ścieżka!"));
							}
							catch (NumberFormatException e)
							{
								if (plugin.getConfig().getString(path) != null)
								{
									List<Integer> list = new ArrayList<Integer>();

									for (int i = 0; i < plugin.getConfig().getStringList(path).size(); i++)
									{
										list.add(i + 1);
									}
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawny numer! Opcje: " + list.toString()));
								}
								else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawna ścieżka!"));
							}
						}
						else sender.sendMessage(ChatColor.RED + "You do not have permission.");

						return true;
					}
					else 
					{
						sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Poprawne użycie:");
						sender.sendMessage(ChatColor.RED + "Reload: /cdm reload");
						sender.sendMessage(ChatColor.RED + "Dodawanie wiadomości do configu: /cdm add [path] [message]");
						sender.sendMessage(ChatColor.RED + "Wyświetlanie wiadomości: /cdm list [path]");
						sender.sendMessage(ChatColor.RED + "Usuwanie wiaqomości z configu: /cdm remove [path] [number]");
					}
				}
				else sender.sendMessage(ChatColor.RED + "Nie masz permisji.");
			}
			else if (args.length == 2)
			{
				if (sender.hasPermission("helldm.list"))
				{
					if (args[0].equalsIgnoreCase("list"))
					{
						String path = args[1];

						if (plugin.getConfig().getStringList(path).size() != 0)
						{
							int listSize = plugin.getConfig().getStringList(path).size();

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6-- Wiadomości --"));
							for (int i = 0; i < listSize; i++)
							{
								int number = i + 1;
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + number +". " + plugin.getConfig().getStringList(path).get(i)));
							}
						}
						else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawna ścieżka!"));

						return true;
					}
				}
				else sender.sendMessage(ChatColor.RED + "Nie masz permisji.");

				if (sender.hasPermission("helldm.toggle"))
				{
					if (args[0].equalsIgnoreCase("toggle"))
					{
						String path = args[1];
						boolean value = !plugin.getConfig().getBoolean(path);

						if (plugin.getConfig().isSet(path))
						{
							plugin.getConfig().set(path, value);
							plugin.saveConfig();
							plugin.reloadConfig();

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Set " + path + " to &c" + value + "!"));
						}
						else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNiepoprawna ścieżka!"));

						return true;
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Poprawne użycie:");
						sender.sendMessage(ChatColor.RED + "Reload: /cdm reload");
						sender.sendMessage(ChatColor.RED + "Dodawanie wiadomości do configu: /cdm add [path] [message]");
						sender.sendMessage(ChatColor.RED + "Wyświetlanie wiadomości: /cdm list [path]");
						sender.sendMessage(ChatColor.RED + "Usuwanie wiaqomości z configu: /cdm remove [path] [number]");
					}
				} 
				else sender.sendMessage(ChatColor.RED + "Nie masz permisji.");
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Poprawne użycie:");
				sender.sendMessage(ChatColor.RED + "Reload: /cdm reload");
				sender.sendMessage(ChatColor.RED + "Dodawanie wiadomości do configu: /cdm add [path] [message]");
				sender.sendMessage(ChatColor.RED + "Wyświetlanie wiadomości: /cdm list [path]");
				sender.sendMessage(ChatColor.RED + "Usuwanie wiaqomości z configu: /cdm remove [path] [number]");
			}
			return false;
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Nie masz permisji");
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) 
	{

		List<String> completionsOne = new ArrayList<String>();
		List<String> completionsTwo = new ArrayList<String>();
		List<String> completionsTwoTwo = new ArrayList<String>();
		List<String> completionsThreeOne = new ArrayList<String>();
		List<String> completionsThreeTwo = new ArrayList<String>();

		List<String> paths = new ArrayList<String>();
		List<String> booleans = new ArrayList<String>();

		String[] one = {"reload", "add", "list", "remove", "toggle"};
		String[] threeOne = {"<message>"};
		String[] threeTwo = {"<number>"};

		for (String path : plugin.getConfig().getKeys(false))
		{
			if (path.contains("message") && !path.equalsIgnoreCase("enable-global-messages") && !path.equalsIgnoreCase("killer-message") && !path.equalsIgnoreCase("victim-message"))
			{
				paths.add(path);
			}

			booleans.add("enable-update-messages");
			booleans.add("enable-pvp-messages");
			booleans.add("do-lightning");
			booleans.add("enable-global-messages");
			booleans.add("original-hover-message");
			booleans.add("enable-item-hover");
			booleans.add("enable-custom-name-entity-messages");

		}

		String[] two = paths.toArray(new String[paths.size()]);
		String[] twoTwo = booleans.toArray(new String[paths.size()]);

		if (args.length == 1)
		{
			StringUtil.copyPartialMatches(args[0], Arrays.asList(one), completionsOne);
			Collections.sort(completionsOne);
			return completionsOne;
		}
		else if (args.length == 2)
		{
			if (!args[0].equalsIgnoreCase("reload"))
			{
				if (args[0].equalsIgnoreCase("toggle"))
				{
					StringUtil.copyPartialMatches(args[1], Arrays.asList(twoTwo), completionsTwoTwo);
					Collections.sort(completionsTwoTwo);
					return completionsTwoTwo;
				}
				else
				{
					StringUtil.copyPartialMatches(args[1], Arrays.asList(two), completionsTwo);
					Collections.sort(completionsTwo);
					return completionsTwo;
				}
			}
		}
		else if (args.length == 3)
		{
			if (!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("list"))
			{
				if (args[0].equalsIgnoreCase("add"))
				{
					StringUtil.copyPartialMatches(args[2], Arrays.asList(threeOne), completionsThreeOne);
					Collections.sort(completionsThreeOne);
					return completionsThreeOne;
				}
				else if (args[0].equalsIgnoreCase("remove"))
				{
					StringUtil.copyPartialMatches(args[2], Arrays.asList(threeTwo), completionsThreeTwo);
					Collections.sort(completionsThreeTwo);
					return completionsThreeTwo;
				}
			}
		}

		return null;
	}
}