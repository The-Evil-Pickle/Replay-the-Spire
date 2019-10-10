package com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.Hallucinations;
import com.megacrit.cardcrawl.mod.replay.cards.curses.LoomingEvil;
import com.megacrit.cardcrawl.mod.replay.cards.curses.SpreadingInfection;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
//import com.megacrit.cardcrawl.monsters.EnemyType;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;

public class BlueRogue extends AbstractMonster
{
    public static final String ID = "Replay:BlueRogue";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 86;
    private static final int A_HP = 90;
    private static final int BLAST_DMG = 18;
    private static final int A_BLAST_DMG = 24;
    private static final int BLAST_DMG_SLF = 4;
    private static final int RUSH_DMG = 3;
    private static final int RUSH_AMT = 3;
    private static final int A_RUSH_DMG = 3;
    private static final int A_RUSH_AMT = 4;
    private static final int LIGHT_BLK = 8;
    private static final int A_LIGHT_BLK = 12;
    private static final int STAB_DMG = 9;
    private static final int STAB_BLK = 5;
    private static final int A_STAB_DMG = 11;
    private static final int A_STAB_BLK = 8;
    private int blastDmg;
    private int rushDmg;
    private int rushAmt;
    private int lightBlk;
    private int stabDmg;
    private int stabBlk;
    private int blindAmt;
    private static final byte LIGHT = 1;
    private static final byte CHAOS = 2;
    private static final byte RUSHDOWN = 3;
    private static final byte BLAST = 4;
    private static final byte QUICKSTAB = 5;
    private boolean roared;
    private int turnCount;
    public BlueRogue() {
        this(0.0f, 0.0f);
    }
    public BlueRogue(final float x, final float y) {
        super(BlueRogue.NAME, BlueRogue.ID, 300, 0.0f, -40.0f, 360.0f, 440.0f, "images/monsters/exord/bluerogue.png", x, y);
        this.roared = false;
        this.turnCount = 0;
        this.type = EnemyType.ELITE;
        //this.loadAnimation("images/monsters/theForest/maw/skeleton.atlas", "images/monsters/theForest/maw/skeleton.json", 1.0f);
        //final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
        this.dialogX = -160.0f * Settings.scale;
        this.dialogY = 40.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.blastDmg = A_BLAST_DMG;
            this.rushDmg = A_RUSH_DMG;
            this.rushAmt = A_RUSH_AMT;
            this.stabDmg = A_STAB_DMG;
        }
        else {
        	this.blastDmg = BLAST_DMG;
            this.rushDmg = RUSH_DMG;
            this.rushAmt = RUSH_AMT;
            this.stabDmg = STAB_DMG;
        }
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_HP);
            this.lightBlk = A_LIGHT_BLK;
            this.stabBlk = A_STAB_BLK;
        }
        else {
            this.setHp(HP);
            this.lightBlk = LIGHT_BLK;
            this.stabBlk = STAB_BLK;
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
        	this.blindAmt = 2;
        }
        else {
        	this.blindAmt = 1;
        }
        this.damage.add(new DamageInfo(this, this.rushDmg));
        this.damage.add(new DamageInfo(this, this.blastDmg));
        this.damage.add(new DamageInfo(this, this.stabDmg));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case CHAOS: {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ChaosPower(AbstractDungeon.player, 1, 3), 1));
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new LanguidPower(AbstractDungeon.player, this.blindAmt+1, true), this.blindAmt+1));
                this.roared = true;
                break;
            }
            case LIGHT: {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ReplayBlindPower(AbstractDungeon.player, this.blindAmt), this.blindAmt));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this.lightBlk));
                break;
            }
            case QUICKSTAB: {
            	AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this.stabBlk));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReflectionPower(this, 3), 3));
                break;
            }
            case RUSHDOWN: {
            	for (int i=0; i < this.rushAmt; i++) {
            		AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            	}
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                break;
            }
            case BLAST: {
            	AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntimidateEffect(this.hb.cX, this.hb.cY), 0.5f));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 1.0f, 1.0f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this, new DamageInfo(this, BLAST_DMG_SLF, DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            	break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    protected void getMove(final int num) {
        ++this.turnCount;
        if (this.turnCount <= 3) {
	        switch (this.turnCount) {
		    	case 1: {
		    		this.setMove(MOVES[4], QUICKSTAB, Intent.ATTACK_DEFEND, this.damage.get(2).base);
		    		break;
		    	}
		    	case 2: {
		    		this.setMove(MOVES[0], LIGHT, Intent.DEFEND_DEBUFF);
		    		break;
		    	}
		    	default: {
		    		if (!this.roared) {
		    			this.setMove(MOVES[1], CHAOS, Intent.STRONG_DEBUFF);
		    		} else {
		    			this.setMove(MOVES[3], BLAST, Intent.ATTACK, this.damage.get(1).base);
		    		}
		    		break;
		    	}
	        }
        	return;
        }
        if (!this.roared && this.lastMove(LIGHT)) {
        	this.setMove(MOVES[1], CHAOS, Intent.STRONG_DEBUFF);
            return;
        }
        if (this.lastMove(QUICKSTAB)) {
        	this.setMove(MOVES[0], LIGHT, Intent.DEFEND_DEBUFF);
        	return;
        }
        if (this.lastMove(RUSHDOWN) || (num < 50 && !this.lastMove(QUICKSTAB) && !this.lastMove(LIGHT))) {
        	this.setMove(MOVES[4], QUICKSTAB, Intent.ATTACK_DEFEND, this.damage.get(2).base);
            return;
        }
        if (!this.lastMove(BLAST) && num % 2 == 0) {
        	this.setMove(MOVES[3], BLAST, Intent.ATTACK, this.damage.get(1).base);
            return;
        }
        if (!this.lastMove(RUSHDOWN) && num % 2 == 1) {
        	this.setMove(MOVES[2], RUSHDOWN, Intent.ATTACK_BUFF, this.damage.get(0).base, this.rushAmt, true);
            return;
        }
        if (!this.lastMove(QUICKSTAB) && !this.lastMove(LIGHT)) {
        	this.setMove(MOVES[4], QUICKSTAB, Intent.ATTACK_DEFEND, this.damage.get(2).base);
        	return;
        }
        if (!this.lastMove(RUSHDOWN) && !this.lastMoveBefore(RUSHDOWN)) {
        	this.setMove(MOVES[2], RUSHDOWN, Intent.ATTACK_BUFF, this.damage.get(0).base, this.rushAmt, true);
            return;
        }
        this.setMove(MOVES[3], BLAST, Intent.ATTACK, this.damage.get(1).base);
    }
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = BlueRogue.monsterStrings.NAME;
        MOVES = BlueRogue.monsterStrings.MOVES;
        DIALOG = BlueRogue.monsterStrings.DIALOG;
    }
}
