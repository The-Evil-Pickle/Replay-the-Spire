package replayTheSpire.minions.grembo;

import kobting.friendlyminions.monsters.*;
import basemod.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import java.util.*;

import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class GremboTsundere extends AbstractFriendlyMonster
{
	private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID = "GremboTsundere";
    private AbstractMonster target;
    private int baseDamage;
    private int baseBlock;
    
    public GremboTsundere(float rot) {
        super(GremboTsundere.NAME, GremboTsundere.ID, 15, -8.0f, 10.0f, 230.0f, 240.0f, "images/monsters/exord/cook.png", -900.0f + 200.0f * (float)Math.cos(rot), -200.0f * (float)Math.sin(rot));
        this.img = null;
        this.loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 4;
        this.baseBlock = 6;
        addMoves();
    }
    
    private void addMoves(){
        this.moves.addMove(new MinionMove(MOVES[1], this, new Texture("images/summons/bubbles/atk_bubble.png"), "Deal " + this.baseDamage + " damage.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this,this.baseDamage,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }));
        this.moves.addMove(new MinionMove(MOVES[0], this, new Texture("images/summons/bubbles/block_bubble.png"),"Gives you " + this.baseBlock + " Block.", () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, this, this.baseBlock));
        }));
        this.moves.addMove(new MinionMove("Defend", this, new Texture("images/summons/bubbles/block_bubble.png"),"Gains " + this.baseBlock + " Block.", () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.baseBlock));
        }));
    }
    
    static {
    	monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinTsundere");
    	NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
