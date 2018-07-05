package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.*;
import java.util.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.ui.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;

public class TransientTotem extends AbstractRelic
{
    public static final String ID = "Transient Totem";
    private int chests;
	public int currentEffect;
	public int relicEffect;
	public String psuedoHasRelic;
	
	public static final int NO_EFFECT = 0;
	public static final int CURSED_KEY = 1;
	public static final int ECTOPLASM = 2;
	public static final int SOZU = 3;
	public static final int MARK_OF_PAIN = 4;
	public static final int VELVET_CHOKER = 5;
	public static final int RUNIC_DOME = 6;
	public static final int PHILOSOPHERS_STONE = 7;
	public static final int BUSTED_CROWN = 8;
	public static final int SNECKO_EYE = 9;
	public static final int RUNIC_PYRAMID = 10;
	public static final int HONEY_JAR = 11;
	public static final int SNECKO_SCALES = 12;
	public static final int PANDORAS_BOX = 13;
	public static final int BLACK_STAR = 14;
	public static final int ETERNAL_FEATHER = 15;
	public static final int WHITE_BEAST = 16;
	public static final int TINY_HOUSE = 17;
	public static final int CALLING_BELL = 18;
	public static final int KINTSUGI = 19;
	public static final int BLACK_BLOOD = 20;
	
    public TransientTotem() {
        super("Transient Totem", "betaRelic.png", RelicTier.BOSS, LandingSound.MAGICAL);
		this.chests = 0;
		this.currentEffect = 0;
		this.relicEffect = 0;
		this.psuedoHasRelic = "Transient Totem";
    }
    
    @Override
    public void onEnterRoom(final AbstractRoom room) {
		if (AbstractDungeon.getCurrRoom() == null || !(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) {
			this.psuedoHasRelic = "Transient Totem";
			this.relicEffect = 0;
			this.currentEffect = 0;
			this.description = this.getUpdatedDescription();
		}
    }
	
    @Override
    public String getUpdatedDescription() {
        if (AbstractDungeon.player != null) {
			switch (this.currentEffect) {
				case 0:
					return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
				case 1:
					return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[2] + this.chests + this.DESCRIPTIONS[3];
				default:
					return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[this.currentEffect + 2];
			}
		}
		return this.DESCRIPTIONS[0];
    }
    
	private void randomizeEffect() {
        this.psuedoHasRelic = "Transient Totem";
		ArrayList<Integer> totemRelicPool = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			totemRelicPool.add(CURSED_KEY);
			totemRelicPool.add(MARK_OF_PAIN);
			totemRelicPool.add(PHILOSOPHERS_STONE);
			if (!AbstractDungeon.player.hasRelic("Ectoplasm")) {
				totemRelicPool.add(ECTOPLASM);
			}
			if (!AbstractDungeon.player.hasRelic("Sozu")) {
				totemRelicPool.add(SOZU);
			}
			if (!AbstractDungeon.player.hasRelic("Velvet Choker")) {
				totemRelicPool.add(VELVET_CHOKER);
			}
		}
		for (int i = 0; i < 2; i++) {
			totemRelicPool.add(PANDORAS_BOX);
			if (!AbstractDungeon.player.hasRelic("Honey Jar")) {
				totemRelicPool.add(HONEY_JAR);
			}
			if (!AbstractDungeon.player.hasRelic("Snecko Eye") && !AbstractDungeon.player.hasRelic("Snecko Heart")) {
				totemRelicPool.add(SNECKO_EYE);
			}
			if (!AbstractDungeon.player.hasRelic("Runic Dome")) {
				totemRelicPool.add(RUNIC_DOME);
			}
		}
		Collections.shuffle(totemRelicPool);
		this.currentEffect = totemRelicPool.get(0);
		this.relicEffect = this.currentEffect;
		this.description = this.getUpdatedDescription();
	}
	
