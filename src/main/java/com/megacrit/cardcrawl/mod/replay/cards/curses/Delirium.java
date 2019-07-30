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

public class Delirium extends CustomCard
{
    public static final String ID = "Delirium";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    private static final int POOL = 1;
    private static final int WEAK_AMT = 1;
    
    public Delirium() {
        super("Delirium", Delirium.NAME, "cards/replay/betaCurse.png", -2, Delirium.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TPH_ConfusionPower(AbstractDungeon.player, 1, true), 1));
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
        return new Delirium();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Delirium");
        NAME = Delirium.cardStrings.NAME;
        DESCRIPTION = Delirium.cardStrings.DESCRIPTION;
    }
}
