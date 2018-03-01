package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;

public class Bandana extends AbstractRelic
{
    public static final String ID = "Bandana";
    
    public Bandana() {
        super("Bandana", "bandana.png", RelicTier.COMMON, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ThieveryPower((AbstractCreature)AbstractDungeon.player, 2), 1));
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
    }
    
    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
		AbstractDungeon.player.gold += 2;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Bandana();
    }
}
