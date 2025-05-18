package net.malachai.cavernsofchaos.block.blockentity;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.ModEntities;
import net.malachai.cavernsofchaos.entity.custom.MimicEntity;
import net.malachai.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ancientChestBlock extends BarrelBlock {
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 15, 15);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty TRAPPED = BlockStateProperties.EYE;
    public static final BooleanProperty LOOT = BlockStateProperties.CONDITIONAL;
    public static final ResourceLocation CHEST_LOOT = new ResourceLocation(Cavernsofchaos.MODID, "chests/ancient_chest");
    public ancientChestBlock(Properties pProperties) {

        super(pProperties);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(TRAPPED, Boolean.FALSE).setValue(LOOT, Boolean.FALSE));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, TRAPPED, LOOT);
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
@Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
    if(blockState.getValue(LOOT)){
        RandomizableContainerBlockEntity.setLootTable(level, RandomSource.create(), blockPos, CHEST_LOOT);
        level.setBlock(blockPos, blockState.setValue(LOOT,Boolean.FALSE), 3);
    }
        if(blockState.getValue(TRAPPED)){
            @NotNull EntityType<MimicEntity> Mimic = ModEntities.MIMIC.get();
            Entity mimic = Mimic.create(level);
            mimic.moveTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 1.0D, (double) blockPos.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(mimic);
            level.playSound((Player) null,blockPos, ModSounds.MIMIC_JUMPSCARE.get(), SoundSource.BLOCKS);
            level.setBlock(blockPos, blockState.setValue(TRAPPED,Boolean.FALSE), 3);
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof ancientChestBlockEntity) {
                player.openMenu((ancientChestBlockEntity) blockentity);
            }
            return InteractionResult.CONSUME;
        }
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ancientChestBlockEntity) {
            ((ancientChestBlockEntity) blockentity).recheckOpen();
        }
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new ancientChestBlockEntity(pos, blockState);
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, @Nullable LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ancientChestBlockEntity) {
                ((ancientChestBlockEntity) blockentity).setCustomName(stack.getHoverName());
            }
        }
    }

    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean force) {
        if (state.hasBlockEntity() && (!(newState.getBlock() instanceof ancientChestBlock) || !newState.hasBlockEntity())) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof Container) {
                Containers.dropContents(level, blockPos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }

            level.removeBlockEntity(blockPos);
        }
    }

}