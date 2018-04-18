package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class PotOfGreed extends AbstractCard
{
    public static final String ID = "Pot Of Greed";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int DRAW_AMT = 2;
    private static final int UPG_DRAW_AMT = 1;
    private static final int POOL = 1;
	public static boolean playedThisTurn = false;
	public boolean copyPlayedThisTurn = false;
    
    public PotOfGreed() {
        super("Pot Of Greed", PotOfGreed.NAME, "status/beta", "status/beta", 0, PotOfGreed.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF, 1);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		//this.copyPlayedThisTurn = true;
		if (!this.upgraded)
		{
			PotOfGreed.playedThisTurn = true;
		}
    }
    
	@Override
	public void atTurnStart() {
		this.copyPlayedThisTurn = false;
		PotOfGreed.playedThisTurn = false;
	}
	
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        final boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (PotOfGreed.playedThisTurn) {
            this.cantUseMessage = PotOfGreed.EXTENDED_DESCRIPTION[0];
            return false;
        } else if (this.copyPlayedThisTurn) {
			this.cantUseMessage = PotOfGreed.EXTENDED_DESCRIPTION[1];
            return false;
		}
        return canUse;
    }
	
    @Override
    public AbstractCard makeCopy() {
        return new PotOfGreed();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(1);
            this.rawDescription = PotOfGreed.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pot Of Greed");
        NAME = PotOfGreed.cardStrings.NAME;
        DESCRIPTION = PotOfGreed.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = PotOfGreed.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = PotOfGreed.cardStrings.EXTENDED_DESCRIPTION;
    }
}
