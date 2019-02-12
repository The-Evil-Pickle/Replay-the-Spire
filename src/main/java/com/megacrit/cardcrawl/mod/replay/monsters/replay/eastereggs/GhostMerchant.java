package com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs;
//hubris crossover thing, might finish it might not
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.evacipated.cardcrawl.mod.hubris.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.esotericsoftware.spine.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.evacipated.cardcrawl.mod.hubris.powers.*;
import com.evacipated.cardcrawl.mod.hubris.relics.NiceRug;
import com.evacipated.cardcrawl.mod.hubris.vfx.combat.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import basemod.ReflectionHacks;
import conspire.powers.GhostlyPower;

import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.powers.*;
import com.evacipated.cardcrawl.mod.hubris.actions.*;
import com.evacipated.cardcrawl.mod.hubris.actions.utility.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.evacipated.cardcrawl.mod.hubris.blights.*;
import com.megacrit.cardcrawl.blights.*;
import java.io.*;
import java.util.*;

public class GhostMerchant extends AbstractMonster
{
    public static final String ID = "Replay:GhostMerchant";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final float DRAW_X;
    private static final float DRAW_Y;
    private static final int START_HP = 10;
    public static final int REAL_HP = 200;
    private static final float TIME_SCALE = 4.0f;
    private static byte ESCAPE = 0;
    private static byte ATTACK = 1;
    private static byte STRENGTH_UP = 2;
    private static byte ATTACK_STRENGTH_UP = 3;
    private static byte HALF_DEAD = 7;
    private static final int METALLICIZE_AMT = 35;
    private static final int ARTIFACT_AMT = 5;
    private static final Map<Byte, Integer> throwAmounts;
    private boolean doEscape;
    private int turn;
    private boolean thresholdReached;
    private int abuse;
    private boolean boss;
    
