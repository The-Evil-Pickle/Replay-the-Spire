package com.megacrit.cardcrawl.mod.replay.monsters.replay.hec;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.utility.MoveCreaturesAction;
import com.megacrit.cardcrawl.mod.replay.actions.utility.MoveMonsterAction;
import com.megacrit.cardcrawl.mod.replay.actions.utility.StartParalaxAction;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.CaptainAbe;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.mod.replay.powers.EnemyLifeBindPower;
import com.megacrit.cardcrawl.mod.replay.powers.ForgedInHellfirePower;
import com.megacrit.cardcrawl.mod.replay.vfx.paralax.ParalaxController;
import com.megacrit.cardcrawl.mod.replay.vfx.paralax.ParalaxObject;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.patches.BeyondScenePatch;

public class HellsEngine extends AbstractMonster {
	public static final String ID = "Replay:Hell Engine";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final AbstractCard STATUS_CARD_1;
    private static final AbstractCard STATUS_CARD_2; 
    private static final UIStrings plannedStrings = CardCrawlGame.languagePack.getUIString("PlannedCardIntent");
    private static final Texture TRAIN_INTENT_TEXTURE = ImageMaster.loadImage("images/ui/replay/intent/attack_intent_train.png");
    static {
    	STATUS_CARD_1 = new Burn();
    	(STATUS_CARD_2 = new Burn()).upgrade();
    }
    private Conductor conductor;
    private AbstractCard plannedCard;
    private BobEffect cardBob;
    public static final int HP = 450;
    public static final int HP_A = 500;
    public static final int START_DMG = 20;
    public static final int START_DMG_A = 25;
    public static final int COALS_DMG = 4;
    public static final int COALS_DMG_A = 5;
    public static final int COALS_AMT = 2;
    public static final int RUN_DMG = 15;
    public static final int RUN_DMG_A = 19;
    public static final int ARTIFACT_INIT = 20;
    public static final int ARTIFACT_INIT_A = 20;
    public static final int STEEL_AMT = 2;
    public static final int STEEL_AMT_A = 3;
    public static final int STEEL_BLK = 10;
    public static final int STEEL_BLK_A = 15;
    
	public boolean isFirstTurn;
    
    private int startDmg;
    private int coalsDmg;
    private int coalsAmt;
    private int runDmg;
    private int steelBlk;
    private int steelAmt;
    
    
    
    
    
    
    
