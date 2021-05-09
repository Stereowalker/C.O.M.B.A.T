var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var InsnList = Java.type("org.objectweb.asm.tree.InsnList");
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');
var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
var LdcInsnNode = Java.type('org.objectweb.asm.tree.LdcInsnNode');
var InvokeDynamicInsnNode = Java.type('org.objectweb.asm.tree.InvokeDynamicInsnNode');
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var FrameNode = Java.type('org.objectweb.asm.tree.FrameNode');
var LineNumberNode = Java.type('org.objectweb.asm.tree.LineNumberNode');

function initializeCoreMod() {

    return {

        // get pointed entity by using attack reach distance
        // replace hardcoded max range check value
        'game_renderer_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.renderer.GameRenderer'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_78473_a",
                    name: "getMouseOver",
                    desc: "(F)V",
                    patches: [patchGameRendererGetMouseOver1, patchGameRendererGetMouseOver2]
                }], classNode, "GameRenderer");
                return classNode;
            }
        },

        // extended attack reach server side
        'server_play_net_handler_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.network.play.ServerPlayNetHandler'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_147340_a",
                    name: "processUseEntity",
                    desc: "(Lnet/minecraft/network/play/client/CUseEntityPacket;)V",
                    patches: [patchServerPlayNetHandlerProcessUseEntity]
                }], classNode, "ServerPlayNetHandler");
                return classNode;
            }
        },

        // allow the burning strike skill to maake entities burn
		//TRICKING. replace hardcoded sweeping weapon check
		// modify the size of the sweep for sweeping weapons
		// increase the range cap on sweeping attacks
        'player_entity_patch': {
            'target': {
                  'type': 'CLASS',
                  'name': 'net.minecraft.entity.player.PlayerEntity'
              },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_71059_n",
                    name: "attackTargetEntityWithCurrentItem",
                    desc: "(Lnet/minecraft/entity/Entity;)V",
                    patches: [patchPlayerEntityAttackTargetEntityWithCurrentItem1, patchPlayerEntityAttackTargetEntityWithCurrentItem2, patchPlayerEntityAttackTargetEntityWithCurrentItem3, patchPlayerEntityAttackTargetEntityWithCurrentItem4]
                }], classNode, "PlayerEntity");
                return classNode;
            }
        },

        //TRICKING. make items enchanted with retaining not pop out of the players inventory when they die. 
        'player_inventory_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.entity.player.PlayerInventory'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_70436_m",
                    name: "dropAllItems",
                    desc: "()V",
                    patches: [patchPlayerInventoryDropAllItems]
                }], classNode, "PlayerInventory");
                return classNode;
            }
        },

        //Allow treasure to spawn instead of normal drops if the player has the treasure hunter skill
        'block_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.block.Block'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_220054_a",
                    name: "spawnDrops",
                    desc: "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
                    patches: [patchBlockSpawnDrops]
                }], classNode, "Block");
                return classNode;
            }
        },

        //Allow us to place snow layers
        'server_world_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.world.server.ServerWorld'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_217441_a",
                    name: "tickEnvironment",
                    desc: "(Lnet/minecraft/world/chunk/Chunk;I)V",
                    patches: [patchServerWorldTickEnvironment]
                }], classNode, "ServerWorld");
                return classNode;
            }
        },

        //Allow us to place snow layers
        'biome_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.world.biome.Biome'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_201850_b",
                    name: "doesSnowGenerate",
                    desc: "(Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;)Z",
                    patches: [patchBiomeDoesSnowGenerate]
                }], classNode, "Biome");
                return classNode;
            }
        },

        //Allows us to apply fall damage based on the jump strength attribute instead of the jump boost potion
        'living_entity_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.entity.LivingEntity'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_225503_b_",
                    name: "onLivingFall",
                    desc: "(FF)Z",
                    patches: [patchLivingEntityOnLivingFall]
                }], classNode, "LivingEntity");
                return classNode;
            }
        },

        //Allows us to render a custom glint texture for legendary items
        'item_renderer_patch': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.renderer.ItemRenderer'
            },
            'transformer': function(classNode) {
                patchMethod([{
                    obfName: "func_225503_b_",
                    name: "renderItem",
                    desc: "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;ZLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IILnet/minecraft/client/renderer/model/IBakedModel;)V",
                    patches: [patchItemRendererRenderItem1, patchItemRendererRenderItem2]
                }], classNode, "ItemRenderer");
                return classNode;
            }
        }
    };
}

