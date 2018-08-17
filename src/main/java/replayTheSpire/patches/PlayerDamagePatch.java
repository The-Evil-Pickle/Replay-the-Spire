package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.GhostHeart;


@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "damage")
public class PlayerDamagePatch {
	//68
	@SpireInsertPatch(rloc = 68)
	public static SpireReturn Insert(AbstractPlayer player, final DamageInfo info) {
		
		if (player.hasRelic(GhostHeart.ID) && ((GhostHeart)player.getRelic(GhostHeart.ID)).counter == -1) {
			player.currentHealth = 0;
			player.getRelic(GhostHeart.ID).onTrigger();
            return SpireReturn.Return(null);
        }
		
		return SpireReturn.Continue();
	}
}
