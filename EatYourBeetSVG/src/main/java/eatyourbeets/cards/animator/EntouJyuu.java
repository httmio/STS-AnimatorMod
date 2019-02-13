package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.EntouJyuuPower;

public class EntouJyuu extends AnimatorCard
{
    public static final String ID = CreateFullID(EntouJyuu.class.getSimpleName());

    public EntouJyuu()
    {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);

        Initialize(0, 0, 2);

        SetSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EntouJyuuPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }
}