function patchMethod(entries, classNode, name) {

    log("Patching " + name + "...");
    for (var i = 0; i < entries.length; i++) {

        var entry = entries[i];
        var method = findMethod(classNode.methods, entry);
        var flag = !!method;
		debug((flag ? "Run" : "Dont Run"));
        if (flag) {

            var obfuscated = !method.name.equals(entry.name);
            for (var j = 0; j < entry.patches.length; j++) {
				var flag2 = true;
                var patch = entry.patches[j];
                if (!patchInstructions(method, patch.filter, patch.action, obfuscated)) {
					flag2 = false;
                    flag = false;
                }
				log("Patch " + (j+1) + (flag2 ? " was successful" : " failed"));
            }
        }

        log("Patching " + name + "#" + entry.name + (flag ? " was successful" : " failed"));
    }
}

function findMethod(methods, entry) {

    for (var i = 0; i < methods.length; i++) {

        var method = methods[i];
		//if (!!method.name)debug("Method Name: "+method.name)
		//if (!!method.desc)debug("Method Desc: "+method.desc)
        if ((method.name.equals(entry.obfName) || method.name.equals(entry.name)) && method.desc.equals(entry.desc)) {
            return method;
        }
    }
}

function patchInstructions(method, filter, action, obfuscated) {

    var instructions = method.instructions.toArray();
    for (var i = 0; i < instructions.length; i++) {

        var node = filter(instructions[i], obfuscated);
		debug("FILTER NODE: "+(!!node));
        if (!!node) {

            break;
        }
    }
	debug("ACTION NODE: "+(!!node));
    if (!!node) {
		debug("Taken Action");
        action(node, method.instructions, obfuscated);
        return true;
    }
}

var patchServerPlayNetHandlerProcessUseEntity = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(3)) {
            var nextNode = node.getNext();
			if (matchesHook(nextNode, "net/minecraft/entity/player/ServerPlayerEntity", obfuscated ? "func_70068_e" : "getDistanceSq", "(Lnet/minecraft/entity/Entity;)D")) {
            //if (matchesMethod(nextNode, "net/minecraft/entity/player/ServerPlayerEntity", obfuscated ? "func_70068_e" : "getDistanceSq", "(Lnet/minecraft/entity/Entity;)D")) {
                nextNode = nextNode.getNext();
                //if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.DLOAD) && nextNode.var.equals(5)) {
                    return nextNode;
                //}
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new InsnNode(Opcodes.POP2));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/network/play/ServerPlayNetHandler", obfuscated ? "field_147369_b" : "player", "Lnet/minecraft/entity/player/ServerPlayerEntity;"));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 3));
        insnList.add(generateHook("getEntityReachDistance", "(Lnet/minecraft/entity/player/ServerPlayerEntity;Lnet/minecraft/entity/Entity;)D"));
        instructions.insert(node, insnList);
    }
};

var patchGameRendererGetMouseOver2 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ILOAD) && node.var.equals(6)) {
            var nextNode = node.getNext();
            if (nextNode instanceof JumpInsnNode && nextNode.getOpcode().equals(Opcodes.IFEQ)) {
                nextNode = nextNode.getNext();
                if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.DLOAD) && nextNode.var.equals(17)) {
                    nextNode = nextNode.getNext();
                    if (nextNode instanceof LdcInsnNode) {
                        return nextNode;
                    }
                }
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new InsnNode(Opcodes.POP2));
        insnList.add(new VarInsnNode(Opcodes.DLOAD, 3));
        insnList.add(generateHook("getMaxSquareRange", "(D)D"));
        instructions.insert(node, insnList);
    }
};

