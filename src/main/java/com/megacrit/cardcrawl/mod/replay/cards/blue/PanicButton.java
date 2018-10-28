package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class PanicButton extends CustomCard
{
    public static final String ID = "Panic Button";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public PanicButton() {
        super("Panic Button", PanicButton.NAME, "cards/replay/panicButton.png", PanicButton.COST, PanicButton.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.NONE);
        this.showEvokeValue = true;
		this.exhaust = true;
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.retain = true;
        AlwaysRetainField.alwaysRetain.set(this, true);
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
            this.rawDescription = PanicButton.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Panic Button");
        NAME = PanicButton.cardStrings.NAME;
        DESCRIPTION = PanicButton.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = PanicButton.cardStrings.UPGRADE_DESCRIPTION;
    }
}
