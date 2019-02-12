package com.megacrit.cardcrawl.mod.replay.monsters.replay.hec;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.mod.replay.powers.EnemyLifeBindPower;
import com.megacrit.cardcrawl.mod.replay.powers.OffTheRailsPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import replayTheSpire.ReplayTheSpireMod;

public class Conductor extends AbstractMonster {
	public static final String ID = "Replay:Conductor";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private HellsEngine engine;

    public static final int START_DMG = 20;
    public static final int START_DMG_A = 25;
    public static final int COALS_DMG = 4;
    public static final int COALS_DMG_A = 5;
    public static final int ARMOR_INIT = 20;
    public static final int ARMOR_INIT_A = 20;
    
    
    
    private int startDmg;
    private int coalsDmg;
    
    
    
    
    
    
    
    //move id bytes
    private static final byte HOT_COALS = 1;
    private static final byte DOORS_CLOSING = 2;
    private static final byte RUNAWAY_TRAIN = 3;
    private static final byte LIVING_STEEL = 4;
    private static final byte STOKE_THE_ENGINE = 5;
    private static final byte HEARTBEAT = 6;
    private static final byte STARTUP = 7;
    public Conductor() {
        super(NAME, ID, 999, 25.0f, 25.0f, 180.0f, 250.0f, "images/monsters/TheCity/abe.png", -75.0f, -75.0f);
		ReplayTheSpireMod.logger.info("init Conductor");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(HellsEngine.HP_A);
        }
        else {
            this.setHp(HellsEngine.HP);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.startDmg = START_DMG_A;
            this.coalsDmg = COALS_DMG_A;
        }
        else {
        	this.startDmg = START_DMG;
            this.coalsDmg = COALS_DMG;
        }
        this.damage.add(new DamageInfo(this, this.startDmg));
        this.damage.add(new DamageInfo(this, this.coalsDmg));
        //this.loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0f);
        //this.state.setAnimation(0, "idle", true);
    }
    
    @Override
    public void usePreBattleAction() {
		this.engine = (HellsEngine)AbstractDungeon.getMonsters().getMonster(HellsEngine.ID);
		int armor = AbstractDungeon.ascensionLevel >= 19 ? ARMOR_INIT_A : ARMOR_INIT;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new EnemyLifeBindPower(this)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OffTheRailsPower(this, -1, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, armor), armor));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, armor));
	}
    
	private void setMoveNow(byte nextTurn) {
		switch (nextTurn) {
            case STARTUP: {
				this.setMove(nextTurn, Intent.UNKNOWN);
				break;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);	
				break;	
			}
		}
	}
	
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
			case STARTUP: {
				int partif = 0;
				if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
					partif = AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount;
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, this, ArtifactPower.POWER_ID));
				}
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
				if (partif > 0) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ArtifactPower(AbstractDungeon.player, partif), partif));
				}
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
			}
        }
    }
    

    @Override
    protected void getMove(final int num) {
		if (this.engine.isFirstTurn || !AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
			this.setMoveNow(STARTUP);
			return;
		}
    }
    
    @Override
    public void damage(final DamageInfo info) {
    	super.damage(info);
    	if (this.currentHealth < this.engine.currentHealth) {
    		AbstractDungeon.effectList.add(new StrikeEffect(this.engine, this.engine.hb.cX, this.engine.hb.cY, this.engine.currentHealth - this.currentHealth));
    		this.engine.currentHealth = this.currentHealth;
    		this.engine.healthBarUpdatedEvent();
    	}
    }

    @Override
    public void heal(int healAmount) {
        super.heal(healAmount);
        if (this.currentHealth > this.engine.currentHealth) {
        	AbstractDungeon.effectList.add(new HealEffect(this.engine.hb.cX - this.engine.animX, this.engine.hb.cY, this.currentHealth - this.engine.currentHealth));
    		this.engine.currentHealth = this.currentHealth;
    		this.engine.healthBarUpdatedEvent();
    	}
    }
    
    @Override
    public void die() {
        super.die();
        if (!this.engine.isDeadOrEscaped() && !this.engine.isDying) {
        	this.engine.die();
        }
    }
}
