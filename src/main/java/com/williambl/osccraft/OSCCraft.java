package com.williambl.osccraft;

import com.williambl.kerbalcraft.common.RPCConnectionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = OSCCraft.MODID, version = OSCCraft.VERSION)
public class OSCCraft {

	@Mod.Instance("kerbalcraft")
	public static OSCCraft instance;

    public static final String MODID = "kerbalcraft";
	public static final String VERSION = "1.0.0";

    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
    	proxy.preInit();
	}

    @EventHandler
	public void init(FMLInitializationEvent event)
	{
    	proxy.init();
	}

    @EventHandler
	public void Postinit(FMLPostInitializationEvent event)
	{
    	proxy.postInit();
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		proxy.serverStart(event);
	}
}