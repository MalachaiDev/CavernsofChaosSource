package net.chance.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.entity.custom.LizagerEntity;
import net.chance.cavernsofchaos.entity.custom.flyskullEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class flyskullRenderer extends MobRenderer<flyskullEntity, flyskullmodel<flyskullEntity>> {
    public flyskullRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new flyskullmodel<>(pContext.bakeLayer(ModModelLayers.FLYSKULL_LAYER)), .4f);
        this.addLayer(new SkullEmissiveLayer<>(this));
    }


    @Override
    public ResourceLocation getTextureLocation(flyskullEntity pEntity) {
        return new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/flyskull.png");
    }

    @Override
    public void render(flyskullEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}