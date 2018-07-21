package com.wumple.webslinger;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModConfig {

	@Name("Web melee chance")
	@Comment("Chance per melee attack that a spider will create a web")
	@RangeDouble(min = 0.0, max = 1.0)
	public static double webMeleeChance = 0.15;

	@Name("Web replacement")
	@Comment("Webs replace water, lava, fire, snow, vines, etc. - any replaceable block")
	public static boolean allowWebReplacement = true;

	@Name("Sling webbing")
	@Comment("Spiders can spit webbing from a distance")
	public static boolean webSlinging = true;

	@Name("Sling cooldown")
	@Comment("Time between web slings")
	@RangeInt(min = 1)
	public static int webReshootTime = 45;

	@Name("Webbing on web")
	@Comment("Webbing hitting web creates more web")
	public static boolean webbingOnWeb = false;
	
	@Name("Sling variance")
	@Comment("Time variance between a spider's web slings")
	public static double webSlingVariance = 2.0F;
	
	@Name("Sling inaccuracy")
	@Comment("Inaccuracy of web slings")
	public static double webSlingInaccuracy = 6.0F;

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {
		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}