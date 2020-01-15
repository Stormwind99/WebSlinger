package com.wumple.webslinger;

import com.wumple.webslinger.webbing.WebbingEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class ModelRegistry
{
	private static final Minecraft MC = Minecraft.getInstance();

	public static void registerModels(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(WebbingEntity.class,
				manager -> new SpriteRenderer<>(MC.getRenderManager(), MC.getItemRenderer()));
	}
}
