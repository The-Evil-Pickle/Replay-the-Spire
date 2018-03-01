package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class LoomingEvil extends AbstractCard
{
    public static final String ID = "Looming Evil";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 3;
    private static final int POOL = 1;
    
    public LoomingEvil() {
        super("Looming Evil", LoomingEvil.NAME, (String)null, "curse/doubt", 3, LoomingEvil.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE, 1);
    }
    
    public void use(final AbstractPlayer abstractPlayer, final AbstractMonster abstractMonster) {
        if (!this.dontTriggerOnUseCard) {
            if (abstractPlayer.hasRelic("Blue Candle")) {
                this.useBlueCandle(abstractPlayer);
            }
            else {
                this.exhaust = true;
            }
        }
        else {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInHandAction(AbstractDungeon.returnRandomCurse(), true));
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SetDontTriggerAction((AbstractCard)this, false));
            if (this.cost > 0) {
                this.upgradeBaseCost(this.cost - 1);
            }
        }
    }
    
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem((AbstractCard)this, (AbstractMonster)null));
    }
    
    public boolean canUse(final AbstractPlayer abstractPlayer, final AbstractMonster abstractMonster) {
        return this.cardPlayable(abstractMonster) && this.hasEnoughEnergy();
    }
    
    public AbstractCard makeCopy() {
        return new LoomingEvil();
    }
    
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Looming Evil");
        NAME = LoomingEvil.cardStrings.NAME;
        DESCRIPTION = LoomingEvil.cardStrings.DESCRIPTION;
    }
}