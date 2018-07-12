package com.megacrit.cardcrawl.monsters.replay;

import replayTheSpire.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;
import tobyspowerhouse.powers.*;

public class CaptainAbe extends AbstractMonster
{
    public static final String ID = "CaptainAbe";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 80;
    public static final int A_HP = 90;
    private static final int COLLAPSE_DAMAGE = 4;
    private static final int COLLAPSE_COUNT = 3;
    private static final int BASH_DAMAGE = 12;
    private static final int SLASH_DAMAGE = 4;
    private static final int SLASH_COUNT = 2;
    private static final int ORDERS_BLOCK = 10;
    private static final int ORDERS_STRENGTH = 3;
    private static final int A_COLLAPSE_DAMAGE = 4;
    private static final int A_COLLAPSE_COUNT = 4;
    private static final int A_BASH_DAMAGE = 14;
    private static final int A_SLASH_DAMAGE = 4;
    private static final int A_SLASH_COUNT = 2;
    private static final int A_ORDERS_BLOCK = 15;
    private static final int FRAIL_AMT = 3;
	private static final int WOUND_CD = 3;
    private int bashDmg;
    private int collapseDmg;
    private int collapseAmt;
    private int slashDmg;
    private int slashAmt;
    private int ordersBlk;
	private int ordersStr;
    private static final byte COLLAPSE = 1;
    private static final byte SLASH = 2;
    private static final byte BASH = 3;
    private static final byte ORDERS = 4;
    private static final byte KEEL_HAUL = 5;
    private static final byte ROB_BLIND = 6;
    private static final byte NONEDEAD = 7;
    private static final byte FINALE_1 = 8;
    private static final byte FINALE_2 = 9;
    private static final byte FINALE_3 = 10;
    private static final byte FINALE_4 = 11;
	public boolean isWaterLogged;
	private int woundCD;
	private boolean isFirstTurn;
	private byte nextPlannedMove;
	private boolean isFirstOrders;
	private boolean neverDeadWeighted;
	
	private ArrayList<AbstractGameAction.AttackEffect> slashEffects;
    
