package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemIconDumper extends AbstractCraftingGuideDumper {

    public ItemIconDumper(CraftingGuideFileManager fileManager) {
        super(fileManager);
    }

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack) {
        Map<String, List<ItemModel>> itemsByMod = modPack.getItemsByMod();
        for (String modId : itemsByMod.keySet()) {
            ModModel mod = modPack.getMod(modId);
            this.dumpMod(mod, itemsByMod.get(modId));
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static boolean checkMatrixStack() {
        return MODELVIEW_DEPTH < 0 || GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH) == MODELVIEW_DEPTH;
    }

    private static void restoreMatrixStack() {
        if (MODELVIEW_DEPTH >= 0) {
            for (int i = GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH); i > MODELVIEW_DEPTH; i--) {
                GL11.glPopMatrix();
            }
        }
    }

    private static Dimension getScreenSize() {
        Minecraft minecraft = Minecraft.getMinecraft();
        return new Dimension(minecraft.displayWidth, minecraft.displayHeight);
    }

    private static boolean isDrawing() {
        try {
            Field isDrawingField = Tessellator.class.getField("isDrawing");
            isDrawingField.setAccessible(true);
            return isDrawingField.getBoolean(Tessellator.instance);
        } catch (Throwable t) {}

        return false;
    }

    static {
        try {
            Field isDrawingField = Tessellator.class.getField("isDrawing");
            isDrawingField.setAccessible(true);
        } catch (Throwable t) {}
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int ICON_SIZE = 48; // must be first

    private static int BORDER_SIZE = 3;

    private static int BOX_SIZE = ICON_SIZE + 2 * BORDER_SIZE;

    private static RenderItem DRAW_ITEMS = new RenderItem();

    private static int MODELVIEW_DEPTH = -1;

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private BufferedImage captureScreenshot() {
        IntBuffer pixelBuffer = null;
        int[] pixelValues = null;

        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2buDUjx
        // BEGIN NEI

        Framebuffer fb = Minecraft.getMinecraft().getFramebuffer();
        Dimension mcSize = getScreenSize();
        Dimension texSize = mcSize;

        if (OpenGlHelper.isFramebufferEnabled()) texSize = new Dimension(fb.framebufferTextureWidth,
            fb.framebufferTextureHeight);

        int k = texSize.width * texSize.height;
        if (pixelBuffer == null || pixelBuffer.capacity() < k) {
            pixelBuffer = BufferUtils.createIntBuffer(k);
            pixelValues = new int[k];
        }

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        pixelBuffer.clear();

        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        } else {
            GL11.glReadPixels(0, 0, texSize.width, texSize.height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
                pixelBuffer);
        }

        pixelBuffer.get(pixelValues);
        TextureUtil.func_147953_a(pixelValues, texSize.width, texSize.height);

        BufferedImage img = new BufferedImage(mcSize.width, mcSize.height, BufferedImage.TYPE_INT_ARGB);
        if (OpenGlHelper.isFramebufferEnabled()) {
            int yOff = texSize.height - mcSize.height;
            for (int y = 0; y < mcSize.height; ++y)
                for (int x = 0; x < mcSize.width; ++x)
                    img.setRGB(x, y, pixelValues[(y + yOff) * texSize.width + x]);
        } else {
            img.setRGB(0, 0, texSize.width, mcSize.height, pixelValues, 0, texSize.width);
        }

        return img;

        // END NEI
    }

    private void drawItem(ItemModel item, int i, int j) {
        FontRenderer fontRenderer = item.rawStack.getItem().getFontRenderer(item.rawStack);
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
        ItemStack itemStack = item.rawStack;
        List<String> stackTraces = new ArrayList<String>();

        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2c1dO8z
        // BEGIN NEI

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        float zLevel = DRAW_ITEMS.zLevel += 100F;
        try {
            DRAW_ITEMS.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, itemStack, i, j);
            DRAW_ITEMS.renderItemOverlayIntoGUI(fontRenderer, renderEngine, itemStack, i, j);

            if (!checkMatrixStack()) throw new IllegalStateException("Modelview matrix stack too deep");
            if (isDrawing()) throw new IllegalStateException("Still drawing");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stackTrace = itemStack + sw.toString();
            if (!stackTraces.contains(stackTrace)) {
                System.err.println("Error while rendering: " + itemStack);
                e.printStackTrace();
                stackTraces.add(stackTrace);
            }

            restoreMatrixStack();
            if (isDrawing()) Tessellator.instance.draw();

            DRAW_ITEMS.zLevel = zLevel;
            DRAW_ITEMS.renderItemIntoGUI(fontRenderer, renderEngine, new ItemStack(Blocks.fire), i, j);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        DRAW_ITEMS.zLevel = zLevel - 100;

        // END NEI
    }

    private void drawAllItems(List<ItemModel> items) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Dimension d = new Dimension(minecraft.displayWidth, minecraft.displayHeight);

        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2c0EEy7
        // BEGIN NEI

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, d.width * 16D / ICON_SIZE, d.height * 16D / ICON_SIZE, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        int rows = d.height / BOX_SIZE;
        int cols = d.width / BOX_SIZE;
        int fit = rows * cols;
        int drawIndex = 0;

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1, 1, 1, 1);

        for (int i = 0; drawIndex < items.size() && i < fit; drawIndex++, i++) {
            int x = i % cols * 18;
            int y = i / cols * 18;
            this.drawItem(items.get(drawIndex), x + 1, y + 1);
        }

        GL11.glFlush();

        // END NEI
    }

    private void dumpMod(ModModel mod, List<ItemModel> items) {
        this.drawAllItems(items);
        this.exportAllItems(mod, items);
    }

    private void exportAllItems(ModModel mod, List<ItemModel> items) {
        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2bBhYVd
        // BEGIN NEI

        BufferedImage image = captureScreenshot();

        int rows = image.getHeight() / BOX_SIZE;
        int cols = image.getWidth() / BOX_SIZE;
        int fit = rows * cols;
        int parseIndex = 0;

        for (int i = 0; parseIndex < items.size() && i < fit; parseIndex++, i++) {
            ItemModel item = items.get(parseIndex);

            try {
                int x = i % cols * BOX_SIZE;
                int y = i / cols * BOX_SIZE;

                BufferedImage subImage = image.getSubimage(x + BORDER_SIZE, y + BORDER_SIZE, ICON_SIZE, ICON_SIZE);
                this.writeImage(subImage, mod, item);
            } catch (IOException e) {
                System.err.println("Failed to print image for " + item.displayName);
            }
        }

        // END NEI
    }

    private void writeImage(BufferedImage image, ModModel mod, ItemModel item) throws IOException {
        if (!this.getFileManager().ensureDir(this.getFileManager().getItemDir(mod, item))) return;
        File iconFile = new File(this.getFileManager().getItemIconFile(mod, item));
        ImageIO.write(image, "png", iconFile);
    }
}
