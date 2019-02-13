package eatyourbeets.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import eatyourbeets.AnimatorResources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AnimatorRelic extends CustomRelic
{
    protected static final Logger logger = LogManager.getLogger(AnimatorRelic.class.getName());

    public static String CreateFullID(String id)
    {
        return "Animator_" + id;
    }

    public AnimatorRelic(String id, RelicTier tier, LandingSound sfx)
    {
        super(id, new Texture(AnimatorResources.GetRelicImage(id)), tier, sfx);
    }
}