    //move id bytes
    private static final byte HOT_COALS = 1;
    private static final byte DOORS_CLOSING = 2;
    private static final byte RUNAWAY_TRAIN = 3;
    private static final byte LIVING_STEEL = 4;
    private static final byte STOKE_THE_ENGINE = 5;
    private static final byte HEARTBEAT = 6;
    private static final byte STARTUP = 7;
    public HellsEngine() {
        super(NAME, ID, 999, -900.0f, 50.0f, 400.0f, 600.0f, "images/monsters/beyond/HEC/e_placeholder.png", 1200.0f, -50.0f);
		ReplayTheSpireMod.logger.info("init Engine");
        this.isFirstTurn = true;
        this.plannedCard = null;
        this.type = EnemyType.BOSS;
        this.cardBob = new BobEffect();
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(HP_A);
        }
        else {
            this.setHp(HP);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.startDmg = START_DMG_A;
            this.coalsDmg = COALS_DMG_A;
            this.runDmg = RUN_DMG_A;
        }
        else {
        	this.startDmg = START_DMG;
            this.coalsDmg = COALS_DMG;
            this.runDmg = RUN_DMG;
        }
        this.coalsAmt = 2;
        this.damage.add(new DamageInfo(this, this.startDmg));
        this.damage.add(new DamageInfo(this, this.coalsDmg));
        this.damage.add(new DamageInfo(this, this.runDmg));
        //this.loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0f);
        //this.state.setAnimation(0, "idle", true);
    }
    
    @Override
    public void usePreBattleAction() {
		CardCrawlGame.music.unsilenceBGM();
		AbstractDungeon.scene.fadeOutAmbiance();
		AbstractDungeon.getCurrRoom().playBgmInstantly("replay/No_Train_No_Game.mp3");
		this.conductor = (Conductor)AbstractDungeon.getMonsters().getMonster(Conductor.ID);
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new EnemyLifeBindPower(this)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ForgedInHellfirePower(this, 50)));
		int artifact = AbstractDungeon.ascensionLevel >= 19 ? ARTIFACT_INIT_A : ARTIFACT_INIT;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, artifact), artifact));
		
		
		ArrayList<Float> levelSpeeds = new ArrayList<Float>();
		levelSpeeds.add(1900.0f);//0 - floor
		levelSpeeds.add(200.0f);//1 - true bg
		levelSpeeds.add(400.0f);//2 - bg addons
		levelSpeeds.add(700.0f);//3 - shapes
		levelSpeeds.add(1400.0f);//4 - thin pillars
		levelSpeeds.add(1850.0f);//5 - a few shapes
		
		BeyondScenePatch.bg_controller = new ParalaxController(levelSpeeds, 4500.0f);

		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/bg_2.png"), 1);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/bg_1.png"), 1);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/bg_3.png"), 1);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/wl_2.png"), 2);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/wl_4.png"), 2);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/wl_1.png"), 2);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/pl_1.png"), 4);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/pl_3.png"), 4);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/pl_2.png"), 4);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/pl_1.png", true), 4);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_2.png", true), 4);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_3.png"), 3);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_1.png"), 3);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/wl_3.png"), 3);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_3.png"), 3);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_1.png"), 3);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_4.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_5.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_2.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/fg_1.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_4.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_5.png"), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject("images/monsters/beyond/HEC/paralax/sh_1.png", true), 5);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, "images/monsters/beyond/HEC/paralax/fl_1.png"), 0);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, "images/monsters/beyond/HEC/paralax/fl_1.png"), 0);
		BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, "images/monsters/beyond/HEC/paralax/fl_1.png"), 0);
		BeyondScenePatch.bg_controller.DistributeObjects();
	}
    
	private void setMoveNow(byte nextTurn) {
		this.plannedCard = null;
		STATUS_CARD_1.target_x = this.intentHb.cX - (128.0f * Settings.scale);
		STATUS_CARD_1.target_y =  this.intentHb.cY - (60.0f * Settings.scale);
		STATUS_CARD_1.current_x = this.intentHb.cX - (128.0f * Settings.scale);
		STATUS_CARD_1.current_y =  this.intentHb.cY - (60.0f * Settings.scale);
		STATUS_CARD_2.current_x =  STATUS_CARD_1.current_x;
		STATUS_CARD_2.current_y =  STATUS_CARD_1.current_y;
		STATUS_CARD_2.target_x =  STATUS_CARD_1.target_x;
		STATUS_CARD_2.target_y =  STATUS_CARD_1.target_y;
		switch (nextTurn) {
	        case STARTUP: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(0).base);
				break;
			}
	        case HOT_COALS: {
	        	this.plannedCard = STATUS_CARD_1;
				this.setMove(MOVES[1], nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(1).base, this.coalsAmt, true);
				break;
			}
	        case RUNAWAY_TRAIN: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(2).base);
				break;
			}
	        case LIVING_STEEL: {
				this.setMove(MOVES[4], nextTurn, Intent.DEFEND);
				break;
			}
	        case HEARTBEAT: {
				this.setMove(nextTurn, this.hasPower(BackAttackPower.POWER_ID) ? Intent.STRONG_DEBUFF : Intent.DEBUFF);
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
				if (this.isFirstTurn) {
					ArrayList<AbstractCreature> mylist = new ArrayList<AbstractCreature>();
					mylist.add(this);
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist, -1575, 0, 0.7f));
					AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.0f, ShakeDur.MED, ShakeIntensity.HIGH));
					AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE, true));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist, -225, 0, 0.1f));
					ArrayList<AbstractCreature> mylist2 = new ArrayList<AbstractCreature>();
					ArrayList<AbstractCreature> mylist3 = new ArrayList<AbstractCreature>();
					mylist2.add(AbstractDungeon.player);
					mylist3.add(AbstractDungeon.player);
					mylist3.add(this.conductor);
					AbstractDungeon.actionManager.addToBottom(new StartParalaxAction(BeyondScenePatch.bg_controller));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist2, 100, 0, 0.05f));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist3, 100, 80, 0.05f));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist3, 100, 50, 0.05f));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist2, 100, -10, 0.05f));
					AbstractDungeon.actionManager.addToBottom(new MoveCreaturesAction(mylist2, 100, -20, 0.05f));
					this.hb_x += 325.0f;
					this.hb_w += 50.0f;
				}
				this.isFirstTurn = false;
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case HOT_COALS: {
				for (int i=0; i<this.coalsAmt; i++) {
					AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
				}
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.plannedCard, this.coalsAmt, true, true));//this.plannedCard != null ? this.plannedCard.makeStatEquivalentCopy() : new Burn()
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case LIVING_STEEL: {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.steelBlk));
				if (this.hasPower(BackAttackPower.POWER_ID)) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, this.steelAmt), this.steelAmt));
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReflectionPower(this, this.steelAmt), this.steelAmt));
				}
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
			case RUNAWAY_TRAIN: {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
			}
        }
    }
    

    @Override
    protected void getMove(final int num) {
    	if (this.conductor != null && !this.conductor.hasPower(StunMonsterPower.POWER_ID))
    		return;
		this.moveRoll(num);
    }
    
    public void moveRoll(final int num) {
    	if (this.isFirstTurn) {
			this.setMoveNow(STARTUP);
			return;
		}
    	if (num % 3 == 1 && this.conductor != null && !this.conductor.isDynamiteOut()) {
    		if (this.lastMove(RUNAWAY_TRAIN) || num < 45 && !this.lastMove(HOT_COALS)) {
    			this.setMoveNow(HOT_COALS);
    		} else {
    			this.setMoveNow(RUNAWAY_TRAIN);
    		}
    		return;
    	}
    	if (!this.lastMove(HOT_COALS) && num < 33 || this.lastMove(RUNAWAY_TRAIN) && num <= 66 ) {
    		this.setMoveNow(HOT_COALS);
    	} else if (num > 66 && !this.lastMove(LIVING_STEEL)) {
    		this.setMoveNow(LIVING_STEEL);
    	} else {
    		this.setMoveNow(RUNAWAY_TRAIN);
    	}
    }
    
    //////////////////render sorta stuff//////////////
    @Override
    public void applyPowers() {
        boolean backAttack = AbstractDungeon.player.hasPower("Surrounded") && ((AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX) || (!AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX));
        if (backAttack) {
        	if (this.intent == Intent.DEBUFF) {
        		this.intent = Intent.STRONG_DEBUFF;
        	} else if (this.intent == Intent.DEFEND) {
        		this.intent = Intent.DEFEND_BUFF;
        	}
        } else {
        	if (this.intent == Intent.STRONG_DEBUFF) {
        		this.intent = Intent.DEBUFF;
        	} else if (this.intent == Intent.DEFEND_BUFF) {
        		this.intent = Intent.DEFEND;
        	}
        }
        super.applyPowers();
        if (this.plannedCard != null) {
        	this.plannedCard = backAttack ? STATUS_CARD_2 : STATUS_CARD_1;
        }
    }
    @Override
    public Texture getAttackIntent() {
    	int dmg = (int)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentDmg");
    	if ((boolean)ReflectionHacks.getPrivate(this, AbstractMonster.class, "isMultiDmg")) {
    		dmg *= (int)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentMultiAmt");
    	}
    	if (dmg > 35 || this.isFirstTurn && dmg >= 20) {
    		return TRAIN_INTENT_TEXTURE;
    	}
    	return super.getAttackIntent();
    }
    @Override
    public Texture getAttackIntent(int dmg) {
    	if (dmg > 35 || this.isFirstTurn && dmg >= 20) {
    		return TRAIN_INTENT_TEXTURE;
    	}
    	return super.getAttackIntent(dmg);
    }
    
    @Override
    public void update() {
    	super.update();
    	if (this.plannedCard != null) {
    		this.plannedCard.update();
    	}
    	this.cardBob.update();
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        super.render(sb);
		if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !this.isFirstTurn) {
			AbstractDungeon.player.render(sb);
			if (this.plannedCard != null && !AbstractDungeon.player.hasRelic("Runic Dome") && !Settings.hideCombatElements) {
				if (!this.hb.hovered) {
					this.plannedCard.targetDrawScale = 0.4f;
					this.plannedCard.current_y = this.intentHb.cY + this.cardBob.y - (25.0f * Settings.scale);
					this.plannedCard.target_y = this.plannedCard.current_y;
				} else {
					this.plannedCard.targetDrawScale = 0.75f;
					this.plannedCard.target_y = this.intentHb.cY - (100.0f * Settings.scale);
					this.plannedCard.current_y = MathHelper.cardLerpSnap(this.plannedCard.current_y, this.plannedCard.target_y);
				}
				this.plannedCard.render(sb);
			}
		}
    }
    @Override
    public void renderTip(final SpriteBatch sb) {
    	super.renderTip(sb);
        
        if (this.plannedCard != null) {
            final ArrayList<PowerTip> tips = (ArrayList<PowerTip>)ReflectionHacks.getPrivateStatic(TipHelper.class, "POWER_TIPS");
            tips.add(new PowerTip(plannedStrings.TEXT[0],this.name + plannedStrings.TEXT[1] + this.plannedCard.name + plannedStrings.TEXT[2]));
        	final float offsetY = (tips.size() - 1) * AbstractMonster.MULTI_TIP_Y_OFFSET + AbstractMonster.TIP_OFFSET_Y;
        	if (this.hb.cX + this.hb.width / 2.0f < AbstractMonster.TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0f + AbstractMonster.TIP_OFFSET_R_X, this.hb.cY + offsetY, tips);
            }
            else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0f + AbstractMonster.TIP_OFFSET_L_X, this.hb.cY + offsetY, tips);
            }
        }
    }
    
    //////////////////[[linked hp stuff]]/////////////
    @Override
    public void damage(final DamageInfo info) {
    	super.damage(info);
    	if (this.currentHealth < this.conductor.currentHealth) {
    		AbstractDungeon.effectList.add(new StrikeEffect(this.conductor, this.conductor.hb.cX, this.conductor.hb.cY, this.conductor.currentHealth - this.currentHealth));
    		this.conductor.currentHealth = this.currentHealth;
    		this.conductor.healthBarUpdatedEvent();
    	}
    }

    @Override
    public void heal(int healAmount) {
        super.heal(healAmount);
        if (this.currentHealth > this.conductor.currentHealth) {
        	AbstractDungeon.effectList.add(new HealEffect(this.conductor.hb.cX - this.conductor.animX, this.conductor.hb.cY, this.currentHealth - this.conductor.currentHealth));
    		this.conductor.currentHealth = this.currentHealth;
    		this.conductor.healthBarUpdatedEvent();
    	}
    }
    
    @Override
    public void die() {
        super.die();
        if (!this.conductor.isDeadOrEscaped() && !this.conductor.isDying) {
        	this.conductor.die();
        }
    }
}
