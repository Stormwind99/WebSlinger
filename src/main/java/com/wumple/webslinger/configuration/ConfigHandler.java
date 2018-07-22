package com.wumple.webslinger.configuration;

import com.wumple.util.Reference;
import com.wumple.util.config.MatchingConfig;
//import com.wumple.util.config.MatchingConfigBase;
import com.wumple.util.config.MatchingConfigBase;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

public class ConfigHandler
{
    public static boolean SLINGERS_DEFAULT = false;

    public static MatchingConfig<Boolean> slingers = new MatchingConfig<Boolean>(ConfigContainer.slingers, SLINGERS_DEFAULT);

    public static void init()
    {
        slingers.addDefaultProperty(MatchingConfigBase.SPIDER_TAG, true);

        ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
    }
}
