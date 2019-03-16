package com.megacrit.cardcrawl.mod.replay.monsters.replay.hec;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.SpawnForestMonsterAction;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.mod.replay.powers.EnemyLifeBindPower;
import com.megacrit.cardcrawl.mod.replay.powers.MightPower;
import com.megacrit.cardcrawl.mod.replay.powers.OffTheRailsPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

public class Conductor extends AbstractMonster {
	public static final String ID = "Replay:Conductor";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private HellsEngine engine;
    public AbstractMonster dynamite;
    private static final UIStrings bombStrings = CardCrawlGame.languagePack.getUIString("Replay:BombIntent");
    private static final Texture BOMB_INTENT_TEXTURE = ImageMaster.loadImage("images/ui/replay/intent/attack_intent_bomb.png");

    public static final int GUN_DMG = 12;
    public static final int GUN_DMG_A = 14;
    public static final int DYN_DMG = 25;
    public static final int DYN_DMG_A = 30;
    public static final int ARMOR_INIT = 20;
    public static final int ARMOR_INIT_A = 20;
    public static final int STOKE_AMT = 1;
    public static final int STOKE_AMT_A = 2;
    public static final int STOKE_BLK = 10;
    public static final int STOKE_BLK_A = 15;
    public static final int HOLD_AMT = 2;
    public static final int HOLD_AMT_A = 3;
    
    
    
    private int railgunnerDmg;
    private int dynamiteDmg;
    private int stokeAmt;
    private int stokeBlk;
    private int holdAmt;
    
    
    
    
    
    
    
