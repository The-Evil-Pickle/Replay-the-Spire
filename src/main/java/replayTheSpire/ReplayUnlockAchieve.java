package replayTheSpire;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.unlock.*;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;

public class ReplayUnlockAchieve {
	
	public String achievementId;
	public String name;
	public String desc;
	public String relicId;
	public boolean isUnlocked;
	
	
	public ReplayUnlockAchieve(String id, String name, String desc, String relicId) {
		this(id, name, desc, relicId, false);
	}
	
	public ReplayUnlockAchieve(String id, String name, String desc, String relicId, boolean isUnlocked) {
		this.achievementId = id;
		this.name = name;
		this.desc = desc;
		this.relicId = relicId;
		this.isUnlocked = isUnlocked;
	}
	
	
}