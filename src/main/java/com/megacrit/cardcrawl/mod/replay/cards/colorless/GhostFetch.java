package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class GhostFetch extends AbstractCard
{
    public static final String ID = "Ghost Fetch";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int POOL = 1;
    
    public GhostFetch() {
        super("Ghost Fetch", GhostFetch.NAME, "colorless/skill/goodInstincts", "colorless/skill/goodInstincts", 0, GhostFetch.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
		this.isEthereal = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BasicFromDeckToHandAction(1, this.upgraded));
    }
    
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        boolean hasBasic = false;
        for (final AbstractCard c : p.drawPile.group) {
            if (c.rarity == AbstractCard.CardRarity.BASIC || c.cardID == "Ghost Defend" || c.cardID == "Ghost Swipe") {
                hasBasic = true;
            }
        }
        if (!hasBasic) {
            this.cantUseMessage = GhostFetch.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }
    
	@Override
	public void triggerOnEndOfPlayerTurn()
	{
		AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
	}
	
    @Override
    public AbstractCard makeCopy() {
        return new GhostFetch();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = GhostFetch.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Fetch");
        NAME = GhostFetch.cardStrings.NAME;
        DESCRIPTION = GhostFetch.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = GhostFetch.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = GhostFetch.cardStrings.EXTENDED_DESCRIPTION;
    }
}
