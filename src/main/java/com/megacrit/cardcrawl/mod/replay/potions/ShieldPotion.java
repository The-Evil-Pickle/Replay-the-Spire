package com.megacrit.cardcrawl.mod.replay.potions;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionColor;
//import com.megacrit.cardcrawl.potions.PotionRarity;
//import com.megacrit.cardcrawl.potions.PotionSize;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class ShieldPotion extends AbstractPotion
{
    public static final String POTION_ID = "ReplayShieldPotion";
    private static final PotionStrings potionStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ShieldPotion() {
        super(ShieldPotion.NAME, ShieldPotion.POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.BLUE);
        this.potency = this.getPotency();
        this.description = ShieldPotion.DESCRIPTIONS[0] + this.potency + ShieldPotion.DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip("Shielding", "An alternate form of #yBlock that can directly block HP loss. NL #yShielding is not lost at the end of each round."));
        this.tips.add(new PowerTip(TipHelper.capitalize("shielding"), GameDictionary.keywords.get("shielding")));
    }
    
    @Override
    public void use(final AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ShieldPotion();
    }
    
    @Override
    public int getPotency(final int ascensionLevel) {
        return 12;
    }
    
    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString(ShieldPotion.POTION_ID);
        NAME = ShieldPotion.potionStrings.NAME;
        DESCRIPTIONS = ShieldPotion.potionStrings.DESCRIPTIONS;
    }
}
