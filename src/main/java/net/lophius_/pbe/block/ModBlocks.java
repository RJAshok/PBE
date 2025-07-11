package net.lophius_.pbe.block;

import net.lophius_.pbe.PBEMain;
import net.lophius_.pbe.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PBEMain.MODID);

    public static final RegistryObject<Block> PATTERN_FLOOR_DRAGONSPIRAL = registerBlock("pattern_floor_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> BLANK_FlOOR_DRAGONSPIRAL = registerBlock("blank_floor_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> PILLAR_FLOOR_DRAGONSPIRAL = registerBlock("pillar_floor_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> BLANK_OUTER_DRAGONSPIRAL = registerBlock("blank_outer_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> BRICK_OUTER_DRAGONSPIRAL = registerBlock("brick_outer_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> SPAWN_FLOOR_DRAGONSPIRAL = registerBlock("spawn_floor_dragonspiral",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
