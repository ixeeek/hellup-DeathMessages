package me.ixek.hellupdeathmessages.enums;

public enum Paths {

	ARROW (""), 
	BEE (""),
	BLAZE (""), 
	CAVE_SPIDER (""), 
	CREEPER (""),
	DOLPHIN (""), 
	DRAGON_FIREBALL (""), 
	DROWNED (""),
	ELDER_GUARDIAN (""),
	ENDER_CRYSTAL (""), 
	ENDER_DRAGON (""),
	ENDERMAN (""), 
	ENDERMITE (""), 
	EVOKER (""),
	EVOKER_FANGS (""), 
	FALLING_BLOCK (""),
	FIREBALL (""), 
	FIREWORK (""), 
	GHAST (""), 
	GUARDIAN (""), 
	HOGLIN (""), 
	HUSK (""),
	ILLUSIONER (""), 
	IRON_GOLEM (""), 
	LIGHTNING (""),
	LLAMA (""), 
	LLAMA_SPIT (""), 
	MAGMA_CUBE (""),
	PANDA (""), 
	PHANTOM (""), 
	PIGLIN (""), 
	PILLAGER (""), 
	PLAYER (""), 
	POLAR_BEAR (""),
	PRIMED_TNT (""), 
	PUFFERFISH (""), 
	RAVAGER (""),
	SHULKER (""), 
	SHULKER_BULLET (""), 
	SILVERFISH (""),
	SKELETON (""), 
	SLIME (""), 
	SMALL_FIREBALL (""),
	SPECTRAL_ARROW (""), 
	SPIDER (""), 
	SPLASH_POTION (""),
	STRAY (""), 
	TRADER_LLAMA (""), 
	TRIDENT (""),
	UNKNOWN (""), 
	VEX (""), 
	VINDICATOR (""), 
	WITCH (""),
	WITHER (""), 
	WITHER_SKELETON (""), 
	WITHER_SKULL (""),
	WOLF (""), 
	ZOGLIN (""), 
	ZOMBIE (""),
	ZOMBIE_VILLAGER (""), 
	ZOMBIFIED_PIGLIN ("");
	
	private String path;
	
	private Paths(String path)
	{
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
}
