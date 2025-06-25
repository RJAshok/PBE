package net.lophius_.pbe.entity.custom;
import net.lophius_.pbe.entity.ModEntities;
import net.lophius_.pbe.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WhitestoneEntity extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(net.minecraft.world.entity.projectile.EyeOfEnder.class, EntityDataSerializers.ITEM_STACK);
    private double tx;
    private double ty;
    private double tz;
    private int life;

    public WhitestoneEntity(EntityType<? extends WhitestoneEntity> pEntityType, Level level) {
        super(pEntityType, level);
    }

    public WhitestoneEntity(Level level, double pX, double pY, double pZ) {
        this(ModEntities.WHITESTONE_ENTITY.get(), level);
        this.setPos(pX, pY, pZ);
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(Items.ENDER_EYE) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }

    }

    private ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(ModItems.WHITESTONE.get()) : itemstack;
    }


    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    /**
     * Checks if the entity is in range to render.
     */
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    public void signalTo(Vec3 target) {
        this.tx = target.x;
        this.ty = target.y;
        this.tz = target.z;

        this.life = 0; // Reset entity lifetime
    }


    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    public void lerpMotion(double pX, double pY, double pZ) {
        this.setDeltaMovement(pX, pY, pZ);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = Math.sqrt(pX * pX + pZ * pZ);
            this.setYRot((float)(Mth.atan2(pX, pZ) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(pY, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

    }

    protected static float lerpRotation(float pCurrentRotation, float pTargetRotation) {
        while(pTargetRotation - pCurrentRotation < -180.0F) {
            pCurrentRotation -= 360.0F;
        }

        while(pTargetRotation - pCurrentRotation >= 180.0F) {
            pCurrentRotation += 360.0F;
        }

        return Mth.lerp(0.2F, pCurrentRotation, pTargetRotation);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        double d3 = vec3.horizontalDistance();

        // Update rotation smoothly to face direction of movement
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d3) * (180F / Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (180F / Math.PI))));

        // Server-side movement calculation
        if (!this.level().isClientSide) {
            double d4 = this.tx - d0;
            double d5 = this.tz - d2;
            float f = (float)Math.sqrt(d4 * d4 + d5 * d5);
            float angle = (float)Math.atan2(d5, d4);

            double newSpeed = Mth.lerp(0.0025D, d3, f);
            double dy = vec3.y;

            if (f < 1.0F) {
                newSpeed *= 0.8D;
                dy *= 0.8D;
            }

            int verticalDirection = this.getY() < this.ty ? 1 : -1;
            vec3 = new Vec3(Math.cos(angle) * newSpeed, dy + (verticalDirection - dy) * 0.015D, Math.sin(angle) * newSpeed);
            this.setDeltaMovement(vec3);
        }

        // Emit custom particle trail (like Ender Eye)
        this.level().addParticle(ParticleTypes.PORTAL,
                d0 - vec3.x * 0.25D + this.random.nextDouble() * 0.6D - 0.3D,
                d1 - vec3.y * 0.25D - 0.5D,
                d2 - vec3.z * 0.25D + this.random.nextDouble() * 0.6D - 0.3D,
                vec3.x, vec3.y, vec3.z);

        if (!this.level().isClientSide) {
            this.setPos(d0, d1, d2);
            double dx = this.getX() - this.tx;
            double dy = this.getY() - this.ty;
            double dz = this.getZ() - this.tz;
            double distanceSq = dx * dx + dy * dy + dz * dz;
            this.life++;
            if (distanceSq < 0.01D && life>80) {
                this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                this.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT, 1.0F, 1.0F);
                this.discard();
                this.level().levelEvent(2003, this.blockPosition(), 0);
                System.out.println(this.getX()+" "+this.getY()+" "+this.getZ());
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.getServer().getCommands().performPrefixedCommand(
                            serverLevel.getServer().createCommandSourceStack()
                                    .withSuppressedOutput()
                                    .withPosition(this.position())
                                    .withLevel(serverLevel),
                            "pokespawn reshiram "+" "+this.getX()+" "+this.getY()+" "+this.getZ()
                    );
                }
            }
        } else {
            this.setPosRaw(d0, d1, d2);
        }

    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    /**
     * Returns {@code true} if it's possible to attack this entity with an item.
     */
    public boolean isAttackable() {
        return false;
    }
}
