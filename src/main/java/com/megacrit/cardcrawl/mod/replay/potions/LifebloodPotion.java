package com.megacrit.cardcrawl.mod.replay.potions;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionColor;
//import com.megacrit.cardcrawl.potions.PotionRarity;
//import com.megacrit.cardcrawl.potions.PotionSize;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.core.*;

public class LifebloodPotion extends AbstractPotion
{
    public static final String POTION_ID = "LifebloodPotion";
    private static final PotionStrings potionStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public LifebloodPotion() {
        super(LifebloodPotion.NAME, LifebloodPotion.POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.BLUE);
        this.potency = this.getPotency();
        this.description = LifebloodPotion.DESCRIPTIONS[0] + this.potency + LifebloodPotion.DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize("temporary"), GameDictionary.keywords.get("temporary_hp")));
    }
    
    @Override
    public void use(final AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new LifebloodPotion();
    }
    
    @Override
    public int getPotency(final int ascensionLevel) {
        return 12;
    }
    
    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString(LifebloodPotion.POTION_ID);
        NAME = LifebloodPotion.potionStrings.NAME;
        DESCRIPTIONS = LifebloodPotion.potionStrings.DESCRIPTIONS;
    }
}
