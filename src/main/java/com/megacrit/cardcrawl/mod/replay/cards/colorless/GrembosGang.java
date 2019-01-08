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
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public GrembosGang() {
        super(ID, NAME, "cards/replay/replayBetaPower.png", COST, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	//AbstractDungeon.actionManager.addToBottom(new ActionGremboSummon(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    	if(p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            AbstractFriendlyMonster grem = GrembosGang.GetRandomGremboi(((AbstractPlayerWithMinions) p).getMinions().monsters.size());
            grem.usePreBattleAction();
            player.addMinion(grem);
        } else {
        	AbstractFriendlyMonster grem = GrembosGang.GetRandomGremboi(0);
        	grem.usePreBattleAction();
        	BasePlayerMinionHelper.addMinion(p, grem);
        }
    }
    
    public static AbstractFriendlyMonster GetRandomGremboi(int dur) {
    	AbstractFriendlyMonster gremboi = null;
    	switch(AbstractDungeon.miscRng.random(1, 4)) {
    	case 0:
    		gremboi = new GremboWizard(false);//this boi doesn't work :rip:
    		break;
    	case 1:
    		gremboi = new GremboFat(dur * 80.0f);
    		break;
    	case 2:
    		gremboi = new GremboTsundere(dur * 80.0f);
    		break;
    	case 3:
    		gremboi = new GremboWarrior(dur * 80.0f);
    		break;
    	default:
    		gremboi = new GremboThief(dur * 80.0f);
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
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
