package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.SporeCloudPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class FF_FungiBeast extends AbstractMonster
{
    public static final String ID = "FF_FungiBeast";
    public static final String DOUBLE_ENCOUNTER = "TwoFungiBeasts";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 22;
    private static final int HP_MAX = 28;
    private static final int A_2_HP_MIN = 24;
    private static final int A_2_HP_MAX = 28;
    private static final float HB_X = 0.0f;
    private static final float HB_Y = -16.0f;
    private static final float HB_W = 260.0f;
    private static final float HB_H = 170.0f;
    private int biteDamage;
    private int strAmt;
    private static final int BITE_DMG = 6;
    private static final int GROW_STR = 3;
    private static final int A_2_GROW_STR = 4;
    private static final byte BITE = 1;
    private static final byte GROW = 2;
    private static final int VULN_AMT = 2;
    
    public FF_FungiBeast(final float x, final float y) {
        super(FF_FungiBeast.NAME, "FF_FungiBeast", 28, 0.0f, -16.0f, 260.0f, 170.0f, null, x, y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(24);
        }
        else {
            this.setHp(20);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.strAmt = 4;
            this.biteDamage = 4;
        }
        else {
            this.strAmt = 3;
            this.biteDamage = 4;
        }
        this.loadAnimation("images/monsters/fadingForest/fungi/skeleton.atlas", "images/monsters/fadingForest/fungi/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(MathUtils.random(0.7f, 1.0f));
        this.damage.add(new DamageInfo(this, this.biteDamage));
    }
    
    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FadingPower(this, 3)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SporeCloudPower(this, 2)));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strAmt), this.strAmt));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    protected void getMove(final int num) {
        if (num < 60) {
            if (this.lastTwoMoves((byte)1)) {
                this.setMove(FF_FungiBeast.MOVES[0], (byte)2, Intent.BUFF);
            }
            else {
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            }
        }
        else if (this.lastMove((byte)2)) {
            this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
        }
        else {
            this.setMove(FF_FungiBeast.MOVES[0], (byte)2, Intent.BUFF);
        }
    }
    
    @Override
    public void changeState(final String key) {
        switch (key) {
            case "ATTACK": {
                this.state.setAnimation(0, "Attack", false);
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
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FungiBeast");
        NAME = "False " + FF_FungiBeast.monsterStrings.NAME;
        MOVES = FF_FungiBeast.monsterStrings.MOVES;
        DIALOG = FF_FungiBeast.monsterStrings.DIALOG;
    }
}
