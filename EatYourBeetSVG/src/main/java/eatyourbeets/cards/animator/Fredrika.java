package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Fredrika extends AnimatorCard
{
    public static final String ID = CreateFullID(Fredrika.class.getSimpleName());

    public Fredrika()
    {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0, 4, 2);

        AddSynergies(Synergies.Chaika, Synergies.ANY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (AbstractMonster m1 : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!m1.isDead && !m1.isDying)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block, true));
            }
        }

        if (HasSynergy())
        {
            AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(p, this.magicNumber));
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }
}