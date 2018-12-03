package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.helpers.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.unlock.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class FF_ComboSlime_L extends AbstractMonster
{
    public static final String ID = "FF_ComboSlime_L";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    private static final String WOUND_NAME;
    private static final String SPLIT_NAME;
    private static final String WEAK_NAME;
    public static final int HP_MIN = 80;
    public static final int A_2_HP_MIN = 100;
    public static final int W_TACKLE_DMG = 11;
    public static final int N_TACKLE_DMG = 16;
    public static final int A_2_W_TACKLE_DMG = 12;
    public static final int A_2_N_TACKLE_DMG = 18;
    public static final int WEAK_TURNS = 2;
    public static final int WOUND_COUNT = 2;
    private static final byte WOUND_TACKLE = 1;
    private static final byte NORMAL_TACKLE = 2;
    private static final byte SPLIT = 3;
    private static final byte WEAK_LICK = 4;
    private float saveX;
    private float saveY;
    private boolean splitTriggered;
    
    public FF_ComboSlime_L(final float x, final float y) {
        this(x, y, 0, 70);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN);
        }
        else {
            this.setHp(HP_MIN);
        }
    }
    
    public FF_ComboSlime_L(final float x, final float y, final int poisonAmount, final int newHealth) {
        super(FF_ComboSlime_L.NAME, "FF_ComboSlime_L", newHealth, 0.0f, 0.0f, 300.0f, 180.0f, null, x, y);
        this.saveX = x;
        this.saveY = y;
        this.splitTriggered = false;
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 18));
        }
        else {
            this.damage.add(new DamageInfo(this, 11));
            this.damage.add(new DamageInfo(this, 16));
        }
        this.powers.add(new SplitPower(this));
        if (poisonAmount >= 1) {
            this.powers.add(new PoisonPower(this, this, poisonAmount));
        }
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/slimeL/skeleton.atlas", "images/monsters/theBottom/slimeL/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        //this.loadAnimation("images/monsters/fadingForest/slimeL/skeleton.atlas", "images/monsters/fadingForest/slimeL/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 4: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0f, 0.1f));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0f));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new SpikeSlime_M(this.saveX - 134.0f, this.saveY + MathUtils.random(-4.0f, 4.0f), 0, this.currentHealth), false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_M(this.saveX + 134.0f, this.saveY + MathUtils.random(-4.0f, 4.0f), 0, this.currentHealth), false));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                this.setMove(FF_ComboSlime_L.SPLIT_NAME, (byte)3, Intent.UNKNOWN);
                break;
            }
        }
    }
    
    @Override
    public void damage(final DamageInfo info) {
        super.damage(info);
        if (!this.isDying && this.currentHealth <= this.maxHealth / 2.0f && this.nextMove != 3 && !this.splitTriggered) {
            this.setMove(FF_ComboSlime_L.SPLIT_NAME, (byte)3, Intent.UNKNOWN);
            this.createIntent();
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, FF_ComboSlime_L.SPLIT_NAME, (byte)3, Intent.UNKNOWN));
            this.splitTriggered = true;
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (num < 30) {
            if (this.lastTwoMoves((byte)1)) {
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
                }
                else {
                    this.setMove(FF_ComboSlime_L.WEAK_NAME, (byte)4, Intent.DEBUFF);
                }
            }
            else {
                this.setMove(FF_ComboSlime_L.WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
            }
        }
        else if (num < 70) {
            if (this.lastMove((byte)2)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.4f)) {
                    this.setMove(FF_ComboSlime_L.WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                }
                else {
                    this.setMove(FF_ComboSlime_L.WEAK_NAME, (byte)4, Intent.DEBUFF);
                }
            }
            else {
                this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
            }
        }
        else if (this.lastTwoMoves((byte)4)) {
            if (AbstractDungeon.aiRng.randomBoolean(0.4f)) {
                this.setMove(FF_ComboSlime_L.WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
            }
            else {
                this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
            }
        }
        else {
            this.setMove(FF_ComboSlime_L.WEAK_NAME, (byte)4, Intent.DEBUFF);
        }
    }
    
    @Override
    public void die() {
        super.die();
        for (final AbstractGameAction a : AbstractDungeon.actionManager.actions) {
            if (a instanceof SpawnMonsterAction) {
                return;
            }
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FF_ComboSlime_L");
        NAME = FF_ComboSlime_L.monsterStrings.NAME;
        MOVES = FF_ComboSlime_L.monsterStrings.MOVES;
        WOUND_NAME = FF_ComboSlime_L.MOVES[0];
        SPLIT_NAME = FF_ComboSlime_L.MOVES[1];
        WEAK_NAME = FF_ComboSlime_L.MOVES[2];
    }
}
