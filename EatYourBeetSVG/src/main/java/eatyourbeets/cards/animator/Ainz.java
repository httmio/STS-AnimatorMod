package eatyourbeets.cards.animator;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.AinzPower;

public class Ainz extends AnimatorCard
{
    public static final String ID = CreateFullID(Ainz.class.getSimpleName());

    public Ainz()
    {
        super(ID, 8, CardType.POWER, CardRarity.RARE, CardTarget.SELF);

        Initialize(0,0,0);

        AddSynergy(Synergies.Overlord);
        AddTooltip(new TooltipInfo("Powers Form", "Either Demon Form, Wraith Form or Echo Form"));
    }

    @Override
    public void triggerOnEndOfPlayerTurn()
    {
        super.triggerOnEndOfPlayerTurn();

        if (this.cost > 0)
        {
            this.modifyCostForCombat(-1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AinzPower(p, this.upgraded), 1));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeBaseCost(7);
        }
    }
}