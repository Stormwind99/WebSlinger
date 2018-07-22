package com.wumple.webslinger.capability;

import javax.annotation.Nullable;

import com.wumple.util.adapter.IThing;
import com.wumple.util.capability.SimpleCapabilityProvider;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class WebSlingerProvider extends SimpleCapabilityProvider<IWebSlinger>
{
    IThing owner = null;

    public WebSlingerProvider(Capability<IWebSlinger> capability, @Nullable EnumFacing facing, IThing ownerIn)
    {
        super(capability, facing, (capability != null) ? capability.getDefaultInstance() : null);
        owner = ownerIn;
    }

    public WebSlingerProvider(Capability<IWebSlinger> capability, @Nullable EnumFacing facing, IWebSlinger instance,
            IThing ownerIn)
    {
        super(capability, facing, instance);
        owner = ownerIn;
    }

    public final IWebSlinger getInstance()
    {
        IWebSlinger cap = super.getInstance();
        cap.setOwner(owner);
        return cap;
    }
}
