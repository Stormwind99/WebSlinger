package com.wumple.webslinger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import josephcsible.webshooter.PlayerInWebMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, updateJSON = Reference.UPDATEJSON, certificateFingerprint=Reference.FINGERPRINT)
public class WebSlinger
{
	@Mod.Instance(Reference.MOD_ID)
	public static WebSlinger instance;

	public static Logger logger;
	public static SimpleNetworkWrapper networkWrapper;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		PlayerInWebMessage.register(networkWrapper);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) 
	{ }

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{ }

    @EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        if (logger == null)
        {
            logger = LogManager.getLogger(Reference.MOD_ID);
        }
        if (logger != null)
        {
            logger.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
            logger.warn("Expected " + event.getExpectedFingerprint() + " found " + event.getFingerprints().toString());
        }
    }
}
