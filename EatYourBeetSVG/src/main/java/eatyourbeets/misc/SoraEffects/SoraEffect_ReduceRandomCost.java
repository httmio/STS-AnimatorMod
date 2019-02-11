package eatyourbeets.misc.SoraEffects;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.actions.RandomCostReductionAction;

public class SoraEffect_ReduceRandomCost extends SoraEffect
{
    public SoraEffect_ReduceRandomCost(int descriptionIndex, int nameIndex)
    {
        super(descriptionIndex,nameIndex);
        sora.baseMagicNumber = sora.magicNumber = 1;
    }

    @Override
    public void EnqueueAction(AbstractPlayer player)
    {
        GameActionsHelper.Special(new RandomCostReductionAction(sora.magicNumber, false));
        GameActionsHelper.Special(new RandomCostReductionAction(sora.magicNumber, false));
    }
}