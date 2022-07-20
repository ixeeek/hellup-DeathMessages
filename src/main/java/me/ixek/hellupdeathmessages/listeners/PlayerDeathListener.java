package me.ixek.hellupdeathmessages.listeners;

import java.util.List;
import java.util.Random;

import me.ixek.hellupdeathmessages.CustomDeathMessages;
import me.ixek.hellupdeathmessages.enums.VersionEnums;
import me.ixek.hellupdeathmessages.other.JsonChat;
import me.ixek.hellupdeathmessages.other.HexChat;
import me.ixek.hellupdeathmessages.other.MsgToJson;
import me.ixek.hellupdeathmessages.other.SkullCreator;

import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


public class PlayerDeathListener implements Listener {
	public CustomDeathMessages plugin;

	public PlayerDeathListener(CustomDeathMessages plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player victim = event.getEntity();
		Player killer = event.getEntity().getKiller();
		Location playerLocation = victim.getLocation();
		if (victim instanceof Player && killer instanceof Player && this.plugin.getConfig().getBoolean("enable-pvp-messages")) {
			victim.sendMessage(HexChat.translateHexCodes(this.plugin.getConfig().getString("victim-message")
					.replace("%killer%", victim.getName())
					.replace("%killer-nick%", victim.getDisplayName())
					.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
					.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
					.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
					.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
					.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
					.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ())), this.plugin));
			killer.sendMessage(HexChat.translateHexCodes(this.plugin.getConfig().getString("killer-message")
					.replace("%victim%", victim.getName())
					.replace("%victim-nick%", victim.getDisplayName())
					.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
					.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
					.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
					.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
					.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
					.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ())), this.plugin));
		}
		double percent = this.plugin.getConfig().getDouble("drop-head-percentage");
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		if (randomDouble <= percent) {
			String headName = HexChat.translateHexCodes(this.plugin.getConfig().getString("head-name")
					.replace("%victim%", victim.getName()), this.plugin);
			if (this.plugin.getConfig().getBoolean("developer-mode"))
				Bukkit.broadcastMessage("HEAD DROPPED");
			ItemStack skull = SkullCreator.itemFromUuid(victim.getUniqueId(), this.plugin);
			SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
			skullMeta.setDisplayName(headName);
			skull.setItemMeta((ItemMeta)skullMeta);
			playerLocation.getWorld().dropItemNaturally(victim.getLocation(), skull);
		}
		if (this.plugin.getConfig().getBoolean("do-lightning"))
			playerLocation.getWorld().strikeLightningEffect(playerLocation);
		if (this.plugin.getConfig().getBoolean("enable-global-messages"))
			if (killer instanceof Player) {
				ItemStack killWeapon = getKillWeapon(killer);
				if (killWeapon.getType() != Material.AIR) {
					String weaponName = killWeapon.getItemMeta().hasDisplayName() ? killWeapon.getItemMeta().getDisplayName() : WordUtils.capitalize(killWeapon.getType().name().replaceAll("_", " ").toLowerCase());
					Random random = new Random();
					List<String> msgs = this.plugin.getConfig().getStringList("global-pvp-death-messages");
					String msg = ((String)msgs.get(random.nextInt(msgs.size())))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%killer%", killer.getName())
							.replace("%killer-nick%", killer.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
							.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
							.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
							.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ()));
					msg = HexChat.translateHexCodes(msg, this.plugin);
					if (this.plugin.getConfig().getBoolean("developer-mode"))
						Bukkit.broadcastMessage("msg test: " + msg);
					if (this.plugin.getConfig().getBoolean("enable-item-hover")) {
						event.setDeathMessage("");
						Bukkit.spigot().broadcast((BaseComponent)(new JsonChat()).getTextComponent(msg, killWeapon, "kill-weapon"));
						return;
					}
					msg = msg.replace("%kill-weapon%", weaponName);
					event.setDeathMessage(msg);
					Bukkit.broadcastMessage(msg);
				} else {
					Random random = new Random();
					List<String> msgs = this.plugin.getConfig().getStringList("melee-death-messages");
					String msg = ((String)msgs.get(random.nextInt(msgs.size())))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%killer%", killer.getName())
							.replace("%killer-nick%", killer.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
							.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
							.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
							.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ()));
					msg = HexChat.translateHexCodes(msg, this.plugin);
					event.setDeathMessage(msg);
					Bukkit.broadcastMessage(msg);
				}
			} else {
				int versionInt = this.plugin.getServerVersion().getVersionInt();
				EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CUSTOM;
				if (victim.getLastDamageCause() != null)
					cause = victim.getLastDamageCause().getCause();
				String path = null;
				if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
					path = "unknown-messages";
				} else if (cause == EntityDamageEvent.DamageCause.FALL) {
					path = "fall-damage-messages";
					if (this.plugin.getConfig().getBoolean("developer-mode"))
						Bukkit.broadcastMessage("fall damage");
				} else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
					path = "drowning-messages";
				} else if (cause == EntityDamageEvent.DamageCause.LAVA) {
					path = "lava-messages";
				} else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
					path = "suffocation-messages";
				} else if (cause == EntityDamageEvent.DamageCause.WITHER) {
					path = "wither-messages";
				} else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
					path = "fire-tick-messages";
				} else if (cause == EntityDamageEvent.DamageCause.FIRE) {
					path = "fire-messages";
				} else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
					path = "starvation-messages";
				} else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
					path = "cactus-messages";
				} else if (cause == EntityDamageEvent.DamageCause.MAGIC) {
					path = "potion-messages";
				} else if (cause == EntityDamageEvent.DamageCause.VOID) {
					path = "void-messages";
				} else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
					path = "lightning-messages";
				} else if (versionInt >= VersionEnums.VERSION_19.getVersionInt() && cause == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
					path = "elytra-messages";
				} else if (versionInt >= VersionEnums.VERSION_110.getVersionInt() && cause == EntityDamageEvent.DamageCause.HOT_FLOOR) {
					path = "magma-block-messages";
				} else if (cause == EntityDamageEvent.DamageCause.SUICIDE) {
					path = "suicide-messages";
				}
				String msg = "";
				if (path == null) {
					if (this.plugin.deathMessage.get(victim.getName()) != null) {
						msg = HexChat.translateHexCodes((String)this.plugin.deathMessage.get(victim.getName()), this.plugin);
						this.plugin.deathMessage.clear();
					} else {
						Random random = new Random();
						List<String> msgs = this.plugin.getConfig().getStringList("unknown-messages");
						msg = ((String)msgs.get(random.nextInt(msgs.size())))
								.replace("%victim%", victim.getName())
								.replace("%victim-nick%", victim.getDisplayName())
								.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
								.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
								.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()));
						msg = HexChat.translateHexCodes(msg, this.plugin);
					}
				} else {
					Random random = new Random();
					List<String> msgs = this.plugin.getConfig().getStringList(path);
					msg = ((String)msgs.get(random.nextInt(msgs.size())))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()));
					msg = HexChat.translateHexCodes(msg, this.plugin);
				}
				if (this.plugin.getConfig().getBoolean("original-hover-message")) {
					String previous = event.getDeathMessage();
					Bukkit.spigot().broadcast((BaseComponent)MsgToJson.translate(msg, previous));
					event.setDeathMessage("");
				} else {
					event.setDeathMessage(msg);
					Bukkit.broadcastMessage(msg);
				}
			}
	}

	public ItemStack getKillWeapon(Player killer) {
		if (this.plugin.getServerVersion().getVersionInt() >= VersionEnums.VERSION_19.getVersionInt())
			return killer.getInventory().getItemInMainHand();
		return killer.getInventory().getItemInHand();
	}
}