package net.thegulag.packetmanipulator.mixin;

import net.thegulag.packetmanipulator.PacketManipulator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class MixinChatScreen {

    @Inject(at = @At("HEAD"), method = "sendMessage", cancellable = true)
    public void hookToggleCommand(String chatText, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        if (PacketManipulator.handleChatMessages(chatText)) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(chatText);
            cir.setReturnValue(true);
        }
    }
}
