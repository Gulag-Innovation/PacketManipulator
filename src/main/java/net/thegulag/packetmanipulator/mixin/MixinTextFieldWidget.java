package net.thegulag.packetmanipulator.mixin;

import net.thegulag.packetmanipulator.util.ITextFieldAdapter;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public abstract class MixinTextFieldWidget extends ClickableWidget implements ITextFieldAdapter {

    @Shadow @Final private TextRenderer textRenderer;
    @Unique
    private Text sideInformation;

    public MixinTextFieldWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderButton", at = @At("RETURN"))
    public void hookCustomSideInformation(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.sideInformation != null)
            context.drawTextWithShadow(textRenderer, this.sideInformation, this.getX() - textRenderer.getWidth(this.sideInformation.getString()) - 10, this.getY() + this.getHeight() / 4, -1);
    }

    @Override
    public void setSideInformation(String information) {
        this.sideInformation = Text.literal(information);
    }
}
