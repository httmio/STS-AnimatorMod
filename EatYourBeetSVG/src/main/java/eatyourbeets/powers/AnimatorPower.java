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
        this.img = new Texture("images/powers/" + id + ".png");

        powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);

        this.name = powerStrings.NAME;
    }

    @Override
    public  void updateDescription()
    {
        this.description = StringUtils.join(powerStrings.DESCRIPTIONS, " ");
    }
}
