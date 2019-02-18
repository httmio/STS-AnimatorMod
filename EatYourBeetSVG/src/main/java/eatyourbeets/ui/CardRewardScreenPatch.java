package eatyourbeets.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import eatyourbeets.effects.HideCardEffect;
import eatyourbeets.relics.PurgingStone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class CardRewardScreenPatch
{
    public static final Logger logger = LogManager.getLogger(CardRewardScreenPatch.class.getName());

    private static final ArrayList<BanCardButton> buttons = new ArrayList<>();
    private static PurgingStone purgingStone;
    private static RewardItem rewardItem;
    private static boolean canBan;

    public static void Open(CardRewardScreen rewardScreen, ArrayList<AbstractCard> cards, RewardItem rItem, String header)
    {
        buttons.clear();
        rewardItem = rItem;
        purgingStone = PurgingStone.GetInstance();

        if (purgingStone == null || !purgingStone.CanActivate(rItem))
        {
            return;
        }

        for (AbstractCard c : cards)
        {
            if (purgingStone.CanBan(c))
            {
                BanCardButton b = new BanCardButton(c);
                b.show();
                buttons.add(b);
                canBan = true;
            }
        }
    }

    public static void OnClose(CardRewardScreen instance)
    {
        buttons.clear();
        canBan = false;
    }

    public static void Update(CardRewardScreen rewardScreen)
    {
        if (!canBan)
        {
            return;
        }

        BanCardButton toRemove = null;
        for (BanCardButton b : buttons)
        {
            b.update();
            if (b.banned)
            {
                AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(b.card));
                AbstractDungeon.effectsQueue.add(new HideCardEffect(b.card));
                rewardItem.cards.remove(b.card);
                purgingStone.Ban(b.card);
                b.hideInstantly();
                toRemove = b;

                if (rewardItem.cards.size() == 0)
                {
                    AbstractDungeon.combatRewardScreen.rewards.remove(rewardItem);
                    AbstractDungeon.combatRewardScreen.positionRewards();
                    if (AbstractDungeon.combatRewardScreen.rewards.isEmpty())
                    {
                        AbstractDungeon.combatRewardScreen.hasTakenAll = true;
                        AbstractDungeon.overlayMenu.proceedButton.show();
                    }
                }
            }
        }

        while (toRemove != null)
        {
            buttons.remove(toRemove);
            toRemove = null;

            for (BanCardButton b : buttons)
            {
                if (!purgingStone.CanBan(b.card))
                {
                    toRemove = b;
                    break;
                }
            }
        }
    }

    public static void Render(CardRewardScreen rewardScreen, SpriteBatch sb)
    {
        if (!canBan)
        {
            return;
        }

        for (BanCardButton b : buttons)
        {
            b.render(sb);
        }
    }
}