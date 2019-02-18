package eatyourbeets.cards.animator;

import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard_Cooldown;
import eatyourbeets.cards.Synergies;

public class Priestess extends AnimatorCard_Cooldown implements CustomSavable<Integer>
{
    public static final String ID = CreateFullID(Priestess.class.getSimpleName());
    private static final int COOLDOWN = 3;

    public Priestess()
    {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        Initialize(0, 0, 4);

        this.tags.add(CardTags.HEALING);
        this.baseSecondaryValue = this.secondaryValue = COOLDOWN;

        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (ProgressCooldown())
        {
            GameActionsHelper.ApplyPower(p, p, new RegenPower(p, this.magicNumber), this.magicNumber);
        }

        if (HasActiveSynergy())
        {
            GameActionsHelper.Special(new AddTemporaryHPAction(p, p, this.magicNumber));
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }

    @Override
    protected int GetBaseCooldown()
    {
        return COOLDOWN;
    }
}