package com.sp.init;

import com.sp.SPBRevamped;
import com.sp.world.levels.BackroomsLevel;
import com.sp.world.levels.WorldRepresentingBackroomsLevel;
import com.sp.world.levels.custom.*;
import com.sp.world.levels.custom.vanilla_representing.OverworldRepresentingBackroomsLevel;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackroomsLevels {
    public static final RegistryKey<DimensionType> LEVEL0_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, new Identifier(SPBRevamped.MOD_ID, "level0_type"));
    public static final RegistryKey<World> LEVEL0_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "level0"));
    public static final RegistryKey<World> LEVEL1_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "level1"));
    public static final RegistryKey<World> LEVEL2_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "level2"));
    public static final RegistryKey<World> POOLROOMS_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "poolrooms"));
    public static final RegistryKey<World> INFINITE_FIELD_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "infinite_field"));
    public static final RegistryKey<World> LEVEL324_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(SPBRevamped.MOD_ID, "level324"));

    public static final BackroomsLevel LEVEL0_BACKROOMS_LEVEL = new Level0BackroomsLevel();
    public static final BackroomsLevel LEVEL1_BACKROOMS_LEVEL = new Level1BackroomsLevel();
    public static final BackroomsLevel LEVEL2_BACKROOMS_LEVEL = new Level2BackroomsLevel();
    public static final BackroomsLevel POOLROOMS_BACKROOMS_LEVEL = new PoolroomsBackroomsLevel();
    public static final BackroomsLevel INFINITE_FIELD_BACKROOMS_LEVEL = new InfiniteGrassBackroomsLevel();
    public static final BackroomsLevel OVERWORLD_REPRESENTING_BACKROOMS_LEVEL = new OverworldRepresentingBackroomsLevel();
    public static final BackroomsLevel LEVEL324_BACKROOMS_LEVEL = new Level324Backroomslevel();

    public static List<BackroomsLevel> BACKROOMS_LEVELS = new ArrayList<>();

    public static void init() {
        BACKROOMS_LEVELS.add(LEVEL0_BACKROOMS_LEVEL);
        BACKROOMS_LEVELS.add(LEVEL1_BACKROOMS_LEVEL);
        BACKROOMS_LEVELS.add(LEVEL2_BACKROOMS_LEVEL);
        BACKROOMS_LEVELS.add(POOLROOMS_BACKROOMS_LEVEL);
        BACKROOMS_LEVELS.add(INFINITE_FIELD_BACKROOMS_LEVEL);
        BACKROOMS_LEVELS.add(OVERWORLD_REPRESENTING_BACKROOMS_LEVEL);
//        BACKROOMS_LEVELS.add(LEVEL324_BACKROOMS_LEVEL);

        for (BackroomsLevel backroomsLevel : BACKROOMS_LEVELS) {
            backroomsLevel.register();
        }
    }

    public static boolean isInBackroomsLevel(World world, BackroomsLevel level) {
        return getLevel(world).map(backroomsLevel -> backroomsLevel.equals(level)).orElse(false);
    }

    public static Optional<BackroomsLevel> getLevel(World world) {
        for (BackroomsLevel backroomsLevel : BACKROOMS_LEVELS) {
            if (backroomsLevel.getWorldKey().equals(world.getRegistryKey())) {
                return Optional.of(backroomsLevel);
            }
        }

        return Optional.empty();
    }

    public static boolean isInBackrooms(RegistryKey<World> world){
        return BACKROOMS_LEVELS.stream().anyMatch(level -> level.getWorldKey().equals(world) && !(level instanceof WorldRepresentingBackroomsLevel));
    }

    public static Vec3d getCurrentLevelsOrigin(RegistryKey<World> world) {
        for (BackroomsLevel backroomsLevel : BACKROOMS_LEVELS) {
            if (backroomsLevel.getWorldKey().equals(world)) {
                return backroomsLevel.getSpawnPos();
            }
        }

        return null;
    }

    public static Map<String, RegistryKey<World>> definitions = Map.of(
            "LEVEL0",         LEVEL0_WORLD_KEY,
            "LEVEL1",         LEVEL1_WORLD_KEY,
            "LEVEL2",         LEVEL2_WORLD_KEY,
//            "LEVEL324",       LEVEL324_WORLD_KEY,
            "POOLROOMS",      POOLROOMS_WORLD_KEY,
            "INFINITE_FIELD", INFINITE_FIELD_WORLD_KEY
    );

    public static Optional<BackroomsLevel> getById(String levelId) {
        for (BackroomsLevel backroomsLevel : BACKROOMS_LEVELS) {
            if (backroomsLevel.getLevelId().equals(levelId)) {
                return Optional.of(backroomsLevel);
            }
        }

        return Optional.empty();
    }
}
