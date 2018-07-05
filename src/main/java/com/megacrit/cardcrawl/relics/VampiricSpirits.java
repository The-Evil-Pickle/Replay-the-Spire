package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.*;
import com.badlogic.gdx.graphics.*;

public class VampiricSpirits extends AbstractRelic
{
    public static final String ID = "Vampiric Spirits";
	public static final int POWER = 25;
    
    public VampiricSpirits() {
        super("Vampiric Spirits", "livingBlood.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + VampiricSpirits.POWER + DESCRIPTIONS[1];
    }
    
    public void atBattleStart() {
       // this.flash();
		this.pulse = true;
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ThieveryPower((AbstractCreature)AbstractDungeon.player, Bandana.POWER), 1));
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
    }
    
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
		if (this.pulse && damageInfo.type == DamageInfo.DamageType.NORMAL && damageInfo.output > 0 && abstractCreature != null && abstractCreature != AbstractDungeon.player) {
			int healAmount = (n * VampiricSpirits.POWER) / 100;
			if (healAmount < 0) {
				return;
			}
			if (healAmount > abstractCreature.currentHealth) {
				healAmount = abstractCreature.currentHealth;
			}
			if (healAmount > 0) {
				AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));
				AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
			}
        }
    }
	
	@Override
    public void atPreBattle() {
		this.pulse = true;
    }
    
    @Override
    public void onPlayerEndTurn() {
		this.pulse = false;
    }
	
    @Override
    public void onVictory() {
        this.pulse = false;
    }
	
    public AbstractRelic makeCopy() {
        return new VampiricSpirits();
    }
}