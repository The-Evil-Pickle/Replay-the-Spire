package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.*;
import com.badlogic.gdx.graphics.*;

public class Bandana extends AbstractRelic
{
    public static final String ID = "Bandana";
	public static final int DURATION = 3;
	public static final int POWER = 2;
	private boolean firstTurn = false;
    
    public Bandana() {
        super("Bandana", "bandana.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + Bandana.POWER + DESCRIPTIONS[1] + Bandana.DURATION + DESCRIPTIONS[2];
    }
    
    public void atBattleStart() {
        this.flash();
		this.pulse = true;
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ThieveryPower((AbstractCreature)AbstractDungeon.player, Bandana.POWER), 1));
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
    }
    
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
		if (this.counter > 0 && damageInfo.type == DamageInfo.DamageType.NORMAL) {
			final AbstractPlayer player = AbstractDungeon.player;
			//player.gold += Bandana.POWER;
			CardCrawlGame.sound.play("GOLD_JINGLE");
			for (int i = 0; i < Bandana.POWER; ++i) {
				AbstractDungeon.effectList.add(new GainPennyEffect(abstractCreature.hb.cX, abstractCreature.hb.cY));
			}
        }
    }
    
	@Override
    public void atPreBattle() {
        this.firstTurn = true;
		this.counter = Bandana.DURATION;
    }
    
    @Override
    public void atTurnStart() {
        if (this.counter > 0 && !this.firstTurn) {
            this.counter--;
			if (this.counter <= 0) {
				AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Thievery"));
				this.pulse = false;
				this.counter = -1;
			}
        }
        this.firstTurn = false;
    }
	
    @Override
    public void onVictory() {
        this.pulse = false;
		this.counter = -1;
    }
	
    public AbstractRelic makeCopy() {
        return new Bandana();
    }
}