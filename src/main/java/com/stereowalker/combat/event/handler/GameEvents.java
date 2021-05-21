package com.stereowalker.combat.event.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.entity.item.BoatModEntity;
import com.stereowalker.combat.entity.monster.VampireEntity;
import com.stereowalker.combat.event.AbominationEvents;
import com.stereowalker.combat.event.LegendaryWeaponEvents;
import com.stereowalker.combat.event.MagicEvents;
import com.stereowalker.combat.inventory.container.FletchingContainer;
import com.stereowalker.combat.item.AccessoryItemCheck;
import com.stereowalker.combat.item.ArchItem;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.DaggerItem;
import com.stereowalker.combat.item.HalberdItem;
import com.stereowalker.combat.item.ILegendaryGear;
import com.stereowalker.combat.item.LongbowItem;
import com.stereowalker.combat.item.ScytheItem;
import com.stereowalker.combat.item.SoulBowItem;
import com.stereowalker.combat.item.SpearItem;
import com.stereowalker.combat.network.server.SPlayerStatsPacket;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.tags.CEntityTypeTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.gen.feature.structure.CStructure;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.unionlib.event.item.ItemAttributeEvent;
import com.stereowalker.unionlib.util.EntityHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
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
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = "combat")
public class GameEvents {

	public static Predicate<LivingEntity> jumpingEntities = (entities) -> {
		return (entities instanceof PlayerEntity || entities instanceof PlayerEntity) && !(entities instanceof RabbitEntity || entities instanceof RabbitEntity) ;
	};

	@SubscribeEvent
	public static void dropXPFromFarming(BreakEvent event) {
		if (event.getState().getBlock() instanceof CropsBlock) {
			CropsBlock crop = (CropsBlock)event.getState().getBlock();
			if (crop.isMaxAge(event.getState())) {
				if (Config.COMMON.xpFromFarming.get() != 0)event.setExpToDrop(Config.COMMON.xpFromFarming.get());
			}
		}
	}

	public static Map<EntityType<?>, Item>bloodMap() {
		return null;

	}