    public GhostMerchant(float x, float y) {
        super(NAME, ID, 10, -10.0f, -30.0f, 180.0f, 150.0f, (String)null, x, y);
        this.doEscape = true;
        this.thresholdReached = false;
        this.boss = true;
        this.loadAnimation("images/npcs/merchant/skeleton.atlas", "images/npcs/merchant/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0f);
        this.flipHorizontal = this.drawX < AbstractDungeon.player.drawX;
        this.type = AbstractMonster.EnemyType.ELITE;
        this.gold = 300;
        this.halfDead = false;
        this.damage.add(new DamageInfo(this, 1));
        this.turn = 0;
        this.maxHealth = 200;
        this.maxHealth *= (int)((this.abuse >= 3) ? 1.5f : 1.0f);
        this.currentHealth = this.maxHealth;
    }
    
    public void render(final SpriteBatch sb) {
        if (!this.boss && (!this.isDeadOrEscaped() || AbstractDungeon.getCurrRoom().cannotLose)) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.MERCHANT_RUG_IMG, GhostMerchant.DRAW_X, GhostMerchant.DRAW_Y, 512.0f * Settings.scale, 512.0f * Settings.scale);
        }
        super.render(sb);
    }
    
    public void usePreBattleAction() {
        final int artifact_amt = 5;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new ArtifactPower(this, artifact_amt), artifact_amt));
        final int metal_amt = 35;
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(this, this, metal_amt, true));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new GoldShieldPower(this, metal_amt), metal_amt));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new GhostlyPower(this)));
        AbstractDungeon.actionManager.addToTop(new TalkAction(this, GhostMerchant.DIALOG[0], 0.5f, 3.0f));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    public void takeTurn() {
        if (this.nextMove == GhostMerchant.ESCAPE) {
        	AbstractDungeon.actionManager.addToBottom(new TalkAction(this, GhostMerchant.DIALOG[1], 0.5f, 3.0f));
        	AbstractDungeon.actionManager.addToBottom(new StealGoldAction(AbstractDungeon.player, this, AbstractDungeon.player.gold, true));
        	this.gold += AbstractDungeon.player.gold;
        	int armor = AbstractDungeon.player.gold / (int)ReflectionHacks.getPrivateStatic(NiceRug.class, "GOLD_AMT");
        	if (armor > 0) {
        		if (AbstractDungeon.player.hasPower(PlatedArmorPower.POWER_ID)) {
            		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, this, PlatedArmorPower.POWER_ID));
            	}
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GoldShieldPower(this, armor), armor));
        	}
        	this.doEscape = false;
        }
        else if (this.nextMove == GhostMerchant.STRENGTH_UP) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
        }
        else {
            final Integer throwAmount = GhostMerchant.throwAmounts.get(this.nextMove);
            if (throwAmount != null && throwAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ThrowGoldAction(AbstractDungeon.player, this, throwAmount, false));
                AbstractDungeon.actionManager.addToBottom(new ForceWaitAction(1.6f));
                for (int i = 0; i < throwAmount; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), true));
                }
            }
            if (this.nextMove == GhostMerchant.ATTACK_STRENGTH_UP) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            }
        }
        if (this.turn >= 0) {
            ++this.turn;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(final int num) {
        if (this.doEscape) {
            this.setMove(GhostMerchant.ESCAPE, AbstractMonster.Intent.BUFF);
            return;
        }
        if (this.turn == 1) {
            this.setMove(GhostMerchant.ATTACK, AbstractMonster.Intent.ATTACK, 1, (int)GhostMerchant.throwAmounts.get(GhostMerchant.ATTACK), true);
            return;
        }
        if (this.turn == 2) {
            this.setMove(GhostMerchant.ATTACK_STRENGTH_UP, AbstractMonster.Intent.ATTACK_BUFF, 1, (int)GhostMerchant.throwAmounts.get(GhostMerchant.ATTACK_STRENGTH_UP), true);
            return;
        }
        if (num < 40) {
            if (this.lastMove(GhostMerchant.STRENGTH_UP)) {
                this.rollMove();
                return;
            }
            this.setMove(StrengthPotion.NAME, GhostMerchant.STRENGTH_UP, AbstractMonster.Intent.BUFF);
        }
        else if (num < 60) {
            this.setMove(GhostMerchant.ATTACK_STRENGTH_UP, AbstractMonster.Intent.ATTACK_BUFF, 1, (int)GhostMerchant.throwAmounts.get(GhostMerchant.ATTACK_STRENGTH_UP), true);
        }
        else {
            this.setMove(GhostMerchant.ATTACK, AbstractMonster.Intent.ATTACK, 1, (int)GhostMerchant.throwAmounts.get(GhostMerchant.ATTACK), true);
        }
    }
    
    public void damage(final DamageInfo info) {
        super.damage(info);
        this.state.setTimeScale(4.0f * (this.currentHealth / this.maxHealth));
        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;
            for (final AbstractPower p2 : this.powers) {
                p2.onDeath();
            }
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath((AbstractMonster)this);
            }
            this.powers.removeIf(p -> p.type == AbstractPower.PowerType.DEBUFF);
            this.setMove(GhostMerchant.HALF_DEAD, AbstractMonster.Intent.UNKNOWN);
            this.createIntent();
            this.applyPowers();
            AbstractDungeon.actionManager.cardQueue.clear();
            for (final AbstractCard c : AbstractDungeon.player.limbo.group) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }
            AbstractDungeon.player.limbo.group.clear();
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
        }
    }
    
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            if (!this.boss) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.hb.cX, this.hb.cY, RelicLibrary.getRelic("hubris:NiceRug").makeCopy());
            }
            if (CardCrawlGame.playerName.equals(new String(new byte[] { 82, 101, 105, 110, 97 }))) {
                if (this.abuse >= 3 && MathUtils.randomBoolean(0.5f)) {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain(this.hb.cX, this.hb.cY, (AbstractBlight)new Reinasbane());
                }
                ++this.abuse;
                try {
                    HubrisMod.otherSaveData.setInt("abuse", this.abuse);
                    HubrisMod.otherSaveData.save();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    static {
        DRAW_X = Settings.WIDTH * 0.5f + 34.0f * Settings.scale;
        DRAW_Y = AbstractDungeon.floorY - 109.0f * Settings.scale;
        (throwAmounts = new HashMap<Byte, Integer>()).put(GhostMerchant.ATTACK, 20);
        GhostMerchant.throwAmounts.put(GhostMerchant.ATTACK_STRENGTH_UP, 15);
    }
}
