package com.wumple.webslinger.capability;

import com.wumple.util.adapter.EntityThing;
import com.wumple.util.adapter.IThing;
import com.wumple.util.base.misc.Util;
import com.wumple.webslinger.Reference;
import com.wumple.webslinger.configuration.ConfigHandler;
import com.wumple.webslinger.configuration.ModConfiguration;
import com.wumple.webslinger.webbing.WebbingAttackGoal;
import com.wumple.webslinger.webbing.WebbingEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WebSlingerCapability implements IWebSlinger
{
	public static ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "webslinger");

	// The {@link Capability} instance	
	@CapabilityInject(IWebSlinger.class)
	public static final Capability<IWebSlinger> CAPABILITY = null;
	public static final Direction DEFAULT_FACING = null;

	/// Data
	IThing owner = null;
	int priority;

	@Override
	public void checkInit(IThing ownerIn, int taskPriority)
	{
		if (owner != ownerIn)
		{
			owner = ownerIn;
			priority = taskPriority;
		}
	}

	protected LivingEntity getOwner()
	{
		if (owner instanceof EntityThing)
		{
			EntityThing thing = Util.as(owner, EntityThing.class);
			LivingEntity living = Util.as(thing.get(), LivingEntity.class);
			return living;
		}

		return null;
	}

	// why called by EntityJoinWorldEvent handler rather than WebSlingerCapability ctor?
	// must call MobEntity.goalSelector.addGoal() later than  
	// the AttachCapabilitiesEvent because it is fired via the Entity constructor 
	// before MobEntity.goalSelector is initialized.
	public void handleAISetup()
	{
		if (ConfigHandler.isEnabled())
		{
			LivingEntity living = getOwner();
			MobEntity mob = Util.as(living, MobEntity.class);
			if (mob != null)
			{
				mob.goalSelector.addGoal(priority, new WebbingAttackGoal(living));
			}
		}
	}

	// ----------------------------------------------------------------------
	// Init

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IWebSlinger.class, new WebSlingerStorage(), WebSlingerCapability::new);
	}

	public WebSlingerCapability()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	public WebSlingerCapability(IThing ownerIn, int priorityIn)
	{
		this();
		checkInit(ownerIn, priorityIn);
	}

	// ----------------------------------------------------------------------
	// Events

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		Entity immediateSource = event.getSource().getImmediateSource();
		Entity trueSource = event.getSource().getTrueSource();
		Entity target = event.getEntity();
		LivingEntity myowner = getOwner();

		// if web is shot, let EntityWebbing handle it - so immediateSource and trueSource must be spider
		if ((immediateSource != null) && (myowner != null) && (immediateSource == myowner)
				&& (immediateSource == trueSource))
		{
			tryAttack(immediateSource, trueSource, target);
		}
	}

	protected static void tryAttack(Entity immediateSource, Entity source, Entity target)
	{
		World world = target.world;

		double threshold = ModConfiguration.General.webMeleeChance.get();
		double chance = world.rand.nextDouble();

		if (threshold <= chance)
		{
			return;
		}

		if ((target != null) && (immediateSource != null))
		{
			double distance = immediateSource.getDistanceSq(target);

			// some mods somehow make LivingAttackEvent fire when spider is still far away
			// so make it so spider only does webbing if very close
			if (distance > 2)
			{
				return;
			}
		}

		BlockPos pos = new BlockPos(target.posX, target.posY, target.posZ);

		WebbingEntity.onHit(world, pos, source, target);
	}

}
