/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [21/02/2016, 16:34:43 (GMT)]
 */
package vazkii.psi.common.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.arl.interf.IItemColorProvider;
import vazkii.arl.item.BasicItem;
import vazkii.psi.api.cad.ICADColorizer;
import vazkii.psi.api.exosuit.IExosuitSensor;
import vazkii.psi.api.exosuit.PsiArmorEvent;
import vazkii.psi.common.crafting.recipe.SensorAttachRecipe;
import vazkii.psi.common.crafting.recipe.SensorRemoveRecipe;

public abstract class ItemExosuitSensor extends BasicItem implements IExosuitSensor, IItemColorProvider {

    // This should be modifiable, for the purposes of cosmetic addons like Magical Psi.
    public static int defaultColor = ICADColorizer.DEFAULT_SPELL_COLOR;
    public static int lightColor = 0xFFEC13;
    public static int underwaterColor = 0x1350FF;
    public static int fireColor = 0xFF1E13;
    public static int lowHealthColor = 0xFF8CC5;

    public ItemExosuitSensor(String name, Item.Properties properties) {
        super(name, properties.maxStackSize(1));

        new SensorAttachRecipe();
        new SensorRemoveRecipe();
    }

    @Override
    public String getEventType(ItemStack stack) {
        return PsiArmorEvent.NONE;
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getColor(ItemStack stack) {
        return defaultColor;
    }
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public IItemColor getItemColor() {
		return (stack, tintIndex) -> tintIndex == 1 ? getColor(stack) : 0xFFFFFF;
	}

}
