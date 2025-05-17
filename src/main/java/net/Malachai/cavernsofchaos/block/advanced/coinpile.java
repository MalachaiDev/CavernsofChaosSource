package net.chance.cavernsofchaos.block.advanced;

import com.sun.tools.jconsole.JConsoleContext;
import net.chance.cavernsofchaos.block.ModBlocks;
import net.chance.cavernsofchaos.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class coinpile extends FallingBlock {
    public coinpile(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void falling(FallingBlockEntity pEntity) {
        pEntity.disableDrop();
        super.falling(pEntity);
    }

    @Override
    public void onBrokenAfterFall(Level pLevel, BlockPos pPos, FallingBlockEntity pFallingBlock) {
        pFallingBlock.spawnAtLocation(ModItems.ANCIENTCOINS.get());
        super.onBrokenAfterFall(pLevel, pPos, pFallingBlock);
    }

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
