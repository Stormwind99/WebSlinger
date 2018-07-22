package com.wumple.webslinger.configuration;

import java.util.HashMap;

import com.wumple.webslinger.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigContainer
{
    @Name("Web replacement")
    @Config.Comment("Webs replace water, lava, fire, snow, vines, etc. - any replaceable block")
    public static boolean allowWebReplacement = true;

    @Name("Melee")
    @Config.Comment("Melee web options")
    public static Melee melee = new Melee();

    public static class Melee
    {
        @Name("Web melee chance")
        @Config.Comment("Chance per melee attack that a spider will create a web")
        @RangeDouble(min = 0.0, max = 1.0)
        public double webMeleeChance = 0.15;
    }
    
    @Name("Slinging")
    @Config.Comment("Ranged web slinging options")
    public static Slinging slinging = new Slinging();
    
    public static class Slinging
    {
        @Name("Sling webbing")
        @Config.Comment("Spiders can spit webbing from a distance")
        public boolean webSlinging = true;

        @Name("Sling cooldown")
        @Config.Comment("Time between web slings")
        @RangeInt(min = 1)
        public int webReshootTime = 45;

        @Name("Sling webbing on web")
        @Config.Comment("Webbing hitting web creates more web")
        public boolean webbingOnWeb = false;

        @Name("Sling variance")
        @Config.Comment("Time variance between a spider's web slings")
        public double webSlingVariance = 2.0F;

        @Name("Sling inaccuracy")
        @Config.Comment("Inaccuracy of web slings")
        public double webSlingInaccuracy = 6.0F;

        @Name("Slingers")
        @Config.Comment("Things that sling webs and AI priority, -1 means no slinging")
        public HashMap<String, Integer> ywebSlingers = new HashMap<String, Integer>();
    }
    
    @Name("Debugging")
    @Config.Comment("Debugging options")
    public static Debugging zdebugging = new Debugging();

    public static class Debugging
    {
        @Name("Debug mode")
        @Config.Comment("Enable debug features on this menu, display extra debug info.")
        public boolean debug = false;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler
    {
        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event
         *            The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(Reference.MOD_ID))
            {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}