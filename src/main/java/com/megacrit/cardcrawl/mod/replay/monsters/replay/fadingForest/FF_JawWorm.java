package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class FF_JawWorm extends AbstractMonster
{
    public static final String ID = "FF_JawWorm";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 55;
    private static final int A_2_HP_MIN = 60;
    private static final float HB_X = 0.0f;
    private static final float HB_Y = -25.0f;
    private static final float HB_W = 260.0f;
    private static final float HB_H = 170.0f;
    private static final int CHOMP_DMG = 11;
    private static final int A_2_CHOMP_DMG = 12;
    private static final int THRASH_DMG = 7;
    private static final int THRASH_BLOCK = 5;
    private static final int BELLOW_STR = 2;
    private static final int A_2_BELLOW_STR = 3;
    private static final int BELLOW_BLOCK = 6;
    private int bellowBlock;
    private int chompDmg;
    private int thrashDmg;
    private int thrashBlock;
    private int bellowStr;
    private static final byte CHOMP = 1;
    private static final byte BELLOW = 2;
    private static final byte THRASH = 3;
    private boolean firstMove;
    
    public FF_JawWorm(final float x, final float y) {
        super(FF_JawWorm.NAME, "FF_JawWorm", 44, 0.0f, -25.0f, 260.0f, 170.0f, null, x, y);
        this.firstMove = true;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN);
        }
        else {
            this.setHp(HP_MIN);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.bellowStr = 4;
            this.bellowBlock = 6;
            this.chompDmg = 12;
            this.thrashDmg = 7;
            this.thrashBlock = 5;
        }
        else {
            this.bellowStr = 3;
            this.bellowBlock = 6;
            this.chompDmg = 11;
            this.thrashDmg = 7;
            this.thrashBlock = 5;
        }
        this.damage.add(new DamageInfo(this, this.chompDmg));
        this.damage.add(new DamageInfo(this, this.thrashDmg));
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/jawWorm/skeleton.atlas", "images/monsters/theBottom/jawWorm/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        //this.loadAnimation("images/monsters/fadingForest/jawWorm/skeleton.atlas", "images/monsters/fadingForest/jawWorm/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "chomp"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            }
            case 2: {
                this.state.setAnimation(0, "tailslam", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2f, ScreenShake.ShakeDur.SHORT, ScreenShake.ShakeIntensity.MED));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.bellowStr), this.bellowStr));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.bellowBlock));
                break;
            }
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.thrashBlock));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    protected void getMove(final int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            return;
        }
        if (num < 25) {
            if (this.lastMove((byte)1)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.5625f)) {
                    this.setMove(FF_JawWorm.MOVES[0], (byte)2, Intent.DEFEND_BUFF);
                }
                else {
                    this.setMove((byte)3, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                }
            }
            else {
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            }
        }
        else if (num < 55) {
            if (this.lastTwoMoves((byte)3)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.357f)) {
                    this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
                }
                else {
                    this.setMove(FF_JawWorm.MOVES[0], (byte)2, Intent.DEFEND_BUFF);
                }
            }
            else {
                this.setMove((byte)3, Intent.ATTACK_DEFEND, this.damage.get(1).base);
            }
        }
        else if (this.lastMove((byte)2)) {
            if (AbstractDungeon.aiRng.randomBoolean(0.416f)) {
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            }
            else {
                this.setMove((byte)3, Intent.ATTACK_DEFEND, this.damage.get(1).base);
            }
        }
        else {
            this.setMove(FF_JawWorm.MOVES[0], (byte)2, Intent.DEFEND_BUFF);
        }
    }
    
    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("JAW_WORM_DEATH");
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("JawWorm");
        NAME = "False" + FF_JawWorm.monsterStrings.NAME;
        MOVES = FF_JawWorm.monsterStrings.MOVES;
        DIALOG = FF_JawWorm.monsterStrings.DIALOG;
    }
}
