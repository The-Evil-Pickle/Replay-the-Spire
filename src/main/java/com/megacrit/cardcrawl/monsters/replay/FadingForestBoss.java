package com.megacrit.cardcrawl.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.replay.fadingForest.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.relicPowers.*;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import basemod.*;
import basemod.abstracts.CustomCard;

public class FadingForestBoss extends AbstractMonster
{
    public static final String ID = "FadingForestBoss";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final ArrayList<EventStrings> eventStrings;
    private static final int HP = 800;
    private static final float HB_X_F = 0.0f;
    private static final float HB_Y_F = -40.0f;
    private static final float HB_W = 460.0f;
    private static final float HB_H = 350.0f;
    private static final int DEATH_DMG = 30;
    private static final int A_2_DEATH_DMG = 40;
	
    private int fish_healing;
	private int jax_damage;
	private int jax_str;
	private int idol_damage;
	private int mushroom_damage;
	private int tesseract_damage;
	private int vampires_damage;
	private int vampires_hploss;
	private int headache_amt;
	private int ravine_damage;
	private int jump_damage;
	
	private byte rnd_atk1;
	private byte rnd_atk2;
	private byte rnd_atk3;
	
	private byte c_event;
	private int c_part;
	private int turncount;
	private int savedDamage;
	private int savedDamage2;
	
    private static final byte ENTER_FOREST = 1;
    private static final byte OPPR_SERPENT = 2;
    private static final byte OPPR_SLIME = 3;
    private static final byte BONUS_FISH = 4;
    private static final byte BONUS_WISP = 5;
    private static final byte ATTACK_IDOL = 6;
    private static final byte ATTACK_MUSHROOMS = 7;
    private static final byte ATTACK_TESSERACT = 8;
    private static final byte ATTACK_VAMPIRES = 9;
    private static final byte ATTACK_RAVINE = 10;
    private static final byte DEBUFF_HEADACHE = 11;
    private static final byte DEBUFF_WALLS = 12;
    private static final byte FORK_NOISE = 13;
    private static final byte FORK_PATHS = 14;
    private static final byte FOREST_FINALE = 15;
	
	/*
	1: enter
	2: serpent/slime
	3: idol, mushroom, ravine
	4: fish
	5: headache
	6: noise/paths fork
	7: vampires, tesseract
	8: walls
	9: idol, ravine
	10: finale
	*/
	
	public GenericEventDialog imageEventText;
    
