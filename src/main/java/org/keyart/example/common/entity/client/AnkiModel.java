package org.keyart.example.common.entity.client;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.keyart.example.common.entity.AnkiEntity;
import org.keyart.example.common.entity.animations.EAnimationDefinitions;

public class AnkiModel<T extends Entity> extends HierarchicalModel<AnkiEntity> {
	private final ModelPart main;
	private final ModelPart leftLeg;
	private final ModelPart leftLegU;
	private final ModelPart leftLegD;
	private final ModelPart rightLeg;
	private final ModelPart rightLegU;
	private final ModelPart rightLegD;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart bone2;
	private final ModelPart bone;
	private final ModelPart body;
	private final ModelPart LeftArm;
	private final ModelPart leftArmU;
	private final ModelPart leftArmD;
	private final ModelPart rightArm;
	private final ModelPart rightArmU;
	private final ModelPart rightArmD;

	public AnkiModel(ModelPart root) {
		this.main = root.getChild("main");
		this.leftLeg = this.main.getChild("leftLeg");
		this.leftLegU = this.leftLeg.getChild("leftLegU");
		this.leftLegD = this.leftLeg.getChild("leftLegD");
		this.rightLeg = this.main.getChild("rightLeg");
		this.rightLegU = this.rightLeg.getChild("rightLegU");
		this.rightLegD = this.rightLeg.getChild("rightLegD");
		this.torso = this.main.getChild("torso");
		this.head = this.torso.getChild("head");
		this.bone2 = this.head.getChild("bone2");
		this.bone = this.head.getChild("bone");
		this.body = this.torso.getChild("body");
		this.LeftArm = this.torso.getChild("LeftArm");
		this.leftArmU = this.LeftArm.getChild("leftArmU");
		this.leftArmD = this.LeftArm.getChild("leftArmD");
		this.rightArm = this.torso.getChild("rightArm");
		this.rightArmU = this.rightArm.getChild("rightArmU");
		this.rightArmD = this.rightArm.getChild("rightArmD");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leftLeg = main.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(-1.6F, -6.0F, 0.0F));

		PartDefinition leftLegU = leftLeg.addOrReplaceChild("leftLegU", CubeListBuilder.create().texOffs(8, 23).addBox(-2.5F, -14.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.6F, 14.0F, 0.0F));

		PartDefinition leftLegD = leftLeg.addOrReplaceChild("leftLegD", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 4.0F, 0.0F));

		PartDefinition rightLeg = main.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(1.5F, -6.0F, 0.0F));

		PartDefinition rightLegU = rightLeg.addOrReplaceChild("rightLegU", CubeListBuilder.create().texOffs(0, 23).addBox(0.6F, -14.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 14.0F, 0.0F));

		PartDefinition rightLegD = rightLeg.addOrReplaceChild("rightLegD", CubeListBuilder.create().texOffs(16, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 4.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(4, 29).addBox(-0.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 18).addBox(-0.5F, -0.75F, -0.75F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.15F, -2.05F, 2.5132F, -0.3272F, 2.7252F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 12).addBox(-0.5F, -0.75F, -0.75F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.15F, -2.05F, 2.5132F, 0.3272F, -2.7252F));

		PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, -8.0F, -1.5F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftArm = torso.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offset(-4.0F, -7.0F, 0.0F));

		PartDefinition leftArmU = LeftArm.addOrReplaceChild("leftArmU", CubeListBuilder.create().texOffs(18, 12).addBox(-5.0F, -22.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 21.0F, 0.0F));

		PartDefinition leftArmD = LeftArm.addOrReplaceChild("leftArmD", CubeListBuilder.create().texOffs(24, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition rightArm = torso.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset(4.0F, -7.0F, 0.0F));

		PartDefinition rightArmU = rightArm.addOrReplaceChild("rightArmU", CubeListBuilder.create().texOffs(18, 18).addBox(3.0F, -22.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 21.0F, 0.0F));

		PartDefinition rightArmD = rightArm.addOrReplaceChild("rightArmD", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return main;
	}

	private void applyHeadRotation(float headYaw, float headPitch, float ageInTicks) {
		headYaw = Mth.clamp(headYaw, -30, 30);
		headPitch = Mth.clamp(headPitch, -25, 45);

		this.head.yRot = headYaw*(((float) Math.PI)/180);
		this.head.xRot = headPitch*(((float) Math.PI)/180);
	}


	@Override
	public void setupAnim(AnkiEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(pNetHeadYaw, pHeadPitch, pAgeInTicks);

		this.animateWalk(EAnimationDefinitions.WALK, pLimbSwing, pLimbSwingAmount, 2, 2.5f);
		this.animate(((AnkiEntity) pEntity).idleAnimationState, EAnimationDefinitions.IDLE, pAgeInTicks, 1);
	}
}