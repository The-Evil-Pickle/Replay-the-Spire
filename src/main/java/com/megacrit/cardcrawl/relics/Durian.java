package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class Durian extends AbstractRelic
{
    public static final String ID = "Durian";
    public static final int HP_BOOST = 5;
	
	
    public Durian() {
        this(null);
    }
    
    public Durian(final AbstractPlayer.PlayerClass c) {
        super("Durian", "durian.png", RelicTier.RARE, LandingSound.FLAT);
    }
    
    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(Durian.HP_BOOST, true);
    }
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + Durian.HP_BOOST + ".";
    }
	
    @Override
    public void atTurnStart() {
		boolean gotem = false;
        for (final AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID.equals("Frail") || p.ID.equals("Weakened") || p.ID.equals("Vulnerable")) {
				if (p.amount > 2) {
					AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, p.ID, p.amount - 2));
					gotem = true;
				}
            }
        }
		if (gotem) {
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Durian();
    }
}
