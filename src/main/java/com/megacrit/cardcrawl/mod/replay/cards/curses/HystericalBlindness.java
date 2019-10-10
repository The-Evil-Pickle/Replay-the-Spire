package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class HystericalBlindness extends CustomCard
{
    public static final String ID = "Replay:Hysterical Blindness";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    
    public HystericalBlindness() {
        super(ID, HystericalBlindness.NAME, "cards/replay/betaCurse.png", -2, HystericalBlindness.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReplayBlindPower(AbstractDungeon.player, this.magicNumber, true), this.magicNumber));
        }
    }
    
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new HystericalBlindness();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = HystericalBlindness.cardStrings.NAME;
        DESCRIPTION = HystericalBlindness.cardStrings.DESCRIPTION;
    }
}
