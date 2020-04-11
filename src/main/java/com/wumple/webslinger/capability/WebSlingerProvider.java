package com.wumple.webslinger.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class WebSlingerProvider implements ICapabilitySerializable<INBT> // implements ICapabilityProvider
{
	IWebSlinger capInstance;
	LazyOptional<IWebSlinger> cap_provider;

	@CapabilityInject(IWebSlinger.class)
	public static final Capability<IWebSlinger> CAPABILITY = null;

	public WebSlingerProvider(LivingEntity ownerIn, int taskPriorityIn)
	{
		capInstance = new WebSlingerCapability(ownerIn, taskPriorityIn);
		cap_provider = LazyOptional.of(() -> capInstance);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side)
	{
		if (capability == WebSlingerCapability.CAPABILITY)
			return cap_provider.cast();
		return LazyOptional.empty();
	}

	@Override
	public INBT serializeNBT()
	{
		return CAPABILITY.writeNBT(capInstance, WebSlingerCapability.DEFAULT_FACING);
	}

	@Override
	public void deserializeNBT(INBT nbt)
	{
		CAPABILITY.readNBT(capInstance, WebSlingerCapability.DEFAULT_FACING, nbt);
	}
}