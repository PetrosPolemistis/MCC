package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButton extends Gui
{
    protected static final ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
    protected int x_pos;
    protected int y_pos;
    public int width;
    public int height;

    /** The string displayed on this control. */
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hoverState;
    private static final String __OBFID = "CL_00000668";

    public GuiButton(int p_i1020_1_/*ID*/, int p_i1020_2_/*width*/, int p_i1020_3_/*height*/, String p_i1020_4_/*display-string*/)
    {
        this(p_i1020_1_, p_i1020_2_, p_i1020_3_, 200, 20, p_i1020_4_);
    }

    public GuiButton(int p_i1021_1_/*button ID*/, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_/*x_pos*/, int p_i1021_5_/*y_pos*/, String p_i1021_6_/*display string*/)
    {
        this.x_pos = 200;
        this.y_pos = 20;
        this.enabled = true;
        this.visible = true;
        this.id = p_i1021_1_;
        this.width = p_i1021_2_;
        this.height = p_i1021_3_;
        this.x_pos = p_i1021_4_;
        this.y_pos = p_i1021_5_;
        this.displayString = p_i1021_6_;
    }

    public int getHoverState(boolean p_146114_1_)
    {
        byte var2 = 1;

        if (!this.enabled)
        {
            var2 = 0;
        }
        else if (p_146114_1_)
        {
            var2 = 2;
        }

        return var2;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX/*x?*/, int mouseY/*y?*/)
    {
        if (this.visible)
        {
            FontRenderer font_renderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(texture);
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            this.hoverState = mouseX >= this.width && mouseY >= this.height && mouseX < this.width + this.x_pos && mouseY < this.height + this.y_pos;
            int hover_value = this.getHoverState(this.hoverState);
            
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            this.drawTexturedModalRect(this.width, this.height, 0, 46 + hover_value * 20, this.x_pos / 2, this.y_pos);
            this.drawTexturedModalRect(this.width + this.x_pos / 2, this.height, 200 - this.x_pos / 2, 46 + hover_value * 20, this.x_pos / 2, this.y_pos);
            
            this.mouseDragged(mc, mouseX, mouseY);
            
            int code = 14737632;

            if (!this.enabled)
            {
                code = 10526880;
            }
            else if (this.hoverState)
            {
                code = 16777120;
            }

            this.drawCenteredString(font_renderer, this.displayString, this.width + this.x_pos / 2, this.height + (this.y_pos - 8) / 2, code);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {}

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int p_146118_1_, int p_146118_2_) {}

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {
        return this.enabled && this.visible && p_146116_2_ >= this.width && p_146116_3_ >= this.height && p_146116_2_ < this.width + this.x_pos && p_146116_3_ < this.height + this.y_pos;
    }
    
    //TODO
    /**
     * Returns mouse hover state.
     * */
    public boolean getHoverState()
    {
        return this.hoverState;
    }

    public void func_146111_b(int p_146111_1_, int p_146111_2_) {}

    public void func_146113_a(SoundHandler p_146113_1_)
    {
        p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getXpos()
    {
        return this.x_pos;
    }

    public int getYpos()
    {
        return this.y_pos;
    }
}
