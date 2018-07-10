package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;
import replayTheSpire.*;

public class MilkshakePotion extends AbstractPotion
{
    public static final String POTION_ID = "Milkshake";
    private static final PotionStrings potionStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public MilkshakePotion() {
        super(MilkshakePotion.NAME, "Milkshake", PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.GREEN);
        this.potency = this.getPotency();
        this.description = MilkshakePotion.DESCRIPTIONS[0] + this.potency + MilkshakePotion.DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]), GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));
    }
    @Override
    public int getPotency(final int ascensionLevel) {
        return 5;//(ascensionLevel < 11) ? 5 : 2;
    }
    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new RetainSomeEnergyPower(target, this.potency), this.potency));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new MilkshakePotion();
    }
    
    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Milkshake");
        NAME = MilkshakePotion.potionStrings.NAME;
        DESCRIPTIONS = MilkshakePotion.potionStrings.DESCRIPTIONS;
    }
	
}