package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;
import replayTheSpire.ReplayTheSpireMod;

public class AbeCurse extends CustomCard
{
    public static final String ID = "AbeCurse";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    
    public AbeCurse() {
        super("AbeCurse", AbeCurse.NAME, "cards/replay/abeCurse.png", -2, AbeCurse.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
		this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        if (ReplayTheSpireMod.foundmod_stslib) {
        	SoulboundField.soulbound.set(this, true);
        	this.rawDescription += " NL Soulbound.";
        	this.initializeDescription();
        }
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, this.magicNumber, true), this.magicNumber));
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
        return new AbeCurse();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("AbeCurse");
        NAME = AbeCurse.cardStrings.NAME;
        DESCRIPTION = AbeCurse.cardStrings.DESCRIPTION;
    }
}
