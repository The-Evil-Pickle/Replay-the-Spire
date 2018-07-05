package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class MidasAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    private boolean isUpgraded;
    public static int numExhausted;
    
	public MidasAction(final AbstractCreature target, final AbstractCreature source, final boolean isUpgraded) {
		this.canPickZero = false;
        this.anyNumber = true;
        this.canPickZero = true;
        this.p = (AbstractPlayer)target;
        this.isRandom = false;
        this.setValues(target, source, 10);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.isUpgraded = isUpgraded;
	}
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                ExhaustAction.numExhausted = this.amount;
                for (int tmp = this.p.hand.size(), i = 0; i < tmp; ++i) {
                    final AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                    this.DoRandoCard();
					//AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.p, 1));
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            if (!this.isRandom) {
                ExhaustAction.numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(AbandonAction.TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }
            for (int j = 0; j < this.amount; ++j) {
                this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(false));
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c2 : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c2);
				//AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.p, 1));
                this.DoRandoCard();
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
    
    private void DoRandoCard() {
		AbstractCard c = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).makeCopy();
		int r = MathUtils.random(10);
		if (r == 10)
		{
			
		}
		if (this.isUpgraded)
		{
		  if (c.canUpgrade())
		  {
			c.upgrade();
		  }
		}
        c.current_x = (-1000.0F * Settings.scale);
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = MidasAction.uiStrings.TEXT;
    }
}
