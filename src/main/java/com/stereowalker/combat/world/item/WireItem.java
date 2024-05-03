package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.level.block.entity.ConnectorBlockEntity;
import com.stereowalker.unionlib.util.VersionHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WireItem extends Item {
	private Type type;

	public WireItem(Type type, Properties properties) {
		super(properties);
		this.type = type;
	}
	
	public enum Type {
		NONE(0,0,0,0,0),COPPER(16,0.05f,0.784313725f,0.470588235f,0.274509804f),MYTHRIL(32,0.025f,0.2f,0.7f,0.4f);
		public record Detail(int maxLength, float size, float r, float g, float b) {}
		public Detail detail;
		private Type(int maxLength, float size, float r, float g, float b) {
			this.detail = new Detail(maxLength, size, r, g, b);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public InteractionResult useOn(UseOnContext context) {
		ItemStack stack = context.getItemInHand();
		if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ConnectorBlockEntity) {
			ConnectorBlockEntity conEnd = (ConnectorBlockEntity)context.getLevel().getBlockEntity(context.getClickedPos());
			if (conEnd.isConnected()) {
				context.getPlayer().sendSystemMessage(Component.literal("There are already wires here"));
			}
			else {
				if (connectionLocation(stack) == null) {
					setConnectionLocation(stack, context.getClickedPos());
					if (!context.getLevel().isClientSide)
						context.getPlayer().sendSystemMessage(Component.literal("Set first end"));
				} else {
					if ((connectionLocation(stack).getX() != context.getClickedPos().getX()) || (connectionLocation(stack).getY() != context.getClickedPos().getY()) || (connectionLocation(stack).getZ() != context.getClickedPos().getZ())) {
						ConnectorBlockEntity conStart = (ConnectorBlockEntity)context.getLevel().getBlockEntity(connectionLocation(stack));
						Validation validation = validateConnection(context.getLevel(), type, conStart, conEnd);
						switch (validation.result()) {
						case OBSTRUCTION:
							if (!context.getLevel().isClientSide)
								context.getPlayer().sendSystemMessage(Component.translatable("connector.obstruction", validation.point).withStyle(ChatFormatting.RED));
							break;
						case TOO_FAR:
							if (!context.getLevel().isClientSide)
								context.getPlayer().sendSystemMessage(Component.translatable("connector.too_far").withStyle(ChatFormatting.RED));
							break;
						case SUCCESS:
							conStart.setConnection(context.getClickedPos(), true, type);
							conEnd.setConnection(connectionLocation(stack), false, type);
							if (!context.getLevel().isClientSide)
								context.getPlayer().sendSystemMessage(Component.literal("Set Other end"));
							break;
						}
						removeConnectionLocation(stack);
						stack.shrink(1);
					} else {if (!context.getLevel().isClientSide)
						context.getPlayer().sendSystemMessage(Component.literal("Removing connection"));
					removeConnectionLocation(stack);
					}
				}
			}
		}
		return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
	}

	public static BlockPos connectionLocation(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains("conX") && tag.contains("conY") && tag.contains("conZ")) {
			return new BlockPos(tag.getInt("conX"),tag.getInt("conY"),tag.getInt("conZ"));
		}
		return null;
	}

	public static void setConnectionLocation(ItemStack stack, BlockPos pos) {
		if (!stack.getOrCreateTag().contains("conX") && !stack.getOrCreateTag().contains("conY") && !stack.getOrCreateTag().contains("conZ")) {
			stack.getTag().putInt("conX", pos.getX());
			stack.getTag().putInt("conY", pos.getY());
			stack.getTag().putInt("conZ", pos.getZ());
		}
	}

	public static void removeConnectionLocation(ItemStack stack) {
		if (stack.getOrCreateTag().contains("conX") && stack.getOrCreateTag().contains("conY") && stack.getOrCreateTag().contains("conZ")) {
			stack.getTag().remove("conX");
			stack.getTag().remove("conY");
			stack.getTag().remove("conZ");
		}
	}

	//
	enum Result {OBSTRUCTION,TOO_FAR,SUCCESS}
	record Validation(BlockPos point, Result result) {
		static Validation createObstruction(BlockPos point) {return new Validation(point, Result.OBSTRUCTION);}
		static Validation createTooFar() {return new Validation(null, Result.TOO_FAR);}
		static Validation createSuccess() {return new Validation(null, Result.SUCCESS);}
	}
	private static Validation validateConnection(Level level, Type type, ConnectorBlockEntity connectionStart, ConnectorBlockEntity connectionEnd) {
		Vec3 vec3 = connectionStart.getWireStartPos();
		double d0 = (double) (((float)Math.PI / 180F)) + (Math.PI / 2D);
		Vec3 vec31 = connectionEnd.getWireEndPos();
		double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
		double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
		double d3 = ((double)connectionEnd.getBlockPos().getX()) + d1;
		double d4 = ((double)connectionEnd.getBlockPos().getY()) + vec31.y;
		double d5 = ((double)connectionEnd.getBlockPos().getZ()) + d2;
		float f = (float)(vec3.x - d3);
		float f1 = (float)(vec3.y - d4);
		float f2 = (float)(vec3.z - d5);
		
		int dist = Mth.ceil(vec3.distanceTo(new Vec3(d3, d4, d5)));
		if (dist >= type.detail.maxLength)
			return Validation.createTooFar();
		dist*=10;
		

		for(int i1 = 0; i1 <= dist - 1; ++i1) {
			Vec3 c = addVertexPairs(f, f1, f2, i1, (float)dist);
			Vec3 d = new Vec3(vec3.x - c.x, vec3.y - c.y - 0.65, vec3.z - c.z);
			BlockPos pos = VersionHelper.posFromDouble(d.x, d.y, d.z);
			System.out.println(vec3+"-"+c+" = "+d);
			BlockState target = level.getBlockState(pos);
			if (!(pos.equals(connectionEnd.getBlockPos()) || pos.equals(connectionStart.getBlockPos()))) {
				if (!target.isAir()) {
					System.out.println(level.isClientSide+" Invalid at "+pos+" "+target);
//					if (!level.isClientSide) level.setBlockAndUpdate(pos, Blocks.GLOWSTONE.defaultBlockState());
					return Validation.createObstruction(pos);
				}
				else
					System.out.println(pos+" < "+BuiltInRegistries.BLOCK.getKey(target.getBlock())+" is valid "+level.isClientSide);
			}
		}
		for(int j1 = dist - 1; j1 >= 0; --j1) {
			Vec3 c = addVertexPairs(f, f1, f2, j1, (float)dist);
			BlockPos pos = VersionHelper.posFromDouble(vec3.x - c.x, vec3.y - c.y - 0.65, vec3.z - c.z);
			BlockState target = connectionEnd.getLevel().getBlockState(pos);
			if (!(pos.equals(connectionEnd.getBlockPos()) || pos.equals(connectionStart.getBlockPos())))
				if (!target.isAir()) return Validation.createObstruction(pos);
		}
		return Validation.createSuccess();
	}

	public static Vec3 addVertexPairs(float p_174310_, float p_174311_, float p_174312_, int p_174321_, float dist) {
		float f = (float)p_174321_ / dist;
		float f5 = p_174310_ * f;
		float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
		float f7 = p_174312_ * f;
		return new Vec3(f5, f6, f7);
	}

}
