package net.malachai.cavernsofchaos.entity.client;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WraithEmissiveLayer<T extends LivingEntity> extends EyesLayer<T, TorchwraithModel<T>> {
    private static final RenderType WRAITH_EYES = RenderType.eyes(new ResourceLocation(Cavernsofchaos.MODID,"textures/entity/wraitheyes.png"));

    public WraithEmissiveLayer(RenderLayerParent<T, TorchwraithModel<T>> p_116964_) {
        super(p_116964_);
    }

    public RenderType renderType() {
        return WRAITH_EYES;
    }
}
