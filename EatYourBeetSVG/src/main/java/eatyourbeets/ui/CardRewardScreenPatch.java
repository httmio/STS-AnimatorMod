package eatyourbeets.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import java.util.ArrayList;

public class CardRewardScreenPatch
{
//    @SpirePostfixPatch
//    public static void Postfix(CardRewardScreen __instance, SpriteBatch sb)
//    {
//        for (AbstractCard c : __instance.rewardGroup)
//        {
//            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, c.current_x, c.current_y + 64 * Settings.scale, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
//            sb.setBlendFunction(770, 1);
//            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
//            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, c.current_x, c.current_y + 64 * Settings.scale, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
//            sb.setBlendFunction(770, 771);
//        }
//    }

    public static void Open(CardRewardScreen rewardScreen, ArrayList<AbstractCard> cards, RewardItem rItem, String header)
    {

    }

    public static void Update(CardRewardScreen rewardScreen)
    {

    }

    public static void Render(CardRewardScreen rewardScreen, SpriteBatch sb)
    {

    }
}