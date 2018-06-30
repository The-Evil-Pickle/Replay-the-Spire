package com.megacrit.cardcrawl.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.replay.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import ReplayTheSpireMod.*;
import basemod.*;
import com.megacrit.cardcrawl.core.*;
import org.apache.logging.log4j.*;

public class PondfishBoss extends AbstractMonster
{
    private static final Logger logger;
    public static final String ID = "PondfishBoss";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 280;
    public static final int A_2_HP = 300;
	
	public static final int CHOMP_DAMAGE = 3;
	public static final int CHOMP_COUNT = 4;
	public static final int RAKE_DAMAGE = 4;
	public static final int RAKE_COUNT = 2;
	public static final int RAKE_VULN = 1;
	public static final int DRAG_DROWN = 4;
	public static final int UNDER_DAMAGE = 2;
	public static final int UNDER_AMT = 6;
	
	public static final int A_CHOMP_DAMAGE = 4;
	public static final int A_CHOMP_COUNT = 4;
	public static final int A_RAKE_DAMAGE = 4;
	public static final int A_RAKE_COUNT = 2;
	public static final int A_RAKE_VULN = 2;
	public static final int A_DRAG_DROWN = 6;
	public static final int A_UNDER_DAMAGE = 2;
	public static final int A_UNDER_AMT = 7;
	
	private int chompDmg;
	private int chompAmt;
	private int rakeDmg;
	private int rakeAmt;
	private int rakeVuln;
	private int dragDwn;
	private int underDmg;
	private int underAmt;
	
	private byte nextPlannedMove;
	private int inslot;
	
    private static final byte CHOMP = 1;
    private static final byte DRAG_TO_HELL = 2;
    private static final byte TEETH_RAKE = 3;
    private static final byte FEET_UNDER = 4;
    private static final byte SPLASH = 5;
    private static final byte LIVING_LANTERN = 6;
    private static final byte STARTUP = 7;
    
