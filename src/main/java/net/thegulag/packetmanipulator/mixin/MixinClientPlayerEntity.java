package net.thegulag.packetmanipulator.mixin;

import net.thegulag.packetmanipulator.PacketManipulator;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "tick", at = @At("RETURN"))
    public void tickShulkerDupe(CallbackInfo ci) {
        PacketManipulator.tickShulkerDupe();
    }
}
