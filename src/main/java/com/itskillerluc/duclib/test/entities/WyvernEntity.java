package com.itskillerluc.duclib.test.entities;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.client.animation.DucAnimation;
import com.itskillerluc.duclib.entity.Animatable;
import com.itskillerluc.duclib.test.client.WyvernModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WyvernEntity extends Animal implements FlyingAnimal, Animatable<WyvernModel> {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RISK = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);

    public static final ResourceLocation LOCATION = new ResourceLocation(DucLib.MOD_ID, "test");
    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);
    private Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> WyvernModel.createStateMap(getAnimation()));

    public WyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(ASLEEP, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(RISK, 0);
    }

    public void setRisk(int risk){
        this.entityData.set(RISK, risk);
    }
    public int increaseRisk(int risk){
        this.entityData.set(RISK, this.entityData.get(RISK) + risk);
        return this.entityData.get(RISK);
    }
    public int getRisk(){
        return this.entityData.get(RISK);
    }
    public void setAttacking(boolean attack) {
        this.entityData.set(ATTACKING, attack);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return !isOnGround() && !onClimbable();
    }


    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("Risk", this.getRisk());
        tag.putBoolean("IsAsleep", this.isAsleep());
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(false);
        return flyingpathnavigation;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.entityData.set(RISK, tag.getInt("Risk"));
        this.entityData.set(ASLEEP, tag.getBoolean("IsAsleep"));
    }

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }

    @Override
    public void positionRider(Entity pPassenger) {
        this.positionRider(pPassenger, Entity::setPos);
    }

    private void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            double d0 = this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset();
            pCallback.accept(pPassenger, this.getX(), d0, this.getZ());
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity p_230268_1_) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(p_230268_1_);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for(Pose pose : p_230268_1_.getDismountPoses()) {
                AABB axisalignedbb = p_230268_1_.getLocalBoundsForPose(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutable, d0);
                        if (DismountHelper.canDismountTo(this.level, p_230268_1_, axisalignedbb.move(vec3))) {
                            p_230268_1_.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(p_230268_1_);
        }
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!level.isClientSide()){
            playAnimation("attack_01", tickCount);
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true, entity -> entity instanceof Cow));
    }
    public void aiStep() {
        super.aiStep();
        if (this.isAsleep()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0.0D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1D);
        }
    }

    public static AttributeSupplier.Builder attributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 92.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.FLYING_SPEED, 0.2D);
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (getPose() == Pose.STANDING && !isFlying() && Animatable.isMoving(this)){
            playAnimation("walk", tickCount);
        } else {
            stopAnimation("walk");
        }
        if (getPose() == Pose.STANDING && isFlying() && Animatable.isMoving(this)){
            playAnimation("fly", tickCount);
        } else {
            stopAnimation("fly");
        }
        if (getPose() == Pose.STANDING && !isFlying() && !Animatable.isMoving(this)){
            playAnimation("idle", tickCount);
        } else {
            stopAnimation("idle");
        }
        if (getPose() == Pose.STANDING && isFlying() && !Animatable.isMoving(this)){
            playAnimation("idle_fly", tickCount);
        } else {
            stopAnimation("idle_fly");
        }
    }

    @Override
    public ResourceLocation getModelLocation() {
        return LOCATION;
    }

    @Override
    public DucAnimation getAnimation() {
        return ANIMATION;
    }

    @Override
    public Lazy<Map<String, AnimationState>> getAnimations() {
        return animations;
    }

    @Override
    public AnimationState getAnimationState(String animation) {
        return getAnimations().get().get("animation.wyvern." + animation);
    }
}
