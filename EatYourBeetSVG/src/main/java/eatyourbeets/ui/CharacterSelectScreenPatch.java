package eatyourbeets.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import eatyourbeets.AnimatorResources;
import eatyourbeets.characters.AnimatorCharacterSelect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.AbstractEnums;

public class CharacterSelectScreenPatch
{
    protected static final Logger logger = LogManager.getLogger(CharacterSelectScreenPatch.class.getName());

//    private static float drawStartX;
//    private static float drawStartY;
//    private static AbstractCard hoveredCard;
//    private static CardGroup visibleCards;
//    private static float padX;
//    private static float padY;

    public static final UIStrings UIStrings = AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.CharacterSelect);

    public static Hitbox startingCardsLabelHb;
    public static Hitbox startingCardsSelectedHb;
    public static Hitbox startingCardsLeftHb;
    public static Hitbox startingCardsRightHb;

    public static CharacterOption selectedOption;

    public static float POS_Y;
    public static float POS_X;

    public static void Initialize(CharacterSelectScreen selectScreen)
    {
        float leftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, UIStrings.TEXT[0], 9999.0F, 0.0F); // Ascension
        float rightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, UIStrings.TEXT[1], 9999.0F, 0.0F); // Level 22

        POS_X = 180f * Settings.scale;
        POS_Y = ((float) Settings.HEIGHT / 2.0F) + (20 * Settings.scale);

        startingCardsLabelHb = new Hitbox(leftTextWidth, 50.0F * Settings.scale);
        startingCardsSelectedHb = new Hitbox(rightTextWidth, 50f * Settings.scale);
        startingCardsLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        startingCardsRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

        startingCardsLabelHb.move(POS_X + (leftTextWidth / 2f), POS_Y);
        startingCardsLeftHb.move(startingCardsLabelHb.x + startingCardsLabelHb.width + (20 * Settings.scale), POS_Y - (10 * Settings.scale));
        startingCardsSelectedHb.move(startingCardsLeftHb.x + startingCardsLeftHb.width + (rightTextWidth / 2f), POS_Y);
        startingCardsRightHb.move(startingCardsSelectedHb.x + startingCardsSelectedHb.width + (10 * Settings.scale), POS_Y - (10 * Settings.scale));

