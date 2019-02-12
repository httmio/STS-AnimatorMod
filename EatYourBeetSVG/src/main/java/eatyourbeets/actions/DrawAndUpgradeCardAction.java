package eatyourbeets.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.Utilities;

import java.util.ArrayList;

public class DrawAndUpgradeCardAction extends AnimatorAction
{
    private final int count;

    public DrawAndUpgradeCardAction(AbstractCreature target, int count)
    {
        this.target = target;
        this.count = count;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        DrawUnupgradedCard(this.count);
        this.isDone = true;
    }

    private void DrawUnupgradedCard(int count)
    {
        CardGroup drawPile = AbstractDungeon.player.drawPile;
        for (AbstractCard c : drawPile.getUpgradableCards().group)
        {
            if (c.canUpgrade())
            {
                count -= 1;
                c.upgrade();
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(c.target_x, c.target_y));
                AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardAction(c));

                if (count == 0)
                {
                    return;
                }
            }
        }

        if (count > 0)
        {
            GameActionsHelper.DrawCard(AbstractDungeon.player, count);
        }
    }
}