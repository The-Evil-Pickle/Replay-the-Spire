package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.BagOfTricks;
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

public class BagOfTricksAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private AbstractMonster m;
	private int energyOnUse;
	private boolean freeToPlayOnce;
	private AbstractCard card;
	private int poison;
	private int shivs;
	private int block;
	private int draw;
	private int energyGain;
    
    public BagOfTricksAction(final AbstractPlayer p, final AbstractMonster m, final AbstractCard card, final int poison, final int shivs, final int block, final int draw, final int energyGain, final boolean freeToPlayOnce, final int energyOnUse) {
        this.actionType = ActionType.SPECIAL;
        this.p = p;
        this.m = m;
		this.poison = poison;
		this.shivs = shivs;
		this.block = block;
		this.draw = draw;
		this.energyGain = energyGain;
		this.freeToPlayOnce = freeToPlayOnce;
		this.energyOnUse = energyOnUse;
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
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
        final ChooseAction choice = new ChooseAction(this.card, m, BagOfTricks.EXTENDED_DESCRIPTION[0] + effect + BagOfTricks.EXTENDED_DESCRIPTION[1], effect);
        choice.add(BagOfTricks.EXTENDED_DESCRIPTION[2], (this.card.upgraded) ? BagOfTricks.EXTENDED_DESCRIPTION[4] : BagOfTricks.EXTENDED_DESCRIPTION[3], () -> {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.poison), this.poison));
            return;
        });
        choice.add(BagOfTricks.EXTENDED_DESCRIPTION[5], BagOfTricks.EXTENDED_DESCRIPTION[6], () -> {
    		AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Shiv(), this.shivs));
            return;
        });
        choice.add(BagOfTricks.EXTENDED_DESCRIPTION[7], BagOfTricks.EXTENDED_DESCRIPTION[8], () -> {
    		AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
            return;
        });
        choice.add(BagOfTricks.EXTENDED_DESCRIPTION[9], BagOfTricks.EXTENDED_DESCRIPTION[10], () -> {
    		AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.draw));
            return;
        });
        choice.add(BagOfTricks.EXTENDED_DESCRIPTION[11], (this.card.upgraded) ? BagOfTricks.EXTENDED_DESCRIPTION[13] : BagOfTricks.EXTENDED_DESCRIPTION[12], () -> {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new EnergizedPower(p, this.energyGain), this.energyGain));
            return;
        });
    	AbstractDungeon.actionManager.addToTop(choice);
		if (!this.freeToPlayOnce) {
			this.p.energy.use(EnergyPanel.totalCount);
		}
        this.isDone = true;
    }
}
