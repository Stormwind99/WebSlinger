package com.wumple.webslinger.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WebSlingerStorage implements IStorage<IWebSlinger>
{
    @Override
    public NBTBase writeNBT(Capability<IWebSlinger> capability, IWebSlinger instance, EnumFacing side)
    {
        NBTTagCompound tags = new NBTTagCompound();

        if (instance != null)
        {
            // tags.setLong("rotLastCheckTime", instance.getLastCheckTime());
        }

        return tags;
    }

    @Override
    public void readNBT(Capability<IWebSlinger> capability, IWebSlinger instance, EnumFacing side, NBTBase nbt)
    {
        NBTTagCompound tags = (NBTTagCompound) nbt;

        if ((tags != null) && (instance != null))
        {
            // instance.setLastCheckTime(tags.getLong("rotLastCheckTime"));
        }
    }
}
