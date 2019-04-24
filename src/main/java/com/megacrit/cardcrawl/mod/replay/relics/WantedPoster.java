package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
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

public class WantedPoster extends AbstractRelic
{
    public static final String ID = "Replay:Wanted Poster";
    static final int GOLD_MAX = 500;
    static final int GOLD_INC = 100;
    static final int GOLD_BONUS = 100;
    
    public WantedPoster() {
        super(ID, "wantedPoster.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GOLD_INC + this.DESCRIPTIONS[1] + GOLD_MAX + this.DESCRIPTIONS[2] + GOLD_BONUS + this.DESCRIPTIONS[3];
    }
	
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
        AbstractDungeon.player.gainGold(GOLD_BONUS);
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
    
    private void giveRandomBuff(AbstractMonster m) {
    	switch (AbstractDungeon.miscRng.random(16)) {
    	case 0:
    	case 1:
    	case 2:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, 1), 1));
    		break;
    	case 3:
    		if (m.hasPower(PlatedArmorPower.POWER_ID) || m.hasPower(MetallicizePower.POWER_ID) || m.hasPower(BarricadePower.POWER_ID) || m.hasPower(MalleablePower.POWER_ID)) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ReflectionPower(m, 3), 2));
    			break;
    		}
    	case 4:
    	case 5:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ArtifactPower(m, 1), 1));
    		break;
    	case 6:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new MalleablePower(m, 2), 2));
    		break;
    	case 7:
    	case 8:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new RegenerateMonsterPower(m, 3), 3));
    		break;
    	case 9:
    	case 10:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PlatedArmorPower(m, 5), 5));
    		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, AbstractDungeon.player, 5));
    		break;
    	case 11:
    		if (!m.hasPower(PainfulStabsPower.POWER_ID)) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PainfulStabsPower(m), 1));
    		} else {
    			giveRandomBuff(m);
    		}
    		break;
    	case 12:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new TimeCollectorPower(m, 8), -2));
    		break;
    	case 13:
    		if (m.hasPower(PlatedArmorPower.POWER_ID) || m.hasPower(MetallicizePower.POWER_ID) || m.hasPower(BarricadePower.POWER_ID) || m.hasPower(CurlUpPower.POWER_ID) || m.hasPower(MalleablePower.POWER_ID)) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ReflectionPower(m, 3), 2));
    			break;
    		}
    	case 14:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ThornsPower(m, 1), 1));
    		break;
    	case 15:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new CuriosityPower(m, 1), 1));
    		break;
    	case 16:
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new MightPower(m, 2, false), 2));
    		break;
    	}
    }
    
    @Override
    public void atPreBattle() {
    	if (AbstractDungeon.player.gold <= GOLD_MAX - GOLD_INC) {
    		this.flash();
            for (int i=0; i < (GOLD_MAX - AbstractDungeon.player.gold) / GOLD_INC; i++) {
            	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            		if (m != null && !m.isDeadOrEscaped()) {
            			if (i==0) {
            				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            			}
            			giveRandomBuff(m);
            		}
            	}
            }
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new WantedPoster();
    }
    
}
