package net.thegulag.packetmanipulator.mixin;

import net.thegulag.packetmanipulator.PacketManipulator;
import net.thegulag.packetmanipulator.util.Side;
import net.thegulag.packetmanipulator.widget.ExploitButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Shadow protected abstract <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement);

    @Shadow public int width;

    @Shadow public abstract List<? extends Element> children();

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("RETURN"))
    public void hookFeatureButtons(MinecraftClient client, int width, int height, CallbackInfo ci) {
        if (!PacketManipulator.isEnabled()) return;

        final List<ExploitButtonWidget> buttons = PacketManipulator.fromScreen((Screen) (Object) this);

        if (buttons.isEmpty()) return;

        int leftHeight = 0;
        int rightHeight = 0;
        for (ExploitButtonWidget next : buttons) {
            next.setX(next.getSide() == Side.LEFT ? PacketManipulator.BOUND : this.width - next.getWidth() - PacketManipulator.BOUND);
            next.setY(PacketManipulator.BOUND + (next.getSide() == Side.LEFT ? leftHeight : rightHeight));

            this.addDrawableChild(next);
            if (next.getSide() == Side.LEFT)
                leftHeight += PacketManipulator.BUTTON_DIFF;
            else
                rightHeight += PacketManipulator.BUTTON_DIFF;
        }
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void hideFeatureButtons(CallbackInfo ci) {
        if (PacketManipulator.isEnabled()) return;

        for (Element child : children()) {
            if (child instanceof ExploitButtonWidget buttonWidget) {
                buttonWidget.visible = false;
            }
        }
    }
}
