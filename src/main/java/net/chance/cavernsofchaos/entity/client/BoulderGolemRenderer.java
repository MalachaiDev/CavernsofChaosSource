package net.chance.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.entity.custom.BoulderGolemEntity;
import net.chance.cavernsofchaos.entity.custom.LizagerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BoulderGolemRenderer extends MobRenderer<BoulderGolemEntity, boulderGolemModel<BoulderGolemEntity>> {
    public BoulderGolemRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new boulderGolemModel<>(pContext.bakeLayer(ModModelLayers.BOULDERGOLEM_LAYER)), .4f);
    }

    @Override
    public ResourceLocation getTextureLocation(BoulderGolemEntity pEntity) {
        return new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/bouldergolem.png");
    }

    @Override
    public void render(BoulderGolemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}