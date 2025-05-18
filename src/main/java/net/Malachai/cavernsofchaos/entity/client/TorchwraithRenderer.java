package net.malachai.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.custom.TorchwraithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TorchwraithRenderer extends MobRenderer<TorchwraithEntity, TorchwraithModel<TorchwraithEntity>> {
    public TorchwraithRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TorchwraithModel<>(pContext.bakeLayer(ModModelLayers.TORCHWRAITH_LAYER)), .2f);
        this.addLayer(new WraithEmissiveLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TorchwraithEntity pEntity) {
        return new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/torchwraith.png");
    }

    @Override
    public void render(TorchwraithEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}