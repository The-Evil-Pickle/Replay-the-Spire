package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.DualPolarity;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.SparkCircuitry;
import com.megacrit.cardcrawl.mod.replay.orbs.ManaSparkOrb;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import ThMod.powers.Marisa.ChargeUpPower;
import replayTheSpire.ReplayTheSpireMod;


public class M_SpellCore extends AbstractRelic
{
    public static final String ID = "m_SpellCore";
    
    public M_SpellCore() {
        super(ID, "crackedOrb.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(3));
    	AbstractOrb o = new ManaSparkOrb();
    	AbstractDungeon.actionManager.addToBottom(new ChannelAction(o));
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChargeUpPower(AbstractDungeon.player, o.passiveAmount), o.passiveAmount));
    }
    
	@Override
    public void onEquip() {
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Aggregate());
        tmpPool.add(new AllForOne());
        tmpPool.add(new Barrage());
        tmpPool.add(new BasicCrystalCard());
        tmpPool.add(new BeamCell());
        tmpPool.add(new BiasedCognition());
        tmpPool.add(new BootSequence());
        tmpPool.add(new Capacitor());
        tmpPool.add(new Chaos());
        tmpPool.add(new Claw());
        tmpPool.add(new ConserveBattery());
        tmpPool.add(new Darkness());
        tmpPool.add(new Defragment());
        tmpPool.add(new DoubleEnergy());
        tmpPool.add(new FTL());
        tmpPool.add(new Fission());
        tmpPool.add(new Fusion());
        tmpPool.add(new GoForTheEyes());
        tmpPool.add(new HelloWorld());
        tmpPool.add(new Loop());
        tmpPool.add(new MachineLearning());
        tmpPool.add(new MeteorStrike());
        tmpPool.add(new MultiCast());
        tmpPool.add(new Overclock());
        tmpPool.add(new Rainbow());
        tmpPool.add(new Reboot());
        tmpPool.add(new Recursion());
        tmpPool.add(new Rebound());
        tmpPool.add(new Recycle());
        tmpPool.add(new Reprogram());
        tmpPool.add(new Scrape());
        tmpPool.add(new Seek());
        tmpPool.add(new Stack());
        tmpPool.add(new Streamline());
        tmpPool.add(new Sunder());
        tmpPool.add(new Turbo());
        tmpPool.add(new Zap());
        tmpPool.add(new ReplayGoodbyeWorld());
        tmpPool.add(new ReplaySort());
        tmpPool.add(new CalculationTraining());
        tmpPool.add(new SolidLightProjector());
        tmpPool.add(new SystemScan());
        tmpPool.add(new ReplayRNGCard());
        tmpPool.add(new SparkCircuitry());
        if (ReplayTheSpireMod.foundmod_conspire) {
        	tmpPool.add(new DualPolarity());
        }
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SpellCore();
    }
}
