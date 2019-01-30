package eatyourbeets.cards.animator;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.HiteiPower;

public class Hitei extends AnimatorCard
{
    public static final String ID = CreateFullID(Hitei.class.getSimpleName());

    public Hitei()
    {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,0,5);

        AddTooltip(new TooltipInfo("Gold Limit", "You cannot gain more than 100 Gold per combat."));
        AddSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HiteiPower(p, this.magicNumber), 0));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(2);
        }
    }
}