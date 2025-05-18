package net.malachai.cavernsofchaos.block.blockentity;

import net.malachai.cavernsofchaos.block.ModBlockEntities;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class bugEggBlockEntity extends BlockEntity {
    private final BaseSpawner spawner = new BaseSpawner() {
        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.blockEvent(p_155768_, ModBlocks.BUGEGG.get(), p_155769_, 0);
        }
        public void setNextSpawnData(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.setNextSpawnData(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState blockstate = p_155771_.getBlockState(p_155772_);
                p_155771_.sendBlockUpdated(p_155772_, blockstate, blockstate, 4);
            }
        }
        @org.jetbrains.annotations.Nullable
        public net.minecraft.world.level.block.entity.BlockEntity getbugEggBlockEntity(){ return bugEggBlockEntity.this; }
    };

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, bugEggBlockEntity pBlockEntity) {
        pBlockEntity.spawner.clientTick(pLevel, pPos);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, bugEggBlockEntity pBlockEntity) {
        pBlockEntity.spawner.serverTick((ServerLevel)pLevel, pPos);
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        List<? extends Player> players = pLevel.players();
        for(Player p : players)
            if(p.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) < 8 * 8 && !p.isSpectator() && !p.isCreative()) {
                Silverfish silverfish = EntityType.SILVERFISH.create(pLevel);
                if (silverfish != null) {
                    silverfish.setHealth(1);
                    silverfish.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                    pLevel.addFreshEntity(silverfish);
                    silverfish.spawnAnim();
                }
                pLevel.destroyBlock(pPos,false);
            }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
    }



    public bugEggBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(ModBlockEntities.BUG_NEST.get(), p_155052_, p_155053_);
    }
}