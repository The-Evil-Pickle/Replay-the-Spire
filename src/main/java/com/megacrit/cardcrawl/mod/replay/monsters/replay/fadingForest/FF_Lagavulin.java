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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import org.apache.logging.log4j.*;

public class FF_Lagavulin extends AbstractMonster
{
    private static final Logger logger;
    public static final String ID = "FF_Lagavulin";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 80;
    private static final int HP_MAX = 80;
    private static final int A_2_HP_MIN = 90;
    private static final int A_2_HP_MAX = 90;
    private static final byte DEBUFF = 1;
    private static final byte STRONG_ATK = 3;
    private static final byte OPEN = 4;
    private static final byte IDLE = 5;
    private static final byte OPEN_NATURAL = 6;
    private static final String DEBUFF_NAME;
    private static final int STRONG_ATK_DMG = 18;
    private static final int DEBUFF_AMT = -1;
    private static final int A_2_STRONG_ATK_DMG = 20;
    private int attackDmg;
    private static final int ARMOR_AMT = 8;
    private boolean isOut;
    private boolean asleep;
    private boolean isOutTriggered;
    private int idleCount;
    private int debuffTurnCount;
    
    public FF_Lagavulin(final boolean setAsleep) {
        super(FF_Lagavulin.NAME, "FF_Lagavulin", 90, 0.0f, -25.0f, 320.0f, 220.0f, null, -50.0f, 20.0f);
        this.isOut = false;
        this.isOutTriggered = false;
        this.idleCount = 1;
        this.debuffTurnCount = 0;
        //this.type = EnemyType.ELITE;
        this.dialogX = -100.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(90);
        }
        else {
            this.setHp(75);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.attackDmg = 17;
        }
        else {
            this.attackDmg = 16;
        }
        this.damage.add(new DamageInfo(this, this.attackDmg));
        this.asleep = setAsleep;
        //this.loadAnimation("images/monsters/fadingForest/lagavulin/skeleton.atlas", "images/monsters/fadingForest/lagavulin/skeleton.json", 1.0f);
        this.tint.color = FadingForestBoss.tintColor.cpy();
        this.loadAnimation("images/monsters/theBottom/lagavulin/skeleton.atlas", "images/monsters/theBottom/lagavulin/skeleton.json", 1.0f);
        this.tint.changeColor(FadingForestBoss.tintColor.cpy());
        AnimationState.TrackEntry e = null;
        if (!this.asleep) {
            this.isOut = true;
            this.isOutTriggered = true;
            e = this.state.setAnimation(0, "Idle_2", true);
            this.updateHitbox(0.0f, -25.0f, 320.0f, 370.0f);
        }
        else {
            e = this.state.setAnimation(0, "Idle_1", true);
        }
        this.stateData.setMix("Attack", "Idle_2", 0.25f);
        this.stateData.setMix("Hit", "Idle_2", 0.25f);
        this.stateData.setMix("Idle_1", "Idle_2", 0.5f);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void usePreBattleAction() {
        if (this.asleep) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 8), 8));
        }
        else {
			/*
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");*/
            this.setMove(FF_Lagavulin.DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
        }
    }
    
    @Override
    public void takeTurn() {
        Label_0543: {
            switch (this.nextMove) {
                case 1: {
                    this.debuffTurnCount = 0;
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DEBUFF"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3f));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DecrepitPower(AbstractDungeon.player, 2, true), 2));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new LanguidPower(AbstractDungeon.player, 2, true), 2));
                    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                    break;
                }
                case 3: {
                    ++this.debuffTurnCount;
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3f));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                    break;
                }
                case 5: {
                    ++this.idleCount;
                    if (this.idleCount >= 3) {
                        FF_Lagavulin.logger.info("idle happened");
                        this.isOutTriggered = true;
                        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, Intent.ATTACK, this.damage.get(0).base));
                    }
                    else {
                        this.setMove((byte)5, Intent.SLEEP);
                    }
                    switch (this.idleCount) {
                        case 1: {
                            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FF_Lagavulin.DIALOG[1], 0.5f, 2.0f));
                            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                            break;
                        }
                        case 2: {
                            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FF_Lagavulin.DIALOG[2], 0.5f, 2.0f));
                            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.STUNNED));
                    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                    break;
                }
                case 6: {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                    this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
                    this.createIntent();
                    this.isOutTriggered = true;
                    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                    break;
                }
            }
        }
    }
    
    @Override
    public void changeState(final String stateName) {
        if (stateName.equals("ATTACK")) {
            this.state.setAnimation(0, "Attack", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0f);
        }
        else if (stateName.equals("DEBUFF")) {
            this.state.setAnimation(0, "Debuff", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0f);
        }
        else if (stateName.equals("OPEN")) {
            this.isOut = true;
            this.updateHitbox(0.0f, -25.0f, 320.0f, 360.0f);
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FF_Lagavulin.DIALOG[3], 0.5f, 2.0f));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this, this, "Metallicize", 8));
            //CardCrawlGame.music.unsilenceBGM();
            //AbstractDungeon.scene.fadeOutAmbiance();
            //AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
            this.state.setAnimation(0, "Coming_out", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0f);
        }
    }
    
    @Override
    public void damage(final DamageInfo info) {
        super.damage(info);
        if (this.currentHealth != this.maxHealth && !this.isOutTriggered) {
            this.setMove((byte)4, Intent.STUN);
            this.createIntent();
            this.isOutTriggered = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
        }
        else if (this.isOutTriggered && info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0f);
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (this.isOut) {
            if (this.debuffTurnCount < 2) {
                if (this.lastTwoMoves((byte)3)) {
                    this.setMove(FF_Lagavulin.DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
                }
                else {
                    this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
                }
            }
            else {
                this.setMove(FF_Lagavulin.DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
            }
        }
        else {
            this.setMove((byte)5, Intent.SLEEP);
        }
    }
    
    @Override
    public void die() {
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        CardCrawlGame.music.fadeOutTempBGM();
    }
    
    static {
        logger = LogManager.getLogger(FF_Lagavulin.class.getName());
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Lagavulin");
        NAME = "False " + FF_Lagavulin.monsterStrings.NAME;
        MOVES = FF_Lagavulin.monsterStrings.MOVES;
        DIALOG = FF_Lagavulin.monsterStrings.DIALOG;
        DEBUFF_NAME = FF_Lagavulin.MOVES[0];
    }
}
