package hu.frontrider.arcana.items;

import hu.frontrider.arcana.items.CreatureEnchanter.EnchantmentData;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static hu.frontrider.arcana.ThaumicArcana.TABARCANA;
import static hu.frontrider.arcana.creatureenchant.backend.CEnchantment.*;
import static hu.frontrider.arcana.items.CreatureEnchanter.createEnchantedItem;

public class EnchantmentUpgradePowder extends ItemBase {

    @GameRegistry.ObjectHolder("thaumcraft:warpward")
    static Potion warpWard = null;
    private final int level;


    public EnchantmentUpgradePowder(int level) {
        super();
        this.level = level;
    }


    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null) {
            if (tagCompound.hasKey("creature_enchants")) {
                NBTTagList creature_enchants = (NBTTagList) tagCompound.getTag("creature_enchants");

                creature_enchants.iterator().forEachRemaining((enchant) -> {
                    String name = ((NBTTagCompound) enchant).getString("name");
                    int level = ((NBTTagCompound) enchant).getInteger("level");
                    tooltip.add(I18n.format("enchant.creature_enchant." + name.toLowerCase()) + " " + level);
                });
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        return (tagCompound != null) && (tagCompound.hasKey("creature_enchants"));
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == TABARCANA) {
            items.add(new ItemStack(this));

            items.add(createEnchantedItem(this, new EnchantmentData(FERTILE, level)));
            items.add(createEnchantedItem(this, new EnchantmentData(RESPIRATION, level)));
            items.add(createEnchantedItem(this, new EnchantmentData(STRENGTH, level)));
            items.add(createEnchantedItem(this, new EnchantmentData(PROTECTION, level)));

        }
    }

    public void transferEnchants(ItemStack from, ItemStack to) {
        if (from.hasTagCompound()) {
            NBTTagCompound tagCompound = from.getTagCompound();
            to.setTagCompound(tagCompound);
        }
    }

}