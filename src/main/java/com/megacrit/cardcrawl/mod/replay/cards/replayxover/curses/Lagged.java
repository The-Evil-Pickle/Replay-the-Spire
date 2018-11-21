package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import constructmod.patches.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import constructmod.*;
import constructmod.cards.AbstractCycleCard;
import constructmod.characters.TheConstruct;

import com.megacrit.cardcrawl.core.*;

public class Lagged extends AbstractCycleCard
{
    public static final String ID;
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -2;
    
    public Lagged() {
        super(Lagged.ID, Lagged.NAME, "cards/replay/betaCurse.png", COST, Lagged.DESCRIPTION, AbstractCard.CardType.STATUS, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF, 1);
    }
    private void doEffect() {
    	//nothing here lol
    }
    @Override
    public void triggerWhenDrawn() {
        if (!this.canCycle()) {
            return;
        }
        super.triggerWhenDrawn();
        this.flash();
        this.doEffect();
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if ((p.hasRelic("Medical Kit")))
        {
    		this.useMedicalKit(p);
        }
        else
        {
        	this.doEffect();
        }
    }
    
    public AbstractCard makeCopy() {
        return new Lagged();
    }
    
    public void upgrade() {
    	this.upgradeName();
    	if (AbstractDungeon.player == null || !(AbstractDungeon.player instanceof TheConstruct)) {
        	this.overheat = MeltdownSequence.OVERHEAT;
        } else {
        	this.overheat = MeltdownSequence.OVERHEAT_CONSTRUCT;
        }
    	this.rawDescription = UPGRADE_DESCRIPTION;
    	this.initializeDescription();
    }
    
    static {
        ID = "ReplayTheSpireMod:Lagged";
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Lagged.ID);
        NAME = Lagged.cardStrings.NAME;
        DESCRIPTION = Lagged.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Lagged.cardStrings.UPGRADE_DESCRIPTION;
    }
}
