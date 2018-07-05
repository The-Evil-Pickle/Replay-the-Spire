package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.colorless.*;

public class AwakenedRitualPower extends AbstractPower
{
    public static final String POWER_ID = "Awakened Ritual";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public AwakenedRitualPower(final AbstractCreature owner) {
        this.name = AwakenedRitualPower.NAME;
        this.ID = "Awakened Ritual";
        this.owner = owner;
        this.amount = 5;
        this.updateDescription();
        this.loadRegion("unawakened");
    }
	
	@Override
	public void onDrawOrDiscard() {
		if (this.amount <= 0) {
			return;
		}
		int amt = 0;
		for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof RitualComponent) {
                amt ++;
            }
        }
		if (amt >= 5) {
			for (final AbstractCard c : AbstractDungeon.player.hand.group) {
				if (c instanceof RitualComponent) {
					AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
					final AbstractCard de = new DarkEchoRitualCard();
					if (c.upgraded) {
						de.upgrade();
					}
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(de, true));
				}
			}
			this.amount = -1;
		} else {
			this.amount = 5 - amt;
		}
	}
	
	
    @Override
    public void atStartOfTurn() {
        if (this.amount <= 0) {
			this.amount = 5;
		}
    }
    
    @Override
    public void updateDescription() {
		if (this.amount > 0) {
			this.description = AwakenedRitualPower.DESCRIPTIONS[0] + AwakenedRitualPower.DESCRIPTIONS[1] + this.amount + AwakenedRitualPower.DESCRIPTIONS[2];
		} else {
			this.description = AwakenedRitualPower.DESCRIPTIONS[0];
		}
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Awakened Ritual");
        NAME = AwakenedRitualPower.powerStrings.NAME;
        DESCRIPTIONS = AwakenedRitualPower.powerStrings.DESCRIPTIONS;
    }
}
