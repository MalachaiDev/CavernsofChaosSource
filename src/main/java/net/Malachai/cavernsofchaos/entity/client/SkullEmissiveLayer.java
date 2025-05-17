package net.chance.cavernsofchaos.entity.client;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkullEmissiveLayer<T extends LivingEntity> extends EyesLayer<T, flyskullmodel<T>> {
    private static final RenderType SKULL_EYES = RenderType.eyes(new ResourceLocation(Cavernsofchaos.MODID,"textures/entity/skulleye.png"));

    public SkullEmissiveLayer(RenderLayerParent<T, flyskullmodel<T>> p_116964_) {
        super(p_116964_);
    }

    public RenderType renderType() {
        return SKULL_EYES;
    }
}
