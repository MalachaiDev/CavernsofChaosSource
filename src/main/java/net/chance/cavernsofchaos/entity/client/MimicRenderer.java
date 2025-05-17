package net.chance.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.entity.custom.BoulderGolemEntity;
import net.chance.cavernsofchaos.entity.custom.MimicEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel<MimicEntity>> {
    public MimicRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MimicModel<>(pContext.bakeLayer(ModModelLayers.MIMIC_LAYER)), .4f);
    }

    @Override
    public ResourceLocation getTextureLocation(MimicEntity pEntity) {
        return new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/mimic.png");
    }

    @Override
    public void render(MimicEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}