var patchGameRendererGetMouseOver1 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(5)) {
            var nextNode = node.getNext();
            if (matchesMethod(nextNode, "net/minecraft/util/math/vector/Vector3d", obfuscated ? "func_72436_e" : "squareDistanceTo", "(Lnet/minecraft/util/math/vector/Vector3d;)D")) {
                nextNode = nextNode.getNext();
                if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.DSTORE) && nextNode.var.equals(8)) {
                    return nextNode;
                }
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(generateHook("getAttackReachDistance", "()D"));
        insnList.add(new VarInsnNode(Opcodes.DSTORE, 3));
        insnList.add(new VarInsnNode(Opcodes.FLOAD, 1));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(generateHook("getSquareAttackDistance", "(FLnet/minecraft/entity/Entity;)D"));
        insnList.add(new VarInsnNode(Opcodes.DSTORE, 8));
        instructions.insert(node, insnList);
    }
};

var patchPlayerEntityAttackTargetEntityWithCurrentItem1 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(0)) {
            var nextNode = node.getNext();
            if (matchesHook(nextNode, "net/minecraft/enchantment/EnchantmentHelper", obfuscated ? "func_90036_a" : "getFireAspectModifier", "(Lnet/minecraft/entity/LivingEntity;)I")) {
                nextNode = nextNode.getNext();
                if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ISTORE) && nextNode.var.equals(15)) {
                    return nextNode;
                }
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(generateHook("getFireAttackModifier", "(Lnet/minecraft/entity/LivingEntity;)I"));
        insnList.add(new VarInsnNode(Opcodes.ISTORE, 15));
        instructions.insert(node, insnList);
    }
};

var patchPlayerEntityAttackTargetEntityWithCurrentItem2 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(13)) {
            var nextNode = node.getNext();
            if (matchesHook(nextNode, "net/minecraft/item/ItemStack", obfuscated ? "func_77973_b" : "getItem", "()Lnet/minecraft/item/Item;")) {
				return nextNode;
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 13));
        insnList.add(generateHook("isSweepingWeapon", "(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/Item;"));
        instructions.insert(node, insnList);
    }
};

var patchPlayerEntityAttackTargetEntityWithCurrentItem3 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(1)) {
            var nextNode = node.getNext();
            if (matchesHook(nextNode, "net/minecraft/entity/Entity", obfuscated ? "func_174813_aQ" : "getBoundingBox", "()Lnet/minecraft/util/math/AxisAlignedBB;")) {
				return nextNode;
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(generateHook("sweepingArc", "(Lnet/minecraft/util/math/AxisAlignedBB;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/math/AxisAlignedBB;"));
        instructions.insert(node, insnList);
    }
};

var patchPlayerEntityAttackTargetEntityWithCurrentItem4 = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(0)) {
            var nextNode = node.getNext();
            if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ALOAD) && nextNode.var.equals(20)) {
                nextNode = nextNode.getNext();
                if (matchesHook(nextNode, "net/minecraft/entity/player/PlayerEntity", obfuscated ? "func_70068_e" : "getDistanceSq", "(Lnet/minecraft/entity/Entity;)D")) {
                    return nextNode;
                }
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 20));
        insnList.add(generateHook("sweepingRange", "(DLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)D"));
        instructions.insert(node, insnList);
    }
};

