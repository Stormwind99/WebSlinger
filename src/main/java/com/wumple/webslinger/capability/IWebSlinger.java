package com.wumple.webslinger.capability;

import com.wumple.util.adapter.IThing;

public interface IWebSlinger
{
	void checkInit(IThing ownerIn, int taskPriority);
	void handleAISetup();
}
