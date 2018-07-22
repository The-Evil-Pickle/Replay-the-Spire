package com.megacrit.cardcrawl.monsters.replay.eastereggs;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class J_louse_3 extends AbstractMonster
{
    public static final String ID = "J_louse_3";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 11;
    private static final int HP_MAX = 17;
    private static final int A_2_HP_MIN = 12;
    private static final int A_2_HP_MAX = 18;
    private static final byte BITE = 3;
    private static final byte WEAKEN = 4;
    private boolean isOpen;
    private static final String CLOSED_STATE = "CLOSED";
    private static final String OPEN_STATE = "OPEN";
    private static final String REAR_IDLE = "REAR_IDLE";
    private final int biteDamage;
    private static final int WEAK_AMT = 2;
    private int specialLeft;
    
    public J_louse_3(final float x, final float y) {
        super(J_louse_3.NAME, J_louse_3.ID, 17, 0.0f, -5.0f, 180.0f, 140.0f, null, x, y);
        this.isOpen = true;
        this.type = EnemyType.ELITE;
        this.specialLeft = 2;
        this.loadAnimation("images/monsters/fadingForest/louseGreen/skeleton.atlas", "images/monsters/fadingForest/louseGreen/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(33, 36);
        }
        else {
            this.setHp(33);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.biteDamage = 6;
        }
        else {
            this.biteDamage = 5;
        }
        this.damage.add(new DamageInfo(this, this.biteDamage));
    }
    
    @Override
    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 8) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 8))));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 8)));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 7))));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 6)));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 6));
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
                if (this.specialLeft > 0) {
                	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SpecialistPower(AbstractDungeon.player, -1), -1));
                	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ChaosPower(AbstractDungeon.player, 2), 1));
                	this.specialLeft--;
                } else {
                	for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (!m.isDying && !m.isEscaping) {
                            AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, 3));
                        }
                    }
                }
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
        if (num < 33 || (num < 75 && this.specialLeft > 1)) {
            if (this.lastTwoMoves((byte)4)) {
                this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
            }
            else {
            	if (this.specialLeft > 0) {
            		this.setMove(J_louse_3.MOVES[0], (byte)4, Intent.DEBUFF);
            	} else {
            		this.setMove(J_louse_3.MOVES[1], (byte)4, Intent.BUFF);
            	}
            }
        }
        else if (this.lastTwoMoves((byte)3)) {
        	if (this.specialLeft > 0) {
        		this.setMove(J_louse_3.MOVES[0], (byte)4, Intent.DEBUFF);
        	} else {
        		this.setMove(J_louse_3.MOVES[1], (byte)4, Intent.BUFF);
        	}
        }
        else {
            this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("J_louse_3");
        NAME = J_louse_3.monsterStrings.NAME;
        MOVES = J_louse_3.monsterStrings.MOVES;
        DIALOG = J_louse_3.monsterStrings.DIALOG;
    }
}
