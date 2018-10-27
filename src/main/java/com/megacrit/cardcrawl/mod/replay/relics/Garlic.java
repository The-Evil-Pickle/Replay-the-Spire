package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

import java.util.*;

public class Garlic extends AbstractRelic
{
    public static final String ID = "Garlic";
    public int TurnsLeft;
    public ArrayList<AbstractMonster> affectedMonsters;
	
    public Garlic() {
        super("Garlic", "garlic.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
        this.TurnsLeft = 4;
		this.affectedMonsters = new ArrayList<AbstractMonster>();
    }
    
    public void atBattleStart() {
        this.flash();
		/*
        this.TurnsLeft = 4;
		this.affectedMonsters = new ArrayList<AbstractMonster>();
        for (final AbstractMonster abstractMonster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)abstractMonster, (AbstractRelic)this));
			if (!abstractMonster.hasPower("Artifact")) {
				this.affectedMonsters.add(abstractMonster);
			}
            abstractMonster.addPower((AbstractPower)new StrengthPower((AbstractCreature)abstractMonster, -4));
        }*/
		for (final AbstractMonster abstractMonster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)abstractMonster, (AbstractRelic)this));
            abstractMonster.addPower((AbstractPower)new LanguidPower((AbstractCreature)abstractMonster, 3, false));
        }
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    /*
    public void atTurnStart() {
        if (this.TurnsLeft > 0) {
            this.flash();
            for (final AbstractMonster abstractMonster : AbstractDungeon.getMonsters().monsters) {
                if (!abstractMonster.isDeadOrEscaped() && this.affectedMonsters.contains(abstractMonster)) {
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)abstractMonster, (AbstractRelic)this));
                    abstractMonster.addPower((AbstractPower)new StrengthPower((AbstractCreature)abstractMonster, 1));
                }
            }
            --this.TurnsLeft;
        }
    }
    */
    public AbstractRelic makeCopy() {
        return new Garlic();
    }
}
