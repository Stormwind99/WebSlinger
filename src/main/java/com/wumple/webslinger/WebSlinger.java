package com.wumple.webslinger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wumple.webslinger.capability.IWebSlinger;
import com.wumple.webslinger.capability.WebSlingerCapability;
import com.wumple.webslinger.capability.WebSlingerStorage;
import com.wumple.webslinger.configuration.ModConfiguration;
import com.wumple.webslinger.webbing.WebbingItem;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MOD_ID)
public class WebSlinger
{
	/*
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
	*/

	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	public Logger getLogger()
	{
		return LogManager.getLogger(Reference.MOD_ID);
	}

	public WebSlinger()
	{
		ModConfiguration.register(ModLoadingContext.get());

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addGenericListener(Item.class, this::registerItems);
		modEventBus.addGenericListener(SoundEvent.class, this::onSoundRegistration);
		modEventBus.addGenericListener(EntityType.class, this::onEntityRegistry);
		modEventBus.addListener(this::registerModels);
		modEventBus.addListener(this::setup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}
	
    private void registerModels(ModelRegistryEvent event) {
        ModelRegistry.registerModels(event);
    }
	
	public void setup(final FMLCommonSetupEvent event)
	{
		//setup.init();
		proxy.init();

		WebSlingerCapability.register();
	}
	
	/*
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event)
	{
	    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	    RenderingRegistry.registerEntityRenderingHandler(EntityWebbing.class, (EntityRendererManager rendererManager) -> new SpriteRenderer<>(rendererManager, itemRenderer));
	}

	 */
	@SubscribeEvent
	public void onFingerprintViolation(final FMLFingerprintViolationEvent event)
	{
		getLogger().warn("Invalid fingerprint detected! The file " + event.getSource().getName()
				+ " may have been tampered with. This version will NOT be supported by the author!");
		getLogger().warn("Expected " + event.getExpectedFingerprint() + " found " + event.getFingerprints().toString());
	}

	private void registerItems(RegistryEvent.Register<Item> event)
	{
		Item.Properties properties = new Item.Properties();

		ObjectHandler.webbing = new WebbingItem(properties);

		event.getRegistry().register(ObjectHandler.webbing);
	}

	public void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event)
	{
		/*
		ObjectHandler.WEBBING = (EntityType<EntityWebbing>) EntityType.Builder
				.<EntityWebbing>create(EntityWebbing::new, EntityClassification.MISC).size(0.25F, 0.25F)
				.build("webbing").setRegistryName(Reference.MOD_ID, "webbing");
		*/

		/*
		ObjectHandler.WEBBING = (EntityType<EntityWebbing>)
				EntityType.Builder.<EntityWebbing>create(EntityWebbing::new, EntityClassification.MISC)
	            .setTrackingRange(64)
	            .setUpdateInterval(10)
	            .setShouldReceiveVelocityUpdates(true)
	            .setCustomClientFactory(EntityWebbing::new)
	            .size(0.25F, 0.25F)
	            .build("webbing")
	            .setRegistryName(Reference.MOD_ID, "webbing");

		event.getRegistry().register(ObjectHandler.WEBBING);
		*/
	}
	
    public void onSoundRegistration(final RegistryEvent.Register<SoundEvent> event)
    {
        ObjectHandler.WEBBING_SHOOT = SoundEvents.ENTITY_SNOWBALL_THROW;
        ObjectHandler.WEBBING_STICK = SoundEvents.BLOCK_SNOW_HIT;
        ObjectHandler.WEBBING_NONSTICK = SoundEvents.BLOCK_SNOW_BREAK;
    } 
}
