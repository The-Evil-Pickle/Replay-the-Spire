package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import basemod.abstracts.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.WhispersOfEvilPower;
import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class WhispersOfEvil extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Whispers of Evil";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public WhispersOfEvil() {
        super(ID, WhispersOfEvil.NAME, "cards/replay/betaCurse.png", COST, WhispersOfEvil.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF);
        AutoplayField.autoplay.set(this, true);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WhispersOfEvilPower(p, this.magicNumber), this.magicNumber));
    }
    
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return true;
    }
    
    public boolean canUpgrade() {
        return false;
    }
    
    public void upgrade() {
    }
    
    public AbstractCard makeCopy() {
        return new WhispersOfEvil();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = WhispersOfEvil.cardStrings.NAME;
        DESCRIPTION = WhispersOfEvil.cardStrings.DESCRIPTION.replace("Echo", "gremlin:Echo");
    }
}