	private boolean isFirstTurn;
    public PondfishBoss(final float x, final float y) {
        super(PondfishBoss.NAME, PondfishBoss.ID, 999, 70.0f, -300.0f, 400.0f, 300.0f, "images/monsters/TheCity/pondfish.png", x, y);
		ReplayTheSpireMod.logger.info("init Fish");
        this.isFirstTurn = true;
		//this.animX = 0f;
		//this.animY = 0f;
		this.inslot = 0;
		this.nextPlannedMove = PondfishBoss.TEETH_RAKE;
		//this.halfDead = true;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP);
        }
        else {
            this.setHp(HP);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.chompDmg = PondfishBoss.A_CHOMP_DAMAGE;
            this.chompAmt = PondfishBoss.A_CHOMP_COUNT;
            this.rakeDmg = PondfishBoss.A_RAKE_DAMAGE;
            this.rakeAmt = PondfishBoss.A_RAKE_COUNT;
            this.rakeVuln = PondfishBoss.A_RAKE_VULN;
            this.dragDwn = PondfishBoss.A_DRAG_DROWN;
            this.underDmg = PondfishBoss.A_UNDER_DAMAGE;
            this.underAmt = PondfishBoss.A_UNDER_AMT;
        }
        else {
            this.chompDmg = PondfishBoss.CHOMP_DAMAGE;
            this.chompAmt = PondfishBoss.CHOMP_COUNT;
            this.rakeDmg = PondfishBoss.RAKE_DAMAGE;
            this.rakeAmt = PondfishBoss.RAKE_COUNT;
            this.rakeVuln = PondfishBoss.RAKE_VULN;
            this.dragDwn = PondfishBoss.DRAG_DROWN;
            this.underDmg = PondfishBoss.UNDER_DAMAGE;
            this.underAmt = PondfishBoss.UNDER_AMT;
        }
        this.damage.add(new DamageInfo(this, this.chompDmg));
        this.damage.add(new DamageInfo(this, this.rakeDmg));
        this.damage.add(new DamageInfo(this, this.underDmg));
        //this.loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0f);
        //this.state.setAnimation(0, "idle", true);
    }
    
    @Override
    public void usePreBattleAction() {
		AbstractDungeon.getCurrRoom().playBgmInstantly("PIRATE_JOKE");
	}
	
	private SetMoveAction setNextTurnAction(byte nextTurn) {
		switch (nextTurn) {
            case STARTUP: {
				return new SetMoveAction(this, nextTurn, Intent.NONE);
			}
            case CHOMP: {
				return new SetMoveAction(this, nextTurn, Intent.ATTACK, this.damage.get(0).base, this.chompAmt, true);
			}
            case DRAG_TO_HELL: {
				return new SetMoveAction(this, PondfishBoss.MOVES[0], nextTurn, Intent.STRONG_DEBUFF);
			}
			case TEETH_RAKE: {
				return new SetMoveAction(this, PondfishBoss.MOVES[2], nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(1).base, this.rakeAmt, true);
			}
			case LIVING_LANTERN: {
				return new SetMoveAction(this, PondfishBoss.MOVES[1], nextTurn, Intent.BUFF);		
			}
			case FEET_UNDER: {
				return new SetMoveAction(this, PondfishBoss.MOVES[3], nextTurn, Intent.ATTACK, this.damage.get(2).base, this.underAmt, true);
			}
			case SPLASH: {
				return new SetMoveAction(this, PondfishBoss.MOVES[4], nextTurn, Intent.STUN);
			}
			default: {
				return new SetMoveAction(this, nextTurn, Intent.NONE);		
			}
		}
	}
	private void setNextTurn(byte nextTurn) {
		AbstractDungeon.actionManager.addToBottom(this.setNextTurnAction(nextTurn));
	}
    
	private void setMoveNow(byte nextTurn) {
		switch (nextTurn) {
            case STARTUP: {
				this.setMove(nextTurn, Intent.NONE);
				break;
			}
            case CHOMP: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(0).base, this.chompAmt, true);
				break;
			}
            case DRAG_TO_HELL: {
				this.setMove(PondfishBoss.MOVES[0], nextTurn, Intent.STRONG_DEBUFF);
				break;
			}
			case TEETH_RAKE: {
				this.setMove(PondfishBoss.MOVES[2], nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(1).base, this.rakeAmt, true);
				break;
			}
			case LIVING_LANTERN: {
				this.setMove(PondfishBoss.MOVES[1], nextTurn, Intent.BUFF);		
				break;
			}
			case FEET_UNDER: {
				this.setMove(PondfishBoss.MOVES[3], nextTurn, Intent.ATTACK, this.damage.get(2).base, this.underAmt, true);
				break;
			}
			case SPLASH: {
				this.setMove(PondfishBoss.MOVES[4], nextTurn, Intent.STUN);
				break;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);	
				break;	
			}
		}
	}
	
	private void advanceTurnSlot() {
		this.inslot = (this.inslot + 1) % 4;
	}
	
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
			case STARTUP: {
				this.updateHitbox(70.0f, 220.0f, 400.0f, 300.0f);
				AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate"));
				AbstractDungeon.actionManager.addToBottom(new MoveMonsterAction(AbstractDungeon.getCurrRoom().monsters.monsters, -75.0f, 330.0f, 0.75f));
				/*for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
					if (m instanceof CaptainAbe) {
						AbstractDungeon.actionManager.addToBottom(new MoveMonsterAction(m, 150.0f, 35.0f, 0.1f));
					}
				}*/
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PondfishDrowning(AbstractDungeon.player), 0));
				this.setNextTurnAction(PondfishBoss.CHOMP);
				for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
					if (m instanceof CaptainAbe) {
						if (!m.halfDead) {
							AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new AbePower(m), 0));
						} else {
							this.setNextTurnAction(PondfishBoss.LIVING_LANTERN);
						}
					}
				}
				this.isFirstTurn = false;
				this.halfDead = false;
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				UnlockTracker.markBossAsSeen("PONDFISH");
				break;
			}
            case CHOMP: {
				this.advanceTurnSlot();
				for (int i = 0; i < this.chompAmt; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale, Color.CHARTREUSE.cpy()), 0.2f));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE, true));
                }
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            case TEETH_RAKE: {
				this.advanceTurnSlot();
				for (int i = 0; i < this.rakeAmt; ++i) {
					if (i == 0) {
						AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale, Color.CHARTREUSE.cpy()), 0.2f));
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
					} else {
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
					}
                }
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.rakeVuln, true), this.rakeVuln));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            case FEET_UNDER: {
				this.advanceTurnSlot();
				for (int i = 0; i < this.underAmt; ++i) {
					if (i == 0) {
						AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0f, 50.0f) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale, Color.CHARTREUSE.cpy()), 0.2f));
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.NONE, true));
					} else if (i == this.underAmt - 1) {
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
					} else {
						AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
					}
                }
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				//this.setNextTurnAction(PondfishBoss.SPLASH);
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            case DRAG_TO_HELL: {
				this.advanceTurnSlot();
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PondfishDrowning(AbstractDungeon.player, this.dragDwn), this.dragDwn));
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                //AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
			case LIVING_LANTERN: {
				this.setNextTurn(this.nextPlannedMove);
				CaptainAbe abe = null;
				for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
					if (m instanceof CaptainAbe && m.halfDead) {
						abe = (CaptainAbe)m;
					} else {
						AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, 20));
					}
				}
				if (abe != null && abe.halfDead) {
					if (MathUtils.randomBoolean()) {
						AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_2", MathUtils.random(-0.1f, 0.1f)));
					}
					else {
						AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_1", MathUtils.random(-0.1f, 0.1f)));
					}
					abe.halfDead = false;
					AbstractDungeon.actionManager.addToBottom(new HealAction(abe, this, (abe.maxHealth / 2) - 10));
					//AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(abe, "REVIVE"));
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abe, abe, new AbePower(abe), 0));
						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abe, this, new IntangiblePower(abe, 1), 1));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(abe, CaptainAbe.DIALOG[(int)(Math.random() * (3)) + 6]));
					/*if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abe, abe, new StrengthPower(abe, 2), 2));
						break;
					}*/
				}
                break;
			}
			default: {
				this.advanceTurnSlot();
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
			}
        }
    }
	
    @Override
    protected void getMove(final int num) {
		if (this.isFirstTurn) {
			this.setMove(PondfishBoss.STARTUP, Intent.NONE);
			return;
		}
		if (this.lastMove(PondfishBoss.FEET_UNDER)) {
			this.setMoveNow(PondfishBoss.SPLASH);
			return;
		}
		//this.setMove(PondfishBoss.CHOMP, Intent.ATTACK, this.chompDmg, this.chompAmt);
		switch (this.inslot) {
			case 0:
				if (this.lastMove(PondfishBoss.DRAG_TO_HELL)) {
					if (num < 50) {
						this.setMoveNow(PondfishBoss.CHOMP);
					} else {
						this.setMoveNow(PondfishBoss.TEETH_RAKE);
					}
				} else {
					if (num < 33) {
						this.setMoveNow(PondfishBoss.CHOMP);
					} else if (num < 67) {
						this.setMoveNow(PondfishBoss.TEETH_RAKE);
					} else {
						this.setMoveNow(PondfishBoss.DRAG_TO_HELL);
					}
				}
				break;
			case 1:
				if (this.lastMove(PondfishBoss.TEETH_RAKE)) {
					if (num < 50) {
						this.setMoveNow(PondfishBoss.CHOMP);
					} else {
						this.setMoveNow(PondfishBoss.FEET_UNDER);
					}
				} else {
					if (num < 33) {
						this.setMoveNow(PondfishBoss.CHOMP);
					} else if (num < 67) {
						this.setMoveNow(PondfishBoss.TEETH_RAKE);
					} else {
						this.setMoveNow(PondfishBoss.FEET_UNDER);
					}
				}
				break;
			case 2:
				if (this.lastTwoMoves(PondfishBoss.CHOMP)) {
					if (num < 50) {
						this.setMoveNow(PondfishBoss.TEETH_RAKE);
					} else {
						this.setMoveNow(PondfishBoss.FEET_UNDER);
					}
				} else {
					if (num < 33) {
						this.setMoveNow(PondfishBoss.CHOMP);
					} else if (num < 67) {
						this.setMoveNow(PondfishBoss.TEETH_RAKE);
					} else {
						this.setMoveNow(PondfishBoss.FEET_UNDER);
					}
				}
				break;
			case 3:
				this.setMoveNow(PondfishBoss.DRAG_TO_HELL); 
				break;
		}
    }
    
    @Override
    public void deathReact() {
		if (!this.isDeadOrEscaped() && !this.isFirstTurn) {
			//AbstractDungeon.actionManager.addToBottom(new TalkAction(this, CaptainAbe.DIALOG[2]));
			 if (this.nextMove != PondfishBoss.LIVING_LANTERN) {
				this.nextPlannedMove = this.nextMove;
				this.setMove(PondfishBoss.MOVES[1], PondfishBoss.LIVING_LANTERN, Intent.BUFF);
				this.createIntent();
				AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
				this.setNextTurn(PondfishBoss.LIVING_LANTERN);
			 }
		}
    }
    
    @Override
    public void changeState(final String stateName) {
        switch (stateName) {
            case "Activate": {
                AbstractDungeon.scene.fadeOutAmbiance();
				CardCrawlGame.music.fadeOutTempBGM();
                AbstractDungeon.getCurrRoom().playBGM("PONDFISH_THEME");
                
                break;
            }
            case "Deactivate": {
                AbstractDungeon.scene.fadeOutAmbiance();
                AbstractDungeon.getCurrRoom().playBGM("PONDFISH_THEME");
                
                break;
            }
        }
    }
	/*
    @Override
    public void damage(final DamageInfo info) {
        final int tmpHealth = this.currentHealth;
        super.damage(info);
        if (this.isOpen && !this.closeUpTriggered && tmpHealth > this.currentHealth && !this.isDying) {
            this.dmgTaken += tmpHealth - this.currentHealth;
            if (this.getPower("Mode Shift") != null) {
                final AbstractPower power = this.getPower("Mode Shift");
                power.amount -= tmpHealth - this.currentHealth;
                this.getPower("Mode Shift").updateDescription();
            }
            if (this.dmgTaken >= this.dmgThreshold) {
                this.dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, false), 0.05f, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                this.closeUpTriggered = true;
            }
        }
    }
    */
    @Override
    public void render(final SpriteBatch sb) {
        super.render(sb);
		if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead) {
			for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (m instanceof CaptainAbe && !m.halfDead) {
					//m.render(sb);
					m.renderHealth(sb);
					this.renderNameFor(m, sb);
				}
			}
		}
    }
	
	private void renderNameFor(AbstractMonster m, final SpriteBatch sb) {
        if ((!AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY) && !m.isDying) {
            final Color c = new Color();
			final float hoverTimer = (float)ReflectionHacks.getPrivate((Object)m, (Class)AbstractMonster.class, "hoverTimer");
            if (hoverTimer != 0.0f) {
                if (hoverTimer * 2.0f > 1.0f) {
                    c.a = 1.0f;
                }
                else {
                    c.a = hoverTimer * 2.0f;
                }
            }
            else {
                c.a = MathHelper.slowColorLerpSnap(c.a, 0.0f);
            }
            final float tmp = Interpolation.exp5Out.apply(1.5f, 2.0f, hoverTimer);
            c.r = Interpolation.fade.apply(Color.DARK_GRAY.r, Settings.CREAM_COLOR.r, hoverTimer * 10.0f);
            c.g = Interpolation.fade.apply(Color.DARK_GRAY.g, Settings.CREAM_COLOR.g, hoverTimer * 3.0f);
            c.b = Interpolation.fade.apply(Color.DARK_GRAY.b, Settings.CREAM_COLOR.b, hoverTimer * 3.0f);
            final float y = Interpolation.exp10Out.apply(m.healthHb.cY, m.healthHb.cY - 8.0f * Settings.scale, c.a);
            final float x = m.hb.cX - m.animX;
            sb.setColor(new Color(0.0f, 0.0f, 0.0f, c.a / 2.0f * m.hbAlpha));
            final TextureAtlas.AtlasRegion img = ImageMaster.MOVE_NAME_BG;
            sb.draw(img, x - img.packedWidth / 2.0f, y - img.packedHeight / 2.0f, img.packedWidth / 2.0f, img.packedHeight / 2.0f, img.packedWidth, img.packedHeight, Settings.scale * tmp, Settings.scale * 2.0f, 0.0f);
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, m.name, x, y, new Color(c.r, c.g, c.b, c.a * m.hbAlpha));
        }
	}
    
    @Override
    public void die() {
        AbstractDungeon.getCurrRoom().cannotLose = false;
        this.useFastShakeAnimation(5.0f);
        CardCrawlGame.screenShake.rumble(4.0f);
        this.deathTimer += 1.5f;
        super.die();
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                m.deathReact();
            }
        }
        //this.onBossVictoryLogic();
        //UnlockTracker.hardUnlockOverride("GUARDIAN");
        //UnlockTracker.unlockAchievement("GUARDIAN");
    }
    
    static {
        logger = LogManager.getLogger(PondfishBoss.class.getName());
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Pondfish");
        NAME = PondfishBoss.monsterStrings.NAME;
        MOVES = PondfishBoss.monsterStrings.MOVES;
        DIALOG = PondfishBoss.monsterStrings.DIALOG;
    }
}
