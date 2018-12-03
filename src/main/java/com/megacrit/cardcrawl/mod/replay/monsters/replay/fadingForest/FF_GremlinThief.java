package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class FF_GremlinThief extends AbstractMonster
{
    public static final String ID = "FF_GremlinThief";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int THIEF_DAMAGE = 9;
    private static final int A_2_THIEF_DAMAGE = 10;
    private static final byte PUNCTURE = 1;
    private static final int HP_MIN = 24;
    private static final int A_2_HP_MIN = 28;
    private int thiefDamage;
    
    public FF_GremlinThief(final float x, final float y) {
        super(FF_GremlinThief.NAME, "FF_GremlinThief", 14, 0.0f, 0.0f, 120.0f, 160.0f, null, x, y);
        this.dialogY = 50.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN);
        }
        else {
            this.setHp(HP_MIN);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.thiefDamage = 10;
        }
        else {
            this.thiefDamage = 9;
        }
        this.damage.add(new DamageInfo(this, this.thiefDamage));
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/thiefGremlin/skeleton.atlas", "images/monsters/theBottom/thiefGremlin/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        //this.loadAnimation("images/monsters/fadingForest/thiefGremlin/skeleton.atlas", "images/monsters/fadingForest/thiefGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                if (!this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.thiefDamage));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
            case 99: {
                this.playSfx();
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, FF_GremlinThief.DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
        }
    }
    
    private void playSfx() {
        final int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINSPAZZY_1A"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINSPAZZY_1B"));
        }
    }
    
    private void playDeathSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2A");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2B");
        }
        else {
            CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2C");
        }
    }
    
    @Override
    public void die() {
        super.die();
        this.playDeathSfx();
    }
    
    @Override
    public void escapeNext() {
        if (!this.cannotEscape && !this.escapeNext) {
            this.escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinThief.DIALOG[2], false));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }
    
    @Override
    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinThief.DIALOG[2], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinThief");
        NAME = "False " + FF_GremlinThief.monsterStrings.NAME;
        MOVES = FF_GremlinThief.monsterStrings.MOVES;
        DIALOG = FF_GremlinThief.monsterStrings.DIALOG;
    }
}
