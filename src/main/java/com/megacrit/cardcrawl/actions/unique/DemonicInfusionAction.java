package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class DemonicInfusionAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade;
	private int energyOnUse;
	private boolean freeToPlayOnce;
	private int upgraded;
    
    public DemonicInfusionAction(final AbstractPlayer p, final int upgraded, final boolean freeToPlayOnce, final int energyOnUse) {
        this.cannotUpgrade = new ArrayList<AbstractCard>();
        this.upgraded = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = p;
		this.upgraded = upgraded;
		this.freeToPlayOnce = freeToPlayOnce;
		this.energyOnUse = energyOnUse;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
	private void doSuperUpgrade(AbstractCard c) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
		effect += this.upgraded;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
			//ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Chemical X").flash();
            //this.p.getRelic("Chemical X").flash();
        }
		for (int i = 0; i < effect; ++i) {
			c.upgrade();
			if (i < effect - 1 && !c.canUpgrade()) {
				c.upgraded = false;
			}
			if (c.timesUpgraded > 1) {
				c.name = c.name.substring(0, c.name.lastIndexOf("+"));
			}
		}
		if (c.timesUpgraded > 1) {
			c.name = c.name + c.timesUpgraded;
		}
		if (!this.freeToPlayOnce) {
			if (EnergyPanel.totalCount > 1) {
				this.p.energy.use(EnergyPanel.totalCount - 1);
			}
		}
	}
	
	private boolean isValidUpgradeTarget(AbstractCard c) {
		return ((c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL) 
		&& (c.canUpgrade() || c.upgraded));
	}
	
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (final AbstractCard c : this.p.hand.group) {
                if (!this.isValidUpgradeTarget(c)) {
                    this.cannotUpgrade.add(c);
                }
            }
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
			
            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (this.isValidUpgradeTarget(c)) {
                        this.doSuperUpgrade(c);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(ArmamentsAction.TEXT[0], 1, false, false, false, true);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                //this.p.hand.getTopCard().upgrade();
				this.doSuperUpgrade(this.p.hand.getTopCard());
                this.returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.doSuperUpgrade(c);//c.upgrade();
                this.p.hand.addToTop(c);
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
    
    private void returnCards() {
        for (final AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
    
}
