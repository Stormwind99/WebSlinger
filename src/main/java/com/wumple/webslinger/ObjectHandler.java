package com.wumple.webslinger;

import com.wumple.webslinger.webbing.WebbingEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class ObjectHandler
{
	//private static int entityID = 1;

	// @ObjectHolder("webslinger:webbing")
	public static Item webbing = null;

	public static SoundEvent WEBBING_SHOOT;
	public static SoundEvent WEBBING_STICK;
	public static SoundEvent WEBBING_NONSTICK;

	public static final EntityType<WebbingEntity> WEBBING = EntityType.Builder
			.<WebbingEntity>create(WebbingEntity::new, EntityClassification.MISC).setTrackingRange(64)
			.setUpdateInterval(10).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(WebbingEntity::new)
			.size(0.5f, 0.5f).build(Reference.MOD_ID + ":webbing");

	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
	{
		WEBBING.setRegistryName(Reference.MOD_ID, "webbing");
		event.getRegistry().register(WEBBING);
	}

	/*
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler extends RegistrationHelpers
	{
	    @SubscribeEvent
	    public static void registerItems(RegistryEvent.Register<Item> event)
	    {
	        final IForgeRegistry<Item> registry = event.getRegistry();
	
	        // MAYBE webbing = Item.REGISTRY.getObject(new ResourceLocation("minecraft:web"));
	        webbing = regHelper(registry, new ItemWebbing());
	    }
	
	    @SideOnly(Side.CLIENT)
	    @SubscribeEvent
	    public static void registerRenders(ModelRegistryEvent event)
	    {
	        registerRender(webbing);
	
	        RenderingRegistry.registerEntityRenderingHandler(EntityWebbing.class,
	                manager -> new RenderSnowball<>(manager, webbing, Minecraft.getMinecraft().getRenderItem()));
	    }
	
	    @SubscribeEvent
	    public static void entityRegistration(final RegistryEvent.Register<EntityEntry> event)
	    {
	        registerEntity(event.getRegistry());
	    }
	
	    protected static void registerEntity(IForgeRegistry<EntityEntry> registry)
	    {
	        EntityEntry entry = EntityEntryBuilder.create()
	                .entity(EntityWebbing.class)
	                .id(new ResourceLocation("webslinger", "webbing"), entityID++)
	                .name("webbing")
	                .tracker(64, 10, true)
	                .build();
	        registry.register(entry);
	    }
	    
	    @SubscribeEvent
	    public static void soundRegistration(final RegistryEvent.Register<SoundEvent> event)
	    {
	        WEBBING_SHOOT = SoundEvents.ENTITY_SNOWBALL_THROW;
	        WEBBING_STICK = SoundEvents.BLOCK_SNOW_HIT;
	        WEBBING_NONSTICK = SoundEvents.BLOCK_SNOW_BREAK;
	    }   
	}
	*/
}
