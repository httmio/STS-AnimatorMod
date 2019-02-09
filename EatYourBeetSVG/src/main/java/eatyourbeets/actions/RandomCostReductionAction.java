package eatyourbeets.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;
import java.util.UUID;

public class RandomCostReductionAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private final int costReduction;

    public RandomCostReductionAction(int costReduction)
    {
        this.costReduction = costReduction;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            boolean betterPossible = false;
            boolean possible = false;

            for (AbstractCard c : this.p.hand.group)
            {
                if (c.costForTurn > 0)
                {
                    betterPossible = true;
                }
                else if (c.cost > 0)
                {
                    possible = true;
                }
            }

            if (betterPossible || possible)
            {
                this.findAndModifyCard(betterPossible);
            }
        }

        this.tickDuration();
    }

    private void findAndModifyCard(boolean better)
    {
        AbstractCard c = this.p.hand.getRandomCard(false);
        if (better)
        {
            if (c.costForTurn > 0)
            {
                c.cost = Math.max(0, c.cost - costReduction);
                c.costForTurn = c.cost;
                c.isCostModified = true;
                c.superFlash(Color.GOLD.cpy());
            }
            else
            {
                this.findAndModifyCard(true);
            }
        }
        else if (c.cost > 0)
        {
            c.cost = Math.max(0, c.cost - costReduction);
            c.costForTurn = c.cost;
            c.isCostModified = true;
            c.superFlash(Color.GOLD.cpy());
        }
        else
        {
            this.findAndModifyCard(false);
        }

    }
}

