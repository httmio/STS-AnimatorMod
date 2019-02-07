package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.ModifyMagicNumberAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Togame extends AnimatorCard
{
    public static final String ID = CreateFullID(Togame.class.getSimpleName());

    public Togame()
    {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,0, 3);

        this.baseSecondaryValue = this.secondaryValue = 2;

        SetSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.baseMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, false, true, true));
        AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this.uuid, -1));

        if (this.magicNumber <= 1)
        {
            this.purgeOnUse = true;
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeSecondaryValue(1);
        }
    }
}