package net.malachai.cavernsofchaos.item.advanced;

import net.malachai.cavernsofchaos.entity.custom.boomstick_projectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class boomstickItem extends ProjectileWeaponItem {


    public boomstickItem(Properties properties) {
        super(properties.stacksTo(1).durability(2));

    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return null;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 0;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getDamageValue() == 2) {return InteractionResultHolder.fail(itemstack);}
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 1.4F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pPlayer.getX(), pPlayer.getY() + 1.3, pPlayer.getZ(), pPlayer.getLookAngle().x * 0.06, 0.01, pPlayer.getLookAngle().z * 0.06);
            if (!pLevel.isClientSide) {
                ItemStack nugget = new ItemStack(Items.IRON_NUGGET);
                float verticalacuracy = (float) (Math.random() - 0.5) * 5;
                float horizontalacuracy = (float) (Math.random() - 0.5) * 15;
                boomstick_projectile buckshot = new boomstick_projectile(pLevel, pPlayer);
                buckshot.setItem(nugget);
                buckshot.shootFromRotation(pPlayer, pPlayer.getXRot() + horizontalacuracy, pPlayer.getYRot() + verticalacuracy, 0.0F, 2.5F, 25);
                pLevel.addFreshEntity(buckshot);
                boomstick_projectile buckshot2 = new boomstick_projectile(pLevel, pPlayer);
                buckshot2.setItem(nugget);
                buckshot2.shootFromRotation(pPlayer, pPlayer.getXRot() + horizontalacuracy, pPlayer.getYRot() + verticalacuracy, 0.0F, 2.5F, 15);
                pLevel.addFreshEntity(buckshot2);
                boomstick_projectile buckshot3 = new boomstick_projectile(pLevel, pPlayer);
                buckshot3.setItem(nugget);
                buckshot3.shootFromRotation(pPlayer, pPlayer.getXRot() + horizontalacuracy, pPlayer.getYRot() + verticalacuracy, 0.0F, 2.5F, 10);
                pLevel.addFreshEntity(buckshot3);
                boomstick_projectile buckshot4 = new boomstick_projectile(pLevel, pPlayer);
                buckshot4.setItem(nugget);
                buckshot4.shootFromRotation(pPlayer, pPlayer.getXRot() + horizontalacuracy, pPlayer.getYRot() + verticalacuracy, 0.0F, 2.5F, 15);
                pLevel.addFreshEntity(buckshot4);
                boomstick_projectile buckshot5 = new boomstick_projectile(pLevel, pPlayer);
                buckshot5.setItem(nugget);
                buckshot5.shootFromRotation(pPlayer, pPlayer.getXRot() + horizontalacuracy, pPlayer.getYRot() + verticalacuracy, 0.0F, 2.5F, 25);
                pLevel.addFreshEntity(buckshot5);
                itemstack.hurtAndBreak(1, pPlayer, null);
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (!pPlayer.getAbilities().instabuild) {

            }

            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        }
    }