package net.lophius_.pbe.item;

import net.lophius_.pbe.PBEMain;
import net.lophius_.pbe.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PBEMain.MODID);

    public static final RegistryObject<CreativeModeTab> PBE_TAB = CREATIVE_MODE_TABS.register("pbe_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DARKSTONE.get()))
            .title(Component.translatable("creativetab.pbe_tab"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.DARKSTONE.get());
                pOutput.accept(ModItems.WHITESTONE.get());
                pOutput.accept(ModBlocks.BLANK_FlOOR_DRAGONSPIRAL.get());
                pOutput.accept(ModBlocks.PATTERN_FLOOR_DRAGONSPIRAL.get());
                pOutput.accept(ModBlocks.PILLAR_FLOOR_DRAGONSPIRAL.get());
                pOutput.accept(ModBlocks.BLANK_OUTER_DRAGONSPIRAL.get());
                pOutput.accept(ModBlocks.BRICK_OUTER_DRAGONSPIRAL.get());
                pOutput.accept(ModBlocks.SPAWN_FLOOR_DRAGONSPIRAL.get());
            })
            .build());

    public static void  register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
