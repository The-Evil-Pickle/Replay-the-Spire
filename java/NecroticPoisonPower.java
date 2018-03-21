package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class NecroticPoisonPower
  extends AbstractPower
{
  public static final String POWER_ID = "Necrotic Poison";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Necrotic Poison");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  private AbstractCreature source;
  
  public NecroticPoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
  {
    this.name = NAME;
    this.ID = "Necrotic Poison";
    this.owner = owner;
    this.source = source;
    this.amount = poisonAmt;
    updateDescription();
    //loadRegion("poison");
	this.img = new Texture("img/powers/NecroticPoison.png");
    this.type = AbstractPower.PowerType.DEBUFF;
    
    this.isTurnBased = true;
  }
  
  public void updateDescription()
  {
    if ((this.owner == null) || (this.owner.isPlayer)) {
      this.description = (DESCRIPTIONS[0] + (this.amount * 2) + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
    } else {
      this.description = (DESCRIPTIONS[2] + (this.amount * 2) + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
    }
  }
  
  public void atStartOfTurn()
  {
    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
      {
        flashWithoutSound();
        AbstractDungeon.actionManager.addToBottom(new PoisonLoseHpAction(this.owner, this.source, this.amount * 2, AbstractGameAction.AttackEffect.POISON));
		if (this.owner.hasPower("Poison")){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, null, new PoisonPower(this.owner, this.source, 1), 1, AbstractGameAction.AttackEffect.NONE));
		}
      }
    }
  }
}
