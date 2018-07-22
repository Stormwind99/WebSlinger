package com.wumple.webslinger;

import com.wumple.util.misc.RegistrationHelpers;
import com.wumple.webslinger.webbing.EntityWebbing;
import com.wumple.webslinger.webbing.ItemWebbing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder("webslinger")
public class ObjectHandler
{
    private static int entityID = 1;

    // @ObjectHolder("webslinger:webbing")
    public static Item webbing = null;

    public static final SoundEvent WEBBING_SHOOT = SoundEvents.ENTITY_SNOWBALL_THROW;
    public static final SoundEvent WEBBING_STICK = SoundEvents.BLOCK_SNOW_HIT;
    public static final SoundEvent WEBBING_NONSTICK = SoundEvents.BLOCK_SNOW_BREAK;

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
    }
}
