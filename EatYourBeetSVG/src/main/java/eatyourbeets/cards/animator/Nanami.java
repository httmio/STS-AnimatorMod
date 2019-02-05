package eatyourbeets.cards.animator;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.red.Pummel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Nanami extends AnimatorCard
{
    public static final String ID = CreateFullID(Nanami.class.getSimpleName());

    public Nanami()
    {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,4);

        SetSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (HasActiveSynergy())
        {
            Pummel pummel = new Pummel();
            if (upgraded)
            {
                pummel.upgrade();
            }

            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE)
            {
                p.createHandIsFullDialog();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(pummel, 1));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(pummel));
            }
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeBlock(2);
        }
    }
}