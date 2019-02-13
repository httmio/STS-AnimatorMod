package eatyourbeets.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.Utilities;
import eatyourbeets.misc.SoraEffects.*;

import java.util.ArrayList;

public class SoraAction extends AnimatorAction
{
    //private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT;
    private static final ArrayList<SoraEffect> attackPool = new ArrayList<>();
    private static final ArrayList<SoraEffect> defendPool = new ArrayList<>();
    private static final ArrayList<SoraEffect> preparePool = new ArrayList<>();

    private final ArrayList<SoraEffect> currentEffects = new ArrayList<>();

    public SoraAction(AbstractCreature target)
    {
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            currentEffects.add(Utilities.GetRandomElement(attackPool, AbstractDungeon.miscRng));
            currentEffects.add(Utilities.GetRandomElement(defendPool, AbstractDungeon.miscRng));
            currentEffects.add(Utilities.GetRandomElement(preparePool, AbstractDungeon.miscRng));

            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (SoraEffect e : currentEffects)
            {
                e.sora.applyPowers();
                e.sora.initializeDescription();

                group.addToTop(e.sora);
            }

            AbstractDungeon.gridSelectScreen.open(group, 1, false, "");
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 0)
        {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.untip();
            card.unhover();

            for (SoraEffect e : currentEffects)
            {
                if (e.sora == card)
                {
                    e.EnqueueAction(AbstractDungeon.player);
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }

    static
    {
        //attackPool.add(new SoraEffect_GainThorns       (9 , 0));
        attackPool.add(new SoraEffect_DamageRandom     (4 , 0));
        attackPool.add(new SoraEffect_DamageAll        (5 , 0));
        attackPool.add(new SoraEffect_GainStrength     (7 , 0));
        attackPool.add(new SoraEffect_ApplyVulnerable  (12, 0));

        defendPool.add(new SoraEffect_GainBlock        (6 , 1));
        defendPool.add(new SoraEffect_GainDexterity    (8 , 1));
        defendPool.add(new SoraEffect_ApplyWeak        (11, 1));
        defendPool.add(new SoraEffect_GainTemporaryHP  (15, 1));

        preparePool.add(new SoraEffect_UpgradeCard     (3 , 2));
        preparePool.add(new SoraEffect_ReduceRandomCost(10, 2));
        preparePool.add(new SoraEffect_CycleCards      (13, 2));
        preparePool.add(new SoraEffect_DrawCards       (14, 2));
    }
}
