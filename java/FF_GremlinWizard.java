package com.megacrit.cardcrawl.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class FF_GremlinWizard extends AbstractMonster
{
    public static final String ID = "FF_GremlinWizard";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 21;
    private static final int HP_MAX = 25;
    private static final int A_2_HP_MIN = 22;
    private static final int A_2_HP_MAX = 26;
    private static final int MAGIC_DAMAGE = 25;
    private static final int A_2_MAGIC_DAMAGE = 30;
    private static final int CHARGE_LIMIT = 3;
    private int currentCharge;
    private static final byte DOPE_MAGIC = 1;
    private static final byte CHARGE = 2;
    
    public FF_GremlinWizard(final float x, final float y) {
        super(FF_GremlinWizard.NAME, "FF_GremlinWizard", 25, 40.0f, -5.0f, 130.0f, 180.0f, null, x - 35.0f, y);
        this.currentCharge = 1;
        this.dialogX = 0.0f * Settings.scale;
        this.dialogY = 50.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(22, 26);
        }
        else {
            this.setHp(21, 25);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 24));
        }
        else {
            this.damage.add(new DamageInfo(this, 20));
        }
        this.loadAnimation("images/monsters/fadingForest/wizardGremlin/skeleton.atlas", "images/monsters/fadingForest/wizardGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 2: {
                ++this.currentCharge;
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, FF_GremlinWizard.DIALOG[1]));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                if (this.currentCharge == 3) {
                    this.playSfx();
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FF_GremlinWizard.DIALOG[2], 1.5f, 3.0f));
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, FF_GremlinWizard.MOVES[1], (byte)1, Intent.ATTACK, this.damage.get(0).base));
                    break;
                }
                this.setMove(FF_GremlinWizard.MOVES[0], (byte)2, Intent.UNKNOWN);
                break;
            }
            case 1: {
                this.currentCharge = 1;
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                this.setMove(FF_GremlinWizard.MOVES[0], (byte)2, Intent.UNKNOWN);
                break;
            }
            case 99: {
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, FF_GremlinWizard.DIALOG[3], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
        }
    }
    
    private void playSfx() {
        final int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINDOPEY_1A"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINDOPEY_1B"));
        }
    }
    
    private void playDeathSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2A");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2B");
        }
        else {
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2C");
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
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 3.0f, FF_GremlinWizard.DIALOG[4], false));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        this.setMove(FF_GremlinWizard.MOVES[0], (byte)2, Intent.UNKNOWN);
    }
    
    @Override
    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 3.0f, FF_GremlinWizard.DIALOG[4], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWizard");
        NAME = "False " + FF_GremlinWizard.monsterStrings.NAME;
        MOVES = FF_GremlinWizard.monsterStrings.MOVES;
        DIALOG = FF_GremlinWizard.monsterStrings.DIALOG;
    }
}
