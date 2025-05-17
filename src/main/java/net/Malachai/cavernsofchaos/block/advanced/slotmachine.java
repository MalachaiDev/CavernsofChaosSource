package net.chance.cavernsofchaos.block.advanced;

import net.chance.cavernsofchaos.block.ModBlocks;
import net.chance.cavernsofchaos.item.ModItems;
import net.chance.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.event.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class slotmachine extends HorizontalDirectionalBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape NORTH = Block.box(2.0D, 0.1D, 8.0D, 14.0D, 32.0D, 15.9D);
    private static final VoxelShape[] DIRECTIONALSHAPES = new VoxelShape[]{
            // South
            Shapes.or(
                    box(2.0D, 0.0D, 8.0D, 14.0D, 32.0D, 16.0D)
            ).optimize(),
            // West
            Shapes.or(
                    box(0.0D, 0.0D, 2.0D, 8.0D, 32.0D, 14.0D)
            ).optimize(),
            // North
            Shapes.or(
                    box(2.0D, 0.0D, 0.0D, 14.0D, 32.0D, 8.0D)
            ).optimize(),
            // East
            Shapes.or(
                    box(8.0D, 0.0D, 2.0D, 16.0D, 32.0D, 14.0D)
            ).optimize()
    };


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return DIRECTIONALSHAPES[direction.get2DDataValue()];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return DIRECTIONALSHAPES[direction.get2DDataValue()];
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }



    public slotmachine(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        FluidState fluid = level.getFluidState(context.getClickedPos());

        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());
    }








    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {


            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (itemstack.is(ModItems.ANCIENTCOINS.get())) {
                    pLevel.playSound((Player) null, pPos, ModSounds.INSERT_COIN.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                    var roll = Math.random();
                    var outcome = Math.random();
                    if (!pPlayer.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if (roll > 0.995) {
                        popResource(pLevel, pPos, new ItemStack(ModItems.ANCIENTCOINS.get(), 32));
                        pLevel.playSound((Player) null, pPos, ModSounds.SLOT_WIN_BIG.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                    } else if (roll > 0.8) {
                        pLevel.playSound((Player) null, pPos, ModSounds.SLOT_WIN_SMALL.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                        if (outcome > 0.9) {
                            popResource(pLevel, pPos, new ItemStack(Items.GOLDEN_APPLE, 1));
                        } else if (outcome > 0.5) {
                            popResource(pLevel, pPos, new ItemStack(Items.GOLDEN_CARROT, 4));
                        } else {
                            popResource(pLevel, pPos, new ItemStack(ModItems.ANCIENTCOINS.get(), 2));
                        }

                    } else if (roll < 0.8 && roll > 0.1) {
                        pLevel.playSound((Player) null, pPos, ModSounds.SLOT_LOSE.get(), SoundSource.BLOCKS, 2.0F, 1.0F);

                    } else if (roll <= 0.1 && roll > 0.001) {
                        if (outcome <= 0.2) {
                            pLevel.playSound((Player) null, pPos, ModSounds.SLOT_LOSE_BAD.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                            Silverfish silverfish = EntityType.SILVERFISH.create(pLevel);
                            if (silverfish != null) {
                                silverfish.setHealth(1);
                                silverfish.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                                pLevel.addFreshEntity(silverfish);
                                silverfish.spawnAnim();
                            }
                        } else if (outcome > 0.2 && outcome < 0.4) {
                            Potion poison = Potions.LONG_WEAKNESS;
                            ThrownPotion thrownpotion = EntityType.POTION.create(pLevel);
                            thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.POTION), poison));
                            thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
                            thrownpotion.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                            pLevel.addFreshEntity(thrownpotion);
                        } else if (outcome > 0.2 && outcome < 0.4) {
                            Potion poison = Potions.SLOWNESS;
                            ThrownPotion thrownpotion = EntityType.POTION.create(pLevel);
                            thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.POTION), poison));
                            thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
                            thrownpotion.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                            pLevel.addFreshEntity(thrownpotion);
                        } else if (outcome > 0.4 && outcome < 0.6) {
                            Potion poison = Potions.HARMING;
                            ThrownPotion thrownpotion = EntityType.POTION.create(pLevel);
                            thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.POTION), poison));
                            thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
                            thrownpotion.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                            pLevel.addFreshEntity(thrownpotion);
                        } else if (outcome > 0.6 && outcome < 0.8) {
                            Zombie zombie = EntityType.ZOMBIE.create(pLevel);
                            zombie.setBaby(TRUE);
                            zombie.setHealth(4);
                            zombie.setSpeed(2);
                            zombie.moveTo((double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, 0.0F, 0.0F);
                            pLevel.addFreshEntity(zombie);
                            pLevel.playSound((Player) null, pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 2.0F, 1.0F);
                        } else if (outcome > 0.8) {
                            popResource(pLevel, pPos, new ItemStack(Items.POISONOUS_POTATO, 2));
                        }

                    } else if (roll <= 0.001) {

                        pLevel.scheduleTick(pPos, this, 30);
                        pLevel.playSound((Player) null, pPos, ModSounds.SLOT_LOSE_CRIT.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                    return InteractionResult.SUCCESS;
            }
        return InteractionResult.FAIL;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Vec3 vec3 = pPos.getCenter();
        pLevel.explode((Entity) null, pLevel.damageSources().explosion(null), (ExplosionDamageCalculator) null, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);
        pLevel.removeBlock(pPos, false);
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}

