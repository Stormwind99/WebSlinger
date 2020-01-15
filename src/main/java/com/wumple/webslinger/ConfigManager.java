package com.wumple.webslinger;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

// See
// https://github.com/McJty/YouTubeModding14/blob/master/src/main/java/com/mcjty/mytutorial/Config.java
// https://wiki.mcjty.eu/modding/index.php?title=Tut14_Ep6

@Mod.EventBusSubscriber
public class ConfigManager
{
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;

	public static final String CATEGORY_GENERAL = "General";
	public static final String CATEGORY_DEBUGGING = "Debugging";

	public static class General
	{
		public static ForgeConfigSpec.BooleanValue allowWebReplacement;
		public static ForgeConfigSpec.DoubleValue webMeleeChance;
		public static ForgeConfigSpec.BooleanValue webSlinging;
		public static ForgeConfigSpec.IntValue webReshootTime;
		public static ForgeConfigSpec.BooleanValue webbingOnWeb;
		public static ForgeConfigSpec.DoubleValue webSlingVariance;
		public static ForgeConfigSpec.DoubleValue webSlingInaccuracy;

		private static void setupConfig()
		{
			COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

			// 	@Name("Web replacement")
			allowWebReplacement = COMMON_BUILDER
					.comment("Webs replace water, lava, fire, snow, vines, etc. - any replaceable block")
					.define("allowWebReplacement",  true);
			
			// @Name("Web melee chance")
			webMeleeChance = COMMON_BUILDER
					.comment("Chance per melee attack that a spider will create a web")
					.defineInRange("webMeleeChance", 0.15F, 0.0F, 1.0F);

			// @Name("Sling webbing")
			webSlinging = COMMON_BUILDER
					.comment("Spiders can spit webbing from a distance")
					.define("webSlinging",  true);

			// @Name("Sling cooldown")
			webReshootTime = COMMON_BUILDER
					.comment("Time between web slings")
					.defineInRange("webReshootTime",  45, 0, Integer.MAX_VALUE);
			
			// @Name("Sling webbing on web")
			webbingOnWeb = COMMON_BUILDER
					.comment("Webbing hitting web creates more web")
					.define("webbingOnWeb",  false);

			// @Name("Sling variance")
			webSlingVariance = COMMON_BUILDER
					.comment("Time variance between a spider's web slings")
					.defineInRange("webSlingVariance", 2.0F, 0F, Double.MAX_VALUE);

			// @Name("Sling inaccuracy")
			webSlingInaccuracy = COMMON_BUILDER
					.comment("Inaccuracy of web slings")
					.defineInRange("webSlingInaccuracy", 6.0F, 0F, Double.MAX_VALUE);

			/*
			// TODO
			@Name("Slingers")
	    	@Config.Comment("Things that sling webs and AI priority, -1 means no slinging")
	    	public HashMap<String, Integer> ywebSlingers = new HashMap<String, Integer>();
			 */

			COMMON_BUILDER.pop();
		}
	}

	public static class Debugging
	{
		public static ForgeConfigSpec.BooleanValue debug;

		private static void setupConfig()
		{
			// @Config.Comment("Debugging options")
			COMMON_BUILDER.comment("Debugging settings").push(CATEGORY_DEBUGGING);

			//@Name("Debug mode")
			debug = COMMON_BUILDER.comment("Enable general debug features, display extra debug info").define("debug",
					false);

			COMMON_BUILDER.pop();
		}
	}

	static
	{
		General.setupConfig();
		Debugging.setupConfig();

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path)
	{

		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();

		configData.load();
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent)
	{
	}

	@SubscribeEvent
	public static void onReload(final ModConfig.ConfigReloading configEvent)
	{
	}

	public static void register(final ModLoadingContext context)
	{
		context.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
		context.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);

		loadConfig(ConfigManager.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Reference.MOD_ID + "-client.toml"));
		loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Reference.MOD_ID + "-common.toml"));
	}
}