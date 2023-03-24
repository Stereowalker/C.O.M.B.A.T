package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.projectile.Bullet;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class GunItem extends Item {
	private int fireRate;
	private int magazineCapacity;
	private float muzzleSpeed;
	private Item magazine;
	private Item emptyMagazine;
	private GunType gunType;
	private SoundEvent fireSound;
	private SoundEvent reloadSound;

	public GunItem(int magazineCapacity, int fireRate, float muzzleSpeed, Item magazine, Item emptyMagazine, GunType gunType, SoundEvent fireSound, SoundEvent reloadSound) {
		super(new Item.Properties().stacksTo(1).tab(CCreativeModeTab.BATTLE));
		this.magazineCapacity = magazineCapacity;
		this.muzzleSpeed = muzzleSpeed;
		this.magazine = magazine;
		this.emptyMagazine = emptyMagazine;
		this.fireRate = fireRate;
		this.gunType = gunType;
		this.fireSound = fireSound;
		this.reloadSound = reloadSound;
	}

	public CompoundTag gunTag() {
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("Ammo", getMagazineCapacity());
		return nbt;
	}

	public ItemStack addPropertiesToGun(ItemStack stack) {
		stack.setTag(gunTag());
		return stack;
	}
	
	public SoundEvent getFireSound() {
		return fireSound;
	}

	public SoundEvent getReloadSound() {
		return reloadSound;
	}

	public Item getMagazine() {
		return magazine;
	}

	public Item getEmptyMagazine() {
		return emptyMagazine;
	}

	public float getMuzzleSpeed() {
		return muzzleSpeed;
	}

	public int getMagazineCapacity() {
		return magazineCapacity;
	}

	public int getFireRate() {
		return fireRate;
	}

	public GunType getGunType() {
		return gunType;
	}

	public int getAmmo(ItemStack stack) {
		return stack.getOrCreateTag().getInt("Ammo");
	}

	public void setAmmo(ItemStack stack, int ammo) {
		stack.getOrCreateTag().putInt("Ammo", ammo);
	}
	
	public boolean needsAmmo(ItemStack stack) {
		return getAmmo(stack) < getMagazineCapacity();
	}

	private ItemStack findMagazine(Player player) {
		if (isMagazine(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return player.getItemInHand(InteractionHand.OFF_HAND);
		} else if (isMagazine(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
				ItemStack itemstack = player.getInventory().getItem(i);
				if (isMagazine(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
	
	private boolean reduceAmmo(ItemStack stack) {
		if (this.getAmmo(stack) > 0) {
			this.setAmmo(stack, this.getAmmo(stack) - 1);
			return true;
		}
		return false;
	}

	protected boolean isMagazine(ItemStack stack) {
		return stack.sameItem(new ItemStack(getMagazine()));
	}
	
	public void reloadGun(Player player) {
		ItemStack stack = player.getMainHandItem();
		if(this.needsAmmo(stack) && this.findMagazine(player) != ItemStack.EMPTY) {
			this.setAmmo(stack, getMagazineCapacity());
			this.findMagazine(player).shrink(1);
			//TODO:player.addStat(CStats.GUNS_RELOADED);
			if(!player.addItem(new ItemStack(this.getEmptyMagazine()))) {
				player.drop(new ItemStack(this.getEmptyMagazine()), true);
			}
			player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), this.getReloadSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
		}
	}

	public void shootGun(Player player) {
		ItemStack stack = player.getMainHandItem();
		boolean flag = player.getAbilities().instabuild || this.getAmmo(stack) > 0;
		int i = this.getUseDuration(stack) - 100;
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, player.level, player, i, flag);
		if (flag) {
			float f = GunItem.getBulletVelocity(stack);
			if (!((double)f < 0.1D)) {
				if (!player.level.isClientSide) {
					AbstractArrow entityarrow = this.createBullet(player.level, player);
					AbstractArrow entityarrow2 = this.createBullet(player.level, player);
					AbstractArrow entityarrow3 = this.createBullet(player.level, player);
					AbstractArrow entityarrow4 = this.createBullet(player.level, player);
					AbstractArrow entityarrow5 = this.createBullet(player.level, player);
					AbstractArrow entityarrow6 = this.createBullet(player.level, player);
					entityarrow.shootFromRotation(player, player.getXRot()+1, player.getYRot(), 0.0F, f * 1.0F, 2.0F);
					entityarrow2.shootFromRotation(player, player.getXRot(), player.getYRot()+1, 0.0F, f * 1.0F, 2.0F);
					entityarrow3.shootFromRotation(player, player.getXRot()+1, player.getYRot()+1, 0.0F, f * 1.0F, 2.0F);
					entityarrow4.shootFromRotation(player, player.getXRot()-1, player.getYRot(), 0.0F, f * 1.0F, 2.0F);
					entityarrow5.shootFromRotation(player, player.getXRot(), player.getYRot()-1, 0.0F, f * 1.0F, 2.0F);
					entityarrow6.shootFromRotation(player, player.getXRot()-1, player.getYRot()-1, 0.0F, f * 1.0F, 2.0F);
					if (f == 1.0F) {
						entityarrow.setCritArrow(true);
						entityarrow2.setCritArrow(true);
						entityarrow3.setCritArrow(true);
						entityarrow4.setCritArrow(true);
						entityarrow5.setCritArrow(true);
						entityarrow6.setCritArrow(true);
					}
					
					int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
					if (j > 0) {
						entityarrow.setBaseDamage(entityarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
						entityarrow2.setBaseDamage(entityarrow2.getBaseDamage() + (double)j * 0.5D + 0.5D);
						entityarrow3.setBaseDamage(entityarrow3.getBaseDamage() + (double)j * 0.5D + 0.5D);
						entityarrow4.setBaseDamage(entityarrow4.getBaseDamage() + (double)j * 0.5D + 0.5D);
						entityarrow5.setBaseDamage(entityarrow5.getBaseDamage() + (double)j * 0.5D + 0.5D);
						entityarrow6.setBaseDamage(entityarrow6.getBaseDamage() + (double)j * 0.5D + 0.5D);
					}
					
					int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
					if (k > 0) {
						entityarrow.setKnockback(k);
						entityarrow2.setKnockback(k);
						entityarrow3.setKnockback(k);
						entityarrow4.setKnockback(k);
						entityarrow5.setKnockback(k);
						entityarrow6.setKnockback(k);
						
					}
					
					if (EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.INCENDIARY, stack) > 0) {
						entityarrow.setSecondsOnFire(100);
						entityarrow2.setSecondsOnFire(100);
						entityarrow3.setSecondsOnFire(100);
						entityarrow4.setSecondsOnFire(100);
						entityarrow5.setSecondsOnFire(100);
						entityarrow6.setSecondsOnFire(100);
					}
					
					this.reduceAmmo(stack);
					
					player.level.addFreshEntity(entityarrow);
					if(isShotgun()) {
						player.level.addFreshEntity(entityarrow2);
						player.level.addFreshEntity(entityarrow3);
						player.level.addFreshEntity(entityarrow4);
						player.level.addFreshEntity(entityarrow5);
						player.level.addFreshEntity(entityarrow6);
					}
				}
				
				
				player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), this.getFireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (player.level.random.nextFloat() * 0.4F + 1.2F) * 0.5F);
				player.setXRot(player.getXRot()+180); 
				//TODO:player.addStat(CStats.SHOTS_FIRED);
				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	public AbstractArrow createBullet(Level p_200887_1_, LivingEntity p_200887_3_) {
		Bullet entitytippedarrow = new Bullet(p_200887_1_, p_200887_3_);
		return entitytippedarrow;
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getBulletVelocity(ItemStack stack) {
		if(stack.getItem() instanceof GunItem) {
			GunItem gun = (GunItem) stack.getItem();
			float f = gun.getMuzzleSpeed()/225.0F;
			return f;
		}
		return 1.0F;
	}
	
	public boolean isShotgun() {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.gunTag());
		}
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		if (stack.getTag() == null) {
			stack.setTag(this.gunTag());
		}
	}
	
	public enum GunType {
		AUTOMATIC,
		SEMI_AUTOMATIC,
	}

}
