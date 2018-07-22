package com.wumple.webslinger.capability;

import com.wumple.util.adapter.EntityThing;
import com.wumple.util.adapter.TileEntityThing;
import com.wumple.webslinger.configuration.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler
{
    /**
     * Attach the {@link IPreserving} capability to relevant items.
     *
     * @param event
     *            The event
     */
    @SubscribeEvent
    public static void attachCapabilitiesTileEntity(AttachCapabilitiesEvent<TileEntity> event)
    {
        TileEntity entity = event.getObject();

        if (ConfigHandler.slingers.doesIt(entity))
        {
            WebSlingerProvider provider = new WebSlingerProvider(WebSlinger.CAPABILITY, WebSlinger.DEFAULT_FACING, new TileEntityThing(entity));
            event.addCapability(WebSlinger.ID, provider);
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        Entity entity = event.getObject();

        if (ConfigHandler.slingers.doesIt(entity))
        {
            WebSlingerProvider provider = new WebSlingerProvider(WebSlinger.CAPABILITY, WebSlinger.DEFAULT_FACING, new EntityThing(entity));
            event.addCapability(WebSlinger.ID, provider);
        }
    }
}