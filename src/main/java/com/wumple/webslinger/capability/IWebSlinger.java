package com.wumple.webslinger.capability;

import net.minecraft.entity.LivingEntity;

public interface IWebSlinger
{
	void checkInit(LivingEntity ownerIn, int taskPriority);
}
