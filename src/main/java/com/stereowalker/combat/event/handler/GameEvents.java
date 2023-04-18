package com.stereowalker.combat.event.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.event.AbominationEvents;
import com.stereowalker.combat.event.LegendaryWeaponEvents;
import com.stereowalker.combat.event.MagicEvents;
import com.stereowalker.combat.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundClientMotionPacket;
import com.stereowalker.combat.tags.EntityTypeCTags;
import com.stereowalker.combat.tags.StructureCTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.inventory.FletchingMenu;
import com.stereowalker.combat.world.item.AccessoryItemCheck;
import com.stereowalker.combat.world.item.ArchItem;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.DaggerItem;
import com.stereowalker.combat.world.item.HalberdItem;
import com.stereowalker.combat.world.item.LegendaryGear;
import com.stereowalker.combat.world.item.LongbowItem;
import com.stereowalker.combat.world.item.ScytheItem;
import com.stereowalker.combat.world.item.SoulBowItem;
import com.stereowalker.combat.world.item.SpearItem;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.unionlib.api.insert.InsertCanceller;
import com.stereowalker.unionlib.api.insert.InsertSetter;
import com.stereowalker.unionlib.util.EntityHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = "combat")
public class GameEvents {

	public static Predicate<LivingEntity> jumpingEntities = (entities) -> {
		return (entities instanceof Player || entities instanceof Player) && !(entities instanceof Rabbit || entities instanceof Rabbit) ;
	};

	@SubscribeEvent
	public static void dropXPFromFarming(BreakEvent event) {
		if (event.getState().getBlock() instanceof CropBlock) {
			CropBlock crop = (CropBlock)event.getState().getBlock();
			if (crop.isMaxAge(event.getState())) {
				if (Combat.RPG_CONFIG.xpFromFarming != 0)event.setExpToDrop(Combat.RPG_CONFIG.xpFromFarming);
			}
		}
	}

	public static Map<EntityType<?>, Item>bloodMap() {
		return null;

	}

	@SubscribeEvent
	public static void modifyAttributes(ItemAttributeModifierEvent event) {
		if (getReach(event.getItemStack().getItem()) > 0 && (!(event.getItemStack().getItem() instanceof ArmorItem) && event.getSlotType() == EquipmentSlot.MAINHAND) || (event.getItemStack().getItem() instanceof ArmorItem && ((ArmorItem)event.getItemStack().getItem()).getSlot() == event.getSlotType())) {
			event.addModifier(CAttributes.ATTACK_REACH, new AttributeModifier(UUIDS.ATTACK_REACH_MODIFIER, "Weapon modifier", getReach(event.getItemStack().getItem()), AttributeModifier.Operation.ADDITION)); 
		}
	}

	public static double getReach(Item item) {
		if (item instanceof HalberdItem) {
			return 1.5f;
		}
		if (item instanceof ScytheItem) {
			return 1.5f;
		}
		if (item instanceof SpearItem) {
			return 1.0f;
		}
		if (item instanceof TridentItem) {
			return 1.0f;
		}
		else if (item instanceof HoeItem) {
			return 1.0f;
		}
		else if (item instanceof SwordItem) {
			return 0.5f;
		}
		else {
			return 0.0f;
		}
	}

