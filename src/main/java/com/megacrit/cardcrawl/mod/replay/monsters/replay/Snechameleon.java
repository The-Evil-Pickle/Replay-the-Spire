package com.megacrit.cardcrawl.mod.replay.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.AgingPower;
import com.megacrit.cardcrawl.mod.replay.powers.ColorShiftPower;
import com.megacrit.cardcrawl.mod.replay.powers.StoryPower;
import com.megacrit.cardcrawl.mod.replay.powers.TPH_ConfusionPower;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EnemyData.MonsterType;
import com.megacrit.cardcrawl.cards.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class Snechameleon extends AbstractMonster
{
    public static final String ID = "Replay:Snechameleon";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final byte GLARE = 1;
    private static final byte BITE = 2;
    private static final byte TAIL = 3;
    private static final int BITE_DAMAGE = 5;
    private static final int BITE_HITS = 3;
    private static final int TAIL_DAMAGE = 6;
    private static final int A_2_BITE_DAMAGE = 6;
    private static final int A_2_BITE_HITS = 3;
    private static final int A_2_TAIL_DAMAGE = 9;
    private int biteDmg;
    private int biteMultihit;
    private int tailDmg;
    private static final int VULNERABLE_AMT = 2;
    private static final int HP_MIN = 140;
    private static final int HP_MAX = 148;
    private static final int A_2_HP_MIN = 145;
    private static final int A_2_HP_MAX = 155;
    private boolean firstTurn;
    
    public Snechameleon() {
        this(0.0f, 0.0f);
    }
    
    public Snechameleon(final float x, final float y) {
        super(Snechameleon.NAME, ID, HP_MAX, -30.0f, -20.0f, 310.0f, 305.0f, null, x, y);
        //this.firstTurn = true;
        this.type = AbstractMonster.EnemyType.ELITE;
        this.loadAnimation("images/monsters/theCity/reptile/skeleton.atlas", "images/monsters/theCity/reptile/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1f);
        e.setTimeScale(0.8f);
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_2_HP_MIN, A_2_HP_MAX);
        }
        else {
            this.setHp(HP_MIN, HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.biteDmg = A_2_BITE_DAMAGE;
            this.tailDmg = A_2_TAIL_DAMAGE;
            this.biteMultihit = A_2_BITE_HITS;
        }
        else {
            this.biteDmg = BITE_DAMAGE;
            this.tailDmg = TAIL_DAMAGE;
            this.biteMultihit = BITE_HITS;
        }
        this.damage.add(new DamageInfo(this, this.biteDmg));
        this.damage.add(new DamageInfo(this, this.tailDmg));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SNECKO_GLARE"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntimidateEffect(this.hb.cX, this.hb.cY), 0.5f));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 1.0f, 1.0f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new TPH_ConfusionPower(AbstractDungeon.player, 1), 1));
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
                for (int i=0; i < this.biteMultihit; i++) {
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale, Color.CHARTREUSE.cpy()), 0.3f));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                }
                break;
            }
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ColorShiftPower(this, (AbstractDungeon.ascensionLevel >= 18) ? 2 : 1)));
    }
    
    @Override
    public void changeState(final String stateName) {
        switch (stateName) {
            case "ATTACK": {
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0f);
                break;
            }
            case "ATTACK_2": {
                this.state.setAnimation(0, "Attack_2", false);
                this.state.addAnimation(0, "Idle", true, 0.0f);
                break;
            }
        }
    }
    
    @Override
    public void damage(final DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0f);
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(Snechameleon.MOVES[0], (byte)1, Intent.DEBUFF);
            return;
        }
        if (num < 30) {
        	if (this.lastMove((byte)3)) {
        		this.setMove(Snechameleon.MOVES[0], (byte)1, Intent.DEBUFF);
        		return;
        	}
            this.setMove(Snechameleon.MOVES[1], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
            return;
        }
        if (num > 95 || this.lastTwoMoves((byte)2)) {
        	this.setMove(Snechameleon.MOVES[0], (byte)1, Intent.DEBUFF);
        }
        else {
            this.setMove(Snechameleon.MOVES[2], (byte)2, Intent.ATTACK, this.damage.get(0).base, this.biteMultihit, true);
        }
    }
    
    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("SNECKO_DEATH");
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Replay:Snechameleon");
        NAME = Snechameleon.monsterStrings.NAME;
        MOVES = Snechameleon.monsterStrings.MOVES;
        DIALOG = Snechameleon.monsterStrings.DIALOG;
    }
}
