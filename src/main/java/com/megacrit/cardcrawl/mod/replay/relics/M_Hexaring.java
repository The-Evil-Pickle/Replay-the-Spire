package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.*;

import theHexaghost.TheHexaghost;
import theHexaghost.cards.*;
import theHexaghost.powers.BurnPower;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.HexaringAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.*;

public class M_Hexaring extends M_MistRelic implements OnApplyPowerRelic
{
    public static final String ID = "m_HexaRing";
    static final int SOULBURN_COUNTMAX = 3;
    static final int POISON_AMT = 2;
    public M_Hexaring() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, TheHexaghost.Enums.GHOST_GREEN, CardColor.GREEN);
        this.counter = 0;
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + SOULBURN_COUNTMAX + this.DESCRIPTIONS[1] + POISON_AMT + this.DESCRIPTIONS[2];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.THE_SILENT) {
    			desc += this.DESCRIPTIONS[3];
    		}
    		if (AbstractDungeon.player.chosenClass != TheHexaghost.Enums.THE_SPIRIT) {
    			desc += this.DESCRIPTIONS[4];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[3] + this.DESCRIPTIONS[4];
    	}
        return desc;
    }
    
    
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_Hexaring();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new DeadlyPoison());
        tmpPool.add(new Envenom());
        tmpPool.add(new BouncingFlask());
        tmpPool.add(new NoxiousFumes());
        tmpPool.add(new Bane());
        tmpPool.add(new CripplingPoison());
        tmpPool.add(new PoisonedStab());
        tmpPool.add(new CorpseExplosion());
        tmpPool.add(new Burst());
        tmpPool.add(new PhantasmalKiller());
        tmpPool.add(new WraithForm());
        

        tmpPool.add(new Firestarter());
        tmpPool.add(new BurningTouch());
        tmpPool.add(new RainOfEmbers());
        tmpPool.add(new ThermalTransfer());
        tmpPool.add(new ExtraCrispy());
        tmpPool.add(new FlamesFromBeyond());
        tmpPool.add(new GhostflameBarrier());
        tmpPool.add(new Incineration());
        tmpPool.add(new SearingWound());
        tmpPool.add(new Toasty());
        tmpPool.add(new WildfireWeapon());
        tmpPool.add(new VolcanoVisage());

		return tmpPool;
	}

	@Override
	public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		if (power.ID == BurnPower.POWER_ID && target != null && source != null && source.isPlayer && !target.isPlayer) {
			this.counter++;
			this.flash();
			if (this.counter >= SOULBURN_COUNTMAX) {
				this.addToTop(new HexaringAction(this, POISON_AMT));
				this.counter = 0;
			}
		}
		
		return true;
	}
}

