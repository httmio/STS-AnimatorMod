package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.EvePower;

public class Eve extends AnimatorCard
{
    public static final String ID = CreateFullID(Eve.class.getSimpleName());

    public Eve()
    {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);

        Initialize(0,0,1);

        AddSynergy(Synergies.Elsword);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        EvePower newPower = new EvePower(p, 1, this.magicNumber);
        if (p.hasPower(newPower.ID))
        {
            EvePower currentPower = (EvePower) p.getPower(newPower.ID);
            currentPower.amount += newPower.amount;
            currentPower.growth += newPower.growth;
            currentPower.updateDescription();
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, newPower));
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            updateCost(-1);
            //upgradeMagicNumber(1);
        }
    }
}