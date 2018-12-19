package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.BagOfTricks;
import com.megacrit.cardcrawl.mod.replay.cards.red.UndeathsTouch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class ManyHandsAction extends AbstractGameAction
{
    private AbstractPlayer p;
	private int energyOnUse;
	private boolean freeToPlayOnce;
    
    public ManyHandsAction(final AbstractPlayer p, final boolean freeToPlayOnce, final int energyOnUse) {
        this.actionType = ActionType.SPECIAL;
        this.p = p;
		this.freeToPlayOnce = freeToPlayOnce;
		this.energyOnUse = energyOnUse;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
    	int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new UndeathsTouch(), effect));
		if (!this.freeToPlayOnce) {
			this.p.energy.use(EnergyPanel.totalCount);
		}
        this.isDone = true;
    }
}