	public static void collectBlood(Player player, InteractionHand hand, Entity target, InsertCanceller canceller, InsertSetter<InteractionResult> cancelResult) {
		ItemStack bottle = player.getItemInHand(InteractionHand.OFF_HAND);
		ItemStack dagger = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
			boolean flag = false;
			if (target instanceof Vampire) {
				player.addItem(new ItemStack(CItems.VAMPIRE_BLOOD));
				flag = true;
			} else if (target instanceof LivingEntity && !((LivingEntity)target).getMobType().equals(MobType.UNDEAD)) {
				player.addItem(new ItemStack(CItems.BLOOD));
				flag = true;
			}
			if (!player.getAbilities().instabuild && flag) {
				bottle.shrink(1);
			}
			if (flag) {
				canceller.cancel();
				cancelResult.set(InteractionResult.SUCCESS);
			}
		}
	}

	public static void setupJumpHeightAttribute(LivingEntity living) {
		boolean isActive = false;
		if (living != null) {
			if (living.hasEffect(CMobEffects.PARALYSIS)) isActive = true;
			if (jumpingEntities.test(living) || isActive) {
				//Cancels the actual jumping motion of the entity
				float actualJumpHeight = EntityHelper.getJumpUpwardsMotion(living) ;
				if (living.hasEffect(MobEffects.JUMP)) {
					actualJumpHeight += 0.1F * (float)(living.getEffect(MobEffects.JUMP).getAmplifier() + 1);
				}
				if (isActive) actualJumpHeight = 100.0F;
				Vec3 vec3d = living.getDeltaMovement();
				living.setDeltaMovement(vec3d.x, (double)-actualJumpHeight , vec3d.z);
			}

			if (jumpingEntities.test(living)) {
				//Implements the modded version of this via the Attribute system
				double modifiedJumpHeight = EntityHelper.getJumpUpwardsMotion(living) + (living.getAttributeValue(CAttributes.JUMP_STRENGTH) - 0.2D);
				Vec3 vec3d2 = living.getDeltaMovement();
				living.setDeltaMovement(vec3d2.x, modifiedJumpHeight , vec3d2.z);
			}

			if (isActive) {
				//Prevents the entity from moving by jumping and moving
				if (living.isSprinting()) {
					float f1 = living.getYRot() * ((float)Math.PI / 180F);
					double x = (double)(-Mth.sin(f1) * 0.2F);
					double z = (double)(Mth.cos(f1) * 0.2F);
					living.setDeltaMovement(living.getDeltaMovement().add(-x, 0.0D, -z));
				}
				living.hasImpulse = false;
			}
		}
	}

	public static void setupHealthRegenerationAttribute(LivingEntity living, InsertSetter<Float> amount) {
		if (living instanceof Player) {
			amount.set((float) (amount.get() * living.getAttributeValue(CAttributes.HEALTH_REGENERATION)));
		}
	}

	public static void xpRingStorage(Player player, ExperienceOrb orb) {
		if (AccessoryItemCheck.isEquipped(player, CItems.XP_STORAGE_RING)) {
			CombatEntityStats.addStoredXP(player, orb.getValue());
			orb.value = 0;
		}
	}

	public static void livingUpdate(LivingEntity living) {
		if (living instanceof Monster) {
			AbominationEvents.updateAbomination((Monster)living);
		}
		if (living instanceof Player) {
			LegendaryWeaponEvents.legendaryCollection((Player) living);
		}
		if (!living.level.isClientSide && living instanceof ServerPlayer) {
			setTower((ServerPlayer) living);
		}
		MagicEvents.manaUpdate(living);
		//Set the players stats once when they spawn into the world
		if(living instanceof Player) {
			Player player = (Player)living;
			CombatEntityStats.addStatsToPlayerOnSpawn(player);
		}
		//Send tickets to the client containing every stat for the player
		if (!living.level.isClientSide && living instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)living;
			CombatEntityStats.setResist(player, player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()*10);
			new ClientboundPlayerStatsPacket(player).send(player);;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void livingUpdateOnClient(LivingEntity living) {
		if (living.level.isClientSide && living instanceof LocalPlayer) {
			new ServerboundClientMotionPacket(living.getDeltaMovement()).send();
		}
	}

	public static void replenishManaOnSleep(LevelAccessor level, InsertSetter<Long> wakeTime) {
		MagicEvents.replenishManaOnSleep(level);
	}

	public static void fovUpdate(Player player, float originalFov, InsertSetter<Float> newFov) {
		float f = 1.0F;
		if (player.isUsingItem() && player.getUseItem().getItem() instanceof LongbowItem) {
			int i = player.getTicksUsingItem();
			float f1 = (float)i / 30.0F;
			if (f1 > 1.5F) {
				f1 = 2.25F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			newFov.set(f);
		}

		if (player.isUsingItem() && player.getUseItem().getItem() instanceof SoulBowItem) {
			int i = player.getTicksUsingItem();
			float f1 = (float)i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			newFov.set(f);
		}

		if (player.isUsingItem() && player.getUseItem().getItem() instanceof ArchItem) {
			int i = player.getTicksUsingItem();
			float f1 = (float)i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			newFov.set(f);
		}
	}

	public static void setTower(ServerPlayer player) {
        BlockPos blockpos = player.getLevel().findNearestMapStructure(StructureCTags.ETHERION_COMPASS_POINTS, player.blockPosition(), 100, false);
		if (blockpos == null) CombatEntityStats.setNearestEtherionTowerPos(player, BlockPos.ZERO);
		else if (CombatEntityStats.getNearestEtherionTowerPos(player) != blockpos) CombatEntityStats.setNearestEtherionTowerPos(player, blockpos != null ? blockpos : BlockPos.ZERO);
	}

	public static void restoreStats(Player thisPlayer, Player thatPlayer, boolean keepEverything) {
		CombatEntityStats.getOrCreateModNBT(thisPlayer);
		if (keepEverything) {
			CombatEntityStats.setVampire(thisPlayer, CombatEntityStats.isVampire(thatPlayer));
			CombatEntityStats.setManabornBonus(thisPlayer, CombatEntityStats.hasManabornBonus(thatPlayer));
			CombatEntityStats.setMana(thisPlayer, CombatEntityStats.getMana(thatPlayer));
		}
		thisPlayer.getAttribute(CAttributes.EARTH_AFFINITY).setBaseValue(thatPlayer.getAttributeBaseValue(CAttributes.EARTH_AFFINITY));
		thisPlayer.getAttribute(CAttributes.FIRE_AFFINITY).setBaseValue(thatPlayer.getAttributeBaseValue(CAttributes.FIRE_AFFINITY));
		thisPlayer.getAttribute(CAttributes.LIGHTNING_AFFINITY).setBaseValue(thatPlayer.getAttributeBaseValue(CAttributes.LIGHTNING_AFFINITY));
		thisPlayer.getAttribute(CAttributes.WATER_AFFINITY).setBaseValue(thatPlayer.getAttributeBaseValue(CAttributes.WATER_AFFINITY));
		thisPlayer.getAttribute(CAttributes.WIND_AFFINITY).setBaseValue(thatPlayer.getAttributeBaseValue(CAttributes.WIND_AFFINITY));
		CombatEntityStats.setManaColor(thisPlayer , CombatEntityStats.getManaColor(thatPlayer));
		CombatEntityStats.setPrimevalAffinity(thisPlayer, CombatEntityStats.getPrimevalAffinity(thatPlayer));
		CombatEntityStats.setAllies(thisPlayer, CombatEntityStats.getAllies(thatPlayer));
		CombatEntityStats.setStoredXP(thisPlayer, CombatEntityStats.getStoredXP(thatPlayer));
	}

	private static ItemStack findSoulGem(Player player) {
		if (isSoulGem(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return player.getItemInHand(InteractionHand.OFF_HAND);
		} else if (isSoulGem(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
				ItemStack itemstack = player.getInventory().getItem(i);
				if (isSoulGem(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected static boolean isSoulGem(ItemStack stack) {
		return stack.getItem() == CItems.EMPTY_SOUL_GEM;
	}

	@SuppressWarnings("resource")
	public static void entityKill(LivingEntity living, DamageSource source) {
		if (living instanceof Monster) {
			AbominationEvents.abominationDeath(source, (Monster)living);
		}
		
		Random rand = new Random();
		ItemStack itemIn = new ItemStack(CItems.SCROLL);
		ItemStack itemIn1 = new ItemStack(CItems.EMPTY_SOUL_GEM);
		if(!living.getCommandSenderWorld().isClientSide && (living instanceof LivingEntity)) {
			if(source.getEntity() instanceof Player) {
				Player player = (Player)source.getEntity();
				if (living.getMobType() != MobType.UNDEAD) {
					if (CEnchantmentHelper.hasSoulSealing(player.getMainHandItem()) || UnionMathHelper.probabilityCheck(50)) {
						if (!findSoulGem(player).isEmpty()) {
							findSoulGem(player).shrink(1);
							player.addItem(new ItemStack(CItems.SOUL_GEM));
						}
					}
				}
				if (living.getMobType() == MobType.UNDEAD) {
					if (UnionMathHelper.probabilityCheck(100) || CEnchantmentHelper.hasContainerExtraction(player.getMainHandItem())) living.spawnAtLocation(itemIn1);
				}
			}
		}
		if(!living.getCommandSenderWorld().isClientSide && living instanceof Monster) {
			Spell spell = SpellUtil.getWeightedRandomSpell(living.getRandom(), SpellCategory.values(ClassType.ELEMENTAL));
			if(source.getEntity() instanceof Player) {
				if(UnionMathHelper.probabilityCheck(Combat.MAGIC_CONFIG.scrollDropChanceFromKill)) {
					System.out.println("Should Drop Scroll");
					living.spawnAtLocation(SpellUtil.addSpellToStack(itemIn, spell));
				}
			} else {
				if(UnionMathHelper.probabilityCheck(Combat.MAGIC_CONFIG.scrollDropChance)) {
					living.spawnAtLocation(SpellUtil.addSpellToStack(itemIn, spell));
				}
			}
		}
		int runeDropChance = 3;
		boolean flag = UnionMathHelper.probabilityCheck(runeDropChance);
		if(!living.getCommandSenderWorld().isClientSide && ((living instanceof Monster && flag) || living.getType().is(EntityTypeCTags.BOSSES))) {
			if(source.getEntity() instanceof Player) {
				Skill skill = PlayerSkills.generateRandomSkill((Player)source.getEntity(), false);
				if (skill != Skills.EMPTY) living.spawnAtLocation(SkillUtil.addSkillToItemStack(new ItemStack(CItems.SKILL_RUNESTONE), skill));
			}
		}

		if(!living.getCommandSenderWorld().isClientSide && living.getType().is(EntityTypeCTags.BOSSES)) {
			if(source.getEntity() instanceof Player) {
				//				for (EntityType<?> entity : ForgeRegistries.ENTITIES) {
				//					if (entity.getRegistryName().toString() == "c") {
				//						
				//					}
				//				}
				Player player = (Player)source.getEntity();
				double luck = player.getAttributeValue(Attributes.LUCK);
				double maxLuck = 10;
				double luckMod = luck/maxLuck;
				if(rand.nextDouble() < luckMod) {
					List<Item> legendaryItems = new ArrayList<Item>();
					for (Item item : ForgeRegistries.ITEMS) {
						if (item instanceof LegendaryGear) {
							legendaryItems.add(item);
						}
					}
					int legendrand = rand.nextInt(legendaryItems.size());
					living.spawnAtLocation(new ItemStack(legendaryItems.get(legendrand)));
				}
			}
		}
	}

	public static void openFletchingTable(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec, InsertCanceller cancel, InsertSetter<InteractionResult> cancelResult) {
		if (!player.isCrouching()) {
			if (Config.COMMON.enable_fletching.get()) {
				Component CONTAINER_NAME = Component.translatable("container.fletching");
				if (player.level.getBlockState(pos).getBlock() == Blocks.FLETCHING_TABLE) {
					cancel.cancel();
					cancelResult.set(InteractionResult.FAIL);
					player.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
						return new FletchingMenu(p_220270_2_, p_220270_3_, ContainerLevelAccess.create(player.level, pos));
					}, CONTAINER_NAME));
				}
			}
		}
	}

	public static void entityJoinWorld(Entity entity, Level level, boolean loadedFromDisk) {
		if (entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)entity;
//			if (CombatEntityStats.getElementalAffinity(player) == SpellCategory.NONE) {
				//				player.sendMessage(new StringTextComponent("Use /affinitiy to set your Elemental, Life and Special Affinity. You won't be able to cast magic until you do"), Util.DUMMY_UUID);
//			}
		}
	}
}
