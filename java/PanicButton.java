package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class PanicButton extends CustomCard
{
    public static final String ID = "Panic Button";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public PanicButton() {
        super("Panic Button", PanicButton.NAME, "cards/replay/replayBetaSkill.png", PanicButton.COST, PanicButton.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.showEvokeValue = true;
		this.exhaust = true;
		this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.retain = true;
    }
    
    @Override
    public void applyPowers() {
		super.applyPowers();
        this.retain = true;
    }
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final int orbCount = p.filledOrbCount();
		for (int i = 0; i < orbCount; ++i) {
			AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
			AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
		}
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new PanicButton();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
			this.upgradeMagicNumber(1);
            this.upgradeName();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Panic Button");
        NAME = PanicButton.cardStrings.NAME;
        DESCRIPTION = PanicButton.cardStrings.DESCRIPTION;
    }
}