	@SubscribeEvent
	public static void modifyAttributes(ItemAttributeEvent event) {
		if (getReach(event.getStack().getItem()) > 0 && (!(event.getStack().getItem() instanceof ArmorItem) && event.getEquipmentSlot() == EquipmentSlotType.MAINHAND) || (event.getStack().getItem() instanceof ArmorItem && ((ArmorItem)event.getStack().getItem()).getEquipmentSlot() == event.getEquipmentSlot())) {
			event.getAttributeMap().put(CAttributes.ATTACK_REACH, new AttributeModifier(UUIDS.ATTACK_REACH_MODIFIER, "Weapon modifier", getReach(event.getStack().getItem()), AttributeModifier.Operation.ADDITION)); 
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
		ItemStack bottle = event.getPlayer().getHeldItem(Hand.OFF_HAND);
		ItemStack dagger = event.getPlayer().getHeldItem(Hand.MAIN_HAND);
		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
			boolean flag = false;
			if (event.getTarget() instanceof VampireEntity) {
				event.getPlayer().addItemStackToInventory(new ItemStack(CItems.VAMPIRE_BLOOD));
				flag = true;
			} else if (event.getTarget() instanceof LivingEntity && !((LivingEntity)event.getTarget()).getCreatureAttribute().equals(CreatureAttribute.UNDEAD)) {
				event.getPlayer().addItemStackToInventory(new ItemStack(CItems.BLOOD));
				flag = true;
			}
			if (!event.getPlayer().abilities.isCreativeMode && flag) {
				bottle.shrink(1);
			}
			if (flag) {
				event.setCanceled(true);
				event.setCancellationResult(ActionResultType.SUCCESS);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void setupJumpHeightAttribute(LivingJumpEvent event) {
		boolean isActive = false;
		LivingEntity living = event.getEntityLiving();
		if (living != null) {
			if (living.isPotionActive(CEffects.PARALYSIS)) isActive = true;
			if (jumpingEntities.test(living) || isActive) {
				//Cancels the actual jumping motion of the entity
				float actualJumpHeight = EntityHelper.getJumpUpwardsMotion(event.getEntityLiving()) ;
				if (event.getEntityLiving().isPotionActive(Effects.JUMP_BOOST)) {
					actualJumpHeight += 0.1F * (float)(event.getEntityLiving().getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1);
				}
				if (isActive) actualJumpHeight = 100.0F;
				Vector3d vec3d = event.getEntityLiving().getMotion();
				event.getEntityLiving().setMotion(vec3d.x, (double)-actualJumpHeight , vec3d.z);
			}

			if (jumpingEntities.test(living)) {
				//Implements the modded version of this via the Attribute system
				double modifiedJumpHeight = EntityHelper.getJumpUpwardsMotion(event.getEntityLiving()) + (living.getAttributeValue(CAttributes.JUMP_STRENGTH) - 0.2D);
				Vector3d vec3d2 = event.getEntityLiving().getMotion();
				event.getEntityLiving().setMotion(vec3d2.x, modifiedJumpHeight , vec3d2.z);
			}

			if (isActive) {
				//Prevents the entity from moving by jumping and moving
				if (event.getEntityLiving().isSprinting()) {
					float f1 = event.getEntityLiving().rotationYaw * ((float)Math.PI / 180F);
					double x = (double)(-MathHelper.sin(f1) * 0.2F);
					double z = (double)(MathHelper.cos(f1) * 0.2F);
					event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().add(-x, 0.0D, -z));
				}
				event.getEntityLiving().isAirBorne = false;
			}
		}
	}

	@SubscribeEvent
	public static void setupHealthRegenerationAttribute(LivingHealEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity) {
			event.setAmount((float) (event.getAmount() * event.getEntityLiving().getAttributeValue(CAttributes.HEALTH_REGENERATION)));
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void xpRingStorage(PlayerXpEvent.PickupXp event) {
		if (AccessoryItemCheck.isEquipped(event.getPlayer(), CItems.XP_STORAGE_RING)) {
			CombatEntityStats.addStoredXP(event.getPlayer(), event.getOrb().getXpValue());
			event.getOrb().xpValue = 0;
		}
	}

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof MonsterEntity) {
			AbominationEvents.updateAbomination((MonsterEntity)event.getEntityLiving());
		}
		if (event.getEntityLiving() instanceof PlayerEntity) {
			LegendaryWeaponEvents.legendaryCollection((PlayerEntity) event.getEntityLiving());
		}
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof ServerPlayerEntity) {
			setTower((ServerPlayerEntity) event.getEntityLiving());
		}
		MagicEvents.manaUpdate(event.getEntityLiving());
		//Set the players stats once when they spawn into the world
		if(event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			CombatEntityStats.addStatsToPlayerOnSpawn(player);
		}
		//Send tickets to the client containing every stat for the player
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
			CombatEntityStats.setResist(player, player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()*10);
			Combat.getInstance().channel.sendTo(new SPlayerStatsPacket(player), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void livingUpdateOnClient(LivingUpdateEvent event) {
		if (event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof ClientPlayerEntity) {
		}
	}

	@SubscribeEvent
	public static void replenishManaOnSleep(SleepFinishedTimeEvent event) {
		MagicEvents.replenishManaOnSleep(event.getWorld());
	}

	@SubscribeEvent
	public static void fovUpdate(FOVUpdateEvent event) {
		float f = 1.0F;
		if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() instanceof LongbowItem) {
			int i = event.getEntity().getItemInUseMaxCount();
			float f1 = (float)i / 30.0F;
			if (f1 > 1.5F) {
				f1 = 2.25F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			event.setNewfov(f);
		}

		if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() instanceof SoulBowItem) {
			int i = event.getEntity().getItemInUseMaxCount();
			float f1 = (float)i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
			event.setNewfov(f);
		}

		if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() instanceof ArchItem) {
			int i = event.getEntity().getItemInUseMaxCount();
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

	public static void setTower(ServerPlayerEntity player) {
		BlockPos blockpos = player.getServerWorld().getChunkProvider().getChunkGenerator().func_235956_a_(player.getServerWorld(), CStructure.ETHERION_TOWER, player.getPosition(), 100, false);
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

	private static ItemStack findSoulGem(PlayerEntity player) {
		if (isSoulGem(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		} else if (isSoulGem(player.getHeldItem(Hand.MAIN_HAND))) {
			return player.getHeldItem(Hand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
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
		if(event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			if (player.getRidingEntity() instanceof BoatModEntity) {
				ClientPlayerEntity psp = Minecraft.getInstance().player;
				BoatModEntity entityboat = (BoatModEntity)player.getRidingEntity();
				if(psp != null && player != null && entityboat != null) {
					entityboat.updateInputs(psp.movementInput.leftKeyDown, psp.movementInput.rightKeyDown, psp.movementInput.forwardKeyDown, psp.movementInput.backKeyDown);
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityKill(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof MonsterEntity) {
			AbominationEvents.abominationDeath(event.getSource(), (MonsterEntity)event.getEntityLiving());
		}
		Random rand = new Random();
		ItemStack itemIn = new ItemStack(CItems.SCROLL);
		ItemStack itemIn1 = new ItemStack(CItems.EMPTY_SOUL_GEM);
		if(!event.getEntityLiving().getEntityWorld().isRemote && (event.getEntityLiving() instanceof LivingEntity)) {
			if(event.getSource().getTrueSource() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
				if (event.getEntityLiving().getCreatureAttribute() != CreatureAttribute.UNDEAD) {
					if (CEnchantmentHelper.hasSoulSealing(player.getHeldItemMainhand()) || UnionMathHelper.probabilityCheck(50)) {
						if (!findSoulGem(player).isEmpty()) {
							findSoulGem(player).shrink(1);
							player.addItemStackToInventory(new ItemStack(CItems.SOUL_GEM));
						}
					}
				}
				if (event.getEntityLiving().getCreatureAttribute() == CreatureAttribute.UNDEAD) {
					if (UnionMathHelper.probabilityCheck(100) || CEnchantmentHelper.hasContainerExtraction(player.getHeldItemMainhand())) event.getEntityLiving().entityDropItem(itemIn1);
				}
			}
		}
		if(!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof MonsterEntity) {
			if(event.getSource().getTrueSource() instanceof PlayerEntity) {
				if(UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.scrollDropChanceFromKill.get())) {
					event.getEntityLiving().entityDropItem(SpellUtil.addSpellToStack(itemIn, SpellUtil.getWeightedRandomSpell(rand)));
				}
			} else {
				if(UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.scrollDropChance.get())) {
					event.getEntityLiving().entityDropItem(SpellUtil.addSpellToStack(itemIn, SpellUtil.getWeightedRandomSpell(rand)));
				}
			}
		}
		int runeDropChance = 3;
		boolean flag = UnionMathHelper.probabilityCheck(runeDropChance);
		if(!event.getEntityLiving().getEntityWorld().isRemote && ((event.getEntityLiving() instanceof MonsterEntity && flag) || event.getEntityLiving().getType().isContained(CEntityTypeTags.BOSSES))) {
			if(event.getSource().getTrueSource() instanceof PlayerEntity) {
				Skill skill = PlayerSkills.generateRandomSkill((PlayerEntity)event.getSource().getTrueSource(), false);
				if (skill != Skills.EMPTY) event.getEntityLiving().entityDropItem(SkillUtil.addSkillToItemStack(new ItemStack(CItems.SKILL_RUNESTONE), skill));
			}
		}

		if(!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving().getType().isContained(CEntityTypeTags.BOSSES)) {
			if(event.getSource().getTrueSource() instanceof PlayerEntity) {
				//				for (EntityType<?> entity : ForgeRegistries.ENTITIES) {
				//					if (entity.getRegistryName().toString() == "c") {
				//						
				//					}
				//				}
				PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
				double luck = player.getAttributeValue(Attributes.LUCK);
				double maxLuck = 10;
				double luckMod = luck/maxLuck;
				if(rand.nextDouble() < luckMod) {
					List<Item> legendaryItems = new ArrayList<Item>();
					for (Item item : ForgeRegistries.ITEMS) {
						if (item instanceof ILegendaryGear) {
							legendaryItems.add(item);
						}
					}
					int legendrand = rand.nextInt(legendaryItems.size());
					event.getEntityLiving().entityDropItem(new ItemStack(legendaryItems.get(legendrand)));
				}
			}
		}
	}

	@SubscribeEvent
	public static void abominationXP(LivingExperienceDropEvent event) {
		int i = event.getDroppedExperience();
		if (event.getEntityLiving() instanceof MonsterEntity) {
			i = AbominationEvents.abominationXP((MonsterEntity) event.getEntityLiving(), i);
		}
		if (i != event.getDroppedExperience()) {
			event.setDroppedExperience(i);
		}
	}


	@SubscribeEvent
	public static void openFletchingTable(RightClickBlock event) {
		if (!event.getPlayer().isCrouching()) {
			if (Config.COMMON.enable_fletching.get()) {
				TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.fletching");
				if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.FLETCHING_TABLE) {
					event.setCanceled(true);
					event.setCancellationResult(ActionResultType.FAIL);
					event.getPlayer().openContainer(new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
						return new FletchingContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.of(event.getWorld(), event.getPos()));
					}, CONTAINER_NAME));
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getEntity();
			if (CombatEntityStats.getElementalAffinity(player) == SpellCategory.NONE) {
				//				player.sendMessage(new StringTextComponent("Use /affinitiy to set your Elemental, Life and Special Affinity. You won't be able to cast magic until you do"), Util.DUMMY_UUID);
			}
		}
	}
}
