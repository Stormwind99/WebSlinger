package com.wumple.webslinger.webbing;

import org.apache.logging.log4j.LogManager;

import com.wumple.webslinger.ConfigManager;
import com.wumple.webslinger.ObjectHandler;
import com.wumple.webslinger.Reference;
import com.wumple.webslinger.WebSlinger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class WebbingEntity extends ProjectileItemEntity implements IRendersAsItem
{
	public WebbingEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	public WebbingEntity(World worldIn)
	{
		super(ObjectHandler.WEBBING, worldIn);
	}

	public WebbingEntity(double x, double y, double z, World worldIn)
	{
		super(ObjectHandler.WEBBING, x, y, z, worldIn);
	}

	public WebbingEntity(LivingEntity livingEntityIn, World worldIn)
	{
		super(ObjectHandler.WEBBING, livingEntityIn, worldIn);
	}

	public WebbingEntity(World worldIn, LivingEntity livingEntityIn)
	{
		super(ObjectHandler.WEBBING, livingEntityIn, worldIn);
	}

	public WebbingEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world)
	{
		this(ObjectHandler.WEBBING, world);
	}

	@Override
	protected Item getDefaultItem()
	{
		return ObjectHandler.webbing;
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		boolean doit = true;
		LivingEntity thrower = getThrower();
		Entity entityHit = null;

		if (result.getType() == RayTraceResult.Type.ENTITY)
		{
			final EntityRayTraceResult rayTraceResult = (EntityRayTraceResult) result;
			entityHit = rayTraceResult.getEntity();
			// 0 damage attack
			entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0);
		}

		if (result.getType() != RayTraceResult.Type.MISS)
		{
			BlockPos pos;

			if (result.getType() == RayTraceResult.Type.BLOCK)
			{
				final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) result;
				BlockPos blockpos = rayTraceResult.getPos();

				// if hitting another cobweb, don't create web - to avoid massive web areas
				if (!ConfigManager.General.webbingOnWeb.get())
				{
					BlockState state = world.getBlockState(blockpos);
					Block oldBlock = state.getBlock();
					doit &= (oldBlock != Blocks.COBWEB);
				}

				// need to check block immediately before hit, not the hit block itself
				pos = blockpos.offset(rayTraceResult.getFace());
			}
			else // RayTraceResult.Type.ENTITY
			{
				final EntityRayTraceResult rayTraceResult = (EntityRayTraceResult) result;
				pos = rayTraceResult.getEntity().getPosition();
			}

			// don't hit the thrower
			doit &= (entityHit == null) || (this.getThrower() != entityHit);

			if (doit)
			{
				onHit(world, pos, thrower, entityHit);
			}
		}

		if (doit && !this.world.isRemote)
		{
			this.world.setEntityState(this, (byte) 3);
			this.remove();
		}
	}

	/*
	// MAYBE from SnowballEntity
	@OnlyIn(Dist.CLIENT)
	private IParticleData makeParticle()
	{
		ItemStack itemstack = this.func_213882_k();
		return (IParticleData) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL
				: new ItemParticleData(ParticleTypes.ITEM, itemstack));
	}
	*/

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		// MAYBE from SnowballEntity 
		// IParticleData iparticledata = this.makeParticle();

		if (id == 3)
		{
			for (int i = 0; i < 3; ++i)
			{
				this.world.addParticle(particleType, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	// ------------------------------------------------------------------------
	// Helpers

	public static WebbingEntity sling(World worldIn, LivingEntity entityIn)
	{
		WebbingEntity entity = null;

		float pitch = 1.0F / (entityIn.getRNG().nextFloat() * 0.4F + 0.8F);
		entityIn.playSound(ObjectHandler.WEBBING_SHOOT, 1.0F, pitch);

		if (!worldIn.isRemote)
		{
			entity = new WebbingEntity(entityIn, worldIn);
			double inaccuracy = ConfigManager.General.webSlingInaccuracy.get();
			entity.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, 0.0F, 1.1F, (float) inaccuracy);
			worldIn.addEntity(entity);
		}

		return entity;
	}

	public static void onHit(World world, BlockPos pos, Entity source, Entity target)
	{
		BlockState state = world.getBlockState(pos);
		Block oldBlock = state.getBlock();
		boolean stick = true;

		if (!state.getMaterial().isReplaceable()
				|| (!ConfigManager.General.allowWebReplacement.get() && !oldBlock.isAir(state, world, pos)))
		{
			stick = false;
		}

		if (!stick)
		{
			world.playSound((PlayerEntity) null, pos, ObjectHandler.WEBBING_NONSTICK, SoundCategory.NEUTRAL, 0.5F,
					0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
			return;
		}

		world.playSound((PlayerEntity) null, pos, ObjectHandler.WEBBING_STICK, SoundCategory.NEUTRAL, 0.5F,
				0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));

		if (world.isRemote)
		{
			return;
		}

		if (ConfigManager.Debugging.debug.get())
		{
			LogManager.getLogger(Reference.MOD_ID).info("Making cobweb at " + pos.toString());
		}
		world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
	}

	// ------------------------------------------------------------------------

	public static final BasicParticleType particleType = ParticleTypes.ITEM_SNOWBALL;

	// ------------------------------------------------------------------------

	@Override
	public ItemStack getItem()
	{
		return new ItemStack(ObjectHandler.webbing);
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