//        float settingsPadX = Settings.CARD_VIEW_PAD_X * 0.2f;
//        float settingsPadY = Settings.CARD_VIEW_PAD_Y * 0.2f;
//        visibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//        drawStartX = (float) Settings.WIDTH;
//        drawStartX -= 6.0F * AbstractCard.IMG_WIDTH * 0.5F;
//        drawStartX -= 4.0F * settingsPadX;
//        drawStartY = (float) Settings.HEIGHT * 0.66F;
//        padX = AbstractCard.IMG_WIDTH * 0.5F + settingsPadX;
//        padY = AbstractCard.IMG_HEIGHT * 0.5F + settingsPadY;
        selectedOption = null;
    }

    public static void Update(CharacterSelectScreen selectScreen)
    {
        UpdateSelectedCharacter(selectScreen);
        if (selectedOption == null)
        {
            return;
        }

        startingCardsLabelHb.update();
        startingCardsRightHb.update();
        startingCardsLeftHb.update();

        if (InputHelper.justClickedLeft)
        {
            if (startingCardsLabelHb.hovered)
            {
                startingCardsLabelHb.clickStarted = true;
            }
            else if (startingCardsRightHb.hovered)
            {
                startingCardsRightHb.clickStarted = true;
            }
            else if (startingCardsLeftHb.hovered)
            {
                startingCardsLeftHb.clickStarted = true;
            }
        }

        if (startingCardsLeftHb.clicked)
        {
            startingCardsLeftHb.clicked = false;
            AnimatorCharacterSelect.PreviousDeck();

            UpdateDeck(selectScreen);
        }

        if (startingCardsRightHb.clicked)
        {
            startingCardsRightHb.clicked = false;
            AnimatorCharacterSelect.NextDeck();

            UpdateDeck(selectScreen);
        }

        //updateCards();
    }

    public static void Render(CharacterSelectScreen selectScreen, SpriteBatch sb)
    {
        if (selectedOption == null)
        {
            return;
        }

        AnimatorCharacterSelect.StartingDeck info = AnimatorCharacterSelect.GetDeckInfo();
        String description = info.GetDescription();
        selectScreen.confirmButton.isDisabled = info.Locked;
        if (description != null)
        {
            float originalScale = FontHelper.cardTitleFont_small_N.getData().scaleX;
            FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * 0.8f);
            FontHelper.renderFont(sb, FontHelper.cardTitleFont_small_N, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale), Settings.GREEN_TEXT_COLOR);
            FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * originalScale);
        }

        FontHelper.renderFont(sb, FontHelper.cardTitleFont_N, UIStrings.TEXT[0], startingCardsLabelHb.x, startingCardsLabelHb.cY, Settings.GOLD_COLOR);
        FontHelper.renderFont(sb, FontHelper.cardTitleFont_N, info.Name, startingCardsSelectedHb.x, startingCardsSelectedHb.cY, Settings.CREAM_COLOR);//.BLUE_TEXT_COLOR);

        if (!startingCardsLeftHb.hovered)
        {
            sb.setColor(Color.LIGHT_GRAY);
        }
        else
        {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0F, startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if (!startingCardsRightHb.hovered)
        {
            sb.setColor(Color.LIGHT_GRAY);
        }
        else
        {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0F, startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        startingCardsLabelHb.render(sb);
        startingCardsLeftHb.render(sb);
        startingCardsRightHb.render(sb);
        //renderCards(sb);
    }

    private static void UpdateSelectedCharacter(CharacterSelectScreen selectScreen)
    {
        CharacterOption current = selectedOption;
        selectedOption = null;
        for (CharacterOption o : selectScreen.options)
        {
            if (o.selected)
            {
                if (o.c.chosenClass == AbstractEnums.Characters.THE_ANIMATOR)
                {
                    if (current != o)
                    {
                        AnimatorCharacterSelect.Refresh();
                        UpdateDeck(selectScreen);
                    }

                    selectedOption = o;
                }

                return;
            }
        }
    }

    private static void UpdateDeck(CharacterSelectScreen selectScreen)
    {
        AnimatorCharacterSelect.StartingDeck deck = AnimatorCharacterSelect.GetDeckInfo();
        //visibleCards.group.clear();
        //deck.SetCards(visibleCards.group);
        selectScreen.bgCharImg = AnimatorResources.GetCharacterPortrait(deck.ID);
    }

//    private static void renderCards(SpriteBatch sb)
//    {
//        for (AbstractCard c : visibleCards.group)
//        {
//            if (c.isFlipped)
//            {
//                c.render(sb);
//            }
//            else
//            {
//                c.renderInLibrary(sb);
//                c.renderCardTip(sb);
//            }
//        }
//
//        if (hoveredCard != null)
//        {
//            if (hoveredCard.isFlipped)
//            {
//                hoveredCard.render(sb);
//            }
//            else
//            {
//                hoveredCard.renderHoverShadow(sb);
//                hoveredCard.renderInLibrary(sb);
//            }
//        }
//    }
//
//    private static void updateCards()
//    {
//        hoveredCard = null;
//        int lineNum = 0;
//        ArrayList<AbstractCard> cards = visibleCards.group;
//
//        for (int i = 0; i < cards.size(); ++i)
//        {
//            int mod = i % 5;
//            if (mod == 0 && i != 0)
//            {
//                ++lineNum;
//            }
//
//            AbstractCard card = cards.get(i);
//
//            card.target_x = drawStartX + (float) mod * padX;
//            card.target_y = drawStartY - (float) lineNum * padY;
//
//            card.update();
//
//            if (hoveredCard == null)
//            {
//                card.updateHoverLogic();
//                if (card.hb.hovered)
//                {
//                    hoveredCard = card;
//                }
//                else
//                {
//                    card.targetDrawScale = 0.5f;
//                }
//            }
//            else
//            {
//                card.unhover();
//                card.targetDrawScale = 0.5f;
//            }
//        }
//    }
}
