package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemIconDumperScreen extends GuiScreen {

    public ItemIconDumperScreen(ImageConsumer imageConsumer) {
        this.setImageConsumer(imageConsumer);
        this.allowUserInput = false;

        MinecraftForge.EVENT_BUS.register(this);
    }

    // Helper Interfaces ///////////////////////////////////////////////////////////////////////////////////////////////

    @FunctionalInterface
    public interface ImageConsumer {

        public void accept(ModModel mod, ItemModel item, BufferedImage image);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void dumpItems(ModPackModel modPack, Map<ModModel, Collection<ItemModel>> items, AsyncStep dumpItemsStep, boolean modIcons) {
        if (this.isExporting()) throw new IllegalStateException("already exporting!");
        this.setIsExporting(true);

        if (items.isEmpty()) {
            this.setIsExporting(false);
            dumpItemsStep.done();
        }

        this.setStep(dumpItemsStep);
        this.setImageConsumer(imageConsumer);
        this.setItemsMap(items);
        this.setModPack(modPack);

        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    @SubscribeEvent
    public void onGuiScreenOpened(GuiOpenEvent event) {
        if (!this.isExporting()) return;

        // don't allow any other GUI screens to interrupt
        if ((event.gui == null) || (!event.gui.getClass().equals(ItemIconDumperScreen.class))) {
            event.setCanceled(true);
        }
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    private int getBorderSize() {
        return this.iconSize / 16;
    }

    private int getBoxSize() {
        return this.iconSize + 2 * this.getBorderSize();
    }

    public boolean isExporting() {
        return this.isExporting;
    }

    private void setIsExporting(boolean isExportComplete) {
        this.isExporting = isExportComplete;
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = (iconSize < MIN_ICON_SIZE) ? MIN_ICON_SIZE : iconSize;
    }

    public ImageConsumer getImageConsumer() {
        return this.imageConsumer;
    }

    public void setImageConsumer(ImageConsumer imageConsumer) {
        if (imageConsumer == null) throw new IllegalArgumentException("imageConsumer cannot be null");
        this.imageConsumer = imageConsumer;
    }

    private void setItemsMap(Map<ModModel, Collection<ItemModel>> newItemsMap) {
        if (newItemsMap == null) {
            newItemsMap = new HashMap<ModModel, Collection<ItemModel>>();
        }
        this.itemsMap = new HashMap<ModModel, Collection<ItemModel>>(newItemsMap);
    }
    
    private void setItems(Collection<ItemModel> newItems) {
        if (newItems == null) {
            newItems = new LinkedList<ItemModel>();
        }
        this.items = new LinkedList<>(newItems);
    }

    private void setMod(ModModel newMod) {
        if (newMod == null) throw new IllegalArgumentException("newMod cannot be null");
        this.mod = newMod;
    }

    private void setStep(AsyncStep step) {
        if (step == null) throw new IllegalArgumentException("step cannot be null");
        this.step = step;
    }
    
    private void setModPack(ModPackModel modPack) {
        if (modPack == null) throw new IllegalArgumentException("modPack cannot be null");
        this.modPack = modPack;
    }

    // GuiScreen Overrides /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void drawScreen(int x, int y, float frame) {
        for (ModModel mod : this.itemsMap.keySet()) {
            this.setMod(mod);
            this.setItems(this.itemsMap.get(mod));
            try {
                while (!this.items.isEmpty()) {
                    this.drawAllItems();
                    this.exportAllItems();
                }
            } catch (Throwable e) {
                logger.error("Failed to render item icon dump for " + this.mod.getDisplayName(), e);
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
        }
        this.setIsExporting(false);
        Minecraft.getMinecraft().displayGuiScreen(null);
        this.step.done();
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

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

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int MIN_ICON_SIZE = 16; // must be first

    private static RenderItem DRAW_ITEMS = new RenderItem();

    private static int MODELVIEW_DEPTH = -1;

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private BufferedImage captureScreenshot() {

        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2buDUjx
        // BEGIN NEI

        Framebuffer fb = Minecraft.getMinecraft().getFramebuffer();
        Dimension mcSize = getScreenSize();
        Dimension texSize = mcSize;

        if (OpenGlHelper.isFramebufferEnabled()) {
            texSize = new Dimension(fb.framebufferTextureWidth, fb.framebufferTextureHeight);
        }

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
            for (int y = 0; y < mcSize.height; ++y) {
                for (int x = 0; x < mcSize.width; ++x) {
                    img.setRGB(x, y, pixelValues[(y + yOff) * texSize.width + x]);
                }
            }
        } else {
            img.setRGB(0, 0, texSize.width, this.height, pixelValues, 0, texSize.width);
        }

        return img;

        // END NEI
    }

    private void drawAllItems() {
        Minecraft minecraft = Minecraft.getMinecraft();
        Dimension d = new Dimension(minecraft.displayWidth, minecraft.displayHeight);

        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2c0EEy7
        // BEGIN NEI

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, d.width * 16 / this.getIconSize(), d.height * 16 / this.getIconSize(), 0, 1000, 3000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        int rows = d.height / this.getBoxSize();
        int cols = d.width / this.getBoxSize();
        int fit = rows * cols;
        int drawIndex = 0;

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1, 1, 1, 1);

        for (int i = 0; drawIndex < this.items.size() && i < fit; drawIndex++, i++) {
            ItemModel item = this.items.get(drawIndex);
            ItemModel icon = item.hasItemStackIcon() ? this.modPack.getItem(item.getItemStackIcon()) : item.isMultiblock() ? null : item;
            if (icon == null) continue;

            int x = i % cols * 18;
            int y = i / cols * 18;
            this.drawItem(icon, x + 1, y + 1);
        }

        GL11.glFlush();

        // END NEI
    }

    private void drawItem(ItemModel item, int i, int j) {
        if (item.isFluid()) {
            
            FluidStack fluid = item.getRawFluidStack();
            
            // The following has been borrowed from SleepyTrousers' EnderCore code at http://bit.ly/2x2hhCf
            // BEGIN EnderCore
            
            IIcon icon = fluid.getFluid().getStillIcon();
            if (icon == null) {
                icon = fluid.getFluid().getIcon();
                if (icon == null) {
                    return;
                }
            }

            int renderAmount = 15;
            int posY = j;

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            int color = fluid.getFluid().getColor(fluid);
            GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

            GL11.glEnable(GL11.GL_BLEND);
            for (int x = 0; x < 15; x += 16) {
                for (int y = 0; y < renderAmount; y += 16) {
                    int drawWidth = (int) Math.min(15 - x, 16);
                    int drawHeight = Math.min(renderAmount - y, 16);

                    int drawX = (int) (x + i);
                    int drawY = posY + y;

                    double minU = icon.getMinU();
                    double maxU = icon.getMaxU();
                    double minV = icon.getMinV();
                    double maxV = icon.getMaxV();

                    Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(drawX, drawY + drawHeight, 0, minU, minV + (maxV - minV) * drawHeight / 16F);
                    tessellator.addVertexWithUV(drawX + drawWidth, drawY + drawHeight, 0, minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F);
                    tessellator.addVertexWithUV(drawX + drawWidth, drawY, 0, minU + (maxU - minU) * drawWidth / 16F, minV);
                    tessellator.addVertexWithUV(drawX, drawY, 0, minU, minV);
                    tessellator.draw();
                }
            }
            GL11.glDisable(GL11.GL_BLEND);
            
            //END EnderCore
            
        } else {
            FontRenderer fontRenderer = item.getRawItemStack().getItem().getFontRenderer(item.getRawItemStack());
            if (fontRenderer == null) {
                fontRenderer = Minecraft.getMinecraft().fontRenderer;
            }
            TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
            ItemStack itemStack = item.getRawItemStack();
            List<String> stackTraces = new ArrayList<String>();

            // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2c1dO8z
            // BEGIN NEI

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            float zLevel = DRAW_ITEMS.zLevel += 100;

            try {
                // Calling `renderItemAndEffectIntoGUI`, for some reason, fails to draw enchanted items properly (either not
                // drawing them at all, or not drawing certain pieces of them). Calling the simple `renderItemIntoGUI`
                // instead works as expected.
                if (item.isEffectRenderable()) {
                    DRAW_ITEMS.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, itemStack, i, j);
                } else {
                    DRAW_ITEMS.renderItemIntoGUI(fontRenderer, renderEngine, itemStack, i, j);
                }

                // Disabled because, for Crafting Guide, we don't actually want the overlays.
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
    }

    private void exportAllItems() {
        // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2bBhYVd
        // BEGIN NEI

        BufferedImage image = captureScreenshot();

        int borderSize = this.getBorderSize();
        int boxSize = this.getBoxSize();
        int iconSize = this.getIconSize();

        int rows = image.getHeight() / boxSize;
        int cols = image.getWidth() / boxSize;
        int fit = rows * cols;

        for (int i = 0; !this.items.isEmpty() && i < fit; i++) {
            ItemModel item = this.items.removeFirst();

            int x = i % cols * boxSize;
            int y = i / cols * boxSize;

            BufferedImage subImage = image.getSubimage(x + borderSize, y + borderSize, iconSize, iconSize);
            this.getImageConsumer().accept(this.mod, item, subImage);
        }

        // END NEI
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ImageConsumer imageConsumer = null;

    private int iconSize = 48;

    private boolean isExporting = false;

    private Map<ModModel, Collection<ItemModel>> itemsMap = new HashMap<ModModel, Collection<ItemModel>>();
    
    private LinkedList<ItemModel> items = new LinkedList<ItemModel>();

    private ModModel mod = null;

    private IntBuffer pixelBuffer = null;

    private int[] pixelValues = null;

    private AsyncStep step = null;
    
    private ModPackModel modPack = null;
}
