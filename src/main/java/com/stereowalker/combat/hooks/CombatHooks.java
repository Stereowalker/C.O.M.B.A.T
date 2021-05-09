package com.stereowalker.combat.hooks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import com.stereowalker.combat.client.renderer.CRenderType;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.event.handler.GameEvents;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.ILegendaryGear;
import com.stereowalker.combat.item.ScytheItem;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.unionlib.util.RegistryHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class CombatHooks {

	@OnlyIn(Dist.CLIENT)
	public static double getAttackReachDistance() {
		ClientPlayerEntity player = (Minecraft.getInstance()).player;
		if (player != null) {
			double attrib = player.getAttributeValue(CAttributes.ATTACK_REACH);
			return !player.abilities.isCreativeMode ? attrib : (attrib + 2.0D);
		} 
		return 0.0D;
	}

	@OnlyIn(Dist.CLIENT)
	public static double getSquareAttackDistance(float partialTicks, Entity entity) {
		double attrib = 0.0D;
		ClientPlayerEntity player = (Minecraft.getInstance()).player;
		if (player != null) {
			attrib += player.getAttributeValue(CAttributes.ATTACK_REACH);
			attrib += !player.abilities.isCreativeMode ? 0.0D : 2.0D;
		} 
		RayTraceResult objectMouseOver = entity.pick(attrib, partialTicks, false);
		Vector3d vec3d = entity.getEyePosition(partialTicks);
		return objectMouseOver.getHitVec().squareDistanceTo(vec3d);
	}

	/**
	 * {@link GameRenderer#getMouseOver(float)}
	 * @param d0
	 * @return
	 */
	@OnlyIn(Dist.CLIENT)
	public static double getMaxSquareRange(double d0) {
		return Math.pow(d0, 2.0D);
	}

	public static double getEntityReachDistance(ServerPlayerEntity player, Entity entity) {
		double attrib = player.getAttributeValue(CAttributes.ATTACK_REACH) + 2.0D;
		double d0 = Math.pow(attrib, 2.0D);
		return player.canEntityBeSeen(entity) ? d0 : (d0 / 4.0D);
	}

	public static int getFireAttackModifier(LivingEntity living) {
		if (living instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)living;
			return EnchantmentHelper.getFireAspectModifier(living) + (PlayerSkills.hasSkill(player, Skills.BURNING_STRIKE) ? 1 : 0);
		}
		return 0;
	}


	/**
	 * Hooked {@link PlayerInventory#dropAllItems() }
	 * @param original - original value
	 * @param object - the itemstack to check if to keep in the inventory
	 * @return true if the item should stay in the inventory, false if it should pop out
	 */
	public static boolean isEmptyOrRetaining(boolean original, ItemStack stack) {
		if (CEnchantmentHelper.hasRetaining(stack)) return true;
		else return original;
	}

	public static double getSweepingArcWidth(ItemStack stack) {
		if (stack.getItem() instanceof SwordItem) {
			return 1.0D;
		} else if (stack.getItem() instanceof ScytheItem) {
			return 1.5D;
		} else
			return 0.0D;
	}

	public static Item isSweepingWeapon(Item original, ItemStack itemstack) {
		Item e = getSweepingArcWidth(itemstack) > 0.0D ? Items.DIAMOND_SWORD : original;
		return e;
	}

	//TODO: Find a way to use the coremods wihout tricking the game
	public static AxisAlignedBB sweepingArc(AxisAlignedBB original, PlayerEntity player, Entity targetEntity) {
		ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND);
		return targetEntity.getBoundingBox().grow(getSweepingArcWidth(itemStack), 0.25D, getSweepingArcWidth(itemStack));
	}

	public static double sweepingRange(double original, PlayerEntity player, Entity livingentity) {
		double actualValue = player.getDistanceSq(livingentity);
		double re;
		if (actualValue < 9.0D) {
			re = actualValue;
		} else if (actualValue < 18.0D) {
			re = 8.99D;
		} else {
			re= original;
		}
		return re;
	}

	/**
	 * Hooked {@link Block#getDrops(BlockState, ServerWorld, BlockPos, TileEntity, Entity, ItemStack)}
	 * @param original - The original drops
	 * @param state - The blockstate broken
	 * @param worldIn - The world
	 * @param pos - The position of the block
	 * @param tileEntityIn - The tile entity
	 * @param entityIn - The entity that broke the block
	 * @param stack - The item used to break the block
	 * @return A modified list of drops
	 */
	public static List<ItemStack> getTreasure(List<ItemStack> original, BlockState state, ServerWorld worldIn, BlockPos pos, @Nullable TileEntity tileEntityIn, @Nullable Entity entityIn, ItemStack stack) {
		boolean isValidTool = false;
		for (ToolType tool : stack.getToolTypes()) {
			if (state.isToolEffective(tool)) {
				isValidTool = true;
			}
		}
		if (entityIn instanceof PlayerEntity && PlayerSkills.hasSkill((PlayerEntity) entityIn, Skills.TREASURE_HUNTER) && UnionMathHelper.probabilityCheck(100) && RegistryHelper.listContainsRegisteredEntry(Config.MAGIC_COMMON.treasureHuntingBlocks.get(), state.getBlock()) && isValidTool) {
			int i = 3;
			int j = 3;
			int k = 30;
			List<ItemStack> validTreasure =  Lists.newArrayList(
					randomlyEnchantAndDamage(worldIn.rand, Items.IRON_SWORD, i, j, worldIn.rand.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.rand, Items.IRON_SHOVEL, i, j, worldIn.rand.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.rand, Items.LEATHER_CHESTPLATE, i, j, worldIn.rand.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.rand, Items.LEATHER_LEGGINGS, i, j, worldIn.rand.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.rand, Items.LEATHER_BOOTS, i, j, worldIn.rand.nextInt(k), true),
					new ItemStack(Items.BONE),
					new ItemStack(Items.DIAMOND),
					new ItemStack(Items.ROTTEN_FLESH),
					new ItemStack(CItems.LIMESTONE_ROCK)
					);

			return Lists.newArrayList(validTreasure.get(worldIn.rand.nextInt(validTreasure.size())));
		}
		else {
			return original;
		}
	}

	public static ItemStack randomlyEnchantAndDamage(Random rand, Item item, int minDamage, int maxDamage, int level, boolean allowTreasure) {
		ItemStack stack = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(item), level, allowTreasure);
		int damage = rand.nextInt(item.getMaxDamage(stack));
		int max = item.getMaxDamage(stack)-maxDamage;
		stack.setDamage(MathHelper.clamp(damage, minDamage, max));
		return stack;
	}

	@SuppressWarnings("deprecation")
	public static BlockState inceaseSnow(BlockState old, ServerWorld world, BlockPos pos) {
		int snowRate = Config.COMMON.snowPileupRate.get();

		if (world.getBlockState(pos).isAir()) {
			return Blocks.SNOW.getDefaultState();
		} else if (world.getBlockState(pos).getBlock() == Blocks.SNOW) {
			int lay = world.getBlockState(pos).get(SnowBlock.LAYERS);
			if (lay < 8) {
				if (snowRate > 0 && world.rand.nextInt(snowRate) == 0) {
					return Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, lay+1);
				} else {
					return world.getBlockState(pos);
				}
			} else {
				return Blocks.SNOW_BLOCK.getDefaultState();
			}
		} else {
			return old;
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean doesSnowGenerate(boolean old, IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isAir(worldIn, pos) || worldIn.getBlockState(pos).getBlock() == Blocks.SNOW;
	}

	public static int calculateFallDamageFromAttribute(LivingEntity livingEntity, float distance, float damageMultiplier) {
		boolean useVanilla = true;
		float f;
		if (GameEvents.jumpingEntities.test(livingEntity) && !useVanilla) {
			f = (((Double)livingEntity.getAttributeValue(CAttributes.JUMP_STRENGTH)).floatValue() - 0.2f) * 10f;
		} else {
			EffectInstance effectinstance = livingEntity.getActivePotionEffect(Effects.JUMP_BOOST);
			f = effectinstance == null ? 0.0F : (float)(effectinstance.getAmplifier() + 1);
		}
		return MathHelper.ceil((distance - 3.0F - f) * damageMultiplier);
	}


	//For rendering the legendary item glint
	public static IVertexBuilder getArmorVertexBuilder(IRenderTypeBuffer buffer, RenderType renderType, boolean noEntity, boolean withGlint, ItemStack stack) {
		if (stack.getItem() instanceof ILegendaryGear) {
			withGlint = true;
			return withGlint ? VertexBuilderUtils.newDelegate(buffer.getBuffer(noEntity ? CRenderType.getArmorGlint() : CRenderType.getArmorEntityGlint()), buffer.getBuffer(renderType)) : buffer.getBuffer(renderType);
		}
		//What it is by default Opcodes 
		//InsnNode
		//MethodInsnNode
		return ItemRenderer.getArmorVertexBuilder(buffer, renderType, noEntity, withGlint);
	}

	public static IVertexBuilder getBuffer(IRenderTypeBuffer bufferIn, RenderType renderTypeIn, boolean isItemIn, ItemStack stack) {
		boolean glintIn = stack.hasEffect();
		if (stack.getItem() instanceof ILegendaryGear) {
			glintIn = true;
			if (glintIn) {
				return Minecraft.isFabulousGraphicsEnabled() && renderTypeIn == Atlases.getItemEntityTranslucentCullType() ? VertexBuilderUtils.newDelegate(bufferIn.getBuffer(CRenderType.getGlintTranslucent()), bufferIn.getBuffer(renderTypeIn)) : VertexBuilderUtils.newDelegate(bufferIn.getBuffer(isItemIn ? CRenderType.getGlint() : CRenderType.getEntityGlint()), bufferIn.getBuffer(renderTypeIn));
			} else {
				return bufferIn.getBuffer(renderTypeIn);
			}
		} 
		//What it is by default
		return ItemRenderer.getBuffer(bufferIn, renderTypeIn, isItemIn, glintIn);
	}

	public static IVertexBuilder getEntityGlintVertexBuilder(IRenderTypeBuffer buffer, RenderType renderType, boolean noEntity, ItemStack stack) {
		boolean withGlint = stack.hasEffect();
		if (stack.getItem() instanceof ILegendaryGear) {
			withGlint = true;
			return withGlint ? VertexBuilderUtils.newDelegate(buffer.getBuffer(noEntity ? CRenderType.getGlintDirect() : CRenderType.getEntityGlintDirect()), buffer.getBuffer(renderType)) : buffer.getBuffer(renderType);	
		}
		//What it is by default
		return ItemRenderer.getEntityGlintVertexBuilder(buffer, renderType, noEntity, withGlint);
	}
}
