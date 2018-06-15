package com.megacrit.cardcrawl.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class FF_GremlinNob extends AbstractMonster
{
    public static final String ID = "FF_GremlinNob";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 80;
    private static final int A_HP = 90;
    private static final int BASH_DMG = 6;
    private static final int RUSH_DMG = 14;
    private static final int A_BASH_DMG = 8;
    private static final int A_RUSH_DMG = 16;
    private static final int DEBUFF_AMT = 2;
    private int bashDmg;
    private int rushDmg;
    private static final byte BULL_RUSH = 1;
    private static final byte SKULL_BASH = 2;
    private static final byte BELLOW = 3;
    private static final int ANGRY_LEVEL = 1;
    private boolean usedBellow;
    
    public FF_GremlinNob() {
        super(FF_GremlinNob.NAME, "FF_GremlinNob", 86, -70.0f, -10.0f, 270.0f, 380.0f, null);
        this.usedBellow = false;
        this.intentOffsetX = -30.0f * Settings.scale;
        //this.type = EnemyType.ELITE;
        this.dialogX = -60.0f * Settings.scale;
        this.dialogY = 50.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(FF_GremlinNob.A_HP);
        }
        else {
            this.setHp(FF_GremlinNob.HP);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.bashDmg = A_BASH_DMG;
            this.rushDmg = A_RUSH_DMG;
        }
        else {
            this.bashDmg = BASH_DMG;
            this.rushDmg = RUSH_DMG;
        }
        this.damage.add(new DamageInfo(this, this.rushDmg));
        this.damage.add(new DamageInfo(this, this.bashDmg));
        this.loadAnimation("images/monsters/fadingForest/nobGremlin/skeleton.atlas", "images/monsters/fadingForest/nobGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    /*@Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
    }*/
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case BELLOW: {
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FF_GremlinNob.DIALOG[0], 1.0f, 3.0f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, FF_GremlinNob.ANGRY_LEVEL), FF_GremlinNob.ANGRY_LEVEL));
                break;
            }
            case SKULL_BASH: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, FF_GremlinNob.DEBUFF_AMT, true), FF_GremlinNob.DEBUFF_AMT));
                break;
            }
            case BULL_RUSH: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
        }
        else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (!this.usedBellow) {
            this.usedBellow = true;
            this.setMove((byte)3, Intent.BUFF);
            return;
        }
        if (num < 33) {
            this.setMove(GremlinNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
            return;
        }
        if (this.lastTwoMoves((byte)1)) {
            this.setMove(GremlinNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
        }
        else {
            this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
        }
    }
    /*
    @Override
    public void die() {
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        CardCrawlGame.music.fadeOutTempBGM();
    }
    */
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinNob");
        NAME = "False " + FF_GremlinNob.monsterStrings.NAME;
        MOVES = FF_GremlinNob.monsterStrings.MOVES;
        DIALOG = FF_GremlinNob.monsterStrings.DIALOG;
    }
}