    @Override
    public void atBattleStartPreDraw() {
		
		switch (this.currentEffect) {
			case CURSED_KEY:
				this.flash();
				if (this.chests > 0) {
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse().makeCopy(), this.chests, true, false));
				}
				break;
			case ECTOPLASM:
				this.psuedoHasRelic = "Ectoplasm";
				break;
			case SOZU:
				this.psuedoHasRelic = "Sozu";
				break;
			case MARK_OF_PAIN:
				this.flash();
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Wound(), 2, true, false));
				break;
			case RUNIC_DOME:
				this.psuedoHasRelic = "Runic Dome";
				break;
			case BUSTED_CROWN:
				this.psuedoHasRelic = "Busted Crown";
				break;
			case HONEY_JAR:
				this.psuedoHasRelic = "Honey Jar";
				break;
			case PANDORAS_BOX:
				this.flash();
				for (int i=0; i < 5; i++) {
					AbstractCard.CardRarity cardRarity = AbstractCard.CardRarity.COMMON;
					final int random = MathUtils.random(20);
					if (random == 20) {
						cardRarity = AbstractCard.CardRarity.RARE;
					}
					else if (random >= 15) {
						cardRarity = AbstractCard.CardRarity.UNCOMMON;
					}
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.getCard(cardRarity).makeCopy(), 1, true, false));
				}
				break;
		}
    }
	
    @Override
    public void atPreBattle() {
		this.randomizeEffect();
		switch (this.currentEffect) {
			case SNECKO_EYE:
				this.flash();
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConfusionPower(AbstractDungeon.player)));
				break;
		}
    }
	
    @Override
    public void atBattleStart() {
		switch (this.currentEffect) {
			case VELVET_CHOKER:
				this.counter = 0;
				break;
			case PHILOSOPHERS_STONE:
				for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
					AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(m, this));
					m.addPower(new StrengthPower(m, 2));
				}
				break;
		}
        
    }
    
    @Override
    public void atTurnStart() {
		switch (this.currentEffect) {
			case VELVET_CHOKER:
				this.counter = 0;
				break;
		}
    }
    
    @Override
    public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
		switch (this.currentEffect) {
			case VELVET_CHOKER:
				if (this.counter < 6 && card.type != AbstractCard.CardType.CURSE) {
					++this.counter;
					if (this.counter >= 6) {
						this.flash();
					}
				}
			break;
		}
    }
    
    @Override
    public boolean canPlay(final AbstractCard card) {
		switch (this.currentEffect) {
			case VELVET_CHOKER:
			if (this.counter >= 6 && card.type != AbstractCard.CardType.CURSE) {
				card.cantUseMessage = "I have already played 6 cards this turn!";
				return false;
			}
			break;
		}
        return true;
    }
    
    @Override
    public void onVictory() {
		this.pulse = false;
		this.currentEffect = 0;
		this.description = this.getUpdatedDescription();
        this.counter = -1;
	}
	
    @Override
    public void onChestOpen(final boolean bossChest) {
        if (!bossChest) {
            this.chests++;
        }
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new TransientTotem();
    }
	
	@Override
    public void renderLock(final SpriteBatch sb, final Color outlineColor) {
		final float rot = (float)ReflectionHacks.getPrivate((Object)this, (Class)AbstractRelic.class, "rotation");
        sb.setColor(outlineColor);
        sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LOCK, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        if (this.hb.hovered) {
            String unlockReq = UnlockTracker.unlockReqs.get(this.relicId);
            if (unlockReq == null) {
                unlockReq = "Missing unlock req.";
            }
            if (InputHelper.mX < 1400.0f * Settings.scale) {
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW && InputHelper.mY < Settings.HEIGHT / 5.0f) {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY + 100.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
                else {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
            }
            else {
                TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
            }
            float tmpX = this.currentX;
            float tmpY = this.currentY;
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LOCK, tmpX - 64.0f, tmpY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        }
        this.hb.render(sb);
    }
}
