package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.ExhaustFromDiscardPileAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Mikaela extends AnimatorCard
{
    public static final String ID = CreateFullID(Mikaela.class.getSimpleName());

    public Mikaela()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(8,0);

        AddSynergy(Synergies.OwariNoSeraph);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        if (p.discardPile.size() > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ExhaustFromDiscardPileAction(1));
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            upgradeDamage(2);
        }
    }
}