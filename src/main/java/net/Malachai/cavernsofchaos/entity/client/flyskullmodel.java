package net.malachai.cavernsofchaos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.malachai.cavernsofchaos.entity.anims.ModAnimationDefinition;
import net.malachai.cavernsofchaos.entity.custom.flyskullEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class flyskullmodel <T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart flyskull;
    private final ModelPart leftwing;
    private final ModelPart rightwing;

	public flyskullmodel(ModelPart root) {
        this.flyskull = root.getChild("flyskull");
        this.leftwing = this.flyskull.getChild("leftwing");
        this.rightwing = this.flyskull.getChild("rightwing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("flyskull", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 2.0F, -3.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition leftwing = head.addOrReplaceChild("leftwing", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -4.0F));

        PartDefinition cube_r1 = leftwing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 15).addBox(0.5517F, -4.8093F, -3.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition rightwing = head.addOrReplaceChild("rightwing", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 4.0F));

        PartDefinition cube_r2 = rightwing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 15).addBox(0.6477F, -4.7919F, -8.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, 0.0F, 0.3054F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(ModAnimationDefinition.SKULLFLIGHT, limbSwing, limbSwingAmount, 3f, 10f);
        this.animate(((flyskullEntity) entity).idleAnimationState, ModAnimationDefinition.SKULLFLIGHT, ageInTicks, 1f);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        flyskull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return flyskull;
    }

}