package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

public class Carrot extends AbstractRelic
{
    public static final String ID = "Carrot";
    public int TurnsLeft;
    
    public Carrot() {
        super("Carrot", "carrot.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
        this.TurnsLeft = 4;
    }
    
    public void atBattleStart() {
        this.TurnsLeft = 4;
        this.flash();
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
        AbstractDungeon.player.addPower((AbstractPower)new FocusPower((AbstractCreature)AbstractDungeon.player, 4));
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void atTurnStart() {
        if (this.TurnsLeft > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
            AbstractDungeon.player.addPower((AbstractPower)new FocusPower((AbstractCreature)AbstractDungeon.player, -1));
            --this.TurnsLeft;
        }
    }
    
    public AbstractRelic makeCopy() {
        return new Carrot();
    }
}
