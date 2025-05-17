package net.chance.cavernsofchaos.entity.custom;

import net.chance.cavernsofchaos.block.ModBlocks;
import net.chance.cavernsofchaos.block.advanced.glowstickblock;
import net.chance.cavernsofchaos.item.ModItems;
import net.chance.cavernsofchaos.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.phys.BlockHitResult;

import static net.minecraft.world.level.block.Block.popResource;

public class glowstickprojectile extends ThrowableItemProjectile {
    public glowstickprojectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public glowstickprojectile(Level pLevel) {
        super(ModEntities.GLOWSTICK_PROJECTILE.get(), pLevel);
    }

    public glowstickprojectile(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.GLOWSTICK_PROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GLOWSTICKITEM.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide()) {
            if(level().getBlockState(blockPosition()).isAir()){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            this.level().setBlock(blockPosition(), ((glowstickblock) ModBlocks.GLOWSTICKBLOCK.get()).defaultBlockState(),3);
            this.discard();
        }else{
                popResource(level(), blockPosition(), new ItemStack(ModItems.GLOWSTICKITEM.get(), 1));
                this.discard();}

        }

        super.onHitBlock(pResult);
    }
}