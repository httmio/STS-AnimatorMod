package eatyourbeets.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AnimatorRelic extends CustomRelic
{
    protected static final Logger logger = LogManager.getLogger(AnimatorRelic.class.getName());

    public AnimatorRelic(String id, Texture texture, RelicTier tier, LandingSound sfx)
    {
        super(id, texture, tier, sfx);
    }

    public AnimatorRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx)
    {
        super(id, texture, outline, tier, sfx);
    }

    public AnimatorRelic(String id, String imgName, RelicTier tier, LandingSound sfx)
    {
        super(id, imgName, tier, sfx);
    }
}
