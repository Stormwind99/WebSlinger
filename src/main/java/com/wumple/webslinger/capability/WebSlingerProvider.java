package com.wumple.webslinger.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class WebSlingerProvider implements ICapabilityProvider
{
    LivingEntity owner = null;
    int taskPriority = -1;

    public WebSlingerProvider(LivingEntity ownerIn, int taskPriorityIn)
    {
        owner = ownerIn;
        taskPriority = taskPriorityIn;
    }
    
    public @Nonnull <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side)
    {
    	 return LazyOptional.of(() -> new WebSlingerCapability(owner, taskPriority)).cast();
    }
}