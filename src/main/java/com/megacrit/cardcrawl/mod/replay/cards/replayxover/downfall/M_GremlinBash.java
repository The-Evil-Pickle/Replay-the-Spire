package com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.DecrepitPower;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;

import java.util.*;
import sneckomod.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import gremlin.characters.*;
import gremlin.orbs.MadGremlin;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import gremlin.actions.*;
import gremlin.cards.AbstractGremlinCard;
import gremlin.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.*;
import gremlin.*;
import com.megacrit.cardcrawl.core.*;

public class M_GremlinBash extends AbstractGremlinCard
{
    public static final String ID;
    private static final CardStrings strings;
    private static final String NAME;
    private static final String IMG_PATH = "cards/gremlin_dance.png";
    private static final AbstractCard.CardType TYPE;
    private static final AbstractCard.CardRarity RARITY;
    private static final AbstractCard.CardTarget TARGET;
    private String gremlin;
    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList;
    
    public M_GremlinBash() {
        super(M_GremlinBash.ID, M_GremlinBash.NAME, "cards/gremlin_dance.png", 2, M_GremlinBash.strings.DESCRIPTION, M_GremlinBash.TYPE, M_GremlinBash.RARITY, M_GremlinBash.TARGET);
        this.cardsList = new ArrayList<AbstractCard>();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.baseDamage = 8;
        this.baseBlock = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.gremlin = "";
        this.cardsList.add((AbstractCard)new M_GremlinBash("angry"));
        this.cardsList.add((AbstractCard)new M_GremlinBash("sneak"));
        this.cardsList.add((AbstractCard)new M_GremlinBash("fat"));
        this.cardsList.add((AbstractCard)new M_GremlinBash("wizard"));
        this.cardsList.add((AbstractCard)new M_GremlinBash("shield"));
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }
    
    public M_GremlinBash(final String gremlin) {
        super(M_GremlinBash.ID, M_GremlinBash.NAME, "cards/gremlin_dance.png", 2, M_GremlinBash.strings.DESCRIPTION, gremlin == "shield" ? CardType.SKILL : M_GremlinBash.TYPE, M_GremlinBash.RARITY, M_GremlinBash.TARGET);
        this.cardsList = new ArrayList<AbstractCard>();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.baseDamage = 8;
        this.baseBlock = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.gremlin = gremlin;
        this.updateContents();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        String gremlin = "";
        boolean isNob = false;
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if (((GremlinCharacter)AbstractDungeon.player).nob) {
                isNob = true;
            }
            else {
                gremlin = ((GremlinCharacter)AbstractDungeon.player).currentGremlin;
            }
        }
        