var patchPlayerInventoryDropAllItems1 = {
    filter: function(node, obfuscated) {
		if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(2)) {
			var nextNode = node.getNext();
			if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ILOAD) && nextNode.var.equals(3)) {
				nextNode = nextNode.getNext();
				if (matchesMethod(nextNode, "java/util/List", obfuscated ? "get" : "get", "(I)Ljava/lang/Object;")) {
                    return nextNode;
				}
			}
		}
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 3));
        insnList.add(generateHook("returnEmptyStack", "(Ljava/lang/Object;Ljava/util/List;I)Ljava/lang/Object;"));
        instructions.insert(node, insnList);
    }
};

var patchPlayerInventoryDropAllItems = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(4)) {
            var nextNode = node.getNext();
			if (matchesHook(nextNode, "net/minecraft/item/ItemStack", obfuscated ? "func_190926_b" : "isEmpty", "()Z")) {
				return nextNode;
			}
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 4));
        insnList.add(generateHook("isEmptyOrRetaining", "(ZLnet/minecraft/item/ItemStack;)Z"));
        instructions.insert(node, insnList);
    }
};

var patchBlockSpawnDrops = {
    filter: function(node, obfuscated) {
        if (node instanceof VarInsnNode && node.getOpcode().equals(Opcodes.ALOAD) && node.var.equals(4)) {
            var nextNode = node.getNext();
            if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ALOAD) && nextNode.var.equals(5)) {
                nextNode = nextNode.getNext();
                if (matchesHook(nextNode, "net/minecraft/block/Block", obfuscated ? "func_220077_a" : "getDrops", "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;")) {
                    return nextNode;
                }
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/server/ServerWorld"));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 3));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 4));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 5));
        insnList.add(generateHook("getTreasure", "(Ljava/util/List;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"));
		var node2 = insnList.get(6);
        instructions.insert(node, insnList);
    }
};

var patchServerWorldTickEnvironment = {
    filter: function(node, obfuscated) {
        if (matchesHook(node, "net/minecraft/block/Blocks", obfuscated ? "field_150433_aE" : "SNOW", "Lnet/minecraft/block/Block;")) {
            var nextNode = node.getNext();
            if (matchesHook(nextNode, "net/minecraft/block/Block", obfuscated ? "func_176223_P" : "getDefaultState", "()Lnet/minecraft/block/BlockState;")) {
                return nextNode;
            }
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 8));
        insnList.add(generateHook("inceaseSnow", "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"));
        instructions.insert(node, insnList);
    }
};

var patchBiomeDoesSnowGenerate = {
    filter: function(node, obfuscated) {
        if (matchesHook(node, "net/minecraft/block/BlockState", obfuscated ? "func_196958_f" : "isAir", "(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z")) {
            return node;
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(generateHook("doesSnowGenerate", "(ZLnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z"));
        instructions.insert(node, insnList);
    }
};

var patchLivingEntityOnLivingFall = {
    filter: function(node, obfuscated) {
        if (matchesHook(node, "net/minecraft/entity/LivingEntity", obfuscated ? "func_225508_e_" : "calculateFallDamage", "(FF)I")) {
			var nextNode = node.getNext();
			if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ISTORE) && nextNode.var.equals(5)) {
            	return nextNode;
			}
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.FLOAD, 1));
        insnList.add(new VarInsnNode(Opcodes.FLOAD, 2));
        insnList.add(generateHook("calculateFallDamageFromAttribute", "(Lnet/minecraft/entity/LivingEntity;FF)I"));
        insnList.add(new VarInsnNode(Opcodes.ISTORE, 5));
        instructions.insert(node, insnList);
    }
};

var patchItemRendererRenderItem1 = {
    filter: function(node, obfuscated) {
        if (matchesHook(node, "net/minecraft/client/renderer/ItemRenderer", obfuscated ? "getBuffer" : "getBuffer", "(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/IVertexBuilder;")) {
			var nextNode = node.getNext();
			if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ASTORE) && nextNode.var.equals(12)) {
            	return nextNode;
			}
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 5));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 11));
        insnList.add(new InsnNode(Opcodes.ICONST_1));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(generateHook("getBuffer", "(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;ZLnet/minecraft/item/ItemStack;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"));
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 12));
        instructions.insert(node, insnList);
    }
};

