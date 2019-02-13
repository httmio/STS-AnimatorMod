package eatyourbeets.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import eatyourbeets.Utilities;

public class HiteiPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(HiteiPower.class.getSimpleName());

    private final AbstractPlayer player;
    private int exhaustCards;
    private int goldGain;
    private int goldCap = 100;

    public HiteiPower(AbstractPlayer owner, int goldGain)
    {
        super(owner, POWER_ID);

        this.player = Utilities.SafeCast(this.owner, AbstractPlayer.class);
        this.amount = 0;
        this.goldGain = goldGain;
        this.exhaustCards = 1;

        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        this.description = (powerStrings.DESCRIPTIONS[0] + goldGain + powerStrings.DESCRIPTIONS[1] + exhaustCards + powerStrings.DESCRIPTIONS[2]);
    }

    @Override
    public void atStartOfTurn()
    {
        super.atStartOfTurn();

        if (this.amount < goldCap)
        {
            this.amount += goldGain;
        }

        for (int i = 0; i < exhaustCards; i++)
        {
            AbstractCard card;
            CardGroup group;
            if (player.drawPile.size() > 0)
            {
                group = player.drawPile;
                card = group.getRandomCard(true);
            }
            else if (player.discardPile.size() > 0)
            {
                group = player.discardPile;
                card = group.getRandomCard(true);
            }
            else
            {
                return;
            }

            if (card != null)
            {
                group.moveToExhaustPile(card);
                card.exhaustOnUseOnce = false;
                card.freeToPlayOnce = false;
            }
        }
        this.flash();
    }

    @Override
    public void onVictory()
    {
        super.onVictory();

        AbstractDungeon.getCurrRoom().addGoldToRewards(this.amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        HiteiPower other = Utilities.SafeCast(power, HiteiPower.class);
        if (other != null && power.owner == target)
        {
            this.goldGain += other.goldGain;
            this.exhaustCards += 1;
            this.goldCap += 10;
        }

        super.onApplyPower(power, target, source);
    }
}
