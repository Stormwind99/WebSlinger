package com.wumple.webslinger.capability;

import com.wumple.util.Reference;
import com.wumple.util.adapter.EntityThing;
import com.wumple.util.adapter.IThing;
import com.wumple.util.misc.Util;
import com.wumple.webslinger.configuration.ConfigContainer;
import com.wumple.webslinger.webbing.AIWebbingAttack;
import com.wumple.webslinger.webbing.EntityWebbing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@Mod.EventBusSubscriber
public class WebSlinger implements IWebSlinger
{
    // The {@link Capability} instance
    @CapabilityInject(IWebSlinger.class)
    public static final Capability<IWebSlinger> CAPABILITY = null;
    public static final EnumFacing DEFAULT_FACING = null;

    // IDs of the capability
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "webslinger");

    /// Data
    IThing owner = null;

    @Override
    public void setOwner(IThing ownerIn)
    {
        if (owner != ownerIn)
        {
            owner = ownerIn;

            initialize();
        }
    }

    protected EntityLiving getOwner()
    {
        if (owner instanceof EntityThing)
        {
            EntityThing thing = Util.as(owner, EntityThing.class);
            EntityLiving living = Util.as(thing.owner, EntityLiving.class);
            return living;
        }

        return null;
    }

    protected void initialize()
    {
        if (ConfigContainer.webSlinging == true)
        {
            EntityLiving living = getOwner();
            if (living != null)
            {
                living.tasks.addTask(7, new AIWebbingAttack(living));
            }
        }
    }

    // ----------------------------------------------------------------------
    // Init

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IWebSlinger.class, new WebSlingerStorage(), () -> new WebSlinger());
    }

    WebSlinger()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    WebSlinger(IThing ownerIn)
    {
        this();
        setOwner(ownerIn);
    }

    // ----------------------------------------------------------------------
    // Events

    /*
     * Handle melee webbing attack for spiders
     */
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event)
    {
        Entity immediateSource = event.getSource().getImmediateSource();
        Entity trueSource = event.getSource().getTrueSource();
        Entity target = event.getEntity();
        EntityLiving myowner = getOwner();

        // if web is shot, let EntityWebbing handle it - so immediateSource and trueSource must be spider
        if ((immediateSource == myowner) && (immediateSource == trueSource))
        {
            tryAttack(immediateSource, trueSource, target);
        }
    }

    protected static void tryAttack(Entity immediateSource, Entity source, Entity target)
    {
        World world = target.world;
     
        if (ConfigContainer.webMeleeChance <= world.rand.nextDouble())
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

        EntityWebbing.onHit(world, pos, source, target);
    }
}
