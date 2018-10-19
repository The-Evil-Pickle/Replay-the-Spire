package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import basemod.ReflectionHacks;

public class Durian extends AbstractRelic
{
    public static final String ID = "Durian";
    public static final int HP_BOOST = 5;
	
	
    public Durian() {
    	super("Durian", "durian.png", RelicTier.RARE, LandingSound.FLAT);
    }
    
    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(Durian.HP_BOOST, true);
    }
    
    public void onDebuffRecieved(ApplyPowerAction __instance) {
    	AbstractDungeon.actionManager.addToTop(new ReplayGainShieldingAction(__instance.target, __instance.target, Math.abs(__instance.amount)));
    }
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + Durian.HP_BOOST + ".";
    }
	
    @Override
    public void atTurnStart() {
		boolean gotem = false;
        for (final AbstractPower p : AbstractDungeon.player.powers) {
            //if (p.ID.equals("Frail") || p.ID.equals("Weakened") || p.ID.equals("Vulnerable")) {
        	if (p.type == AbstractPower.PowerType.DEBUFF && (boolean)ReflectionHacks.getPrivate((Object)p, (Class)AbstractPower.class, "isTurnBased")) {
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
