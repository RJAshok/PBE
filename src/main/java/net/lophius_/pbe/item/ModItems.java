package net.lophius_.pbe.item;

import net.lophius_.pbe.PBEMain;
import net.lophius_.pbe.item.custom.DarkstoneItem;
import net.lophius_.pbe.item.custom.WhitestoneItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PBEMain.MODID);

    public static final RegistryObject<Item> DARKSTONE = ITEMS.register("darkstone", () -> new DarkstoneItem(new Item.Properties()));
    public static final RegistryObject<Item> WHITESTONE = ITEMS.register("whitestone", () -> new WhitestoneItem(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
