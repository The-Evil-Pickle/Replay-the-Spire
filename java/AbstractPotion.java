package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;
import com.megacrit.cardcrawl.vfx.PotionTrailEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractPotion
{
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AbstractPotion");
  public static final String[] TEXT = uiStrings.TEXT;
  public String ID;
  public String name;
  public String description;
  public int targetSlot = -1;
  public ArrayList<PowerTip> tips = new ArrayList();
  private Texture containerImg;
  private Texture liquidImg;
  private Texture hybridImg;
  private Texture spotsImg;
  private Texture outlineImg;
  public float currentX;
  public float currentY;
  public float targetX;
  public float targetY;
  private static final int RAW_W = 64;
  private static final float IMG_WIDTH = 64.0F * Settings.scale;
  private ArrayList<FlashPotionEffect> effect = new ArrayList();
  private float vX;
  private float vY;
  private float floorY;
  private float rotationSpeed;
  public float scale = Settings.scale;
  private static final float GRAVITY = -1600.0F * Settings.scale;
  public boolean isDone = false;
  public boolean isAnimating = false;
  public boolean isObtained = false;
  private ArrayList<Vector2> prevPositions = new ArrayList();
  private static final int FLASH_COUNT = 1;
  private static final float FLASH_INTERVAL = 0.33F;
  private int flashCount = 0;
  private float flashTimer = 0.0F;
  private float sparkleTimer = 1.0F;
  private static final float SPARKLE_OFFSET = 45.0F * Settings.scale;
  public final PotionColor color;
  public Color liquidColor;
  public Color hybridColor = null;
  public Color spotsColor = null;
  public PotionSize size;
  protected int potency = 0;
  public Hitbox hb;
  private static final float OBTAIN_SPEED = 6.0F;
  private static final float OBTAIN_THRESHOLD = 0.5F;
  private float angle = 0.0F;
  protected boolean canUse = false;
  public boolean discarded = false;
  public boolean isThrown = false;
  public boolean targetRequired = false;
  private static final Color PLACEHOLDER_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.75F);
  public PotionRarity rarity = PotionRarity.COMMON;
  
  public static enum PotionSize
  {
    T,  S,  M,  L,  H;
    
    private PotionSize() {}
  }
  
  public static enum PotionColor
  {
    POISON,  BLUE,  FIRE,  GREEN,  EXPLOSIVE,  WEAK,  STRENGTH,  CLEANSE,  ANCIENT,  ELIXIR,  NONE,  ENERGY,  SWIFT, SPOOKY, DOOM;
    
    private PotionColor() {}
  }
  
  public static enum PotionRarity
  {
    COMMON,  UNCOMMON,  RARE,  ULTRA, SPECIAL,  SHOP;
    
    private PotionRarity() {}
  }
  
  public AbstractPotion(String name, String id, PotionSize size, PotionColor color)
  {
    this.size = size;
    this.ID = id;
    this.name = name;
    switch (size)
    {
    case T: 
      this.containerImg = ImageMaster.POTION_T_CONTAINER;
      this.liquidImg = ImageMaster.POTION_T_LIQUID;
      this.hybridImg = ImageMaster.POTION_T_HYBRID;
      this.spotsImg = ImageMaster.POTION_T_SPOTS;
      this.outlineImg = ImageMaster.POTION_T_OUTLINE;
      break;
    case S: 
      this.containerImg = ImageMaster.POTION_S_CONTAINER;
      this.liquidImg = ImageMaster.POTION_S_LIQUID;
      this.hybridImg = ImageMaster.POTION_S_HYBRID;
      this.spotsImg = ImageMaster.POTION_S_SPOTS;
      this.outlineImg = ImageMaster.POTION_S_OUTLINE;
      break;
    case M: 
      this.containerImg = ImageMaster.POTION_M_CONTAINER;
      this.liquidImg = ImageMaster.POTION_M_LIQUID;
      this.hybridImg = ImageMaster.POTION_M_HYBRID;
      this.spotsImg = ImageMaster.POTION_M_SPOTS;
      this.outlineImg = ImageMaster.POTION_M_OUTLINE;
      break;
    case L: 
      this.containerImg = ImageMaster.POTION_L_CONTAINER;
      this.liquidImg = ImageMaster.POTION_L_LIQUID;
      this.hybridImg = ImageMaster.POTION_L_HYBRID;
      this.spotsImg = ImageMaster.POTION_L_SPOTS;
      this.outlineImg = ImageMaster.POTION_L_OUTLINE;
      break;
    case H: 
      this.containerImg = ImageMaster.POTION_H_CONTAINER;
      this.liquidImg = ImageMaster.POTION_H_LIQUID;
      this.hybridImg = ImageMaster.POTION_H_HYBRID;
      this.spotsImg = ImageMaster.POTION_H_SPOTS;
      this.outlineImg = ImageMaster.POTION_H_OUTLINE;
      break;
    default: 
      this.containerImg = null;
      this.liquidImg = null;
      this.hybridImg = null;
      this.spotsImg = null;
    }
    this.color = color;
    initializeColor();
    this.hb = new Hitbox(-1000.0F, -1000.0F, 80.0F * Settings.scale, 80.0F * Settings.scale);
  }
  
  public void flash()
  {
    this.flashCount = 1;
  }
  
  private void initializeColor()
  {
    switch (this.color)
    {
    case BLUE: 
      this.liquidColor = Color.SKY.cpy();
      break;
    case CLEANSE: 
      this.liquidColor = Color.WHITE.cpy();
      this.hybridColor = Color.LIGHT_GRAY.cpy();
      break;
    case ENERGY: 
      this.liquidColor = Color.YELLOW.cpy();
      this.spotsColor = Color.ORANGE.cpy();
      break;
    case EXPLOSIVE: 
      this.liquidColor = Color.ORANGE.cpy();
      break;
    case FIRE: 
      this.liquidColor = Color.RED.cpy();
      this.hybridColor = Color.ORANGE.cpy();
      break;
    case GREEN: 
      this.liquidColor = Color.CHARTREUSE.cpy();
      break;
    case POISON: 
      this.liquidColor = Color.OLIVE.cpy();
      this.spotsColor = Color.CHARTREUSE.cpy();
      break;
    case STRENGTH: 
      this.liquidColor = Color.DARK_GRAY.cpy();
      this.spotsColor = Color.CORAL.cpy();
      break;
    case SWIFT: 
      this.liquidColor = Color.valueOf("0d429dff");
      this.spotsColor = Color.CYAN.cpy();
      break;
    case WEAK: 
      this.liquidColor = Color.VIOLET.cpy();
      this.hybridColor = Color.MAROON.cpy();
      break;
    case ELIXIR: 
      this.liquidColor = Color.GOLD.cpy();
      this.spotsColor = Color.DARK_GRAY.cpy();
      break;
    case ANCIENT: 
      this.liquidColor = Color.GOLD.cpy();
      this.spotsColor = Color.CYAN.cpy();
      break;
	case SPOOKY:
	  this.liquidColor = Color.GOLD.cpy();
	  this.hybridColor = Color.CHARTREUSE.cpy();
	  break;
	case DOOM:
	  this.liquidColor = Color.valueOf("0d429dff");
	  this.hybridColor = Color.DARK_GRAY.cpy();
	  break;
    }
  }
  
  public void moveInstantly(float x, float y)
  {
    this.currentX = x;
    this.currentY = y;
    this.targetX = x;
    this.targetY = y;
  }
  
  public void move(float x, float y)
  {
    this.currentX = x;
    this.currentY = y;
  }
  
  public void setTarget(float x, float y)
  {
    this.targetX = x;
    this.targetY = y;
  }
  
  public void adjustPosition(int slot)
  {
    this.currentX = (TopPanel.POTION_X + slot * Settings.POTION_W);
    this.currentY = Settings.POTION_Y;
    this.targetX = this.currentX;
    this.targetY = this.currentY;
  }
  
  public int getPrice()
  {
	switch(this.rarity){
		case UNCOMMON:
		  return 60;
		case RARE:
		  return 75;
		case ULTRA:
		  return 90;
		case SHOP:
		  return 40;
		default:
		  return 50;
	}
  }
  
  public void upgradePotion()
  {
	  int lastPotency = this.potency;
	  this.potency *= 3;
	  this.potency /= 2;
	  if (this.potency == lastPotency)
	  {
		  this.potency += 1;
	  }
	  upgradeDescription();
  }
  
  public void upgradeDescription(){}
  
  public abstract void use(AbstractCreature paramAbstractCreature);
  
  public boolean canUse()
  {
    if ((AbstractDungeon.getCurrRoom().monsters == null) || 
      (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) || (AbstractDungeon.actionManager.turnHasEnded) || 
      (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)) {
      return false;
    }
    return true;
  }
  
  public void update()
  {
    if (!this.isDone)
    {
      sparkle();
      if (this.isAnimating)
      {
        AbstractDungeon.effectList.add(new PotionTrailEffect(this.currentX, this.currentY, this.angle));
        
        this.angle += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
        this.currentX += this.vX * Gdx.graphics.getDeltaTime();
        this.currentY += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += GRAVITY * Gdx.graphics.getDeltaTime();
        if ((this.currentY < this.floorY) && (this.vY < 0.0F))
        {
          this.prevPositions.clear();
          this.isAnimating = false;
          this.hb = new Hitbox(this.currentX - IMG_WIDTH * 0.66F, this.currentY - IMG_WIDTH * 0.66F, IMG_WIDTH * 1.5F, IMG_WIDTH * 1.5F);
          
          playPotionLandingSFX();
        }
      }
      if (this.isObtained)
      {
        if (this.angle != 0.0F) {
          this.angle = MathUtils.lerp(this.angle, 0.0F, Gdx.graphics.getDeltaTime() * 6.0F * 2.0F);
        }
        if (this.currentX != this.targetX)
        {
          this.currentX = MathUtils.lerp(this.currentX, this.targetX, Gdx.graphics.getDeltaTime() * 6.0F);
          if (Math.abs(this.currentX - this.targetX) < 0.5F) {
            this.currentX = this.targetX;
          }
        }
        if (this.currentY != this.targetY)
        {
          this.currentY = MathUtils.lerp(this.currentY, this.targetY, Gdx.graphics.getDeltaTime() * 6.0F);
          if (Math.abs(this.currentY - this.targetY) < 0.5F) {
            this.currentY = this.targetY;
          }
        }
        if ((this.currentY == this.targetY) && (this.currentX == this.targetX))
        {
          if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("POTION_DROP_1");
          } else {
            CardCrawlGame.sound.play("POTION_DROP_2");
          }
          this.isDone = true;
          flash();
          AbstractDungeon.topPanel.incrementPotion(this.targetSlot, this);
        }
      }
      if (this.hb != null)
      {
        this.hb.update();
        if (this.hb.hovered)
        {
          int slot = findAvailableSlot();
          this.targetSlot = slot;
          if ((InputHelper.justClickedLeft) && (!this.isObtained) && (!this.isAnimating) && (!AbstractDungeon.isScreenUp))
          {
            InputHelper.justClickedLeft = false;
            if (slot == -1)
            {
              AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
              
              return;
            }
            AbstractDungeon.topPanel.reservePotionSlot(this.targetSlot, this);
            this.isObtained = true;
            playPotionSound();
            
            this.targetX = (TopPanel.POTION_X + Settings.POTION_W * slot);
            this.targetY = Settings.POTION_Y;
            this.hb = AbstractDungeon.topPanel.potionHitboxes[slot];
          }
        }
      }
    }
    else
    {
      this.hb.update();
    }
  }
  
  private void updateFlash()
  {
    if (this.flashCount != 0)
    {
      this.flashTimer -= Gdx.graphics.getDeltaTime();
      if (this.flashTimer < 0.0F)
      {
        this.flashTimer = 0.33F;
        this.flashCount -= 1;
        this.effect.add(new FlashPotionEffect(this));
      }
    }
    for (Iterator<FlashPotionEffect> i = this.effect.iterator(); i.hasNext();)
    {
      FlashPotionEffect e = (FlashPotionEffect)i.next();
      e.update();
      if (e.isDone) {
        i.remove();
      }
    }
  }
  
  private void playPotionLandingSFX()
  {
    int tmp = MathUtils.random(1);
    if (tmp == 0) {
      CardCrawlGame.sound.play("POTION_DROP_1");
    } else {
      CardCrawlGame.sound.play("POTION_DROP_2");
    }
  }
  
  public static void playPotionSound()
  {
    int tmp = MathUtils.random(2);
    if (tmp == 0) {
      CardCrawlGame.sound.play("POTION_1");
    } else if (tmp == 1) {
      CardCrawlGame.sound.play("POTION_2");
    } else {
      CardCrawlGame.sound.play("POTION_3");
    }
  }
  
  private void sparkle()
  {
    this.sparkleTimer -= Gdx.graphics.getDeltaTime();
    if (this.sparkleTimer < 0.0F)
    {
      this.sparkleTimer = MathUtils.random(0.0F, 0.8F);
      AbstractDungeon.effectList.add(new ShineLinesEffect(this.currentX + 
      
        MathUtils.random(-SPARKLE_OFFSET, SPARKLE_OFFSET), this.currentY + 
        MathUtils.random(-SPARKLE_OFFSET, SPARKLE_OFFSET)));
    }
  }
  
  private int findAvailableSlot()
  {
    for (int i = 0; i < AbstractDungeon.player.potions.length; i++) {
      if (((AbstractDungeon.player.potions[i] instanceof PotionPlaceholder)) && (((PotionPlaceholder)AbstractDungeon.player.potions[i]).reservedPotion == this)) {
        return i;
      }
    }
    for (int i = 0; i < AbstractDungeon.player.potions.length; i++) {
      if (((AbstractDungeon.player.potions[i] instanceof PotionPlaceholder)) && (((PotionPlaceholder)AbstractDungeon.player.potions[i]).reservedPotion == null)) {
        return i;
      }
    }
    return -1;
  }
  
  public void renderLightOutline(SpriteBatch sb)
  {
    if (!(this instanceof PotionPlaceholder))
    {
      sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      sb.draw(this.outlineImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    }
  }
  
  public void renderOutline(SpriteBatch sb)
  {
    if (!(this instanceof PotionPlaceholder))
    {
      sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      sb.draw(this.outlineImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    }
  }
  
  public void renderShiny(SpriteBatch sb)
  {
    if (!(this instanceof PotionPlaceholder))
    {
      sb.setBlendFunction(770, 1);
      sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
      sb.draw(this.containerImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
      
      sb.setBlendFunction(770, 771);
    }
  }
  
  public void render(SpriteBatch sb)
  {
    updateFlash();
    if ((this instanceof PotionPlaceholder))
    {
      sb.setColor(PLACEHOLDER_COLOR);
      sb.draw(ImageMaster.POTION_PLACEHOLDER, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.angle, 0, 0, 64, 64, false, false);
    }
    else
    {
      sb.setColor(this.liquidColor);
      sb.draw(this.liquidImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
      if (this.hybridColor != null)
      {
        sb.setColor(this.hybridColor);
        sb.draw(this.hybridImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
      }
      if (this.spotsColor != null)
      {
        sb.setColor(this.spotsColor);
        sb.draw(this.spotsImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
      }
      sb.setColor(Color.WHITE);
      if ((this.hb != null) && (this.hb.hovered)) {
        sb.setColor(Color.CYAN);
      }
      sb.draw(this.containerImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    }
    for (FlashPotionEffect e : this.effect) {
      e.render(sb, this.currentX, this.currentY);
    }
    if (this.hb != null) {
      this.hb.render(sb);
    }
  }
  
  public void shopRender(SpriteBatch sb)
  {
    updateFlash();
    if (this.hb.hovered)
    {
      TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, this.name, this.description);
      this.scale = (1.5F * Settings.scale);
    }
    else
    {
      this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
    }
    renderOutline(sb);
    
    sb.setColor(this.liquidColor);
    sb.draw(this.liquidImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    if (this.hybridColor != null)
    {
      sb.setColor(this.hybridColor);
      sb.draw(this.hybridImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    }
    if (this.spotsColor != null)
    {
      sb.setColor(this.spotsColor);
      sb.draw(this.spotsImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    }
    sb.setColor(Color.WHITE);
    sb.draw(this.containerImg, this.currentX - 32.0F, this.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.angle, 0, 0, 64, 64, false, false);
    for (FlashPotionEffect e : this.effect) {
      e.render(sb, this.currentX, this.currentY);
    }
    if (this.hb != null) {
      this.hb.render(sb);
    }
  }
  
  public abstract AbstractPotion makeCopy();
}