    //move id bytes
    private static final byte RAIL_GUNNER = 1;
    private static final byte DYNAMITE = 2;
    private static final byte CARGO_HOLD = 3;
    private static final byte LIVING_STEEL = 4;
    private static final byte STOKE_THE_ENGINE = 5;
    private static final byte HEARTBEAT = 6;
    private static final byte STARTUP = 7;
    public Conductor() {
        super(NAME, ID, 999, 25.0f, 25.0f, 180.0f, 250.0f, "images/monsters/beyond/HEC/c_placeholder.png", -75.0f, -75.0f);
		ReplayTheSpireMod.logger.info("init Conductor");
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(HellsEngine.HP_A);
            this.stokeBlk = STOKE_BLK_A;
        }
        else {
            this.setHp(HellsEngine.HP);
            this.stokeBlk = STOKE_BLK_A;
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.railgunnerDmg = GUN_DMG_A;
            this.dynamiteDmg = DYN_DMG_A;
        }
        else {
        	this.railgunnerDmg = GUN_DMG;
            this.dynamiteDmg = DYN_DMG;
        }
        if (AbstractDungeon.ascensionLevel >= 19) {
        	this.stokeAmt = STOKE_AMT_A;
        	this.holdAmt = HOLD_AMT_A;
        } else {
        	this.stokeAmt = STOKE_AMT;
        	this.holdAmt = HOLD_AMT;
        }
        this.damage.add(new DamageInfo(this, this.railgunnerDmg));
        this.damage.add(new DamageInfo(this, this.dynamiteDmg));
        //this.loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0f);
        //this.state.setAnimation(0, "idle", true);
    }
    
    @Override
    public void usePreBattleAction() {
		this.engine = (HellsEngine)AbstractDungeon.getMonsters().getMonster(HellsEngine.ID);
		int armor = AbstractDungeon.ascensionLevel >= 19 ? ARMOR_INIT_A : ARMOR_INIT;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new EnemyLifeBindPower(this)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OffTheRailsPower(this, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, armor), armor));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, armor));
	}
    
	private void setMoveNow(byte nextTurn) {
		switch (nextTurn) {
        case STARTUP: {
			this.setMove(nextTurn, Intent.UNKNOWN);
			break;
		}
        case RAIL_GUNNER: {
			this.setMove(MOVES[3], nextTurn, Intent.ATTACK, this.damage.get(0).base);
			break;
		}
        case DYNAMITE: {
			this.setMove(nextTurn, Intent.ATTACK, this.damage.get(1).base);
			break;
		}
        case CARGO_HOLD: {
			this.setMove(MOVES[4], nextTurn, this.hasPower(BackAttackPower.POWER_ID) ? Intent.STRONG_DEBUFF : Intent.DEBUFF);
			break;
		}
        case STOKE_THE_ENGINE: {
			this.setMove(MOVES[5], nextTurn, this.hasPower(BackAttackPower.POWER_ID) ? Intent.DEFEND_BUFF : Intent.BUFF);
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
			case STARTUP: {
				int partif = 0;
				if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
					partif = AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount;
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, this, ArtifactPower.POWER_ID));
				}
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
				if (partif > 0) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ArtifactPower(AbstractDungeon.player, partif), partif));
				}
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case RAIL_GUNNER: {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case DYNAMITE: {
				this.dynamite = new Dynamite(-200.0f, 100.0f, (int)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentDmg"));
				AbstractDungeon.actionManager.addToBottom(new SpawnForestMonsterAction(this.dynamite, true, 2));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case CARGO_HOLD: {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
				if (this.hasPower(BackAttackPower.POWER_ID)) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
				}
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case STOKE_THE_ENGINE: {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.engine, this, new MightPower(this.engine, this.stokeAmt, true), this.stokeAmt));
				if (this.hasPower(BackAttackPower.POWER_ID)) {
					AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.engine, this, this.stokeBlk));
					int armor = 5;
					if (!this.hasPower(PlatedArmorPower.POWER_ID)) {
						armor = 10;
					} else {
						armor = Math.max(armor, 10 - this.getPower(PlatedArmorPower.POWER_ID).amount);
					}
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
				}
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
        }
    }
    
    @Override
    protected void getMove(final int num) {
    	if (this.engine != null)
    		this.engine.moveRoll(num);
		this.moveRoll(num);
    }
    
    public void moveRoll(final int num) {
    	if (this.engine == null || this.engine.isFirstTurn || !AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
			this.setMoveNow(STARTUP);
			return;
		}
    	if (num % 3 == 1 && !this.isDynamiteOut()) {
    		this.setMoveNow(DYNAMITE);
    		return;
    	}
    	if (!this.lastMove(RAIL_GUNNER) && num < 33 || this.lastMove(CARGO_HOLD) && num <= 66 ) {
    		this.setMoveNow(RAIL_GUNNER);
    	} else if (num > 66 && !this.lastMove(STOKE_THE_ENGINE)) {
    		this.setMoveNow(STOKE_THE_ENGINE);
    	} else {
    		this.setMoveNow(CARGO_HOLD);
    	}
    }
    
    @Override
    public void damage(final DamageInfo info) {
    	super.damage(info);
    	if (this.currentHealth < this.engine.currentHealth) {
    		AbstractDungeon.effectList.add(new StrikeEffect(this.engine, this.engine.hb.cX, this.engine.hb.cY, this.engine.currentHealth - this.currentHealth));
    		this.engine.currentHealth = this.currentHealth;
    		this.engine.healthBarUpdatedEvent();
    	}
    }

    @Override
    public void heal(int healAmount) {
        super.heal(healAmount);
        if (this.currentHealth > this.engine.currentHealth) {
        	AbstractDungeon.effectList.add(new HealEffect(this.engine.hb.cX - this.engine.animX, this.engine.hb.cY, this.currentHealth - this.engine.currentHealth));
    		this.engine.currentHealth = this.currentHealth;
    		this.engine.healthBarUpdatedEvent();
    	}
    }
    
    @Override
    public void die() {
        super.die();
        if (!this.engine.isDeadOrEscaped() && !this.engine.isDying) {
        	this.engine.die();
        }
    }
    
    @Override
    public void applyPowers() {
        boolean backAttack = AbstractDungeon.player.hasPower("Surrounded") && ((AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX) || (!AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX));
        if (backAttack) {
        	if (this.intent == Intent.DEBUFF) {
        		this.intent = Intent.STRONG_DEBUFF;
        	} else if (this.intent == Intent.BUFF) {
        		this.intent = Intent.DEFEND_BUFF;
        	}
        } else {
        	if (this.intent == Intent.STRONG_DEBUFF) {
        		this.intent = Intent.DEBUFF;
        	} else if (this.intent == Intent.DEFEND_BUFF) {
        		this.intent = Intent.BUFF;
        	}
        }
        super.applyPowers();
    }
    
    public boolean isDynamiteOut() {
    	if (this.dynamite == null) {
    		return false;
    	} else {
    		return (!this.dynamite.isDeadOrEscaped());
    	}
    }
    
    //////////render stuff//////////////////
    
    @Override
    public Texture getAttackIntent() {
    	if (this.nextMove == DYNAMITE) {
    		return BOMB_INTENT_TEXTURE;
    	}
    	return super.getAttackIntent();
    }
    @Override
    public Texture getAttackIntent(int dmg) {
    	if (this.nextMove == DYNAMITE) {
    		return BOMB_INTENT_TEXTURE;
    	}
    	return super.getAttackIntent(dmg);
    }
    @Override
    public void renderTip(final SpriteBatch sb) {
    	super.renderTip(sb);
        if (this.nextMove == DYNAMITE && this.intentAlphaTarget == 1.0f && !AbstractDungeon.player.hasRelic("Runic Dome")) {
            final ArrayList<PowerTip> tips = (ArrayList<PowerTip>)ReflectionHacks.getPrivateStatic(TipHelper.class, "POWER_TIPS");
            tips.get(0).header = bombStrings.TEXT[0];
            tips.get(0).body = bombStrings.TEXT[1] + (int)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentDmg") + bombStrings.TEXT[2];
            tips.get(0).img = BOMB_INTENT_TEXTURE;
        	final float offsetY = (tips.size() - 1) * AbstractMonster.MULTI_TIP_Y_OFFSET + AbstractMonster.TIP_OFFSET_Y;
        	if (this.hb.cX + this.hb.width / 2.0f < AbstractMonster.TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0f + AbstractMonster.TIP_OFFSET_R_X, this.hb.cY + offsetY, tips);
            }
            else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0f + AbstractMonster.TIP_OFFSET_L_X, this.hb.cY + offsetY, tips);
            }
        }
    }
}
