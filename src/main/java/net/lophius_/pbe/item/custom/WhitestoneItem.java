package net.lophius_.pbe.item.custom;

import net.lophius_.pbe.block.ModBlocks;
import net.lophius_.pbe.entity.custom.WhitestoneEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;


public class WhitestoneItem extends Item {
    public WhitestoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    private Vec3 findNearbyDragonspiralBlock(Level level, BlockPos origin, int radiusXZ, int radiusY) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int y = -radiusY; y <= radiusY; y++) {
            for (int x = -radiusXZ; x <= radiusXZ; x++) {
                for (int z = -radiusXZ; z <= radiusXZ; z++) {
                    mutable.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    if (level.getBlockState(mutable).is(ModBlocks.SPAWN_FLOOR_DRAGONSPIRAL.get())) {
                        return new Vec3(mutable.getX() + 0.5, mutable.getY() + 2, mutable.getZ() + 0.5);
                    }
                }
            }
        }
        return null;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        if (pLevel instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)pLevel;
            Vec3 blockpos = findNearbyDragonspiralBlock(serverlevel, pPlayer.blockPosition(), 10,2);
            if (blockpos == null) {
                pPlayer.displayClientMessage(Component.literal("You feel a mysterious force and ponder about truth"), true);
                return InteractionResultHolder.fail(itemstack);
            }
            else {
                WhitestoneEntity whitestone = new WhitestoneEntity(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                whitestone.setItem(itemstack);
                whitestone.signalTo(blockpos);
                pLevel.gameEvent(GameEvent.PROJECTILE_SHOOT, whitestone.position(), GameEvent.Context.of(pPlayer));
                pLevel.addFreshEntity(whitestone);
                pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.levelEvent((Player)null, 1003, pPlayer.blockPosition(), 0);
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                pPlayer.swing(pHand, true);
                return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }
}