    public CaptainAbe(final float x, final float y) {
        super(CaptainAbe.NAME, "CaptainAbe", 80, -10.0f, 75.0f, 180.0f, 200.0f, "images/monsters/TheCity/abe.png", x, y);
		ReplayTheSpireMod.logger.info("init Abe");
		this.isFirstTurn = true;
		this.isFirstOrders = true;
		this.neverDeadWeighted = true;
		//this.animX = 0f;
		//this.animY = 0f;
		this.nextPlannedMove = CaptainAbe.SLASH;
        this.dialogX = 0.0f * Settings.scale;
        this.dialogY = 50.0f * Settings.scale;
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(CaptainAbe.A_HP);
			this.ordersBlk = CaptainAbe.A_ORDERS_BLOCK;
			this.ordersStr = CaptainAbe.ORDERS_STRENGTH;
        }
        else {
            this.setHp(CaptainAbe.HP);
			this.ordersBlk = CaptainAbe.ORDERS_BLOCK;
			this.ordersStr = CaptainAbe.ORDERS_STRENGTH;
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.bashDmg = CaptainAbe.A_BASH_DAMAGE;
            this.slashDmg = CaptainAbe.A_SLASH_DAMAGE;
			this.slashAmt = CaptainAbe.A_SLASH_COUNT;
            this.collapseDmg = CaptainAbe.A_COLLAPSE_DAMAGE;
            this.collapseAmt = CaptainAbe.A_COLLAPSE_COUNT;
        }
        else {
			this.bashDmg = CaptainAbe.BASH_DAMAGE;
            this.slashDmg = CaptainAbe.SLASH_DAMAGE;
			this.slashAmt = CaptainAbe.SLASH_COUNT;
            this.collapseDmg = CaptainAbe.COLLAPSE_DAMAGE;
            this.collapseAmt = CaptainAbe.COLLAPSE_COUNT;
        }
        this.damage.add(new DamageInfo(this, this.collapseDmg));
        this.damage.add(new DamageInfo(this, this.slashDmg));
        this.damage.add(new DamageInfo(this, this.bashDmg));
        //this.loadAnimation("images/monsters/theCity/romeo/skeleton.atlas", "images/monsters/theCity/romeo/skeleton.json", 1.0f);
        //final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
        //this.stateData.setMix("Hit", "Idle", 0.2f);
        //this.state.setTimeScale(0.8f);
		this.isWaterLogged = false;
		this.woundCD = 2;
		this.slashEffects = new ArrayList<AbstractGameAction.AttackEffect>();
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SLASH_HEAVY);
		this.slashEffects.add(AbstractGameAction.AttackEffect.SMASH);
		this.slashEffects.add(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
		this.slashEffects.add(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }
    
	private boolean notWaterLogged() {
		return (!this.hasPower("AbePower") || !(((AbePower)this.getPower("AbePower")).atActiveDepth));
	}
	
    @Override
    public void deathReact() {
		if (this.halfDead) {
			AbstractDungeon.getCurrRoom().cannotLose = false;
			this.halfDead = false;
			for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				m.die();
			}
		} else {
			if (!this.isDeadOrEscaped()) {
				ArrayList<AbstractMonster> thisguy = new ArrayList<AbstractMonster>();
				thisguy.add(this);
				AbstractDungeon.actionManager.addToBottom(new WaitAction(0.75f));
				AbstractDungeon.actionManager.addToBottom(new MoveMonsterAction(thisguy, 0.0f, -330.0f, 0.15f));
				//AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[2]));
				this.setMove(CaptainAbe.MOVES[4], CaptainAbe.FINALE_1, Intent.STRONG_DEBUFF);
				this.createIntent();
				AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
				this.setNextTurn(CaptainAbe.FINALE_1);
			}
		}
    }
    
    @Override
    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().cannotLose = true;
		if (AbstractDungeon.ascensionLevel >= 9) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
		}
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegrowPower(this)));
    }
	
    @Override
    public void damage(final DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            this.powers.clear();
            //Darkling.logger.info("This monster is now half dead.");
            boolean allDead = true;
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != this && !m.isDeadOrEscaped()) {
                    allDead = false;
					m.deathReact();
                }
            }
            //Darkling.logger.info("All dead: " + allDead);
            if (!allDead) {
                if (this.nextMove != NONEDEAD) {
					this.nextPlannedMove = this.nextMove;
                    this.setMove(NONEDEAD, Intent.NONE);
                    this.createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, NONEDEAD, Intent.NONE));
                }
            }
            else {
                AbstractDungeon.getCurrRoom().cannotLose = false;
                this.halfDead = false;
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    m.die();
                }
            }
        }
        else if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            //this.state.setAnimation(0, "Hit", false);
            //this.state.setTimeScale(0.8f);
            //this.state.addAnimation(0, "Idle", true, 0.0f);
        }
    }
    
    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
			ReplayTheSpireMod.completeAchievement("abe_win");
			if (this.neverDeadWeighted) {
				ReplayTheSpireMod.completeAchievement("abe_perfect");
			}
            this.deathTimer += 1.5f;
			super.die();
			this.onBossVictoryLogic();
        }
    }
	/*
	private SetMoveAction setNextTurnAction(byte nextTurn) {
		switch (nextTurn) {
            case COLLAPSE: {
				return new SetMoveAction(this, CaptainAbe.MOVES[0], nextTurn, Intent.ATTACK, this.damage.get(0).base, this.collapseAmt);
				break;
			}
            case SLASH: {
				return new SetMoveAction(this, nextTurn, Intent.ATTACK, this.damage.get(1).base, this.slashAmt);
				break;
			}
            case BASH: {
				return new SetMoveAction(this, nextTurn, Intent.ATTACK, this.damage.get(2).base);
				break;
			}
			case KEEL_HAUL: {
				return new SetMoveAction(this, CaptainAbe.MOVES[2], nextTurn, Intent.DEBUFF);		
			}
			case ORDERS: {
				return new SetMoveAction(this, CaptainAbe.MOVES[1], nextTurn, Intent.DEFEND_BUFF);		
			}
			case ROB_BLIND: {
				return new SetMoveAction(this, CaptainAbe.MOVES[3], nextTurn, Intent.UNKNOWN);
			}
			case NONEDEAD: {
				return new SetMoveAction(this, nextTurn, Intent.NONE);
			}
			case FINALE_1: {
				return new SetMoveAction(this, CaptainAbe.MOVES[4], nextTurn, Intent.STRONG_DEBUFF);
			}
			case FINALE_2: case FINALE_3: {
				return new SetMoveAction(this, nextTurn, Intent.STUN);
			}
			case FINALE_4: {
				return new SetMoveAction(this, CaptainAbe.MOVES[5], nextTurn, Intent.UNKNOWN);
			}
			default: {
				return new SetMoveAction(this, nextTurn, Intent.NONE);		
			}
		}
	}*/
	private void setNextTurn(byte nextTurn) {
		//AbstractDungeon.actionManager.addToBottom(this.setNextTurnAction(nextTurn));
		switch (nextTurn) {
            case COLLAPSE: {
				this.setMove(CaptainAbe.MOVES[0], nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(0).base, this.collapseAmt, true);
				return;
			}
            case SLASH: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(1).base, this.slashAmt, true);
				return;
			}
            case BASH: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(2).base);
				return;
			}
			case KEEL_HAUL: {
				this.setMove(CaptainAbe.MOVES[2], nextTurn, Intent.DEBUFF);		
				return;
			}
			case ORDERS: {
				this.setMove(CaptainAbe.MOVES[1], nextTurn, Intent.DEFEND_BUFF);	
				return;	
			}
			case ROB_BLIND: {
				this.setMove(CaptainAbe.MOVES[3], nextTurn, Intent.UNKNOWN);
				return;
			}
			case NONEDEAD: {
				this.setMove(nextTurn, Intent.NONE);
				return;
			}
			case FINALE_1: {
				this.setMove(CaptainAbe.MOVES[4], nextTurn, Intent.STRONG_DEBUFF);
				return;
			}
			case FINALE_2: case FINALE_3: {
				this.setMove(nextTurn, Intent.STUN);
				return;
			}
			case FINALE_4: {
				this.setMove(CaptainAbe.MOVES[5], nextTurn, Intent.UNKNOWN);
				return;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);
				return;
			}
		}
	}
	private AbstractGameAction.AttackEffect randoEffect() {
		return this.randoEffect(SLASH, 0);
	}
	private AbstractGameAction.AttackEffect randoEffect(byte b, int c) {
		switch (this.nextMove) {
            case COLLAPSE: {
				if (c == this.collapseAmt - 1) {
					return AbstractGameAction.AttackEffect.BLUNT_HEAVY;
				}
				return this.slashEffects.get((int)(Math.random() * (5)));
			}
            default: {
				return this.slashEffects.get((int)(Math.random() * (6)) + 3);
			}
		}
	}
	
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case ORDERS: {
				if (this.notWaterLogged()) {
					if (this.isFirstOrders) {
						AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[5]));
					}
					for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
						if (m.isDying) {
                            continue;
                        }
						if (this.ordersStr > 0 && (this.isFirstOrders || ((m == this && (!m.hasPower("Strength") || m.getPower("Strength").amount < this.ordersStr)) || !m.hasPower("Strength") || m.getPower("Strength").amount < 1))) {
							AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 1), 1));
						} else {
							AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, 10));
						}
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, this.ordersBlk));
					}
					this.isFirstOrders = false;
				} else {
					this.getPower("AbePower").onSpecificTrigger();
					this.neverDeadWeighted = false;
				}
				this.setNextTurn(CaptainAbe.SLASH);
                break;
            }
            case COLLAPSE: {
                //AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAB"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[(int)(Math.random() * (4))]));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25f));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.MAROON, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5f));
				AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6f, 0.2f));
				for (int i=0; i < this.collapseAmt; i++) {
					AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), this.randoEffect(COLLAPSE, i)));
				}
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, -1), -1));
				
				this.setNextTurn(CaptainAbe.SLASH);
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            case SLASH: {
				if (this.notWaterLogged()) {
					//AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAB"));
					AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
					for (int i=0; i < this.slashAmt; i++) {
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), this.randoEffect()));
					}
				} else {
					this.getPower("AbePower").onSpecificTrigger();
					this.neverDeadWeighted = false;
				}
                this.setNextTurn(CaptainAbe.KEEL_HAUL);
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(1).base));
                break;
            }
            case BASH: {
				if (this.notWaterLogged()) {
					//AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAB"));
					AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
					AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), this.randoEffect()));
				} else {
					this.getPower("AbePower").onSpecificTrigger();
					this.neverDeadWeighted = false;
				}
				if (AbstractDungeon.player.currentBlock > 45 && !this.isFirstOrders) {
					this.setNextTurn(CaptainAbe.ROB_BLIND);
				} else {
					this.setNextTurn(CaptainAbe.ORDERS);
				}
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(1).base));
                break;
            }
			case KEEL_HAUL: {
				this.woundCD--;
				if (this.notWaterLogged()) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, FRAIL_AMT, true), FRAIL_AMT));
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Wound(), 1, true, false));
				} else {
					this.getPower("AbePower").onSpecificTrigger();
					this.neverDeadWeighted = false;
				}
				this.setNextTurn(CaptainAbe.BASH);
				break;
			}
            case FINALE_1: {
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[10]));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HexPower(AbstractDungeon.player, 1)));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TPH_ConfusionPower(AbstractDungeon.player, 3, true)));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PondfishDrowning(AbstractDungeon.player, 3), 3));
				AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                this.setNextTurn(FINALE_2);
                break;
            }
            case FINALE_2: {
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[11]));
                this.setNextTurn(FINALE_3);
                break;
            }
            case FINALE_3: {
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[12]));
                this.setNextTurn(FINALE_4);
                break;
            }
            case FINALE_4: {
				final AbstractCard abeCurse = new AbeCurse();
				UnlockTracker.markCardAsSeen(abeCurse.cardID);
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.MAROON, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5f));
				AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(abeCurse, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, false));
				AbstractDungeon.actionManager.addToBottom(new SuicideAction(this));
                this.setNextTurn(NONEDEAD);
                break;
            }
			case ROB_BLIND: {
				if (AbstractDungeon.player.currentBlock > 10) {
					AbstractMonster thefish = AbstractDungeon.getCurrRoom().monsters.getMonster("PondfishBoss");
					boolean tog = false;
					for (int i = AbstractDungeon.player.currentBlock; i >= 10; i -= 10) {
						if (tog) {
							AbstractDungeon.actionManager.addToBottom(new StealBlockAction(AbstractDungeon.player, thefish, 10));
							tog = false;
						} else {
							AbstractDungeon.actionManager.addToBottom(new StealBlockAction(AbstractDungeon.player, this, 10));
							tog = true;
						}
					}
				} else {
					int goldAmount = Math.min(AbstractDungeon.player.gold, 30);
					AbstractDungeon.player.gold -= goldAmount;
					for (int i = 0; i < goldAmount; ++i) {
						AbstractDungeon.effectList.add(new GainPennyEffect(this, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, false));
					}
				}
				this.setNextTurn(CaptainAbe.SLASH);
				break;
			}
			case NONEDEAD: {
				this.setNextTurn(this.nextPlannedMove);
                break;
			}
        }
    }
	
    @Override
    protected void getMove(final int num) {
		if (this.isFirstTurn) {
			this.isFirstTurn = false;
			this.setNextTurn(CaptainAbe.COLLAPSE);
			//this.setMove(CaptainAbe.MOVES[0], CaptainAbe.COLLAPSE, Intent.ATTACK, this.damage.get(0).base, this.collapseAmt, true);
			return;
		}
		this.setNextTurn(CaptainAbe.BASH);
        //this.setMove(CaptainAbe.BASH, Intent.ATTACK, this.damage.get(2).base);
    }
    /*
    @Override
    public void changeState(final String key) {
        switch (key) {
            case "STAB": {
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0f);
                break;
            }
        }
    }
	*/
	/*
    @Override
    public void render(final SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {
            if (this.damageFlash) {
                ShaderHelper.setShader(sb, ShaderHelper.Shader.WHITE_SILHOUETTE);
            }
            if (this.atlas == null) {
                sb.setColor(this.tint.color);
                sb.draw(this.img, this.drawX - this.img.getWidth() * Settings.scale / 2.0f + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY, this.img.getWidth() * Settings.scale, this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
            }
            else {
                this.state.update(Gdx.graphics.getDeltaTime());
                this.state.apply(this.skeleton);
                this.skeleton.updateWorldTransform();
                this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY);
                this.skeleton.setColor(this.tint.color);
                this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
                sb.end();
                CardCrawlGame.psb.begin();
                AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
                sb.setBlendFunction(770, 771);
            }
            if (this == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && this.atlas == null) {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
                sb.draw(this.img, this.drawX - this.img.getWidth() * Settings.scale / 2.0f + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY, this.img.getWidth() * Settings.scale, this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
                sb.setBlendFunction(770, 771);
            }
            if (this.damageFlash) {
                ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
                --this.damageFlashFrames;
                if (this.damageFlashFrames == 0) {
                    this.damageFlash = false;
                }
            }
            if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != Intent.NONE && !Settings.hideCombatElements) {
                this.renderIntentVfxBehind(sb);
                this.renderIntent(sb);
                this.renderIntentVfxAfter(sb);
                this.renderDamageRange(sb);
            }
            this.hb.render(sb);
            this.intentHb.render(sb);
            this.healthHb.render(sb);
        }
		/*
        if (!AbstractDungeon.player.isDead) {
            this.renderHealth(sb);
            this.renderName(sb);
        }
		/
    }*/
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Captain Abe");
        NAME = CaptainAbe.monsterStrings.NAME;
        MOVES = CaptainAbe.monsterStrings.MOVES;
        DIALOG = CaptainAbe.monsterStrings.DIALOG;
    }
}
