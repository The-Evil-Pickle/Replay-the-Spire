package com.megacrit.cardcrawl.monsters.replay;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.GremCookAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class GremlinCook extends AbstractMonster
{
    public static final String ID = "GremlinCook";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 12;
    private static final int HP_MAX = 15;
    private static final int A_2_HP_MIN = 13;
    private static final int A_2_HP_MAX = 17;
    private static final int BLOCK_AMOUNT = 7;
    private static final int A_2_BLOCK_AMOUNT = 8;
    private static final int BASH_DAMAGE = 6;
    private static final int A_2_BASH_DAMAGE = 8;
    private int blockAmt;
    private int bashDmg;
    private int flexAmt;
    private static final byte PROTECT = 1;
    private static final byte BASH = 2;
    private static final AbstractCard burnCard;
    
    public GremlinCook(final float x, final float y) {
        super(NAME, ID, 16, 0.0f, 0.0f, 120.0f, 210.0f, "images/monsters/exord/cook.png", x, y);
        this.dialogY = 60.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(14, 18);
            this.blockAmt = 6;
        }
        else {
            this.setHp(13, 16);
            this.blockAmt = 5;
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.bashDmg = 4;
        }
        else {
            this.bashDmg = 2;
        }
        if (AbstractDungeon.ascensionLevel >= 16) {
            this.flexAmt = 3;
        }
        else {
            this.flexAmt = 2;
        }
        this.damage.add(new DamageInfo(this, this.bashDmg));
        //this.loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0f);
        //final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new GremCookAction(this, this.blockAmt, this.flexAmt));
                int aliveCount = 0;
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDying && !m.isEscaping) {
                        ++aliveCount;
                    }
                }
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                if (aliveCount > 1) {
                    this.setMove(GremlinCook.MOVES[0], (byte)1, Intent.BUFF);
                    break;
                }
                this.setMove(GremlinCook.MOVES[1], (byte)2, Intent.ATTACK, this.damage.get(0).base);
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(burnCard.makeCopy()));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, GremlinCook.MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(0).base));
                break;
            }
            case 99: {
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, GremlinCook.DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
        }
    }
    
    @Override
    public void die() {
        super.die();
    }
    
    @Override
    public void escapeNext() {
        if (!this.cannotEscape && !this.escapeNext) {
            this.escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, GremlinCook.DIALOG[2], false));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        this.setMove(GremlinCook.MOVES[0], (byte)1, Intent.BUFF);
    }
    
    @Override
    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0f, GremlinCook.DIALOG[2], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinCook");
        NAME = GremlinCook.monsterStrings.NAME;
        MOVES = GremlinCook.monsterStrings.MOVES;
        DIALOG = GremlinCook.monsterStrings.DIALOG;
        burnCard = new Burn();
        burnCard.upgrade();
    }
}
