package com.wumple.webslinger.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WebSlingerStorage implements IStorage<IWebSlinger>
{
    @Override
    public INBT writeNBT(Capability<IWebSlinger> capability, IWebSlinger instance, Direction side)
    {
        CompoundNBT tags = new CompoundNBT();

        if (instance != null)
        {
            // tags.setLong("rotLastCheckTime", instance.getLastCheckTime());
        }

        return tags;
    }

    @Override
    public void readNBT(Capability<IWebSlinger> capability, IWebSlinger instance, Direction side, INBT nbt)
    {
        CompoundNBT tags = (CompoundNBT) nbt;

        if ((tags != null) && (instance != null))
        {
            // instance.setLastCheckTime(tags.getLong("rotLastCheckTime"));
        }
    }
}