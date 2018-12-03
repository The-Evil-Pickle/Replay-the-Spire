package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class FF_LouseNormal extends AbstractMonster
{
    public static final String ID = "FF_LouseNormal";
    public static final String THREE_LOUSE = "ThreeLouse";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 18;
    private static final int A_2_HP_MIN = 20;
    private static final byte BITE = 3;
    private static final byte STRENGTHEN = 4;
    private boolean isOpen;
    private static final String CLOSED_STATE = "CLOSED";
    private static final String OPEN_STATE = "OPEN";
    private static final String REAR_IDLE = "REAR_IDLE";
    private int biteDamage;
    private static final int STR_AMOUNT = 3;
    
    public FF_LouseNormal(final float x, final float y) {
        super(FF_LouseNormal.NAME, "FF_LouseNormal", 15, 0.0f, -5.0f, 180.0f, 140.0f, null, x, y);
        this.isOpen = true;
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/louseRed/skeleton.atlas", "images/monsters/theBottom/louseRed/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        //this.loadAnimation("images/monsters/fadingForest/louseRed/skeleton.atlas", "images/monsters/fadingForest/louseRed/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN);
        }
        else {
            this.setHp(HP_MIN);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.biteDamage = 6;
        }
        else {
            this.biteDamage = 5;
        }
        this.damage.add(new DamageInfo(this, this.biteDamage));
    }
    
    @Override
    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 7) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, 7)));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, 6)));
        }
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 3: {
                if (!this.isOpen) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                }
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            }
            case 4: {
                if (!this.isOpen) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.2f));
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR_IDLE"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.9f));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this, 2), 1));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    public void changeState(final String stateName) {
        if (stateName.equals("CLOSED")) {
            this.state.setAnimation(0, "transitiontoclosed", false);
            this.state.addAnimation(0, "idle closed", true, 0.0f);
            this.isOpen = false;
        }
        else if (stateName.equals("OPEN")) {
            this.state.setAnimation(0, "transitiontoopened", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
            this.isOpen = true;
        }
        else if (stateName.equals("REAR_IDLE")) {
            this.state.setAnimation(0, "rear", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
            this.isOpen = true;
        }
        else {
            this.state.setAnimation(0, "transitiontoopened", false);
            this.state.addAnimation(0, "rear", false, 0.0f);
            this.state.addAnimation(0, "idle", true, 0.0f);
            this.isOpen = true;
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (num < 25) {
            if (this.lastTwoMoves((byte)4)) {
                this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
            }
            else {
                this.setMove(FF_LouseNormal.MOVES[0], (byte)4, Intent.BUFF);
            }
        }
        else if (this.lastTwoMoves((byte)3)) {
            this.setMove(FF_LouseNormal.MOVES[0], (byte)4, Intent.BUFF);
        }
        else {
            this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FuzzyLouseNormal");
        NAME = "False " + FF_LouseNormal.monsterStrings.NAME;
        MOVES = FF_LouseNormal.monsterStrings.MOVES;
        DIALOG = FF_LouseNormal.monsterStrings.DIALOG;
    }
}
