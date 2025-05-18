package net.malachai.cavernsofchaos.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.malachai.cavernsofchaos.entity.anims.ModAnimationDefinition;
import net.malachai.cavernsofchaos.entity.custom.MimicEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class MimicModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "custommodel"), "main");
	private final ModelPart mimic;
	private final ModelPart arm2;
	private final ModelPart arm1;
	private final ModelPart arm3;
	private final ModelPart eye;
	private final ModelPart arm4;

	public MimicModel(ModelPart root) {
		this.mimic = root.getChild("mimic");
		this.arm2 = this.mimic.getChild("arm2");
		this.arm1 = this.mimic.getChild("arm1");
		this.arm3 = this.mimic.getChild("arm3");
		this.eye = this.mimic.getChild("eye");
		this.arm4 = this.mimic.getChild("arm4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mimic = partdefinition.addOrReplaceChild("mimic", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arm2 = mimic.addOrReplaceChild("arm2", CubeListBuilder.create(), PartPose.offset(3.0F, -7.5F, 0.0F));

		PartDefinition cube_r1 = arm2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.9635F, 0.0F));

		PartDefinition cube_r2 = arm2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 30).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.1781F, 0.0F));

		PartDefinition arm1 = mimic.addOrReplaceChild("arm1", CubeListBuilder.create(), PartPose.offset(-2.0F, -7.5F, 0.0F));

		PartDefinition cube_r3 = arm1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.5F, 1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition arm3 = mimic.addOrReplaceChild("arm3", CubeListBuilder.create(), PartPose.offset(-3.0F, -7.5F, 0.0F));

		PartDefinition cube_r4 = arm3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.9635F, 0.0F));

		PartDefinition cube_r5 = arm3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(32, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.1781F, 0.0F));

		PartDefinition eye = mimic.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(32, 15).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -1.0F));

		PartDefinition arm4 = mimic.addOrReplaceChild("arm4", CubeListBuilder.create(), PartPose.offset(0.0F, -7.5F, 0.0F));

		PartDefinition cube_r6 = arm4.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 15).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(ModAnimationDefinition.MIMIC_IDLE, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((MimicEntity) entity).idleAnimationState, ModAnimationDefinition.MIMIC_IDLE, ageInTicks, 1f);
	}




	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mimic.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return mimic;
	}
}