package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.*;

public class ReflectiveCoating extends AbstractPotion
{
    public static final String POTION_ID = "Reflective Coating";
    private static final PotionStrings potionStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ReflectiveCoating() {
        super(ReflectiveCoating.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.GREEN);
        this.potency = this.getPotency();
        this.description = ReflectiveCoating.DESCRIPTIONS[0] + this.potency + ReflectiveCoating.DESCRIPTIONS[1] + this.potency + ReflectiveCoating.DESCRIPTIONS[2];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]), GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));
    }
    @Override
    public int getPotency(final int ascensionLevel) {
        return 3;//(ascensionLevel < 11) ? 5 : 2;
    }
    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new BlurPower(target, this.potency), this.potency));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new ReflectionPower(target, this.potency), this.potency));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ReflectiveCoating();
    }
    
    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
        NAME = ReflectiveCoating.potionStrings.NAME;
        DESCRIPTIONS = ReflectiveCoating.potionStrings.DESCRIPTIONS;
    }
	
}