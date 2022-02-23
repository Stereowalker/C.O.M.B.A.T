package com.stereowalker.combat.event.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.event.AbominationEvents;
import com.stereowalker.combat.event.LegendaryWeaponEvents;
import com.stereowalker.combat.event.MagicEvents;
import com.stereowalker.combat.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundClientMotionPacket;
import com.stereowalker.combat.tags.CEntityTypeTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;
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
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.unionlib.util.EntityHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
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
				if (Config.COMMON.xpFromFarming.get() != 0)event.setExpToDrop(Config.COMMON.xpFromFarming.get());
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

	@SubscribeEvent
	public static void collectBlood(EntityInteract event) {
		ItemStack bottle = event.getPlayer().getItemInHand(InteractionHand.OFF_HAND);
		ItemStack dagger = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
			boolean flag = false;
			if (event.getTarget() instanceof Vampire) {
				event.getPlayer().addItem(new ItemStack(CItems.VAMPIRE_BLOOD));
				flag = true;
			} else if (event.getTarget() instanceof LivingEntity && !((LivingEntity)event.getTarget()).getMobType().equals(MobType.UNDEAD)) {
				event.getPlayer().addItem(new ItemStack(CItems.BLOOD));
				flag = true;
			}
			if (!event.getPlayer().getAbilities().instabuild && flag) {
				bottle.shrink(1);
			}
			if (flag) {
				event.setCanceled(true);
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void setupJumpHeightAttribute(LivingJumpEvent event) {
		boolean isActive = false;
		LivingEntity living = event.getEntityLiving();
		if (living != null) {
			if (living.hasEffect(CMobEffects.PARALYSIS)) isActive = true;
			if (jumpingEntities.test(living) || isActive) {
				//Cancels the actual jumping motion of the entity
				float actualJumpHeight = EntityHelper.getJumpUpwardsMotion(event.getEntityLiving()) ;
				if (event.getEntityLiving().hasEffect(MobEffects.JUMP)) {
					actualJumpHeight += 0.1F * (float)(event.getEntityLiving().getEffect(MobEffects.JUMP).getAmplifier() + 1);
				}
				if (isActive) actualJumpHeight = 100.0F;
				Vec3 vec3d = event.getEntityLiving().getDeltaMovement();
				event.getEntityLiving().setDeltaMovement(vec3d.x, (double)-actualJumpHeight , vec3d.z);
			}

			if (jumpingEntities.test(living)) {
				//Implements the modded version of this via the Attribute system
				double modifiedJumpHeight = EntityHelper.getJumpUpwardsMotion(event.getEntityLiving()) + (living.getAttributeValue(CAttributes.JUMP_STRENGTH) - 0.2D);
				Vec3 vec3d2 = event.getEntityLiving().getDeltaMovement();
				event.getEntityLiving().setDeltaMovement(vec3d2.x, modifiedJumpHeight , vec3d2.z);
			}

			if (isActive) {
				//Prevents the entity from moving by jumping and moving
				if (event.getEntityLiving().isSprinting()) {
					float f1 = event.getEntityLiving().getYRot() * ((float)Math.PI / 180F);
					double x = (double)(-Mth.sin(f1) * 0.2F);
					double z = (double)(Mth.cos(f1) * 0.2F);
					event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().add(-x, 0.0D, -z));
				}
				event.getEntityLiving().hasImpulse = false;
			}
		}
	}

	@SubscribeEvent
	public static void setupHealthRegenerationAttribute(LivingHealEvent event) {
		if (event.getEntityLiving() instanceof Player) {
			event.setAmount((float) (event.getAmount() * event.getEntityLiving().getAttributeValue(CAttributes.HEALTH_REGENERATION)));
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void xpRingStorage(PlayerXpEvent.PickupXp event) {
		if (AccessoryItemCheck.isEquipped(event.getPlayer(), CItems.XP_STORAGE_RING)) {
			CombatEntityStats.addStoredXP(event.getPlayer(), event.getOrb().getValue());
			event.getOrb().value = 0;
		}
	}

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof Monster) {
			AbominationEvents.updateAbomination((Monster)event.getEntityLiving());
		}
		if (event.getEntityLiving() instanceof Player) {
			LegendaryWeaponEvents.legendaryCollection((Player) event.getEntityLiving());
		}
		if (!event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof ServerPlayer) {
			setTower((ServerPlayer) event.getEntityLiving());
		}
		MagicEvents.manaUpdate(event.getEntityLiving());
		//Set the players stats once when they spawn into the world
		if(event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			CombatEntityStats.addStatsToPlayerOnSpawn(player);
		}
		//Send tickets to the client containing every stat for the player
		if (!event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)event.getEntityLiving();
			CombatEntityStats.setResist(player, player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()*10);
			Combat.getInstance().channel.sendTo(new ClientboundPlayerStatsPacket(player), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void livingUpdateOnClient(LivingUpdateEvent event) {
		if (event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof LocalPlayer) {
			new ServerboundClientMotionPacket(event.getEntityLiving().getDeltaMovement()).send();
		}
	}

	@SubscribeEvent
	public static void replenishManaOnSleep(SleepFinishedTimeEvent event) {
		MagicEvents.replenishManaOnSleep(event.getWorld());
	}

	@SubscribeEvent
	public static void fovUpdate(FOVUpdateEvent event) {
		float f = 1.0F;
		if (event.getEntity().isUsingItem() && event.getEntity().getUseItem().getItem() instanceof LongbowItem) {
			int i = event.getEntity().getTicksUsingItem();
			float f1 = (float)i / 30.0F;
			if (f1 > 1.5F) {
				f1 = 2.25F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			event.setNewfov(f);
		}

		if (event.getEntity().isUsingItem() && event.getEntity().getUseItem().getItem() instanceof SoulBowItem) {
			int i = event.getEntity().getTicksUsingItem();
			float f1 = (float)i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			event.setNewfov(f);
		}

		if (event.getEntity().isUsingItem() && event.getEntity().getUseItem().getItem() instanceof ArchItem) {
			int i = event.getEntity().getTicksUsingItem();
			float f1 = (float)i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			event.setNewfov(f);
		}
	}

	public static void setTower(ServerPlayer player) {
		BlockPos blockpos = player.getLevel().getChunkSource().getGenerator().findNearestMapFeature(player.getLevel(), CStructureFeature.ETHERION_TOWER, player.blockPosition(), 100, false);
		if (blockpos == null) blockpos = BlockPos.ZERO;
		if (CombatEntityStats.getNearestEtherionTowerPos(player) != blockpos) CombatEntityStats.setNearestEtherionTowerPos(player, blockpos);
	}

	@SubscribeEvent
	public static void restoreStats(PlayerEvent.Clone event) {
		CombatEntityStats.getOrCreateModNBT(event.getPlayer());
		if (!event.isWasDeath()) {
			CombatEntityStats.setVampire(event.getPlayer(), CombatEntityStats.isVampire(event.getOriginal()));
			CombatEntityStats.setManabornBonus(event.getPlayer(), CombatEntityStats.hasManabornBonus(event.getOriginal()));
			CombatEntityStats.setMana(event.getPlayer(), CombatEntityStats.getMana(event.getOriginal()));
		}
		CombatEntityStats.setManaColor(event.getPlayer() , CombatEntityStats.getManaColor(event.getOriginal()));
		CombatEntityStats.setSubElementalAffinity1(event.getPlayer(), CombatEntityStats.getSubElementalAffinity1(event.getOriginal()));
		CombatEntityStats.setSubElementalAffinity2(event.getPlayer(), CombatEntityStats.getSubElementalAffinity2(event.getOriginal()));
		CombatEntityStats.setLifeAffinity(event.getPlayer(), CombatEntityStats.getLifeAffinity(event.getOriginal()));
		CombatEntityStats.setSpecialAffinity(event.getPlayer(), CombatEntityStats.getSpecialAffinity(event.getOriginal()));
		CombatEntityStats.setElementalAffinity(event.getPlayer(), CombatEntityStats.getElementalAffinity(event.getOriginal()));
		CombatEntityStats.setAllies(event.getPlayer(), CombatEntityStats.getAllies(event.getOriginal()));
		CombatEntityStats.setStoredXP(event.getPlayer(), CombatEntityStats.getStoredXP(event.getOriginal()));
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

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void rowBoat(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			if (player.getVehicle() instanceof BoatMod) {
				LocalPlayer psp = Minecraft.getInstance().player;
				BoatMod entityboat = (BoatMod)player.getVehicle();
				if(psp != null && player != null && entityboat != null) {
					entityboat.setInput(psp.input.left, psp.input.right, psp.input.up, psp.input.down);
				}
			}
		}
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void entityKill(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof Monster) {
			AbominationEvents.abominationDeath(event.getSource(), (Monster)event.getEntityLiving());
		}
		Random rand = new Random();
		ItemStack itemIn = new ItemStack(CItems.SCROLL);
		ItemStack itemIn1 = new ItemStack(CItems.EMPTY_SOUL_GEM);
		if(!event.getEntityLiving().getCommandSenderWorld().isClientSide && (event.getEntityLiving() instanceof LivingEntity)) {
			if(event.getSource().getEntity() instanceof Player) {
				Player player = (Player)event.getSource().getEntity();
				if (event.getEntityLiving().getMobType() != MobType.UNDEAD) {
					if (CEnchantmentHelper.hasSoulSealing(player.getMainHandItem()) || UnionMathHelper.probabilityCheck(50)) {
						if (!findSoulGem(player).isEmpty()) {
							findSoulGem(player).shrink(1);
							player.addItem(new ItemStack(CItems.SOUL_GEM));
						}
					}
				}
				if (event.getEntityLiving().getMobType() == MobType.UNDEAD) {
					if (UnionMathHelper.probabilityCheck(100) || CEnchantmentHelper.hasContainerExtraction(player.getMainHandItem())) event.getEntityLiving().spawnAtLocation(itemIn1);
				}
			}
		}
		if(!event.getEntityLiving().getCommandSenderWorld().isClientSide && event.getEntityLiving() instanceof Monster) {
			if(event.getSource().getEntity() instanceof Player) {
				if(UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.scrollDropChanceFromKill.get())) {
					event.getEntityLiving().spawnAtLocation(SpellUtil.addSpellToStack(itemIn, SpellUtil.getWeightedRandomSpell(rand)));
				}
			} else {
				if(UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.scrollDropChance.get())) {
					event.getEntityLiving().spawnAtLocation(SpellUtil.addSpellToStack(itemIn, SpellUtil.getWeightedRandomSpell(rand)));
				}
			}
		}
		int runeDropChance = 3;
		boolean flag = UnionMathHelper.probabilityCheck(runeDropChance);
		if(!event.getEntityLiving().getCommandSenderWorld().isClientSide && ((event.getEntityLiving() instanceof Monster && flag) || event.getEntityLiving().getType().is(CEntityTypeTags.BOSSES))) {
			if(event.getSource().getEntity() instanceof Player) {
				Skill skill = PlayerSkills.generateRandomSkill((Player)event.getSource().getEntity(), false);
				if (skill != Skills.EMPTY) event.getEntityLiving().spawnAtLocation(SkillUtil.addSkillToItemStack(new ItemStack(CItems.SKILL_RUNESTONE), skill));
			}
		}

		if(!event.getEntityLiving().getCommandSenderWorld().isClientSide && event.getEntityLiving().getType().is(CEntityTypeTags.BOSSES)) {
			if(event.getSource().getEntity() instanceof Player) {
				//				for (EntityType<?> entity : ForgeRegistries.ENTITIES) {
				//					if (entity.getRegistryName().toString() == "c") {
				//						
				//					}
				//				}
				Player player = (Player)event.getSource().getEntity();
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
					event.getEntityLiving().spawnAtLocation(new ItemStack(legendaryItems.get(legendrand)));
				}
			}
		}
	}

	@SubscribeEvent
	public static void openFletchingTable(RightClickBlock event) {
		if (!event.getPlayer().isCrouching()) {
			if (Config.COMMON.enable_fletching.get()) {
				TranslatableComponent CONTAINER_NAME = new TranslatableComponent("container.fletching");
				if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.FLETCHING_TABLE) {
					event.setCanceled(true);
					event.setCancellationResult(InteractionResult.FAIL);
					event.getPlayer().openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
						return new FletchingMenu(p_220270_2_, p_220270_3_, ContainerLevelAccess.create(event.getWorld(), event.getPos()));
					}, CONTAINER_NAME));
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)event.getEntity();
			if (CombatEntityStats.getElementalAffinity(player) == SpellCategory.NONE) {
				//				player.sendMessage(new StringTextComponent("Use /affinitiy to set your Elemental, Life and Special Affinity. You won't be able to cast magic until you do"), Util.DUMMY_UUID);
			}
		}
	}
}
