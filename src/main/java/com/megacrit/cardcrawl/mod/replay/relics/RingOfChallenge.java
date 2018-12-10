package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.mod.replay.powers.TimeCollectorPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class RingOfChallenge extends ReplayAbstractRelic implements ClickableRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Challenge";
    
    private int usedThisBattle;
    private boolean isFirstTurn;

    public static final ReplayIntSliderSetting SETTING_MAX_PER_BATTLE = new ReplayIntSliderSetting("Cring_Challenge_PerBattle", "Limit Per Battle", 3, 1, 5);
    public static final ReplayIntSliderSetting SETTING_GOLD_GAIN = new ReplayIntSliderSetting("Cring_Challenge_Gold", "Gold", 15, 0, 100);
    public static final ReplayIntSliderSetting SETTING_RELIC_INTERVAL = new ReplayIntSliderSetting("Cring_Challenge_Relic", "Uses For Relic", 10, 1, 15);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
  		ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
  		r.add(SETTING_MAX_PER_BATTLE);
  		r.add(SETTING_GOLD_GAIN);
  		r.add(SETTING_RELIC_INTERVAL);
  		return r;
  	}
    public RingOfChallenge() {
        super(ID, "betaRelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
        this.usedThisBattle = 0;
        this.counter = 0;
        this.isFirstTurn = false;
        this.SettingsPriorety++;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SETTING_MAX_PER_BATTLE.value + this.DESCRIPTIONS[1] + SETTING_GOLD_GAIN.value + this.DESCRIPTIONS[2] + SETTING_RELIC_INTERVAL.value + this.DESCRIPTIONS[3];
    }

    @Override
    public void atBattleStart() {
    	this.usedThisBattle = 0;
    	this.isFirstTurn = true;
    	this.pulse = true;
    	this.beginPulse();
    }
    @Override
    public void onVictory() {
    	this.usedThisBattle = 0;
    	this.isFirstTurn = false;
    	this.pulse = false;
    }
    
    
    @Override
    public void onPlayerEndTurn() {
    	this.isFirstTurn = false;
    	this.pulse = false;
    }
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfChallenge();
    }
	@Override
	public void onRightClick() {
		if (this.isObtained && this.isFirstTurn && this.usedThisBattle < SETTING_MAX_PER_BATTLE.value) {
			this.counter++;
			this.usedThisBattle++;
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
        		if (m != null && !m.isDeadOrEscaped()) {
        			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
        			giveRandomBuff(m, AbstractDungeon.actNum);
        		}
        	}
			AbstractDungeon.player.gainGold(SETTING_GOLD_GAIN.value);
			if (this.counter >= SETTING_RELIC_INTERVAL.value && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
				this.flash();
				this.counter = 0;
				AbstractDungeon.getCurrRoom().addNoncampRelicToRewards(returnRandomRelicTier());
			}
		}
	}
	private AbstractRelic.RelicTier returnRandomRelicTier() {
		int roll = AbstractDungeon.relicRng.random(0, 99);
        if (ModHelper.isModEnabled("Elite Swarm") && AbstractDungeon.getCurrRoom().eliteTrigger) {
            roll += 10;
        }
        if (roll < 66) {
            return AbstractRelic.RelicTier.COMMON;
        }
        if (roll > 85) {
            return AbstractRelic.RelicTier.RARE;
        }
        return AbstractRelic.RelicTier.UNCOMMON;
	}
    private void giveRandomBuff(AbstractMonster m, int pwr) {
    	switch (AbstractDungeon.miscRng.random(19)) {
    	case 0:
    	case 1:
    	case 2:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, pwr + 1), pwr + 1));
    		break;
    	case 3:
    		if (m.hasPower(PlatedArmorPower.POWER_ID) || m.hasPower(MetallicizePower.POWER_ID) || m.hasPower(BarricadePower.POWER_ID) || m.hasPower(MalleablePower.POWER_ID)) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ReflectionPower(m, 3), pwr));
    			break;
    		}
    	case 4:
    	case 5:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ArtifactPower(m, pwr + 1), pwr + 1));
    		break;
    	case 6:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new MalleablePower(m, pwr * 2), pwr * 2));
    		break;
    	case 7:
    	case 8:
    	case 9:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new RegenerateMonsterPower(m, pwr * 2 + 1), pwr * 2 + 1));
    		break;
    	case 10:
    	case 11:
    	case 12:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new MetallicizePower(m, pwr * 2 + 2), pwr * 2 + 2));
    		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, AbstractDungeon.player, pwr * 2 + 2));
    		break;
    	case 13:
    		if (!m.hasPower(PainfulStabsPower.POWER_ID) && pwr > 1) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PainfulStabsPower(m), 1));
    			break;
    		}
    	case 14:
    	case 15:
    	case 16:
    		AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(m, 0.25f, true));
    		break;
    	case 17:
    		if (m.hasPower(PlatedArmorPower.POWER_ID) || m.hasPower(MetallicizePower.POWER_ID) || m.hasPower(BarricadePower.POWER_ID) || m.hasPower(CurlUpPower.POWER_ID) || m.hasPower(MalleablePower.POWER_ID)) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ReflectionPower(m, 3), pwr));
    			break;
    		}
    	case 18:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ThornsPower(m, pwr), pwr));
    		break;
    	case 19:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new CuriosityPower(m, Math.max(pwr - 1, 1)), Math.max(pwr - 1, 1)));
    		break;
    	}
    }
}