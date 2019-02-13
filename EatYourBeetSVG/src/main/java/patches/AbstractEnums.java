package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class AbstractEnums
{
    public static class Characters
    {
        @SpireEnum
        public static PlayerClass THE_ANIMATOR;
    }

    public static class Cards
    {
        @SpireEnum
        public static AbstractCard.CardColor THE_ANIMATOR;
    }

    public static class Library
    {
        @SpireEnum
        public static CardLibrary.LibraryType THE_ANIMATOR;
    }

    public static class Rewards
    {
        @SpireEnum
        public static RewardItem.RewardType SYNERGY_CARDS;
    }
}
