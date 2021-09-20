package me.eddie.testing.inventoryguiapi;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.*;

/**
 * Implementation of ItemMeta to use for testing (As the API doesn't provide an implementation)
 */
public class FakeItemMeta implements ItemMeta {
    private String displayName = null;
    private String localizedName = null;
    private List<String> lore = new ArrayList<String>();
    private Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
    private List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
    private boolean unbreakable = false;
    private int customModelData = -1;
    private Multimap<Attribute, AttributeModifier> attributeModifiers = HashMultimap.create();

    public FakeItemMeta(){

    }

    public FakeItemMeta(ItemMeta im){
        this.displayName = im.getDisplayName();
        this.lore = new ArrayList<String>();
        if(im.hasLore()){
            this.lore.addAll(im.getLore());
        }
        this.enchants = new HashMap<Enchantment, Integer>();
        if(im.hasEnchants()){
            this.enchants.putAll(im.getEnchants());
        }
        this.itemFlags = new ArrayList<ItemFlag>();
        this.itemFlags.addAll(im.getItemFlags());
        this.unbreakable = unbreakable;
    }

    public FakeItemMeta(String displayName, List<String> lore, Map<Enchantment, Integer> enchants, List<ItemFlag> flags, boolean unbreakable){
        this.displayName = displayName;
        this.lore = new ArrayList<String>(lore);
        this.enchants = new HashMap<Enchantment, Integer>(enchants);
        this.itemFlags = new ArrayList<ItemFlag>(flags);
        this.unbreakable = unbreakable;
    }

    @Override
    public boolean hasDisplayName() {
        return displayName != null;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public boolean hasLocalizedName() {
        return localizedName != null;
    }

    @Override
    public String getLocalizedName() {
        return localizedName;
    }

    @Override
    public void setLocalizedName( String name ) {
        this.localizedName = name;
    }

    @Override
    public boolean hasLore() {
        return this.lore != null && this.lore.size() > 0;
    }

    @Override
    public List<String> getLore() {
        return Collections.unmodifiableList(this.lore);
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore = new ArrayList<String>(lore);
    }

    @Override
    public boolean hasCustomModelData() {
        return customModelData != -1;
    }

    @Override
    public int getCustomModelData() {
        return customModelData;
    }

    @Override
    public void setCustomModelData(Integer data) {
        this.customModelData = data;
    }

    @Override
    public boolean hasEnchants() {
        return enchants.size() > 0;
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        return enchants.containsKey(ench);
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        if(!hasEnchant(ench)){
            return 0;
        }
        return enchants.get(ench);
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return new HashMap<Enchantment, Integer>(enchants);
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        enchants.put(ench, level);
        return true;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        enchants.remove(ench);
        return true;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.removeAll(Arrays.asList(itemFlags));
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return new HashSet<ItemFlag>(itemFlags);
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        for(ItemFlag itemFlag:itemFlags){
            if(itemFlag.equals(flag)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    @Override
    public boolean hasAttributeModifiers() {
        return !this.attributeModifiers.isEmpty();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return this.attributeModifiers;
    }

    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute) {
        return attributeModifiers.get(attribute);
    }

    @Override
    public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        return this.attributeModifiers.put(attribute, modifier);
    }

    @Override
    public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
    }

    @Override
    public boolean removeAttributeModifier(Attribute attribute) {
        return !attributeModifiers.removeAll(attribute).isEmpty();
    }

    @Override
    public boolean removeAttributeModifier(EquipmentSlot slot) {
        return false;
    }

    @Override
    public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        return attributeModifiers.remove(attribute, modifier);
    }

    @Override
    public CustomItemTagContainer getCustomTagContainer() {
        return null;
    }

    @Override
    public void setVersion( int version ) {

    }

    @Override
    public ItemMeta clone() {
        return new FakeItemMeta(displayName, lore, enchants, itemFlags, unbreakable);
    }

    @Override
    public Spigot spigot() {
        return new ItemMeta.Spigot(){
        };
    }

    @Override
    public Map<String, Object> serialize() {
        //Not used anywhere, so no point implementing
        return null;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return null;
    }
}
