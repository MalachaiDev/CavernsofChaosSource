package net.malachai.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.custom.LizagerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class lizagerRenderer extends MobRenderer<LizagerEntity, lizagermodel<LizagerEntity>> {
    public lizagerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new lizagermodel<>(pContext.bakeLayer(ModModelLayers.LIZAGER_LAYER)), .4f);
    }

    @Override
    public ResourceLocation getTextureLocation(LizagerEntity pEntity) {
        return new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/lizager.png");
    }

    @Override
    public void render(LizagerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}