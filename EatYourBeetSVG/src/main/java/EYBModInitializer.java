import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.animator.Gilgamesh;
import eatyourbeets.characters.AnimatorCharacter;
import eatyourbeets.powers.PlayerStatistics;
import eatyourbeets.relics.LivingPicture;
import eatyourbeets.relics.TheMissingPiece;
import eatyourbeets.rewards.SynergyCardsReward;
import eatyourbeets.variables.SecondaryValueVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.AbstractCardEnum;
import patches.AbstractClassEnum;
import patches.RewardTypeEnum;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class EYBModInitializer implements EditCharactersSubscriber, EditStringsSubscriber, EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber,
                                          OnStartBattleSubscriber, PreMonsterTurnSubscriber, PostInitializeSubscriber, PostEnergyRechargeSubscriber//, OnCardUseSubscriber
{
    private static final Logger logger = LogManager.getLogger(EYBModInitializer.class.getName());

    private static final String ANIMATOR_ATTACK = "images/cardui/512/bg_attack_canvas.png";
    private static final String ANIMATOR_SKILL = "images/cardui/512/bg_skill_canvas.png";
    private static final String ANIMATOR_POWER = "images/cardui/512/bg_power_canvas.png";
    private static final String ANIMATOR_ORB_A = "images/cardui/512/card_a_orb_canvas.png";
    private static final String ANIMATOR_ORB_C = "images/cardui/512/card_c_orb_canvas.png";

    private static final String ANIMATOR_ATTACK_P = "images/cardui/1024/bg_attack_canvas.png";
    private static final String ANIMATOR_SKILL_P = "images/cardui/1024/bg_skill_canvas.png";
    private static final String ANIMATOR_POWER_P = "images/cardui/1024/bg_power_canvas.png";
    private static final String ANIMATOR_ORB_B = "images/cardui/1024/card_b_orb_canvas.png";

    private static final String ANIMATOR_CHAR_BUTTON = "images/ui/charselect/animator_button.png";
    private static final String ANIMATOR_CHAR_PORTRAIT = "images/ui/charselect/animator_portrait.jpg";

    public static void initialize()
    {
        new EYBModInitializer();
    }

    private EYBModInitializer()
    {
        logger.info("EYBModInitializer()");

        BaseMod.subscribe(this);
        Color color = CardHelper.getColor(210, 147, 106);
        BaseMod.addColor(AbstractCardEnum.THE_ANIMATOR, color, color, color, color, color, color, color,
                ANIMATOR_ATTACK, ANIMATOR_SKILL, ANIMATOR_POWER, ANIMATOR_ORB_A, ANIMATOR_ATTACK_P, ANIMATOR_SKILL_P,
                ANIMATOR_POWER_P, ANIMATOR_ORB_B, ANIMATOR_ORB_C);
    }

    //@Override
    //public void receiveCardUsed(AbstractCard abstractCard)
    //{
    //    AnimatorCard.SetLastCardPlayed(abstractCard);
    //}

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom)
    {
        PlayerStatistics.EnsurePowerIsApplied();
        PlayerStatistics.Instance.OnBattleStart();
    }

    @Override
    public void receivePostEnergyRecharge()
    {
        PlayerStatistics.EnsurePowerIsApplied();
    }

    @Override // false = skips monster turn
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster)
    {
        PlayerStatistics.EnsurePowerIsApplied();

        return true;
    }

    @Override
    public void receiveEditCharacters()
    {
        AnimatorCharacter animatorCharacter = new AnimatorCharacter(AnimatorCharacter.NAME, AbstractClassEnum.THE_ANIMATOR);
        BaseMod.addCharacter(animatorCharacter, ANIMATOR_CHAR_BUTTON, ANIMATOR_CHAR_PORTRAIT, AbstractClassEnum.THE_ANIMATOR);
    }

    @Override
    public void receiveEditStrings()
    {
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "localization/eng/CharacterStrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/eng/CardStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/eng/RelicStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/eng/PowerStrings.json");
    }

    @Override
    public void receiveEditKeywords()
    {
        final String json = Gdx.files.internal("localization/eng/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));

        final com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = new Gson().fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        if (keywords != null)
        {
            for (final com.evacipated.cardcrawl.mod.stslib.Keyword keyword : keywords)
            {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelicToCustomPool(new LivingPicture(), AbstractCardEnum.THE_ANIMATOR);
        BaseMod.addRelicToCustomPool(new TheMissingPiece(), AbstractCardEnum.THE_ANIMATOR);
    }

    @Override
    public void receiveEditCards() 
    {
        BaseMod.addDynamicVariable(new SecondaryValueVariable());

        String jsonString = Gdx.files.internal("localization/eng/CardStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Map<String, CardStrings> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, CardStrings>>() {}.getType());
        for (String s : map.keySet())
        {
            logger.info("Adding: " + s);
            try
            {
                AddAndUnlock(Class.forName("eatyourbeets.cards.animator." + s.replace("animator_", "")));
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receivePostInitialize()
    {
        BaseMod.registerCustomReward(
                RewardTypeEnum.SYNERGY_CARDS,
                (rewardSave) -> new SynergyCardsReward(Synergies.All.get(rewardSave.amount)),
                (customReward) -> new RewardSave(customReward.type.toString(), null,
                        Synergies.All.indexOf(((SynergyCardsReward)customReward).synergy), 0));
    }

    private void AddAndUnlock(Class cardClass)
    {
        try
        {
            AddAndUnlock((AbstractCard) cardClass.newInstance(), (String)cardClass.getDeclaredField("ID").get(null));
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    private void AddAndUnlock(AbstractCard card, String ID)
    {
        if (UnlockTracker.isCardLocked(ID))
        {
            UnlockTracker.unlockCard(ID);
        }

        BaseMod.addCard(card);
    }
}