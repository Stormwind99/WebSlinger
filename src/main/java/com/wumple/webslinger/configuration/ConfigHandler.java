package com.wumple.webslinger.configuration;

import com.wumple.webslinger.Reference;
import com.wumple.util.config.MatchingConfig;
//import com.wumple.util.config.MatchingConfigBase;
import com.wumple.util.config.MatchingConfigBase;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

public class ConfigHandler
{
    public static ConfigHandler INSTANCE = null;
    
    public static final int SLINGERS_DEFAULT = -1;

    public MatchingConfig<Integer> webSlingers = new MatchingConfig<Integer>(ConfigContainer.slinging.ywebSlingers, SLINGERS_DEFAULT);
    
    public static ConfigHandler getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ConfigHandler();
        }
        return INSTANCE;
    }
    
    public ConfigHandler()
    { }
    
    public void init()
    {
        webSlingers.addDefaultProperty(MatchingConfigBase.SPIDER_TAG, 3);

        ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
     }
}
