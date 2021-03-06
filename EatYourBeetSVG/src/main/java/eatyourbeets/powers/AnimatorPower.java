package eatyourbeets.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import eatyourbeets.cards.AnimatorCard;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AnimatorPower extends AbstractPower
{
    protected static final Logger logger = LogManager.getLogger(AnimatorCard.class.getName());

    protected PowerStrings powerStrings;

    public AnimatorPower(AbstractCreature owner, String id)
    {
        this.owner = owner;
        this.ID = "Animator_" + id;
        this.img = new Texture("images/powers/" + ID + ".png");

        powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);

        this.name = powerStrings.NAME;
    }

    @Override
    public  void updateDescription()
    {
        switch (powerStrings.DESCRIPTIONS.length)
        {
            case 0:
            {
                logger.error("powerStrings.Description was an empty array, " + this.name);
                break;
            }

            case 1:
            {
                this.description = powerStrings.DESCRIPTIONS[0];
                break;
            }

            case 2:
            {
                this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
                break;
            }

            default:
            {
                this.description = StringUtils.join(powerStrings.DESCRIPTIONS, " ");
            }
        }
        logger.info(powerStrings.DESCRIPTIONS.length + ": " + powerStrings.DESCRIPTIONS[0]);
        logger.info(this.description);
    }
}
