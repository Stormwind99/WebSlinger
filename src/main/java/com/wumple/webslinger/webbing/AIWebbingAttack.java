package com.wumple.webslinger.webbing;

import com.wumple.webslinger.configuration.ConfigContainer;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class AIWebbingAttack extends EntityAIBase
{
    private final static double maxDistance = 256.0D; // 16 is 256, 32 is 1024, 64 is 4096
    private final static double minDistance = 4.0D;
    private final EntityLiving parentEntity;
    public int attackTimer;

    public AIWebbingAttack(EntityLiving entity)
    {
        this.parentEntity = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        // got a NPE below once with parentEntity or entitylivingbase being null
        EntityLivingBase entitylivingbase = (parentEntity != null) ? this.parentEntity.getAttackTarget() : null;
        
        return (entitylivingbase != null) ? (entitylivingbase.getDistanceSq(this.parentEntity) >= minDistance) : false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.attackTimer = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        // got a NPE below once with parentEntity or entitylivingbase being null
        EntityLivingBase entitylivingbase = (parentEntity != null) ? this.parentEntity.getAttackTarget() : null;

        if ((parentEntity != null) &&
                (entitylivingbase != null) &&
                (entitylivingbase.getDistanceSq(this.parentEntity) < maxDistance) &&
                this.parentEntity.canEntityBeSeen(entitylivingbase))
        {
            World world = parentEntity.world;

            ++this.attackTimer;

            /*
             * // MAYBE play pre-shoot event if (this.attackTimer == (reshootTime/2)) { world.playEvent((EntityPlayer)null, effect, new BlockPos(this.parentEntity), 0); }
             */

            if (this.attackTimer >= ConfigContainer.slinging.webReshootTime)
            {
                // MAYBE source.world.playEvent((EntityPlayer)null, effect, new BlockPos(this.parentEntity), 0);

                EntityWebbing.sling(world, parentEntity);

                double cooldown = ConfigContainer.slinging.webReshootTime +
                        ConfigContainer.slinging.webReshootTime * world.rand.nextFloat() * ConfigContainer.slinging.webSlingVariance;

                this.attackTimer -= cooldown;

            }
        }
        else if (this.attackTimer > 0)
        {
            --this.attackTimer;
        }
    }
}