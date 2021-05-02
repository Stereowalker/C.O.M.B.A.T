package com.stereowalker.combat.item;

import com.stereowalker.combat.enchantment.CEnchantments;
import com.stereowalker.combat.entity.projectile.BulletEntity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

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
		super(new Item.Properties().maxStackSize(1).group(CItemGroup.BATTLE));
		this.magazineCapacity = magazineCapacity;
		this.muzzleSpeed = muzzleSpeed;
		this.magazine = magazine;
		this.emptyMagazine = emptyMagazine;
		this.fireRate = fireRate;
		this.gunType = gunType;
		this.fireSound = fireSound;
		this.reloadSound = reloadSound;
	}

	public CompoundNBT gunTag() {
		CompoundNBT nbt = new CompoundNBT();
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

	private ItemStack findMagazine(PlayerEntity player) {
		if (isMagazine(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		} else if (isMagazine(player.getHeldItem(Hand.MAIN_HAND))) {
			return player.getHeldItem(Hand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
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
		return stack.isItemEqual(new ItemStack(getMagazine()));
	}
	
	public void reloadGun(PlayerEntity player) {
		ItemStack stack = player.getHeldItemMainhand();
		if(this.needsAmmo(stack) && this.findMagazine(player) != ItemStack.EMPTY) {
			this.setAmmo(stack, getMagazineCapacity());
			this.findMagazine(player).shrink(1);
			//TODO:player.addStat(CStats.GUNS_RELOADED);
			if(!player.addItemStackToInventory(new ItemStack(this.getEmptyMagazine()))) {
				player.dropItem(new ItemStack(this.getEmptyMagazine()), true);
			}
			player.world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), this.getReloadSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}

	public void shootGun(PlayerEntity player) {
		ItemStack stack = player.getHeldItemMainhand();
		boolean flag = player.abilities.isCreativeMode || this.getAmmo(stack) > 0;
		int i = this.getUseDuration(stack) - 100;
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, player.world, player, i, flag);
		if (flag) {
			float f = GunItem.getBulletVelocity(stack);
			if (!((double)f < 0.1D)) {
				if (!player.world.isRemote) {
					AbstractArrowEntity entityarrow = this.createBullet(player.world, player);
					AbstractArrowEntity entityarrow2 = this.createBullet(player.world, player);
					AbstractArrowEntity entityarrow3 = this.createBullet(player.world, player);
					AbstractArrowEntity entityarrow4 = this.createBullet(player.world, player);
					AbstractArrowEntity entityarrow5 = this.createBullet(player.world, player);
					AbstractArrowEntity entityarrow6 = this.createBullet(player.world, player);
					entityarrow.setDirectionAndMovement(player, player.rotationPitch+1, player.rotationYaw, 0.0F, f * 1.0F, 2.0F);
					entityarrow2.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw+1, 0.0F, f * 1.0F, 2.0F);
					entityarrow3.setDirectionAndMovement(player, player.rotationPitch+1, player.rotationYaw+1, 0.0F, f * 1.0F, 2.0F);
					entityarrow4.setDirectionAndMovement(player, player.rotationPitch-1, player.rotationYaw, 0.0F, f * 1.0F, 2.0F);
					entityarrow5.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw-1, 0.0F, f * 1.0F, 2.0F);
					entityarrow6.setDirectionAndMovement(player, player.rotationPitch-1, player.rotationYaw-1, 0.0F, f * 1.0F, 2.0F);
					if (f == 1.0F) {
						entityarrow.setIsCritical(true);
						entityarrow2.setIsCritical(true);
						entityarrow3.setIsCritical(true);
						entityarrow4.setIsCritical(true);
						entityarrow5.setIsCritical(true);
						entityarrow6.setIsCritical(true);
					}
					
					int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
					if (j > 0) {
						entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
						entityarrow2.setDamage(entityarrow2.getDamage() + (double)j * 0.5D + 0.5D);
						entityarrow3.setDamage(entityarrow3.getDamage() + (double)j * 0.5D + 0.5D);
						entityarrow4.setDamage(entityarrow4.getDamage() + (double)j * 0.5D + 0.5D);
						entityarrow5.setDamage(entityarrow5.getDamage() + (double)j * 0.5D + 0.5D);
						entityarrow6.setDamage(entityarrow6.getDamage() + (double)j * 0.5D + 0.5D);
					}
					
					int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
					if (k > 0) {
						entityarrow.setKnockbackStrength(k);
						entityarrow2.setKnockbackStrength(k);
						entityarrow3.setKnockbackStrength(k);
						entityarrow4.setKnockbackStrength(k);
						entityarrow5.setKnockbackStrength(k);
						entityarrow6.setKnockbackStrength(k);
						
					}
					
					if (EnchantmentHelper.getEnchantmentLevel(CEnchantments.INCENDIARY, stack) > 0) {
						entityarrow.setFire(100);
						entityarrow2.setFire(100);
						entityarrow3.setFire(100);
						entityarrow4.setFire(100);
						entityarrow5.setFire(100);
						entityarrow6.setFire(100);
					}
					
					this.reduceAmmo(stack);
					
					player.world.addEntity(entityarrow);
					if(isShotgun()) {
						player.world.addEntity(entityarrow2);
						player.world.addEntity(entityarrow3);
						player.world.addEntity(entityarrow4);
						player.world.addEntity(entityarrow5);
						player.world.addEntity(entityarrow6);
					}
				}
				
				
				player.world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), this.getFireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (player.world.rand.nextFloat() * 0.4F + 1.2F) * 0.5F);
				player.rotationPitch+=180; 
				//TODO:player.addStat(CStats.SHOTS_FIRED);
				player.addStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	public AbstractArrowEntity createBullet(World p_200887_1_, LivingEntity p_200887_3_) {
		BulletEntity entitytippedarrow = new BulletEntity(p_200887_1_, p_200887_3_);
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
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.gunTag());
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		if (stack.getTag() == null) {
			stack.setTag(this.gunTag());
		}
	}
	
	public enum GunType {
		AUTOMATIC,
		SEMI_AUTOMATIC,
	}

}
