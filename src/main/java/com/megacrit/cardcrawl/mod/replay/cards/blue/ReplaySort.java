package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class ReplaySort extends CustomCard
{
    public static final String ID = "ReplaySort";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    
    public ReplaySort() {
        super("ReplaySort", ReplaySort.NAME, "cards/replay/replayBetaSkill.png", ReplaySort.COST, ReplaySort.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
		this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void applyPowers() {
		super.applyPowers();
		if (this.upgraded) {
			this.retain = true;
		}
    }
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	final ChooseAction choice = new ChooseAction((AbstractCard)this, m, EXTENDED_DESCRIPTION[0]);
    	choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ShuffleRareAction());
    		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ShuffleNonStatusAction());
    		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
            return;
        });
    	AbstractDungeon.actionManager.addToBottom(choice);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReplaySort();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
			//this.upgradeMagicNumber(1);
            this.upgradeName();
            this.retain = true;
            this.rawDescription = ReplaySort.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReplaySort");
        NAME = ReplaySort.cardStrings.NAME;
        DESCRIPTION = ReplaySort.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplaySort.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = ReplaySort.cardStrings.EXTENDED_DESCRIPTION;
    }
}
