package me.ixek.hellupdeathmessages.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.ixek.hellupdeathmessages.CustomDeathMessages;
import me.ixek.hellupdeathmessages.enums.VersionEnums;

public class PlayerKilledByEntityListener implements Listener {

	public CustomDeathMessages plugin;

	public PlayerKilledByEntityListener (CustomDeathMessages plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeathByMob (EntityDamageByEntityEvent event)
	{
		if (plugin.getConfig().getBoolean("enable-global-messages")) 
		{
			if (event.getEntity() instanceof Player && !(event.getDamager() instanceof Player))
			{
				Player victim = (Player) event.getEntity();

				if (victim.getHealth() <= event.getFinalDamage())
				{
					int versionInt = plugin.getServerVersion().getVersionInt();

					EntityType entity = event.getDamager().getType();
					String path = "unknown-messages";

					boolean hasCustomName = event.getDamager().getCustomName() != null;

					if (entity == EntityType.SLIME) {
						path = "slime-messages";
					} else if (entity == EntityType.SKELETON) { // added before supported v
						path = "skeleton-messages";   		
					} else if (entity == EntityType.MAGMA_CUBE) { // added before supported v
						path = "magmacube-messages";
					} else if (entity == EntityType.SPIDER) { // added before supported v
						path = "spider-messages";
					} else if (entity == EntityType.CAVE_SPIDER) { // added before supported v
						path = "cavespider-messages";
					} else if (entity == EntityType.WITHER) { // added before supported v
						path = "witherboss-messages";
					} else if (entity == EntityType.ENDER_DRAGON) { // added before supported v
						path = "dragon-messages";
					} else if (entity == EntityType.PRIMED_TNT) { // added before supported v
						path = "tnt-messages";
					} else if (entity == EntityType.CREEPER) { // added before supported v
						path = "creeper-messages";
					} else if (entity == EntityType.GHAST) { // added before supported v
						path = "ghast-messages";
					} else if (entity == EntityType.ENDERMAN) { // added before supported v
						path = "enderman-messages";
					} else if (entity == EntityType.SILVERFISH) { // added before supported v
						path = "silverfish-messages";
					} else if (entity == EntityType.WITCH) { // added before supported v
						path = "witch-messages";
					} else if (entity == EntityType.GUARDIAN) { // added before supported v
						path = "guardian-messages";
					} else if (entity == EntityType.IRON_GOLEM) { // added before supported v
						path = "golem-messages";
					} else if (entity == EntityType.ENDERMITE) { // added before supported v
						path = "endermite-messages";
					} else if (entity == EntityType.ZOMBIE) { // added before supported v
						path = "zombie-messages";
					} else if (entity == EntityType.WOLF) { // added before supported v
						path = "wolf-messages";
					} else if (entity == EntityType.BLAZE) { // added before supported v
						path = "blaze-messages";
					} else if (entity == EntityType.FIREBALL) { // added before supported v
						path = "fireball-messages";
					} else if (entity == EntityType.ARROW) // added before supported v
					{ 
						//if (((Projectile) event.getDamager()).getShooter() instanceof Skeleton) {
						//	path = "skeleton-messages";
						//} else if (versionInt >= VersionEnums.VERSION_110.getVersionInt() && ((Projectile) event.getDamager()).getShooter() instanceof Stray) {
						//	path = "stray-messages";
						//} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && ((Projectile) event.getDamager()).getShooter() instanceof Pillager) {
						//	path = "pillager-messages";
						//} else {
							path = "arrow-messages";
						//}
					} else if (entity == EntityType.LIGHTNING) { // 1.11
						path = "lightning-messages";
					} else if (versionInt <= VersionEnums.VERSION_115.getVersionInt() && entity.toString().equals("PIG_ZOMBIE")) { // 1.15 and below (must use entity name due to unknown field)
						path = "pigman-messages";
					} else if (versionInt >= VersionEnums.VERSION_19.getVersionInt() && entity == EntityType.SHULKER) { // 1.9
						path = "shulker-messages";
					} else if (versionInt >= VersionEnums.VERSION_110.getVersionInt() && entity == EntityType.STRAY) { // 1.10
						path = "stray-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.LLAMA) { // 1.11
						path = "llama-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.DRAGON_FIREBALL) { // id changed in 1.11
						path = "dragon-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.LLAMA_SPIT) { // 1.11
						path = "llama-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.WITHER_SKELETON) { // differation of wither skeletons and skeletons added 1.11
						path = "witherskeleton-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.ELDER_GUARDIAN) { // differation of elder guardians and guardians added 1.11
						path = "elderguardian-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.ZOMBIE_VILLAGER) { // differation of zombies and zombie villagers added 1.11
						path = "zombievillager-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.EVOKER) { // 1.11
						path = "evoker-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.VEX) { // 1.11
						path = "vex-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.VINDICATOR) { // 1.11
						path = "vindicator-messages";
					} else if (versionInt >= VersionEnums.VERSION_111.getVersionInt() && entity == EntityType.HUSK) { // 1.11
						path = "husk-messages";
					} else if (versionInt >= VersionEnums.VERSION_112.getVersionInt() && entity == EntityType.ILLUSIONER) { // 1.12
						path = "illusioner-messages";
					} else if (versionInt >= VersionEnums.VERSION_113.getVersionInt() && entity == EntityType.PHANTOM) { // 1.13
						path = "phantom-messages";
					} else if (versionInt >= VersionEnums.VERSION_113.getVersionInt() && entity == EntityType.DROWNED) { // 1.13
						path = "drowned-messages";
					} else if (versionInt >= VersionEnums.VERSION_113.getVersionInt() && entity == EntityType.PUFFERFISH) { // 1.13
						path = "pufferfish-messages";
					} else if (versionInt >= VersionEnums.VERSION_113.getVersionInt() && entity == EntityType.POLAR_BEAR) { // 1.13
						path = "polar-bear-messages";
					} else if (versionInt >= VersionEnums.VERSION_113.getVersionInt() && entity == EntityType.DOLPHIN) { // 1.13
						path = "dolphin-messages";
					} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && entity == EntityType.PILLAGER) { // 1.14
						path = "pillager-messages";
					} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && entity == EntityType.RAVAGER) { // 1.14
						path = "ravager-messages";
					} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && entity == EntityType.TRADER_LLAMA) { // 1.14
						path = "llama-messages";
					} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && entity == EntityType.PANDA) { // 1.14
						path = "panda-messages";
					} else if (versionInt >= VersionEnums.VERSION_115.getVersionInt() && entity == EntityType.BEE) { // 1.15
						path = "bee-messages";
					} else if (versionInt >= VersionEnums.VERSION_116.getVersionInt() && entity == EntityType.PIGLIN) { // 1.16
						path = "piglin-messages";
					} else if (versionInt >= VersionEnums.VERSION_116.getVersionInt() && entity == EntityType.ZOGLIN) { // 1.16
						path = "zoglin-messages";
					} else if (versionInt >= VersionEnums.VERSION_116.getVersionInt() && entity == EntityType.HOGLIN) { // 1.16
						path = "hoglin-messages";
					} else if (versionInt >= VersionEnums.VERSION_116.getVersionInt() && entity == EntityType.ZOMBIFIED_PIGLIN) { // 1.16
						path = "zombified-piglin-messages";
					}

					if (hasCustomName && plugin.getConfig().getBoolean("enable-custom-name-entity-messages"))
					{
						path = "custom-name-entity-messages";
					}

					Random rand = new Random();
					List<String> msgs = plugin.getConfig().getStringList(path);
					String message = msgs.get(rand.nextInt(msgs.size()))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%victim-x%", victim.getName())
							.replace("%victim-y%", victim.getName())
							.replace("%victim-z%", victim.getName());

					if (path.equals("custom-name-entity-messages"))
					{
						message = message.replace("%entity-name%", event.getDamager().getName());
					}

					plugin.deathMessage.clear();
					plugin.deathMessage.put(victim.getName(), message);

					if (plugin.getConfig().getBoolean("developer-mode"))
					{
						Bukkit.broadcastMessage(plugin.deathMessage.get(victim.getName()));
						Bukkit.broadcastMessage(entity.toString());
					}
				}
			}
		}
	}
}
