package net.lophius_.pbe.entity;

import net.lophius_.pbe.PBEMain;
import net.lophius_.pbe.entity.custom.DarkstoneEntity;
import net.lophius_.pbe.entity.custom.WhitestoneEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PBEMain.MODID);

    public static final RegistryObject<EntityType<DarkstoneEntity>> DARKSTONE_ENTITY =
            ENTITY_TYPES.register("darkstone_entity", () ->
                    EntityType.Builder.<DarkstoneEntity>of(DarkstoneEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build("darkstone_entity"));

    public static final RegistryObject<EntityType<WhitestoneEntity>> WHITESTONE_ENTITY =
            ENTITY_TYPES.register("whitestone_entity", () ->
                    EntityType.Builder.<WhitestoneEntity>of(WhitestoneEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build("whitestone_entity"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
