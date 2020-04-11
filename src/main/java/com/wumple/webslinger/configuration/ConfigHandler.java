package com.wumple.webslinger.configuration;

import com.wumple.util.config.MatchingConfig;

public class ConfigHandler
{
	public static boolean isEnabled()
	{
		return ModConfiguration.General.webSlinging.get();
	}
	
	public static boolean isDebugging()
	{
		return ModConfiguration.Debugging.debug.get();
	}
	
	public static boolean doesItSling(Integer priorityIn)
	{
		return priorityIn != NO_SLINGING;
	}
		
    // ----------------------------------------------------------------------
    // Slinging
    
    public static final int NO_SLINGING = -1;

    public static MatchingConfig<Integer> webSlingers = new MatchingConfig<Integer>(NO_SLINGING);
        
    public static void init()
    {
    	webSlingers.clear();
    }
    
    public static void postinit()
    {
    	addDefaults();
    }
    
    public static void addDefaults()
    {
    	webSlingers.addDefaultProperty("minecraft:spider", 3);
    }
}