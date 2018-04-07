package ReplayTheSpireMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import basemod.*;
import com.megacrit.cardcrawl.helpers.*;
import ReplayTheSpireMod.*;
import java.util.*;
import org.apache.logging.log4j.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.PotionHelper", method = "initialize")
public class ReplayPotionInitPatch
{
    private static final Logger logger;
    
    public static void Postfix() {
        PotionHelper.potions.add(potionID);
    }
    
    static {
        logger = LogManager.getLogger(PotionHelperInitialize.class.getName());
    }
}