        if (gremlin.equals("shield")) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        else if (gremlin.equals("angry")) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        else if (gremlin.equals("wizard")) {
            this.wizardry = true;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        if (gremlin.equals("angry")) {
        	for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber));
            }
        }
        else if (isNob) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        } else if (gremlin.equals("fat")) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LanguidPower(m, this.magicNumber, false), this.magicNumber));
        	AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(new MadGremlin(0)));
        } else {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        	if (gremlin.equals("sneak")) {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DecrepitPower(m, this.magicNumber-1, false), this.magicNumber-1));
        	}
        	if (gremlin.equals("wizard")) {
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WizPower(p, this.magicNumber), this.magicNumber));
        	}
        }
    }
    
    public void update() {
        super.update();
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0f) {
                this.rotationTimer = 2.0f;
                if (this.cardsList.size() == 0) {
                    this.cardsToPreview = CardLibrary.cards.get("Madness");
                }
                else {
                    this.cardsToPreview = this.cardsList.get(this.previewIndex);
                }
                if (this.previewIndex == this.cardsList.size() - 1) {
                    this.previewIndex = 0;
                }
                else {
                    ++this.previewIndex;
                }
            }
            else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
    
    public AbstractCard makeCopy() {
        if (this.gremlin.equals("")) {
            return (AbstractCard)new M_GremlinBash();
        }
        return (AbstractCard)new M_GremlinBash(this.gremlin);
    }
    
    public void applyPowers() {
        this.updateContents();
        super.applyPowers();
    }
    
    private void updateContents() {
        this.tags.remove(GremlinMod.MAD_GREMLIN);
        this.tags.remove(GremlinMod.FAT_GREMLIN);
        this.tags.remove(GremlinMod.SHIELD_GREMLIN);
        this.tags.remove(GremlinMod.SNEAKY_GREMLIN);
        this.tags.remove(GremlinMod.WIZARD_GREMLIN);
        this.tags.remove(GremlinMod.NOB_GREMLIN);
        this.type = CardType.ATTACK;
        this.rawDescription = M_GremlinBash.strings.EXTENDED_DESCRIPTION[0];
        if (!this.gremlin.equals("") || AbstractDungeon.player instanceof GremlinCharacter) {
            if (this.gremlin.equals("") && ((GremlinCharacter)AbstractDungeon.player).nob) {
                this.rawDescription += M_GremlinBash.strings.EXTENDED_DESCRIPTION[6];
                this.isMultiDamage = false;
                this.target = AbstractCard.CardTarget.ENEMY;
                this.wizardry = false;
                this.tags.add(GremlinMod.NOB_GREMLIN);
                this.setBackgrounds();
            }
            else {
                String gremlin = this.gremlin;
                if (gremlin.equals("")) {
                    gremlin = ((GremlinCharacter)AbstractDungeon.player).currentGremlin;
                }
                final String s = gremlin;
                switch (s) {
                    case "angry": {
                        this.rawDescription += M_GremlinBash.strings.EXTENDED_DESCRIPTION[1] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[2];
                        this.isMultiDamage = true;
                        this.target = AbstractCard.CardTarget.ALL_ENEMY;
                        this.wizardry = false;
                        this.tags.add(GremlinMod.MAD_GREMLIN);
                        this.setBackgrounds();
                        break;
                    }
                    case "fat": {
                        this.rawDescription += M_GremlinBash.strings.EXTENDED_DESCRIPTION[5] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[3];
                        this.isMultiDamage = false;
                        this.target = AbstractCard.CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(GremlinMod.FAT_GREMLIN);
                        this.setBackgrounds();
                        break;
                    }
                    case "shield": {
                        this.rawDescription = M_GremlinBash.strings.EXTENDED_DESCRIPTION[4] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[1] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[3];
                        this.isMultiDamage = false;
                        this.target = AbstractCard.CardTarget.ENEMY;
                        this.wizardry = false;
                        this.type = CardType.SKILL;
                        this.tags.add(GremlinMod.SHIELD_GREMLIN);
                        this.setBackgrounds();
                        break;
                    }
                    case "sneak": {
                        this.rawDescription += M_GremlinBash.strings.EXTENDED_DESCRIPTION[1] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[7];
                        this.isMultiDamage = false;
                        this.target = AbstractCard.CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(GremlinMod.SNEAKY_GREMLIN);
                        this.setBackgrounds();
                        break;
                    }
                    case "wizard": {
                        this.rawDescription += M_GremlinBash.strings.EXTENDED_DESCRIPTION[1] + M_GremlinBash.strings.EXTENDED_DESCRIPTION[8];
                        this.isMultiDamage = false;
                        this.target = AbstractCard.CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(GremlinMod.WIZARD_GREMLIN);
                        this.setBackgrounds();
                        break;
                    }
                }
            }
        }
        this.initializeDescription();
    }
    
    @Override
    public void onGremlinSwapInDeck() {
        this.updateContents();
        super.applyPowers();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
            this.upgradeBlock(4);
        }
    }
    
    static {
        ID = "m_GremlinBash";
        strings = CardCrawlGame.languagePack.getCardStrings(M_GremlinBash.ID);
        NAME = M_GremlinBash.strings.NAME;
        TYPE = AbstractCard.CardType.ATTACK;
        RARITY = AbstractCard.CardRarity.BASIC;
        TARGET = AbstractCard.CardTarget.ENEMY;
    }
}
