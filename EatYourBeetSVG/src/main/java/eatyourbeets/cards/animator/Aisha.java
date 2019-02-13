package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.AishaAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Aisha extends AnimatorCard
{
    public static final String ID = CreateFullID(Aisha.class.getSimpleName());

    public Aisha()
    {
        super(ID, 1, AbstractCard.CardType.SKILL, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

        Initialize(0,0, 1);

        SetSynergy(Synergies.Elsword);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int costReduction = HasActiveSynergy() ? 1 : 0;
        AbstractDungeon.actionManager.addToBottom(new AishaAction(costReduction));
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeBaseCost(0);
        }
    }
}
