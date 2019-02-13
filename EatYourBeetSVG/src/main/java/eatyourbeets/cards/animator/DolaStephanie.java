package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.StephanieAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class DolaStephanie extends AnimatorCard
{
    public static final String ID = CreateFullID(DolaStephanie.class.getSimpleName());

    public DolaStephanie()
    {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,0);

        SetSynergy(Synergies.NoGameNoLife);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new StephanieAction(p, 1));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            this.isInnate = true;
        }
    }
}