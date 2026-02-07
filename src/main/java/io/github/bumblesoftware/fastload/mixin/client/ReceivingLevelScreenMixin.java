package io.github.bumblesoftware.fastload.mixin.client;

import io.github.bumblesoftware.fastload.util.obj_holders.MutableObjectHolder;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static io.github.bumblesoftware.fastload.client.FLClientEvents.Locations.DTS_TICK;
import static io.github.bumblesoftware.fastload.common.FLCommonEvents.Events.BOOLEAN_EVENT;


@Mixin(ReceivingLevelScreen.class)
public abstract class ReceivingLevelScreenMixin {
    @Accessor("oneTickSkipped")
    public abstract boolean isOneTickSkipped();

    @Accessor("oneTickSkipped")
    public abstract void setOneTickSkipped(boolean value);

    @Inject(at = @At("HEAD"), method = "loadingPacketsReceived")
    public void tick(final CallbackInfo ci) {
        final var returnValue = new MutableObjectHolder<>(isOneTickSkipped());
        if (BOOLEAN_EVENT.isNotEmpty(DTS_TICK))
                BOOLEAN_EVENT.execute(List.of(DTS_TICK), true, returnValue);
        setOneTickSkipped(returnValue.getHeldObj());
    }
}
