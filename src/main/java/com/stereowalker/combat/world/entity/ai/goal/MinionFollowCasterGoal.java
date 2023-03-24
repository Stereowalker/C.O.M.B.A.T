package com.stereowalker.combat.world.entity.ai.goal;

import java.util.EnumSet;

import com.stereowalker.combat.world.entity.monster.Minion;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class MinionFollowCasterGoal extends Goal {
   private final Minion<?> tameable;
   private LivingEntity owner;
   protected final LevelReader world;
   private final double followSpeed;
   private final PathNavigation navigator;
   private int timeToRecalcPath;
   private final float maxDist;
   private final float minDist;
   private float oldWaterCost;

   public MinionFollowCasterGoal(Minion<?> tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.tameable = tameableIn;
      this.world = ((Mob)tameableIn).level;
      this.followSpeed = followSpeedIn;
      this.navigator = ((Mob)tameableIn).getNavigation();
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(((Mob)tameableIn).getNavigation() instanceof GroundPathNavigation) && !(((Mob)tameableIn).getNavigation() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean canUse() {
	   LivingEntity entitylivingbase = this.tameable.getMaster();
      if (entitylivingbase == null) {
         return false;
      } else if (entitylivingbase instanceof Player && ((Player)entitylivingbase).isSpectator()) {
         return false;
      } else if (((Mob)this.tameable).distanceToSqr(entitylivingbase) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.owner = entitylivingbase;
         return true;
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.navigator.isDone() && ((Mob)this.tameable).distanceToSqr(this.owner) > (double)(this.maxDist * this.maxDist);
   }

   @Override
   public void start() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = ((Mob)this.tameable).getPathfindingMalus(BlockPathTypes.WATER);
      ((Mob)this.tameable).setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
   }

   @Override
   public void stop() {
      this.owner = null;
      this.navigator.stop();
      ((Mob)this.tameable).setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
   }

   @Override
   public void tick() {
	   ((Mob)this.tameable).getLookControl().setLookAt(this.owner, 10.0F, (float)((Mob)this.tameable).getMaxHeadXRot());
	         if (--this.timeToRecalcPath <= 0) {
	            this.timeToRecalcPath = 10;
	            if (!this.navigator.moveTo(this.owner, this.followSpeed)) {
	               if (!((Mob)this.tameable).isLeashed() && !((Mob)this.tameable).isPassenger()) {
	                  if (!(((Mob)this.tameable).distanceToSqr(this.owner) < 144.0D)) {
	                     int i = Mth.floor(this.owner.getX()) - 2;
	                     int j = Mth.floor(this.owner.getZ()) - 2;
	                     int k = Mth.floor(this.owner.getBoundingBox().minY);

	                     for(int l = 0; l <= 4; ++l) {
	                        for(int i1 = 0; i1 <= 4; ++i1) {
	                           if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
	                        	   ((Mob)this.tameable).moveTo((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), ((Mob)this.tameable).getYRot(), ((Mob)this.tameable).getXRot());
	                              this.navigator.stop();
	                              return;
	                           }
	                        }
	                     }

	                  }
	               }
	            }
	         }
	   }

   protected boolean canTeleportToBlock(BlockPos pos) {
	      BlockState blockstate = this.world.getBlockState(pos);
	      return blockstate.isValidSpawn(this.world, pos, ((Mob)this.tameable).getType()) && this.world.isEmptyBlock(pos.above()) && this.world.isEmptyBlock(pos.above(2));
	   }
}