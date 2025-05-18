package net.malachai.cavernsofchaos.block.advanced;

import net.malachai.cavernsofchaos.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class nitroblock extends FallingBlock {
    public nitroblock(Properties pProperties) {
        super(pProperties);
    }
   // @Override
    //public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
     //                            Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      //  Vec3 vec3 = pPos.getCenter();
      //  pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
      //  return InteractionResult.SUCCESS;
    //}

    @Override
    public void onLand(Level pLevel, BlockPos pPos, BlockState pState, BlockState pReplaceableState, FallingBlockEntity pFallingBlock) {
        Vec3 vec3 = pPos.getCenter();
        pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);
        super.onLand(pLevel, pPos, pState, pReplaceableState, pFallingBlock);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState pState, Level pLevel, BlockPos pPos, Player player, boolean willHarvest, FluidState fluid) {
        Vec3 vec3 = pPos.getCenter();
        pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);

        return super.onDestroyedByPlayer(pState, pLevel, pPos, player, willHarvest, fluid);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        Vec3 vec3 = pPos.getCenter();
        pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        Vec3 vec3 = pHit.getLocation();
        pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);
        super.onProjectileHit(pLevel, pState, pHit, pProjectile);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public void onBlockExploded(BlockState state, Level pLevel, BlockPos pPos, Explosion explosion) {
        pLevel.removeBlock(pPos, false);
        Vec3 vec3 = pPos.getCenter();
        pLevel.explode((Entity)null, pLevel.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);

        super.onBlockExploded(state, pLevel, pPos, explosion);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.removeBlock(pPos, false);
        int dropcount = (int)Math.round(Math.random()*2) + 1;
        popResource(pLevel, pPos, new ItemStack(ModItems.NITROBOTTLE.get(), dropcount));
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
    }
}