var patchItemRendererRenderItem2 = {
    filter: function(node, obfuscated) {
		debug("");
		debug("*****************");
		if(node != null){
			if(!!node.owner) debug("Node Owner: "+node.owner);
			if(!!node.name) debug("Node Name: "+node.name);
			if(!!node.desc) debug("Node Desc: "+node.desc);
			//debug("Node Is Var: "+(node instanceof VarInsnNode));
			//debug("Node Is Jump: "+(node instanceof JumpInsnNode));
			if(!!node.getOpcode()) debug("Node Opcodes: "+node.getOpcode());
			if(!!node.var) debug("Node Var: "+node.var);	
		}
		debug("*****************");
		debug("");
        if (matchesHook(node, "net/minecraft/client/renderer/ItemRenderer", obfuscated ? "func_239391_c_" : "getEntityGlintVertexBuilder", "(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/IVertexBuilder;")) {
			var nextNode = node.getNext();
			if (nextNode instanceof VarInsnNode && nextNode.getOpcode().equals(Opcodes.ASTORE) && nextNode.var.equals(12)) {
            	return nextNode;
			}
        }
    },
    action: function(node, instructions, obfuscated) {
        var insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 5));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 11));
        insnList.add(new InsnNode(Opcodes.ICONST_1));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(generateHook("getEntityGlintVertexBuilder", "(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;ZLnet/minecraft/item/ItemStack;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"));
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 12));
		
		debug("#########MC#########");
		if(!!node.owner)debug("MC Node Owner: "+node.owner);
		debug("MC Node Name: "+node.name);
		debug("MC Node Desc: "+node.desc);
		debug("MC Node Is Var: "+(node instanceof VarInsnNode));
		debug("MC Node Is Method: "+(node instanceof MethodInsnNode));
		debug("MC Node Opcodes: "+node.getOpcode());
		debug("MC Node Var: "+node.var);	
		debug("#########MC#########");
		var node2 = insnList.get(4);
		debug("#########CT#########");
		if(!!node2.owner)debug("CT Node Owner: "+node2.owner);
		debug("CT Node Name: "+node2.name);
		debug("CT Node Desc: "+node2.desc);
		debug("CT Node Is Var: "+(node2 instanceof VarInsnNode));
		debug("CT Node Is Method: "+(node2 instanceof MethodInsnNode));
		debug("CT Node Opcodes: "+node2.getOpcode());
		debug("CT Node Var: "+node2.var);	
		debug("#########CT#########");
		
        instructions.insert(node, insnList);
    }
};

function matchesHook(node, owner, name, desc) {
	
	return !!node.owner && !!node.name && !!node.desc && matchesNode(node, owner, name, desc);
}

function matchesMethod(node, owner, name, desc) {

    return node instanceof MethodInsnNode && matchesNode(node, owner, name, desc);
}

function matchesField(node, owner, name, desc) {

    return node instanceof FieldInsnNode && matchesNode(node, owner, name, desc);
}

function matchesNode(node, owner, name, desc) {

    return node.owner.equals(owner) && node.name.equals(name) && node.desc.equals(desc);
}

function generateHook(name, desc) {

    return new MethodInsnNode(Opcodes.INVOKESTATIC, "com/stereowalker/combat/hooks/CombatHooks", name, desc, false);
}

function getNthNode(node, n) {

    for (var i = 0; i < Math.abs(n); i++) {

        if (n < 0) {

            node = node.getPrevious();
        } else {

            node = node.getNext();
        }
    }

    return node;
}

function log(message) {

    print("[C.O.M.B.A.T Transformer]: " + message);
}

function debug(message) {
	if (false) {
		print("[C.O.M.B.A.T Transformer Debug]: " + message);
	}
}