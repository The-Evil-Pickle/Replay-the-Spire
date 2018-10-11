package com.megacrit.cardcrawl.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class Jyrdo extends AbstractMonster
{
    public static final String ID = "Jyrdo";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String IMAGE = "images/monsters/theCity/byrdFlying.png";
    private static final int HP_MIN = 25;
    private static final int HP_MAX = 31;
    private static final int A_2_HP_MIN = 26;
    private static final int A_2_HP_MAX = 33;
    private static final float HB_X_F = 0.0f;
    private static final float HB_X_G = 10.0f;
    private static final float HB_Y_F = 50.0f;
    private static final float HB_Y_G = -50.0f;
    private static final float HB_W = 240.0f;
    private static final float HB_H = 180.0f;
    private static final int PECK_DMG = 1;
    private static final int PECK_COUNT = 5;
    private static final int SWOOP_DMG = 12;
    private static final int A_2_PECK_COUNT = 6;
    private static final int A_2_SWOOP_DMG = 14;
    private int peckDmg;
    private int peckCount;
    private int swoopDmg;
    private int flightAmt;
    private static final int HEADBUTT_DMG = 3;
    private static final int CAW_STR = 1;
    private static final byte PECK = 1;
    private static final byte GO_AIRBORNE = 2;
    private static final byte SWOOP = 3;
    private static final byte STUNNED = 4;
    private static final byte HEADBUTT = 5;
    private static final byte BELLOW = 6;
    private static final byte GAZE = 7;
    private boolean firstMove;
    private boolean isFlying;
    public static final String FLY_STATE = "FLYING";
    public static final String GROUND_STATE = "GROUNDED";
    
    public Jyrdo(final float x, final float y) {
        super(NAME, ID, 31, 0.0f, 50.0f, 240.0f, 180.0f, null, x, y);
        this.firstMove = true;
        this.isFlying = true;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(26, 33);
        }
        else {
            this.setHp(25, 31);
        }
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.flightAmt = 4;
        }
        else {
            this.flightAmt = 3;
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.peckDmg = 1;
            this.peckCount = 6;
            this.swoopDmg = 14;
        }
        else {
            this.peckDmg = 1;
            this.peckCount = 5;
            this.swoopDmg = 12;
        }
        this.damage.add(new DamageInfo(this, this.peckDmg));
        this.damage.add(new DamageInfo(this, this.swoopDmg));
        this.damage.add(new DamageInfo(this, 3));
        this.loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle_flap", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case PECK: {
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                for (int i = 0; i < this.peckCount; ++i) {
                    this.playRandomBirdSFx();
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
                }
                break;
            }
            case HEADBUTT: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                this.setMove((byte)2, Intent.UNKNOWN);
                return;
            }
            case GO_AIRBORNE: {
                this.isFlying = true;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "FLYING"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));
                break;
            }
            case BELLOW: {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("BYRD_DEATH"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.DIALOG[0], 1.2f, 1.2f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                break;
            }
            case SWOOP: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            }
            case STUNNED: {
                AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "head_lift"));
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.STUNNED));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playRandomBirdSFx() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_BYRD_ATTACK_" + MathUtils.random(0, 5)));
    }
    
    @Override
    public void changeState(final String stateName) {
        switch (stateName) {
            case "FLYING": {
                this.loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0f);
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle_flap", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                this.updateHitbox(0.0f, 50.0f, 240.0f, 180.0f);
                break;
            }
            case "GROUNDED": {
                this.setMove((byte)4, Intent.STUN);
                this.createIntent();
                this.isFlying = false;
                this.loadAnimation("images/monsters/theCity/byrd/grounded.atlas", "images/monsters/theCity/byrd/grounded.json", 1.0f);
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                this.updateHitbox(10.0f, -50.0f, 240.0f, 180.0f);
                break;
            }
        }
    }
    private void setNextTurn(byte nextTurn) {
		//AbstractDungeon.actionManager.addToBottom(this.setNextTurnAction(nextTurn));
		switch (nextTurn) {
	        case GAZE: {
				this.setMove(this.MOVES[0], nextTurn, Intent.STRONG_DEBUFF);
				return;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);
				return;
			}
		}
	}
    @Override
    protected void getMove(final int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(GAZE, Intent.STRONG_DEBUFF);
            return;
        }
        if (this.isFlying) {
        	if (!AbstractDungeon.player.hasPower("TPH_Confusion")) {
        		
        	}
            if (num < 50) {
                if (this.lastTwoMoves((byte)1)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.4f)) {
                        this.setMove((byte)3, Intent.ATTACK, this.damage.get(1).base);
                    }
                    else {
                        this.setMove((byte)6, Intent.BUFF);
                    }
                }
                else {
                    this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base, this.peckCount, true);
                }
            }
            else if (num < 70) {
                if (this.lastMove((byte)3)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.375f)) {
                        this.setMove((byte)6, Intent.BUFF);
                    }
                    else {
                        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base, this.peckCount, true);
                    }
                }
                else {
                    this.setMove((byte)3, Intent.ATTACK, this.damage.get(1).base);
                }
            }
            else if (this.lastMove((byte)6)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.2857f)) {
                    this.setMove((byte)3, Intent.ATTACK, this.damage.get(1).base);
                }
                else {
                    this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base, this.peckCount, true);
                }
            }
            else {
                this.setMove((byte)6, Intent.BUFF);
            }
        }
        else {
            this.setMove((byte)5, Intent.ATTACK, this.damage.get(2).base);
        }
    }
    
    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("BYRD_DEATH");
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Jyrdo");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
