package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class LizardPriest extends AnimatorCard
{
    public static final String ID = CreateFullID(LizardPriest.class.getSimpleName());

    public LizardPriest()
    {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);

        Initialize(0, 0, 4);

        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        GameActionsHelper.Special(new RemoveAllBlockAction(m, p));
        GameActionsHelper.GainBlock(m, this.magicNumber);

        GameActionsHelper.CycleCardAction(1);
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(-3);
        }
    }
}