package com.wumple.webslinger.webbing;

import com.wumple.webslinger.configuration.ModConfiguration;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

public class WebbingAttackGoal extends Goal
{
	private final static double maxDistance = 256.0D; // 16 is 256, 32 is 1024, 64 is 4096
	private final static double minDistance = 4.0D;
	private final LivingEntity attacker;
	public int attackTimer;

	public WebbingAttackGoal(LivingEntity attackerIn)
	{
		attacker = attackerIn;
	}
	
	protected LivingEntity getTarget()
	{
		LivingEntity target = null;

		// got a NPE below once with parentEntity or LivingEntitybase being null
		if (attacker instanceof MobEntity)
		{
			MobEntity mobattacker = (MobEntity)attacker;
			target = (attacker != null) ? mobattacker.getAttackTarget() : null;
		}
		else
		{
			target = (attacker != null) ? this.attacker.getLastAttackedEntity() : null;
		}

		return target;
	}
	
	@Override
	public boolean shouldExecute()
	{
		LivingEntity target = getTarget();
		
		boolean shouldExecute = (target != null) ? (target.getDistanceSq(this.attacker) >= minDistance) : false;
		
		return shouldExecute;
	}

	@Override
	public void startExecuting()
	{
		this.attackTimer = 0;
	}

	@Override
	public void resetTask()
	{
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick()
	{
		LivingEntity target = getTarget();

		if ((attacker != null) && (target != null)
				&& (target.getDistanceSq(this.attacker) < maxDistance)
				&& this.attacker.canEntityBeSeen(target))
		{
			World world = attacker.world;

			++this.attackTimer;

			/*
			 * // MAYBE play pre-shoot event if (this.attackTimer == (reshootTime/2)) { world.playEvent((EntityPlayer)null, effect, new BlockPos(this.parentEntity), 0); }
			 */

			if (this.attackTimer >= ModConfiguration.General.webReshootTime.get())
			{
				// MAYBE source.world.playEvent((EntityPlayer)null, effect, new BlockPos(this.parentEntity), 0);

				WebbingEntity.sling(world, attacker);

				double cooldown = ModConfiguration.General.webReshootTime.get() + ModConfiguration.General.webReshootTime.get()
						* world.rand.nextFloat() * ModConfiguration.General.webSlingVariance.get();

				this.attackTimer -= cooldown;

			}
		}
		else if (this.attackTimer > 0)
		{
			--this.attackTimer;
		}
	}
}