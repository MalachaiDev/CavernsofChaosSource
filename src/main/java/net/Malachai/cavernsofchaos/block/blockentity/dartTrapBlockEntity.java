package net.malachai.cavernsofchaos.block.blockentity;

import net.malachai.cavernsofchaos.block.ModBlockEntities;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class dartTrapBlockEntity extends BlockEntity {
    private final BaseSpawner spawner = new BaseSpawner() {
        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.blockEvent(p_155768_, ModBlocks.DARTTRAP.get(), p_155769_, 0);
        }
        public void setNextSpawnData(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.setNextSpawnData(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState blockstate = p_155771_.getBlockState(p_155772_);
                p_155771_.sendBlockUpdated(p_155772_, blockstate, blockstate, 4);
            }
        }
        @org.jetbrains.annotations.Nullable
        public BlockEntity getdartTrapBlockEntity(){ return dartTrapBlockEntity.this; }
    };


    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, dartTrapBlockEntity pBlockEntity) {
        pBlockEntity.spawner.clientTick(pLevel, pPos);
    }
    static int cooldown = 0;
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, dartTrapBlockEntity pBlockEntity) {

        if(cooldown > 0){cooldown--;}
        pBlockEntity.spawner.serverTick((ServerLevel)pLevel, pPos);
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        List<? extends Player> players = pLevel.players();
        if(cooldown == 0) {
            for (Player p : players)
                if (!p.isSpectator() && !p.isCreative()) {
                    if (p.getY() > pPos.getY() - 1.5 && p.getY() < pPos.getY() + 1.5) {
                        if (p.getX() > pPos.getX() - 0.5 && p.getX() < pPos.getX() + 1.5 && p.getZ() > pPos.getZ()) {
                            Arrow arrow = new Arrow(pLevel, pPos.getX()+.5,pPos.getY(),pPos.getZ()+1);
                            arrow.setDeltaMovement(0,0.25,1.5);
                            pLevel.addFreshEntity(arrow);
                            pBlockEntity.playSound(pState, SoundEvents.SKELETON_SHOOT);
                            cooldown = 200;
                        } else if (p.getX() > pPos.getX() - 0.5 && p.getX() < pPos.getX() + 1.5 && p.getZ() < pPos.getZ()) {
                            Arrow arrow = new Arrow(pLevel, pPos.getX()+.5,pPos.getY()+0.5,pPos.getZ());
                            arrow.setDeltaMovement(0,0.25,-1.5);
                            pLevel.addFreshEntity(arrow);
                            pBlockEntity.playSound(pState, SoundEvents.SKELETON_SHOOT);
                            cooldown = 200;
                        } else if (p.getZ() > pPos.getZ() - 0.5 && p.getZ() < pPos.getZ() + 1.5 && p.getX() > pPos.getX()) {
                            Arrow arrow = new Arrow(pLevel, pPos.getX()+1,pPos.getY()+0.5,pPos.getZ()+.5);
                            arrow.setDeltaMovement(1.5,0.25,0);
                            pLevel.addFreshEntity(arrow);
                            pBlockEntity.playSound(pState, SoundEvents.SKELETON_SHOOT);
                            cooldown = 200;
                        } else if (p.getZ() > pPos.getZ() - 0.5 && p.getZ() < pPos.getZ() + 1.5 && p.getX() < pPos.getX()) {
                            Arrow arrow = new Arrow(pLevel, pPos.getX(),pPos.getY()+0.5,pPos.getZ()+.5);
                            arrow.setDeltaMovement(-1.5,0.25,0);
                            pLevel.addFreshEntity(arrow);
                            pBlockEntity.playSound(pState, SoundEvents.SKELETON_SHOOT);
                            cooldown = 200;
                        }
                    }
                }
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
    }



    public dartTrapBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(ModBlockEntities.DART_TRAP.get(), p_155052_, p_155053_);
    }

    private void playSound(BlockState p_58601_, SoundEvent p_58602_) {
        double d0 = (double) this.worldPosition.getX() + 0.5D;
        double d1 = (double) this.worldPosition.getY() + 0.5D;
        double d2 = (double) this.worldPosition.getZ() + 0.5D;
        this.level.playSound((Player) null, d0, d1, d2, p_58602_, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}