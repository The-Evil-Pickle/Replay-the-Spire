package com.megacrit.cardcrawl.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class MimicMonster extends AbstractMonster
{
    public static final String ID = "ReplayMimic";
    public static final String NAME;
    private static final MonsterStrings monsterStrings;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int[] HP = {80, 120, 160};
    private static final int HP_DV = 2;
    private static final int A_HP = 15;
	private static final int[] LV_ARMOR_MAX = {6, 10, 15};
	private static final int[] LV_ARMOR_GAIN = {4, 5, 5};
	private static final int[] LV_DOUBLESTRIKE_DMG = {2, 2, 3};
	private static final int A_DOUBLESTRIKE_DMG = 1;
	private static final int[] LV_BIGSTRIKE_DMG = {9, 11, 13};
	private static final int A_BIGSTRIKE_DMG = 2;
	private static final int[] LV_STR = {1, 1, 2};
	private static final int[] LV_THORN = {1, 2, 2};
	private static final int[] LV_DAZE = {1, 2, 2};
	private static final int[] LV_MAD = {2, 3, 3};
	private int lv;
    private int lid_armor_gain;
    private int lid_armor_max;
    private int lid_artifact;
    private int doubleStrike_dmg;
    private int doubleStrike_slime;
    private int bigHit_dmg;
    private int selfBuff_str;
    private int selfBuff_thrns;
    private int boo_surprise;
    private int boo_dazed;
	private int mad_duration;
    private static final byte CLOSE_LID = 1;
    private static final byte DOUBLE_STRIKE = 2;
    private static final byte BLADE_MIMICRY = 3;
    private static final byte MAD_MIMICRY = 4;
    private static final byte BIG_HIT = 5;
    private static final byte BOO = 6;
    private boolean firstMove;
    public static final String ARMOR_BREAK = "ARMOR_BREAK";
    
    public MimicMonster(final float x, final float y, final int lv) {
        super(MimicMonster.NAME, MimicMonster.ID, 72, 20.0f, -6.0f, 350.0f, 260.0f, null, x, y);
		this.lv = lv;
        this.firstMove = true;
        this.loadAnimation("images/monsters/theCity/shellMonster/skeleton.atlas", "images/monsters/theCity/shellMonster/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2f);
        e.setTimeScale(0.8f);
        this.dialogX = -50.0f * Settings.scale;
		this.lid_artifact = 1;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(HP[lv] - HP_DV + A_HP, HP[lv] - HP_DV + A_HP);
			if (this.lv > 0) {
				this.lid_artifact = 2;
			}
        }
        else {
            this.setHp(HP[lv] - HP_DV, HP[lv] - HP_DV);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
			this.doubleStrike_dmg = LV_DOUBLESTRIKE_DMG[lv] + A_DOUBLESTRIKE_DMG;
            this.bigHit_dmg = LV_BIGSTRIKE_DMG[lv] + A_BIGSTRIKE_DMG;
        }
        else {
            this.doubleStrike_dmg = LV_DOUBLESTRIKE_DMG[lv];
            this.bigHit_dmg = LV_BIGSTRIKE_DMG[lv];
        }
		this.lid_armor_gain = LV_ARMOR_GAIN[lv];
		this.lid_armor_max = LV_ARMOR_MAX[lv];
		this.mad_duration = LV_MAD[lv];
		this.boo_dazed = LV_DAZE[lv];
		this.selfBuff_str = LV_STR[lv];
		this.selfBuff_thrns = LV_THORN[lv];
        this.damage.add(new DamageInfo(this, this.doubleStrike_dmg));
        this.damage.add(new DamageInfo(this, this.bigHit_dmg));
		this.boo_surprise = 2;
    }
    
    public MimicMonster(int lv) {
        this(-20.0f, 10.0f, lv);
    }
    
    @Override
    public void usePreBattleAction() {
		AbstractPower offGuard = new EntanglePower(AbstractDungeon.player);
		offGuard.name = MimicMonster.DIALOG[0];
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, offGuard));
		this.boo_surprise = AbstractDungeon.player.energy.energy - 1;
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 14)));
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 14));
    }
    
	private void setMoveNow(byte nextTurn) {
		switch (nextTurn) {
            case CLOSE_LID: {
				this.setMove(MimicMonster.MOVES[0], nextTurn, Intent.BUFF);
				break;
			}
            case DOUBLE_STRIKE: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(0).base, 2, true);
				break;
			}
            case BLADE_MIMICRY: {
				this.setMove(MimicMonster.MOVES[1], nextTurn, Intent.BUFF);
				break;
			}
			case MAD_MIMICRY: {
				this.setMove(MimicMonster.MOVES[2], nextTurn, Intent.DEBUFF);		
				break;
			}
			case BIG_HIT: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(1).base);
				break;
			}
			case BOO: {
				this.setMove(MimicMonster.MOVES[3], nextTurn, Intent.STRONG_DEBUFF);		
				break;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);	
				break;	
			}
		}
	}
	
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case CLOSE_LID: {
				if (!this.hasPower(PlatedArmorPower.POWER_ID) || this.getPower(PlatedArmorPower.POWER_ID).amount + this.lid_armor_gain <= this.lid_armor_max) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, this.lid_armor_gain), this.lid_armor_gain));
				} else if (this.getPower(PlatedArmorPower.POWER_ID).amount < this.lid_armor_max) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, this.lid_armor_max - this.getPower(PlatedArmorPower.POWER_ID).amount), this.lid_armor_max - this.getPower(PlatedArmorPower.POWER_ID).amount));
				}
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, this.lid_artifact), this.lid_artifact));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case DOUBLE_STRIKE: {
				AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                for (int i = 0; i < 2; ++i) {
					AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0f, 25.0f) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0f, 25.0f) * Settings.scale, Color.GOLD.cpy()), 0.0f));
					AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                }
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAndDeckAction(new Slimed()));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case BIG_HIT: {
				AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
				AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case BLADE_MIMICRY: {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, this.selfBuff_thrns), this.selfBuff_thrns));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.selfBuff_str), this.selfBuff_str));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case MAD_MIMICRY: {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.mad_duration, true), this.mad_duration));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.mad_duration, true), this.mad_duration));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAndDeckAction(new MimicStatus()));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            case BOO: {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new MimicSurprisePower(AbstractDungeon.player, this.boo_surprise, true), this.boo_surprise));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.boo_dazed));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
        }
    }
    
    @Override
    public void changeState(final String stateName) {
        switch (stateName) {
            case "ATTACK": {
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0f);
                break;
            }
            case "ARMOR_BREAK": {
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3f));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3f));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                this.setMove((byte)4, Intent.STUN);
                this.createIntent();
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
    
    @Override
    protected void getMove(final int num) {
		
		if (this.lastMove(CLOSE_LID)) {
			this.setMoveNow(DOUBLE_STRIKE);
			return;
		}
		if (this.lastMove(DOUBLE_STRIKE)) {
			if (num > 66 || GameActionManager.turn < 5) {
				this.setMoveNow(BLADE_MIMICRY);
			} else {
				this.setMoveNow(MAD_MIMICRY);
			}
			return;
		}
		if (this.lastMove(BLADE_MIMICRY) || this.lastMove(MAD_MIMICRY)) {
			this.setMoveNow(BIG_HIT);
			return;
		}
		if (this.lastMove(BIG_HIT)) {
			this.setMoveNow(BOO);
			return;
		}
		this.setMoveNow(CLOSE_LID);
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MimicMonster.ID);
        NAME = MimicMonster.monsterStrings.NAME;
        MOVES = MimicMonster.monsterStrings.MOVES;
        DIALOG = MimicMonster.monsterStrings.DIALOG;
    }
}
