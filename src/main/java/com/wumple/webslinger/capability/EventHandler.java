package com.wumple.webslinger.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler
{
	/*
	// TODO
	@SubscribeEvent
	public static void attachCapabilitiesTileEntity(AttachCapabilitiesEvent<TileEntity> event)
	{
	    TileEntity entity = event.getObject();
	
	    int priority = ConfigHandler.getInstance().webSlingers.getValue(entity);
	    
	    if (ConfigHandler.getInstance().webSlingers.doesIt(priority))
	    {
	        WebSlingerProvider provider = new WebSlingerProvider(WebSlingerCapability.CAPABILITY, WebSlingerCapability.DEFAULT_FACING, new TileEntityThing(entity), priority);
	        event.addCapability(WebSlingerCapability.ID, provider);
	    }
	}
	*/

	@SubscribeEvent
	public static void attachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
	{
		Entity entity = event.getObject();

		int priority = 3; // ConfigHandler.getInstance().webSlingers.getValue(entity);

		if (entity instanceof SpiderEntity) // TODO ConfigHandler.getInstance().webSlingers.doesIt(priority))
		{
			LivingEntity livingEntity = (LivingEntity) entity;
			WebSlingerProvider provider = new WebSlingerProvider(livingEntity, priority);
			event.addCapability(WebSlingerCapability.ID, provider);

			// make capability be created and initialized ASAP since it adds goals, event handlers, etc
			// but needs deferred until after entity ctors finish (since attachCapabilitiesEntity occurs
			// during construction)
			MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
			if (server != null)
			{
				server.deferTask(new Runnable()
				{
					@Override
					public void run()
					{
						LazyOptional<IWebSlinger> cap = livingEntity.getCapability(WebSlingerCapability.CAP);
						cap.ifPresent((x) -> {
						});
					}
				});
			}
		}
	}
}