package net.chance.cavernsofchaos.sound;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Cavernsofchaos.MODID);


    public static final RegistryObject<SoundEvent> SLOT_WIN_BIG = registerSoundEvents("slotwinbig");
    public static final RegistryObject<SoundEvent> SLOT_WIN_SMALL = registerSoundEvents("slotwinsmall");
    public static final RegistryObject<SoundEvent> SLOT_LOSE = registerSoundEvents("slotlose");
    public static final RegistryObject<SoundEvent> SLOT_LOSE_BAD = registerSoundEvents("slotlosebad");
    public static final RegistryObject<SoundEvent> SLOT_LOSE_CRIT = registerSoundEvents("slotlosecrit");
    public static final RegistryObject<SoundEvent> INSERT_COIN = registerSoundEvents("insertcoin");
    public static final RegistryObject<SoundEvent> CRUMBLE = registerSoundEvents("crumble");
    public static final RegistryObject<SoundEvent> BASS = registerSoundEvents("bass");
    public static final RegistryObject<SoundEvent> MIMIC_HURT = registerSoundEvents("mimichurt");
    public static final RegistryObject<SoundEvent> MIMIC_DEATH = registerSoundEvents("mimicdeath");
    public static final RegistryObject<SoundEvent> MIMIC_AMBIENT = registerSoundEvents("mimicambient");
    public static final RegistryObject<SoundEvent> MIMIC_JUMPSCARE = registerSoundEvents("mimicjumpscare");
    public static final RegistryObject<SoundEvent> SCRAPE = registerSoundEvents("scrape");
    public static final RegistryObject<SoundEvent> TRAPSET = registerSoundEvents("trapset");
    public static final RegistryObject<SoundEvent> TRAPSNAP = registerSoundEvents("trapsnap");




    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Cavernsofchaos.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
