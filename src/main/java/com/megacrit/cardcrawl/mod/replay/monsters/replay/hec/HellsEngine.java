package com.megacrit.cardcrawl.mod.replay.monsters.replay.hec;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import replayTheSpire.ReplayTheSpireMod;

public class HellsEngine extends AbstractMonster {
	public static final String ID = "Replay:Hell Engine";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private Conductor conductor;

    public static final int HP = 450;
    public static final int HP_A = 500;
    public static final int START_DMG = 20;
    public static final int START_DMG_A = 25;
    public static final int COALS_DMG = 4;
    public static final int COALS_DMG_A = 5;
    public static final int ARTIFACT_INIT = 20;
    public static final int ARTIFACT_INIT_A = 20;
    
	private boolean isFirstTurn;
    
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
    public HellsEngine() {
        super(NAME, ID, 999, -1400.0f, 50.0f, 400.0f, 300.0f, "images/monsters/beyond/HEC/e_placeholder.png", 1200.0f, 50.0f);
		ReplayTheSpireMod.logger.info("init Engine");
        this.isFirstTurn = true;
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(HP_A);
        }
        else {
            this.setHp(HP);
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
		CardCrawlGame.music.unsilenceBGM();
		AbstractDungeon.scene.fadeOutAmbiance();
		AbstractDungeon.getCurrRoom().playBgmInstantly("replay/No_Train_No_Game.mp3");
		this.conductor = (Conductor)AbstractDungeon.getMonsters().getMonster(Conductor.ID);
		int artifact = AbstractDungeon.ascensionLevel >= 19 ? ARTIFACT_INIT_A : ARTIFACT_INIT;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, artifact), artifact));
	}
    
	private void setMoveNow(byte nextTurn) {
		switch (nextTurn) {
            case STARTUP: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(0).base);
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
				this.isFirstTurn = false;
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
			}
        }
    }
    

    @Override
    protected void getMove(final int num) {
		if (this.isFirstTurn) {
			this.setMoveNow(STARTUP);
			return;
		}
    }
    
    @Override
    public void damage(final DamageInfo info) {
    	super.damage(info);
    	if (this.currentHealth < this.conductor.currentHealth) {
    		this.conductor.currentHealth = this.currentHealth;
    	}
    }

    @Override
    public void heal(int healAmount) {
        super.heal(healAmount);
        if (this.currentHealth > this.conductor.currentHealth) {
    		this.conductor.currentHealth = this.currentHealth;
    	}
    }
}
