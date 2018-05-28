package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;

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
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
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
