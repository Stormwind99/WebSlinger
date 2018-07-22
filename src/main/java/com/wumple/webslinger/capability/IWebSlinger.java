package com.wumple.webslinger.capability;

import com.wumple.util.adapter.IThing;

public interface IWebSlinger
{
    /**
     * Set the owner of this capability, and init based on that owner
     */
    void checkInit(IThing ownerIn, int taskPriority);
}
