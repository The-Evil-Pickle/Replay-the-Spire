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

public class MeltdownSequence extends AbstractCycleCard
{
    public static final String ID;
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    public static final int OVERHEAT = 2;
    public static final int OVERHEAT_CONSTRUCT = 6;
    
    public MeltdownSequence() {
        super(MeltdownSequence.ID, MeltdownSequence.NAME, "cards/replay/betaCurse.png", COST, MeltdownSequence.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF, 1);
        if (AbstractDungeon.player == null || !(AbstractDungeon.player instanceof TheConstruct)) {
        	this.overheat = OVERHEAT;
        } else {
        	this.overheat = OVERHEAT_CONSTRUCT;
        }
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    private void doEffect() {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, this.magicNumber, false), this.magicNumber));
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
    	if ((p.hasRelic("Blue Candle")))
        {
          useBlueCandle(p);
        }
        else
        {
        	this.doEffect();
        }
    }
    
    public AbstractCard makeCopy() {
        return (AbstractCard)new MeltdownSequence();
    }
    
    public void upgrade() {}
    
    static {
        ID = "ReplayTheSpireMod:Meltdown Sequence";
        cardStrings = CardCrawlGame.languagePack.getCardStrings(MeltdownSequence.ID);
        NAME = MeltdownSequence.cardStrings.NAME;
        DESCRIPTION = MeltdownSequence.cardStrings.DESCRIPTION;
    }
}
