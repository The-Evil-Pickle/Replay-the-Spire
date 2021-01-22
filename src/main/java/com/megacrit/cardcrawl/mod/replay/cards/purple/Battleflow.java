package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ChooseAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShuffleNonStatusAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShuffleRareAction;
import com.megacrit.cardcrawl.mod.replay.powers.MightPower;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.mod.replay.stances.GuardStance;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Battleflow extends CustomCard
{
    public static final String ID = "Replay:Battleflow";
    private static final CardStrings cardStrings;
    private static final String[] EXTENDED_DESCRIPTION;
    
    public Battleflow() {
        super(ID, Battleflow.cardStrings.NAME, "cards/replay/battleflow.png", 0, Battleflow.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 3;
        GraveField.grave.set(this, true);
    }

    @Override
    public void triggerWhenDrawn() {
    	this.updateStuff();
    }
    
    @Override
    public void switchedStance() {
    	this.updateStuff();
    }
    
    private void updateStuff() {
    	if (AbstractDungeon.player != null && (AbstractDungeon.player.stance == null || AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID))) {
    		this.cost = 0;
    	} else {
    		this.cost = -2;
    	}
		if (!this.isCostModifiedForTurn) {
			this.costForTurn = this.cost;
		}
    }
    @Override
    public boolean cardPlayable(final AbstractMonster m) {
    	this.updateStuff();
        return (this.cost == -2) ? false : super.cardPlayable(m);
    }
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	final ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
    	choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Wrath"));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Calm"));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[5], EXTENDED_DESCRIPTION[6], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new GuardStance()));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[7], EXTENDED_DESCRIPTION[8], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MantraPower(p, this.magicNumber), this.magicNumber));
            return;
        });
    	AbstractDungeon.actionManager.addToBottom(choice);
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            GraveField.grave.set(this, false);
            this.upgradeMagicNumber(1);
            this.rawDescription = Battleflow.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Battleflow();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
