package com.minecraft.client.resources;

public class NewComputer {
	private static String appdata = System.getenv("APPDATA");
	//set up the initial folders
	public final String resourcesDirectory 			= new String(appdata + "\\.MINECRAFT2D\\Recources");
	public final static String savesDirectory 		= new String(appdata + "\\.MINECRAFT2D\\saves");
	public final String gameDirectory 				= new String(appdata + "\\.MINECRAFT2D");
	public final String animationsDirectory 		= new String(resourcesDirectory + "\\animations");
	public final String armorDirectory 				= new String(resourcesDirectory + "\\armor");
	public final String blocksDirectory 			= new String(resourcesDirectory + "\\blocks");
	public final String itemsDirectory 				= new String(resourcesDirectory + "\\items");
	public final String miscDirectory 				= new String(resourcesDirectory + "\\misc");
}