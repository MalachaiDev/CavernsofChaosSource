package net.malachai.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.custom.CaveSlime;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CaveSlimeRenderer extends MobRenderer<CaveSlime, CaveSlimeModel<CaveSlime>> {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(Cavernsofchaos.MODID, "textures/entity/caveslime.png");

    public CaveSlimeRenderer(EntityRendererProvider.Context p_174391_) {
        super(p_174391_, new CaveSlimeModel<>(p_174391_.bakeLayer(ModModelLayers.CAVESLIME_LAYER)), 0.25F);
        this.addLayer(new CaveSlimeOuterlayer<>(this, p_174391_.getModelSet()));
    }

    public void render(CaveSlime pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = 0.25F * (float)pEntity.getSize();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    protected void scale(CaveSlime pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float f = 0.999F;
        pMatrixStack.scale(0.999F, 0.999F, 0.999F);
        pMatrixStack.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float)pLivingEntity.getSize();
        float f2 = Mth.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        pMatrixStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(CaveSlime pEntity) {
        return SLIME_LOCATION;
    }
}