import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import eatyourbeets.AnimatorResources;
import eatyourbeets.characters.AnimatorCharacter;
import eatyourbeets.powers.PlayerStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.AbstractEnums;

@SpireInitializer
public class EYBModInitializer
        implements EditCharactersSubscriber, EditStringsSubscriber, EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber,
                   OnStartBattleSubscriber, PreMonsterTurnSubscriber, PostInitializeSubscriber, PostEnergyRechargeSubscriber
{
    private static final Logger logger = LogManager.getLogger(EYBModInitializer.class.getName());

    public static void initialize()
    {
        // Entry Point
        new EYBModInitializer();
    }

    private EYBModInitializer()
    {
        logger.info("EYBModInitializer()");

        BaseMod.subscribe(this);
        Color color = CardHelper.getColor(210, 147, 106);
        BaseMod.addColor(AbstractEnums.Cards.THE_ANIMATOR, color, color, color, color, color, color, color,
                AnimatorResources.ATTACK_PNG, AnimatorResources.SKILL_PNG , AnimatorResources.POWER_PNG ,
                AnimatorResources.ORB_A_PNG , AnimatorResources.ATTACK_P_PNG , AnimatorResources.SKILL_P_PNG ,
                AnimatorResources.POWER_P_PNG, AnimatorResources.ORB_B_PNG , AnimatorResources.ORB_C_PNG);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom)
    {
        PlayerStatistics.EnsurePowerIsApplied();
        PlayerStatistics.Instance.OnBattleStart();
    }

    @Override
    public void receivePostEnergyRecharge()
    {
        // Ensure PlayerStatistics is always active at turn start
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
        AnimatorCharacter animatorCharacter = new AnimatorCharacter(AnimatorCharacter.NAME, AbstractEnums.Characters.THE_ANIMATOR);
        BaseMod.addCharacter(animatorCharacter, AnimatorResources.CHAR_BUTTON_PNG, AnimatorResources.CHAR_PORTRAIT_JPG, AbstractEnums.Characters.THE_ANIMATOR);
    }

    @Override
    public void receiveEditStrings()
    {
        AnimatorResources.LoadGameStrings();
    }

    @Override
    public void receiveEditKeywords()
    {
        AnimatorResources.LoadCustomKeywords();
    }

    @Override
    public void receiveEditRelics()
    {
        AnimatorResources.LoadCustomRelics();
    }

    @Override
    public void receiveEditCards()
    {
        try
        {
            AnimatorResources.LoadCustomCards();
        }
        catch (Exception e)
        {
            logger.error(e);
        }

        if (Loader.DEBUG)
        {
            if (AnimatorResources.CreateDebugFile())
            {
                logger.info("Created Debug File at c:\\temp");
            }
        }
    }

    @Override
    public void receivePostInitialize()
    {
        AnimatorResources.LoadCustomRewards();
    }
}