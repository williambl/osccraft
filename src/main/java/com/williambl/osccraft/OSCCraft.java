package com.williambl.osccraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = OSCCraft.MODID, version = OSCCraft.VERSION)
public class OSCCraft {

	public static final String MODID = "osccraft";
	@Mod.Instance("osccraft")
	public static OSCCraft instance;
	public static final String VERSION = "1.0.0";

    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

    @EventHandler
	public void init(FMLInitializationEvent event)
	{
	}

    @EventHandler
	public void Postinit(FMLPostInitializationEvent event)
	{
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {

	}
}