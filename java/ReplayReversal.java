package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class ReplayReversal extends CustomCard
{
    public static final String ID = "ReplayReversal";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public ReplayReversal() {
        super("ReplayReversal", ReplayReversal.NAME, "cards/replay/betaSkill.png", ReplayReversal.COST, ReplayReversal.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, Math.max(1,this.magicNumber - 1)), Math.max(1,this.magicNumber - 1)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReplayReversal(this.bluramt);
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
			this.rawDescription = ReplayReversal.EXTENDED_DESCRIPTION[0] + Math.max(1,this.magicNumber - 1) + ReplayReversal.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReplayReversal");
        NAME = ReplayReversal.cardStrings.NAME;
        DESCRIPTION = ReplayReversal.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayReversal.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = ReplayReversal.cardStrings.EXTENDED_DESCRIPTION;
    }
}
