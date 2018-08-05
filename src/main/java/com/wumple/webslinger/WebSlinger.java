package com.wumple.webslinger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wumple.util.mod.ModBase;
import com.wumple.webslinger.configuration.ConfigHandler;

import josephcsible.webshooter.PlayerInWebMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES,
        updateJSON = Reference.UPDATEJSON, certificateFingerprint = Reference.FINGERPRINT)
public class WebSlinger extends ModBase
{
    @Mod.Instance(Reference.MOD_ID)
    public static WebSlinger instance;

    public static SimpleNetworkWrapper networkWrapper;

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        com.wumple.webslinger.capability.WebSlingerCapability.register();

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        PlayerInWebMessage.register(networkWrapper);
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        ConfigHandler.getInstance().init();   
    }
    
    @EventHandler
    @Override
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        super.onFingerprintViolation(event);
    }
    
    @Override
    public Logger getLoggerFromManager()
    {
        return LogManager.getLogger(Reference.MOD_ID);
    }
}
