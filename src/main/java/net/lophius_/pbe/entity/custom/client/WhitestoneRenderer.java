package net.lophius_.pbe.entity.custom.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lophius_.pbe.entity.custom.WhitestoneEntity;
import net.lophius_.pbe.item.ModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WhitestoneRenderer extends EntityRenderer<WhitestoneEntity> {

    private final ItemRenderer itemRenderer;
    private final ItemStack renderStack;

    public WhitestoneRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.renderStack = new ItemStack(ModItems.WHITESTONE.get()); // The item you want rendered
    }

    @Override
    public void render(WhitestoneEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        //poseStack.translate(0.0D, 0.25D, 0.0D); // Optional: small vertical offset
        itemRenderer.renderStatic(
                renderStack,
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                0
        );
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(WhitestoneEntity entity) {
        return null; // Not needed when using ItemRenderer
    }
}
