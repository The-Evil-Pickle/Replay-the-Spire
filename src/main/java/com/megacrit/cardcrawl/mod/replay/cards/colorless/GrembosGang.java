package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.BasicFromDeckToHandAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.helpers.BasePlayerMinionHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import replayTheSpire.minions.grembo.*;

public class GrembosGang extends CustomCard
{
    public static final String ID = "GremboGang";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -1;
    private static final int NUM_BOIS = 2;
    
    public GrembosGang() {
        super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = NUM_BOIS;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ActionGremboSummon(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    }
    
    public static AbstractFriendlyMonster GetRandomGremboi(int dur) {
    	AbstractFriendlyMonster gremboi = null;
    	switch(AbstractDungeon.miscRng.random(0, 3)) {
    	case 0:
    		gremboi = new GremboWizard((dur <= 1));
    		break;
    	case 1:
    		gremboi = new GremboFat();
    		break;
    	case 2:
    		gremboi = new GremboTsundere();
    		break;
    	default:
    		gremboi = new GremboThief();
    		break;
    	}
    	//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(gremboi, gremboi, new CowardicePower(gremboi, dur * 2)));
    	return gremboi;
    }
    
	@Override
	public void triggerOnEndOfPlayerTurn()
	{
		AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
	}
	
    @Override
    public AbstractCard makeCopy() {
        return new GrembosGang();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
