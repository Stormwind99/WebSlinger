package com.wumple.webslinger.capability;

import com.wumple.util.adapter.EntityThing;
import com.wumple.util.adapter.TileEntityThing;
import com.wumple.webslinger.configuration.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler
{
	@SubscribeEvent
	public static void attachCapabilitiesTileEntity(AttachCapabilitiesEvent<TileEntity> event)
	{
		if (ConfigHandler.isEnabled())
		{
			TileEntity entity = event.getObject();
			int priority = ConfigHandler.webSlingers.getValue(entity);

			if (ConfigHandler.doesItSling(priority))
			{
				WebSlingerProvider provider = new WebSlingerProvider(new TileEntityThing(entity), priority);
				event.addCapability(WebSlingerCapability.ID, provider);
			}
		}
	}

	@SubscribeEvent
	public static void attachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
	{
		if (ConfigHandler.isEnabled())
		{
			Entity entity = event.getObject();
			int priority = ConfigHandler.webSlingers.getValue(entity);

			if (ConfigHandler.doesItSling(priority))
			{
				WebSlingerProvider provider = new WebSlingerProvider(new EntityThing(entity), priority);
				event.addCapability(WebSlingerCapability.ID, provider);
			}
		}
	}
	
	@SubscribeEvent
	public static void entityJoinWorldEventHandler(EntityJoinWorldEvent event)
	{
		Entity entity = event.getEntity();
		LazyOptional<IWebSlinger> cap = entity.getCapability(WebSlingerCapability.CAPABILITY);
		cap.ifPresent((x) -> { x.handleAISetup(); } );
	}
}