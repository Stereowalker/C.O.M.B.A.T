package com.stereowalker.combat.mixinhooks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.CRenderType;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.LegendaryGear;
import com.stereowalker.combat.world.item.Mythril;
import com.stereowalker.combat.world.item.ScytheItem;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.unionlib.util.RegistryHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CombatHooks {

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static double getAttackReachDistance() {
		LocalPlayer player = (Minecraft.getInstance()).player;
		if (player != null) {
			double attrib = player.getAttributeValue(CAttributes.ATTACK_REACH);
			return !player.getAbilities().instabuild ? attrib : (attrib + 2.0D);
		} 
		return 0.0D;
	}

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static double getSquareAttackDistance(float partialTicks, Entity entity) {
		double attrib = 0.0D;
		LocalPlayer player = (Minecraft.getInstance()).player;
		if (player != null) {
			attrib += player.getAttributeValue(CAttributes.ATTACK_REACH);
			attrib += !player.getAbilities().instabuild ? 0.0D : 2.0D;
		} 
		HitResult objectMouseOver = entity.pick(attrib, partialTicks, false);
		Vec3 vec3d = entity.getEyePosition(partialTicks);
		return objectMouseOver.getLocation().distanceToSqr(vec3d);
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

	public static double getEntityReachDistance(ServerPlayer player, Entity entity) {
		double attrib = player.getAttributeValue(CAttributes.ATTACK_REACH) + 2.0D;
		double d0 = Math.pow(attrib, 2.0D);
		return player.hasLineOfSight(entity) ? d0 : (d0 / 4.0D);
	}

	public static int getFireAttackModifier(LivingEntity living) {
		int burning_fist = (PlayerSkills.isSkillActive(living, Skills.BURNING_FIST) && living.getMainHandItem().isEmpty() ? 1 : 0);
		return EnchantmentHelper.getFireAspect(living) + (PlayerSkills.isSkillActive(living, Skills.BURNING_STRIKE) ? 1 : 0) + burning_fist;
	}


	/**
	 * Hooked {@link Inventory#dropAll() }
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
	public static AABB sweepingArc(AABB original, Player player, Entity targetEntity) {
		ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
		return targetEntity.getBoundingBox().inflate(getSweepingArcWidth(itemStack), 0.25D, getSweepingArcWidth(itemStack));
	}

	public static double sweepingRange(double original, Player player, Entity livingentity) {
		double actualValue = player.distanceToSqr(livingentity);
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
	 * Hooked {@link Block#getDrops(BlockState, ServerLevel, BlockPos, BlockEntity, Entity, ItemStack)}
	 * @param original - The original drops
	 * @param state - The blockstate broken
	 * @param worldIn - The world
	 * @param pos - The position of the block
	 * @param tileEntityIn - The tile entity
	 * @param entityIn - The entity that broke the block
	 * @param stack - The item used to break the block
	 * @return A modified list of drops
	 */
	public static List<ItemStack> getTreasure(List<ItemStack> original, BlockState state, ServerLevel worldIn, BlockPos pos, @Nullable BlockEntity tileEntityIn, @Nullable Entity entityIn, ItemStack stack) {
		boolean isValidTool = false;
		//TODO: Find an alternative to this
//		for (ToolType tool : stack.getToolTypes()) {
//			if (state.isToolEffective(tool)) {
//				isValidTool = true;
//			}
//		}
		if (stack.isCorrectToolForDrops(state)) {
			isValidTool = true;
		}
		if (entityIn instanceof Player && PlayerSkills.hasSkill((Player) entityIn, Skills.TREASURE_HUNTER) && UnionMathHelper.probabilityCheck(100) && RegistryHelper.listContainsRegisteredEntry(Combat.MAGIC_CONFIG.treasureHuntingBlocks, state.getBlock()) && isValidTool) {
			int i = 3;
			int j = 3;
			int k = 30;
			List<ItemStack> validTreasure =  Lists.newArrayList(
					randomlyEnchantAndDamage(worldIn.random, Items.IRON_SWORD, i, j, worldIn.random.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.random, Items.IRON_SHOVEL, i, j, worldIn.random.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.random, Items.LEATHER_CHESTPLATE, i, j, worldIn.random.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.random, Items.LEATHER_LEGGINGS, i, j, worldIn.random.nextInt(k), true),
					randomlyEnchantAndDamage(worldIn.random, Items.LEATHER_BOOTS, i, j, worldIn.random.nextInt(k), true),
					new ItemStack(Items.BONE),
					new ItemStack(Items.DIAMOND),
					new ItemStack(Items.ROTTEN_FLESH),
					new ItemStack(CItems.LIMESTONE_ROCK)
					);

			return Lists.newArrayList(validTreasure.get(worldIn.random.nextInt(validTreasure.size())));
		}
		else {
			return original;
		}
	}

	public static ItemStack randomlyEnchantAndDamage(Random random, Item item, int minDamage, int maxDamage, int level, boolean allowTreasure) {
		ItemStack stack = EnchantmentHelper.enchantItem(random, new ItemStack(item), level, allowTreasure);
		int damage = random.nextInt(item.getMaxDamage(stack));
		int max = item.getMaxDamage(stack)-maxDamage;
		stack.setDamageValue(Mth.clamp(damage, minDamage, max));
		return stack;
	}

	public static BlockState inceaseSnow(BlockState old, ServerLevel world, BlockPos pos) {
		int snowRate = Config.COMMON.snowPileupRate.get();

		if (world.getBlockState(pos).isAir()) {
			return Blocks.SNOW.defaultBlockState();
		} else if (world.getBlockState(pos).getBlock() == Blocks.SNOW) {
			int lay = world.getBlockState(pos).getValue(SnowLayerBlock.LAYERS);
			if (lay < 8) {
				if (snowRate > 0 && world.random.nextInt(snowRate) == 0) {
					return Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, lay+1);
				} else {
					return world.getBlockState(pos);
				}
			} else {
				return Blocks.SNOW_BLOCK.defaultBlockState();
			}
		} else {
			return old;
		}
	}

	public static boolean doesSnowGenerate(boolean old, BlockGetter worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isAir() || worldIn.getBlockState(pos).getBlock() == Blocks.SNOW;
	}


	//For rendering the legendary item glint
	public static VertexConsumer getArmorFoilBuffer(MultiBufferSource buffer, RenderType renderType, boolean noEntity, boolean withGlint, ItemStack stack) {
		if (stack.getItem() instanceof Mythril) {
			Mythril mythrilItem = (Mythril)stack.getItem();
			if (mythrilItem.isUsingEnergy(stack)) {
				if (stack.hasFoil()) {
					return VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getEncMythrilArmorGlint() : CRenderType.getEncMythrilArmorEntityGlint()), buffer.getBuffer(renderType));
				} else {
					return VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getMythrilArmorGlint() : CRenderType.getMythrilArmorEntityGlint()), buffer.getBuffer(renderType));
				}
			} else {
				return buffer.getBuffer(renderType);
			}
		}
		if (stack.getItem() instanceof LegendaryGear) {
			return VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getLegendaryArmorGlint() : CRenderType.getLegendaryArmorEntityGlint()), buffer.getBuffer(renderType));
		}
		//What it is by default Opcodes 
		//InsnNode
		//MethodInsnNode
		return ItemRenderer.getArmorFoilBuffer(buffer, renderType, noEntity, withGlint);
	}

	public static VertexConsumer getBuffer(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean isItemIn, ItemStack stack) {
		boolean glintIn = stack.hasFoil();
		if (stack.getItem() instanceof Mythril) {
			Mythril mythrilItem = (Mythril)stack.getItem();
			if (mythrilItem.isUsingEnergy(stack)) {
				if (stack.hasFoil()) {
					return Minecraft.useShaderTransparency() && renderTypeIn == Sheets.translucentItemSheet() ? VertexMultiConsumer.create(bufferIn.getBuffer(CRenderType.getEncMythrilGlintTranslucent()), bufferIn.getBuffer(renderTypeIn)) : VertexMultiConsumer.create(bufferIn.getBuffer(isItemIn ? CRenderType.getEncMythrilGlint() : CRenderType.getEnchantedMythrilEntityGlint()), bufferIn.getBuffer(renderTypeIn));
				} else {
					return Minecraft.useShaderTransparency() && renderTypeIn == Sheets.translucentItemSheet() ? VertexMultiConsumer.create(bufferIn.getBuffer(CRenderType.getMythrilGlintTranslucent()), bufferIn.getBuffer(renderTypeIn)) : VertexMultiConsumer.create(bufferIn.getBuffer(isItemIn ? CRenderType.getMythrilGlint() : CRenderType.getMythrilEntityGlint()), bufferIn.getBuffer(renderTypeIn));
				}
			} else {
				return bufferIn.getBuffer(renderTypeIn);
			}
		} 
		if (stack.getItem() instanceof LegendaryGear) {
			return Minecraft.useShaderTransparency() && renderTypeIn == Sheets.translucentItemSheet() ? VertexMultiConsumer.create(bufferIn.getBuffer(CRenderType.getLegendaryGlintTranslucent()), bufferIn.getBuffer(renderTypeIn)) : VertexMultiConsumer.create(bufferIn.getBuffer(isItemIn ? CRenderType.getLegendaryGlint() : CRenderType.getLegendaryEntityGlint()), bufferIn.getBuffer(renderTypeIn));
		} 
		//What it is by default
		return ItemRenderer.getFoilBuffer(bufferIn, renderTypeIn, isItemIn, glintIn);
	}

	public static VertexConsumer getFoilBufferDirect(MultiBufferSource buffer, RenderType renderType, boolean noEntity, ItemStack stack) {
		boolean withGlint = stack.hasFoil();
		if (stack.getItem() instanceof Mythril) {
			Mythril mythrilItem = (Mythril)stack.getItem();
			if (mythrilItem.isUsingEnergy(stack)) {
				if (stack.hasFoil()) {
					return VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getEncMythrilGlintDirect() : CRenderType.getEncMythrilEntityGlintDirect()), buffer.getBuffer(renderType));	
				} else {
					return VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getMythrilGlintDirect() : CRenderType.getMythrilEntityGlintDirect()), buffer.getBuffer(renderType));	
				}
			} else {
				return buffer.getBuffer(renderType);
			}
		}
		if (stack.getItem() instanceof LegendaryGear) {
			withGlint = true;
			return withGlint ? VertexMultiConsumer.create(buffer.getBuffer(noEntity ? CRenderType.getLegendaryGlintDirect() : CRenderType.getLegendaryEntityGlintDirect()), buffer.getBuffer(renderType)) : buffer.getBuffer(renderType);	
		}
		//What it is by default
		return ItemRenderer.getFoilBufferDirect(buffer, renderType, noEntity, withGlint);
	}
}
