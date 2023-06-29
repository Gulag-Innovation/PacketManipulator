package net.thegulag.packetmanipulator.mixin;

import net.thegulag.packetmanipulator.PacketManipulator;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Inject(method = "sendImmediately", at = @At("HEAD"), cancellable = true)
    public void hookExploitCancels(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) {
        if (PacketManipulator.shouldCancel(packet)) ci.cancel();
    }
}
