package com.megacrit.cardcrawl.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class FF_GremlinFat extends AbstractMonster
{
    public static final String ID = "FF_GremlinFat";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 13;
    private static final int HP_MAX = 17;
    private static final int A_2_HP_MIN = 14;
    private static final int A_2_HP_MAX = 18;
    private static final int BLUNT_DAMAGE = 4;
    private static final int A_2_BLUNT_DAMAGE = 5;
    private static final int WEAK_AMT = 1;
    private static final byte BLUNT = 2;
    
    public FF_GremlinFat(final float x, final float y) {
        super(FF_GremlinFat.NAME, "FF_GremlinFat", 17, 0.0f, 0.0f, 110.0f, 220.0f, null, x, y);
        this.dialogY = 30.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(14, 18);
        }
        else {
            this.setHp(13, 17);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 5));
        }
        else {
            this.damage.add(new DamageInfo(this, 4));
        }
        this.loadAnimation("images/monsters/fadingForest/fatGremlin/skeleton.atlas", "images/monsters/fadingForest/fatGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case 99: {
                this.playSfx();
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, FF_GremlinFat.DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
        }
    }
    
    private void playSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1A"));
        }
        else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1B"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1C"));
        }
    }
    
    private void playDeathSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_GREMLINFAT_2A");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_GREMLINFAT_2B");
        }
        else {
            CardCrawlGame.sound.play("VO_GREMLINFAT_2C");
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
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinFat.DIALOG[2], false));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        this.setMove(FF_GremlinFat.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
    }
    
    @Override
    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, FF_GremlinFat.DIALOG[2], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinFat");
        NAME = "False " + FF_GremlinFat.monsterStrings.NAME;
        MOVES = FF_GremlinFat.monsterStrings.MOVES;
        DIALOG = FF_GremlinFat.monsterStrings.DIALOG;
    }
}
