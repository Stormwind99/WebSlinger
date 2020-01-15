package com.wumple.webslinger.webbing;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class WebbingItem extends Item
{
	public static final String ID = "webslinger:webbing";

	public WebbingItem(Item.Properties builder)
	{
		super(builder.maxStackSize(16));
		// TODO this.setCreativeTab(CreativeTabs.MISC);

		setRegistryName(ID);
	}

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.isCreative())
        {
            itemstack.shrink(1);
        }

        WebbingEntity.sling(worldIn, playerIn);

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
    }
    
    /*
    public ActionResult<ItemStack> onItemRightClick2(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
           itemstack.shrink(1);
        }

        worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
        	EntityWebbing snowballentity = new EntityWebbing(worldIn, playerIn);
           snowballentity.setItem(itemstack);
           snowballentity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
           worldIn.addEntity(snowballentity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
     }
     */
}