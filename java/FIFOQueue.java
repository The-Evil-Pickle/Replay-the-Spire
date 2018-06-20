package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class FIFOQueue extends CustomCard
{
    public static final String ID = "FIFO Queue";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    
    public FIFOQueue() {
        super("FIFO Queue", FIFOQueue.NAME, "cards/replay/replayBetaPower.png", FIFOQueue.COST, FIFOQueue.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new FIFOQ_Action(p, 2, 1, this.freeToPlayOnce, this.energyOnUse));
		} else {
			AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.magicNumber));
		}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new FIFOQueue();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(1);
            //this.isInnate = true; final int diff = this.cost - this.costForTurn;
			this.cost = -1;
			this.costForTurn = -1;
			this.upgradedCost = true;
            this.rawDescription = FIFOQueue.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FIFO Queue");
        NAME = FIFOQueue.cardStrings.NAME;
        DESCRIPTION = FIFOQueue.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = FIFOQueue.cardStrings.UPGRADE_DESCRIPTION;
    }
}