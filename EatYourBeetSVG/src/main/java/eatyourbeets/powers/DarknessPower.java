package eatyourbeets.powers;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import eatyourbeets.Utilities;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;

public class DarknessPower extends AnimatorPower
{
    private AbstractPlayer player;

    public DarknessPower(AbstractPlayer owner, int cards)
    {
        super(owner, "Darkness");
        this.player = Utilities.SafeCast(this.owner, AbstractPlayer.class);
        this.amount = cards;

        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        this.description = (powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1]);
    }

    @Override
    public int onLoseHp(int damageAmount)
    {
        ArrayList<AbstractCard> toUpgrade = new ArrayList<>();

        MutableInt count = new MutableInt(this.amount);
        GetUpgradableCards(toUpgrade, player.hand, count);
        GetUpgradableCards(toUpgrade, player.drawPile, count);
        GetUpgradableCards(toUpgrade, player.discardPile, count);

        for (AbstractCard c : toUpgrade)
        {
            c.upgrade();
            c.superFlash();
        }

        return super.onLoseHp(damageAmount);
    }

    private void GetUpgradableCards(ArrayList<AbstractCard> toUpgrade, CardGroup group, MutableInt count)
    {
        if (count.intValue() <= 0)
        {
            return;
        }

        int result = 0;
        if (group.size() > 0)
        {
            ArrayList<AbstractCard> upgradable = group.getUpgradableCards().group;
            while (upgradable.size() > 0 && count.intValue() > 0)
            {
                int index = MathUtils.random(upgradable.size() - 1);

                toUpgrade.add(upgradable.get(index));
                upgradable.remove(index);

                count.decrement();
                result += 1;
            }
        }
    }
}
