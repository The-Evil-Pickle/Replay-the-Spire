package com.megacrit.cardcrawl.mod.replay.potions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionColor;
//import com.megacrit.cardcrawl.potions.PotionRarity;
//import com.megacrit.cardcrawl.potions.PotionSize;

public class FlashbangPotion extends AbstractPotion
{
    public static final String POTION_ID = "Flashbang";
    private static final PotionStrings potionStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public FlashbangPotion() {
        super(FlashbangPotion.NAME, POTION_ID, PotionRarity.RARE, PotionSize.T, PotionColor.GREEN);
        this.potency = this.getPotency();
        this.description = FlashbangPotion.DESCRIPTIONS[0];
        this.targetRequired = true;
        if (this.potency > 1) {
        	this.description = FlashbangPotion.DESCRIPTIONS[1];
            this.targetRequired = false;
        }
        this.isThrown = true;
        this.tips.add(new PowerTip(this.name, this.description));
    }
    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;//(ascensionLevel < 11) ? 5 : 2;
    }
    @Override
    public void use(AbstractCreature target) {
        if (this.potency > 1) {
        	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
        		if (!m.isDeadOrEscaped()) {
    				AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, AbstractDungeon.player));
        		}
    	    }
        } else {
        	AbstractDungeon.actionManager.addToBottom(new StunMonsterAction((AbstractMonster) target, AbstractDungeon.player));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new FlashbangPotion();
    }
    
    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
        NAME = FlashbangPotion.potionStrings.NAME;
        DESCRIPTIONS = FlashbangPotion.potionStrings.DESCRIPTIONS;
    }
	
}