    public FadingForestBoss() {
        super(FadingForestBoss.NAME, "FadingForestBoss", FadingForestBoss.HP, 200.0f, 160.0f, 300.0f, 350.0f, "images/monsters/fadingForest/fadingForest.png", 0.0f, -200.0f);
		AbstractEvent.type = AbstractEvent.EventType.IMAGE;
		this.imageEventText = new GenericEventDialog();
		this.imageEventText.hide();
		this.imageEventText.clear();
		ForestEventAction.forest = this;
        this.turncount = 0;
		this.savedDamage = 10;
		this.savedDamage2 = 10;
        this.dialogX = -100.0f * Settings.scale;
        this.dialogY -= 20.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.jax_damage = 7;
            this.idol_damage = 18;
            this.mushroom_damage = 14;
            this.tesseract_damage = 6;
            this.vampires_damage = 14;
            this.vampires_hploss = 9;
            this.ravine_damage = 18;
            this.jump_damage = 24;
			this.fish_healing = 8;
			this.headache_amt = 3;
        }
        else {
            this.jax_damage = 5;
            this.idol_damage = 14;
            this.mushroom_damage = 12;
            this.tesseract_damage = 4;
            this.vampires_damage = 12;
            this.vampires_hploss = 8;
            this.ravine_damage = 14;
            this.jump_damage = 18;
			this.fish_healing = 10;
			this.headache_amt = 2;
        }
        this.damage.add(new DamageInfo(this, this.jax_damage));
        this.damage.add(new DamageInfo(this, this.idol_damage));
        this.damage.add(new DamageInfo(this, this.mushroom_damage));
        this.damage.add(new DamageInfo(this, this.tesseract_damage));
        this.damage.add(new DamageInfo(this, this.vampires_damage));
        this.damage.add(new DamageInfo(this, this.vampires_hploss));
        this.damage.add(new DamageInfo(this, this.ravine_damage));
    }
    
    @Override
    public void usePreBattleAction() {
		AbstractEvent.type = AbstractEvent.EventType.IMAGE;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FadingPower(this, 10)));
		if (AbstractDungeon.ascensionLevel >= 9) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 5)));
		}
		AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FadingForestBoss.DIALOG[(int)(Math.random() * (4))]));
    }
    
	private void giveRelicEffect() {
		switch (AbstractDungeon.miscRng.random(0, 5)) {
			case 0:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_HourglassPower(AbstractDungeon.player), 3));
				break;
			case 1:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_ThreadPower(AbstractDungeon.player), 1));
				break;
			case 2:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_OrichalcumPower(AbstractDungeon.player), 6));
				break;
			case 3:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_KunaiPower(AbstractDungeon.player), 1));
				break;
			case 4:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_GiryaPower(AbstractDungeon.player), 3));
				break;
			case 5:
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new RP_LetterOpenerPower(AbstractDungeon.player), 5));
				break;
		}
	}
	
	private void setNextTurn(byte nextTurn) {
		switch (nextTurn) {
            case ENTER_FOREST: case OPPR_SERPENT: case OPPR_SLIME: case FORK_NOISE: case FORK_PATHS: case BONUS_FISH: case BONUS_WISP: {
				this.setMove(nextTurn, Intent.UNKNOWN);
				break;
			}
            case DEBUFF_HEADACHE: {
				this.setMove(nextTurn, Intent.DEBUFF);
				break;
			}
            case DEBUFF_WALLS: {
				this.setMove(nextTurn, Intent.STRONG_DEBUFF);
				break;
			}
			case ATTACK_IDOL: {
				this.setMove(nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
				break;
			}
			case ATTACK_MUSHROOMS: {
				this.setMove(nextTurn, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
				break;
			}
			case ATTACK_RAVINE: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(6).base);
				break;
			}
			case ATTACK_TESSERACT: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(3).base, 2, true);
				break;
			}
			case ATTACK_VAMPIRES: {
				this.setMove(nextTurn, Intent.ATTACK, this.damage.get(4).base);
				break;
			}
			default: {
				this.setMove(nextTurn, Intent.NONE);	
				break;	
			}
		}
	}
	
	private String eDesc(int i) {
		return FadingForestBoss.eventStrings.get(((int)this.c_event) - 1).DESCRIPTIONS[i];
	}
	private String eOp(int i) {
		return FadingForestBoss.eventStrings.get(((int)this.c_event) - 1).OPTIONS[i];
	}
	private String eTitle() {
		return FadingForestBoss.eventStrings.get(((int)this.c_event) - 1).NAME;
	}
	
    @Override
    public void takeTurn() {
		AbstractEvent.type = AbstractEvent.EventType.IMAGE;
		this.c_event = this.nextMove;
		this.c_part = 0;
		this.turncount++;
		ReflectionHacks.setPrivate((Object)this.imageEventText, (Class)GenericEventDialog.class, "title", (Object)this.eTitle());
        switch (this.nextMove) {
            case ENTER_FOREST: {
            	AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FadingForestBoss.DIALOG[4 + (int)(Math.random() * (4))]));
				this.imageEventText.loadImage("images/events/fadingForest/fadingForest.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case OPPR_SERPENT: {
				this.imageEventText.loadImage("images/events/fadingForest/liarsGame.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0), new Doubt());
				this.imageEventText.setDialogOption(this.eOp(1));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case OPPR_SLIME: {
				this.imageEventText.loadImage("images/events/fadingForest/scrapOoze.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0), new Slimed());
				this.imageEventText.setDialogOption(this.eOp(1));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case ATTACK_MUSHROOMS: {
				this.imageEventText.loadImage("images/events/fadingForest/mushrooms.jpg");
				this.savedDamage = this.damage.get(2).base;
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2) + this.savedDamage + this.eOp(3), new SpreadingInfection());
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case BONUS_FISH: {
				this.imageEventText.loadImage("images/events/fadingForest/fishing.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0) + this.fish_healing + this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2) + 1 + this.eOp(3));
				this.imageEventText.setDialogOption(this.eOp(4), new Regret());
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case BONUS_WISP: {
				this.imageEventText.loadImage("images/events/fadingForest/bonfire.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0) + this.fish_healing + this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2));
				AbstractCard c = new Burn();
				c.upgrade();
				this.imageEventText.setDialogOption(this.eOp(3), c);
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case DEBUFF_HEADACHE: {
				this.imageEventText.loadImage("images/events/fadingForest/fadingForest.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0) + this.headache_amt + this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2) + this.headache_amt + this.eOp(3));
				this.imageEventText.setDialogOption(this.eOp(4) + (this.headache_amt * 2) + this.eOp(5), new Dazed());
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case FORK_NOISE: {
				this.imageEventText.loadImage("images/events/fadingForest/fadingForest.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0));
				this.imageEventText.setDialogOption(this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case FORK_PATHS: {
				this.imageEventText.loadImage("images/events/fadingForest/fadingForest.jpg");
                this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(0));
				this.imageEventText.setDialogOption(this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case ATTACK_VAMPIRES: {
				this.imageEventText.loadImage("images/events/fadingForest/fadingForest.jpg");
				this.savedDamage = this.damage.get(5).base;
				this.savedDamage2 = this.damage.get(4).base;
				if (AbstractDungeon.player instanceof Ironclad) {
					this.imageEventText.updateBodyText(this.eDesc(0));
				} else if (AbstractDungeon.player instanceof TheSilent) {
					this.imageEventText.updateBodyText(this.eDesc(1));
				} else {
					this.imageEventText.updateBodyText(this.eDesc(2));
				}
				this.imageEventText.setDialogOption(this.eOp(0) + this.savedDamage + this.eOp(1));
				this.imageEventText.setDialogOption(this.eOp(2) + this.savedDamage2 + this.eOp(3));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
            case ATTACK_TESSERACT: {
				this.imageEventText.loadImage("images/events/fadingForest/sensoryStone.jpg");
				this.savedDamage = this.damage.get(3).base;
				this.imageEventText.updateBodyText(this.eDesc(0));
				this.imageEventText.setDialogOption(this.eOp(6));
				AbstractDungeon.actionManager.addToBottom(new ForestEventAction());
				AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
				break;
            }
        }
    }
    
    @Override
    protected void getMove(final int num) {
		if (this.turncount <= 0) {
			this.setMove(ENTER_FOREST, Intent.UNKNOWN);
			return;
		}
		switch(this.turncount) {
			case 1:
				if (num > 50) {
					this.setNextTurn(OPPR_SERPENT);
				} else {
					this.setNextTurn(OPPR_SLIME);
				}
				return;
			case 2:
				this.setNextTurn(ATTACK_MUSHROOMS);
				return;
			case 3:
				if (num > 50) {
					this.setNextTurn(BONUS_FISH);
				} else {
					this.setNextTurn(BONUS_WISP);
				}
				return;
			case 4:
				this.setNextTurn(DEBUFF_HEADACHE);
				return;
			case 5:
				if (num > 50) {
					this.setNextTurn(FORK_NOISE);
				} else {
					this.setNextTurn(FORK_PATHS);
				}
				return;
			case 6:
				if (num > 50) {
					this.setNextTurn(ATTACK_VAMPIRES);
				} else {
					this.setNextTurn(ATTACK_TESSERACT);
				}
				return;
		}
    }
	
	public void buttonEffect(final int p0) {
		switch(c_event) {
			case ENTER_FOREST: {
				switch(c_part) {
					case 0:
						this.imageEventText.updateBodyText(this.eDesc(1));
						this.imageEventText.updateDialogOption(0, this.eOp(1));
						this.imageEventText.setDialogOption(this.eOp(2));
						this.imageEventText.setDialogOption(this.eOp(3));
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part++;
						break;
					case 1:
						AbstractMonster m = new FF_GremlinNob();
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(2));
								m = new FF_GremlinNob();
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
							case 1:
								this.imageEventText.updateBodyText(this.eDesc(3));
								m = new FF_Lagavulin(true);
								//AbstractDungeon.actionManager.addToTop(new GainBlockAction(m, m, 8));
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
							default:
								this.imageEventText.updateBodyText(this.eDesc(4));
								m = new FF_Sentry(0, 30, false);
								//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new ArtifactPower(m, 1), 1));
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								m = new FF_Sentry(-200, 30, true);
								//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new ArtifactPower(m, 1), 1));
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
						}
						this.imageEventText.updateDialogOption(0, this.eOp(4));
						this.imageEventText.clearRemainingOptions();
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part++;
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case OPPR_SERPENT: {
				switch(this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(1));
								this.imageEventText.updateDialogOption(0, this.eOp(2));
								this.imageEventText.clearRemainingOptions();
								this.c_part = 1;
								break;
							default:
								this.imageEventText.updateBodyText(this.eDesc(2));
								this.imageEventText.updateDialogOption(0, this.eOp(3));
								this.imageEventText.clearRemainingOptions();
								this.c_part = 2;
								break;
						}
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						break;
					case 1:
						this.imageEventText.updateBodyText(this.eDesc(3));
						this.imageEventText.updateDialogOption(0, this.eOp(3));
						this.imageEventText.clearRemainingOptions();
						this.giveRelicEffect();
						AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(new Doubt(), 1, true, false));
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part = 2;
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case OPPR_SLIME: {
				switch(this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(1));
								this.imageEventText.updateDialogOption(0, this.eOp(1));
								this.imageEventText.clearRemainingOptions();
								this.c_part = 1;
								AbstractDungeon.actionManager.addToTop(new ForestEventAction());
								break;
							default:
								this.imageEventText.clear();
								break;
						}
						break;
					case 1:
						this.giveRelicEffect();
						AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(new Slimed(), 3, true, false));
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case ATTACK_MUSHROOMS: {
				switch(p0) {
					case 0:
						AbstractMonster m = new FF_FungiBeast(-350f, 0f);
						//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new FadingPower(m, 3), 3));
						//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new SporeCloudPower(m, 1), 1));
						AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
						break;
					default:
						AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(new SpreadingInfection(), 1));
						AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(this, this.savedDamage), AbstractGameAction.AttackEffect.POISON));
						break;
				}
				this.imageEventText.clear();
				break;
			}
			case BONUS_FISH: {
				switch(this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(1));
								this.imageEventText.updateDialogOption(0, this.eOp(5));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.fish_healing));
								break;
							case 1:
								this.imageEventText.updateBodyText(this.eDesc(2));
								this.imageEventText.updateDialogOption(0, this.eOp(5));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, 1), 1));
								break;
							case 2:
								this.imageEventText.updateBodyText(this.eDesc(3));
								this.imageEventText.updateDialogOption(0, this.eOp(5));
								this.imageEventText.clearRemainingOptions();
								this.giveRelicEffect();
								AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(new Regret(), 1, true, false));
								break;
							default:
								this.imageEventText.clear();
								break;
						}
						this.c_part = 1;
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case BONUS_WISP: {
				switch(this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(1));
								this.imageEventText.updateDialogOption(0, this.eOp(4));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.fish_healing));
								break;
							case 1:
								this.imageEventText.updateBodyText(this.eDesc(2));
								this.imageEventText.updateDialogOption(0, this.eOp(4));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new WispExhaustAction(AbstractDungeon.player, this));
								break;
							case 2:
								this.imageEventText.updateBodyText(this.eDesc(3));
								this.imageEventText.updateDialogOption(0, this.eOp(4));
								this.imageEventText.clearRemainingOptions();
								this.giveRelicEffect();
								AbstractCard c = new Burn();
								c.upgrade();
								AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(c, 1, true, false));
								break;
							default:
								this.imageEventText.clear();
								break;
						}
						this.c_part = 1;
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case DEBUFF_HEADACHE: {
				switch(this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(1));
								this.imageEventText.updateDialogOption(0, this.eOp(6));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.headache_amt, true), this.headache_amt));
								break;
							case 1:
								this.imageEventText.updateBodyText(this.eDesc(3));
								this.imageEventText.updateDialogOption(0, this.eOp(6));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.headache_amt, true), this.headache_amt));
								break;
							case 2:
								this.imageEventText.updateBodyText(this.eDesc(4));
								this.imageEventText.updateDialogOption(0, this.eOp(6));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(new Dazed(), this.headache_amt * 2));
								break;
						}
						this.c_part = 1;
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case FORK_NOISE: {
				switch(c_part) {
					case 0:
						AbstractMonster m = new FF_ComboSlime_L(-250, 0);
						switch(p0) {
							case 0:
								this.imageEventText.updateBodyText(this.eDesc(2));
								m = new FF_ComboSlime_L(-250, 0);
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
							case 1:
								this.imageEventText.updateBodyText(this.eDesc(3));
								m = new FF_JawWorm(-400, 0);
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								m = new FF_LouseNormal(-325, 0);
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								m = new FF_LouseDefensive(-250, 0);
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
							default:
								this.imageEventText.updateBodyText(this.eDesc(4));
								m = new FF_Sentry(0, 30, false);
								//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new ArtifactPower(m, 1), 1));
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								m = new FF_Sentry(-200, 30, true);
								//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new ArtifactPower(m, 1), 1));
								AbstractDungeon.actionManager.addToTop(new SpawnForestMonsterAction(m, true));
								break;
						}
						this.imageEventText.updateDialogOption(0, this.eOp(4));
						this.imageEventText.clearRemainingOptions();
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part++;
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
			case ATTACK_VAMPIRES: {
				switch (this.c_part) {
					case 0:
						switch(p0) {
							case 0:
								AbstractCard b = new Bite();
								AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(b, 5, true, true));
								ArrayList<AbstractCard> handCopy = new ArrayList<AbstractCard>();
								for (AbstractCard c : AbstractDungeon.player.hand.group) {
								  if (c instanceof Strike_Red || c instanceof Strike_Green || c instanceof Strike_Blue || (c instanceof CustomCard && ((CustomCard)c).isStrike())) {
									handCopy.add(c);
								  }
								}
								ArrayList<AbstractCard> deckCopy = new ArrayList<AbstractCard>();
								for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
								  if (c instanceof Strike_Red || c instanceof Strike_Green || c instanceof Strike_Blue || (c instanceof CustomCard && ((CustomCard)c).isStrike())) {
									deckCopy.add(c);
								  }
								}
								ArrayList<AbstractCard> discardCopy = new ArrayList<AbstractCard>();
								for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
								  if (c instanceof Strike_Red || c instanceof Strike_Green || c instanceof Strike_Blue || (c instanceof CustomCard && ((CustomCard)c).isStrike())) {
									discardCopy.add(c);
								  }
								}
								if (handCopy.isEmpty() && deckCopy.isEmpty() && discardCopy.isEmpty()) {
								  //AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, DESCRIPTIONS[3], true));
								}
								else
								{
								  for (AbstractCard c : handCopy) {
									AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
								  }
								  handCopy.clear();
								  for (AbstractCard c : deckCopy) {
									AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
								  }
								  deckCopy.clear();
								  for (AbstractCard c : discardCopy) {
									AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
								  }
								  discardCopy.clear();
								}
								AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, this, this.savedDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
								this.imageEventText.updateBodyText(this.eDesc(3));
								this.imageEventText.updateDialogOption(0, this.eOp(4));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new ForestEventAction());
								this.c_part++;
								break;
							default:
								AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(this, this.savedDamage2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
								this.imageEventText.updateBodyText(this.eDesc(4));
								this.imageEventText.updateDialogOption(0, this.eOp(4));
								this.imageEventText.clearRemainingOptions();
								AbstractDungeon.actionManager.addToTop(new ForestEventAction());
								this.c_part++;
								break;
						}
						break;
					default:
						this.imageEventText.clear();
						break;
				}
			}
			case ATTACK_TESSERACT: {
				switch(c_part) {
					case 0:
						this.imageEventText.updateBodyText(this.eDesc(1));
						this.imageEventText.setDialogOption(this.eOp(0) + this.savedDamage + this.eOp(3) + 2 + this.eOp(4));
						this.imageEventText.setDialogOption(this.eOp(1) + this.savedDamage + this.eOp(3) + 3 + this.eOp(4));
						this.imageEventText.setDialogOption(this.eOp(2) + this.savedDamage + this.eOp(3) + 4 + this.eOp(4));
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part++;
						break;
					case 1:
						for (int i = 0; i < p0 + 1; ++i) {
			                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomColorlessCardInCombat().makeCopy(), 1, false));
			            }
						for (int i = 0; i < p0 + 2; ++i) {
							AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(this, this.savedDamage), AbstractGameAction.AttackEffect.FIRE));
			            }
						this.imageEventText.updateBodyText(this.eDesc(p0 + 2));
						this.imageEventText.updateDialogOption(0, this.eOp(5));
						this.imageEventText.clearRemainingOptions();
						AbstractDungeon.actionManager.addToTop(new ForestEventAction());
						this.c_part++;
						break;
					default:
						this.imageEventText.clear();
						break;
				}
				break;
			}
		}
	}
	
    @Override
    public void render(final SpriteBatch sb) {
        super.render(sb);
		if (!AbstractDungeon.id.equals("Exordium")) {
			this.imageEventText.render(sb);
		}
		/*
		if (this.tint.color.a <= 0.751f) {
			this.tint.changeColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		} else if (this.tint.color.a >= 0.99f) {
			this.tint.changeColor(new Color(1.0f, 1.0f, 1.0f, 0.75f));
		}*/
		
    }
    
    @Override
    public void die() {
		this.imageEventText.clear();
		ForestEventAction.forest = null;
        this.deathTimer += 1.5f;
        super.die();
        this.onBossVictoryLogic();
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FadingForestBoss");
        NAME = FadingForestBoss.monsterStrings.NAME;
        MOVES = FadingForestBoss.monsterStrings.MOVES;
        DIALOG = FadingForestBoss.monsterStrings.DIALOG;
		eventStrings = new ArrayList<EventStrings>();
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Enter"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Serpent"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Scrap Ooze"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Big Fish"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Will-o-Wisp"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Golden Idol"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Mushrooms"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_SensoryStone"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Vampires"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Ravine"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Headache"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Curse"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Fork"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Fork2"));
		eventStrings.add(CardCrawlGame.languagePack.getEventString("FF_Finale"));
    }
}
