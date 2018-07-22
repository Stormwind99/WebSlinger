package com.wumple.webslinger.capability;

import javax.annotation.Nullable;

import com.wumple.util.adapter.IThing;
import com.wumple.util.capability.SimpleCapabilityProvider;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class WebSlingerProvider extends SimpleCapabilityProvider<IWebSlinger>
{
    IThing owner = null;
    int taskPriority = -1;

    public WebSlingerProvider(Capability<IWebSlinger> capability, @Nullable EnumFacing facing, IThing ownerIn, int taskPriorityIn)
    {
        super(capability, facing, (capability != null) ? capability.getDefaultInstance() : null);
        owner = ownerIn;
        taskPriority = taskPriorityIn;
    }

    public WebSlingerProvider(Capability<IWebSlinger> capability, @Nullable EnumFacing facing, IWebSlinger instance,
            IThing ownerIn, int taskPriorityIn)
    {
        super(capability, facing, instance);
        owner = ownerIn;
        taskPriority = taskPriorityIn;
    }

    public final IWebSlinger getInstance()
    {
        IWebSlinger cap = super.getInstance();
        cap.checkInit(owner, taskPriority);
        return cap;
    }
}
