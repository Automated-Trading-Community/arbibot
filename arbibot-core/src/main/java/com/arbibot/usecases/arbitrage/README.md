# Abitrage triangulaire méthode 

## Taux de change impliqué
Le `taux de chage impliqué (TCI)` est le taux de change entre un asset de départ et un asset d'arrivée dérivé indirectement à partir des taux de change des autres assets utilisés lors de l'arbitrage. Pour résumé, le TCI représente le taux de change entre deux devises en passant par une troisième.

***SEB : je ne pense pas que la partie 'pour résumé' soit utile.***


La valeur du TCI se calcule alors via l'équation suivante :

$$TI_{A/C} = \frac{V_{A}}{V_{B}}.\frac{V_{B}}{V_{C}}$$

Avec :
- *A*, *B* et *C* respectivement l'asset de départ, l'asset intermédiaire et l'asset de fin de l'arbitrage.
- $TI_{A / C}$ le TCI entre les assets *A* et *C*.
- $V_{i}$ la valeur de l'asset *i*.

## Opportunité et conditions d'arbitrage

En prenant *A*, *B* et *C* trois assets dont les paires de trading `A/B`, `B/C` et `C/A` existent :  

- Si le TCI `A/C` est supérieur au taux réel `A/C` alors il y a une opportunité d'achat de *A* en *B* et de vente en *C* (i.e., triangle *A -> B -> C*).

-  Si le TCI `A/C` est inférieur au taux réel `A/C` alors il y a une opportunité d'achat de A en C et de vente en B (i.e., triangle *A -> C -> B*).


## Déclenchement de l'arbitrage
### Définitions des frais

Dans la plupart des exchanges les frais sont exprimés dans l'asset acheté (achat sur l'asset *A* donc frais exprimé en *A*). Soit $F_{A/USD}$ les frais payés pour l'achat / vente de l'asset *A* en USD tels que
$$F_{A/USD} = F_{A} \times TR_{A/USD}$$

Avec :
- $TR_{A/USD}$ le taux de change réel de la paire *A/USD*
- $F_{A}$ les frais payés avec l'asset *A* tels que

$$F_{A} = QTT_{A} \times \frac{P}{100}$$

Avec :
- $QTT_{A}$ le montant de l'asset *A* engagé.
- ***SEB : et P représente quoi ?***
 

### Calcul des bénéfices 

On définit le bénéfice comme suit : 

$$B = QTT_{A}.(TCI-TR)-F_{A/USD} $$

avec : 

- $QTT_{A}$ le montant de l'asset *A* engagé.
- $TCI$ le taux de change impliqué ***SEB : ici il manque l'indice A/B, qu'est qu'il faut mettre ? $TCI_{A/C}$ ?***
- $TR$ le taux réel ***SEB : pareil, ici je pense qu'il faille ajouter la paire pour lequelle on exprime les frais.***
- $F_{A/USD}$ les frais en usd ici

Si *B* est supérieur à 1 alors une opportunité est présente. 

## Cas concret
***SEB : ça serait bien de reprendre les notations $F_{A/USD}$, $QTT$ ... pour qu'on puisse se raccrocher aux éléments que tu présentes plus haut.***

Nous souhaitons arbitrer sur ETH/USDT avec BTC comme intermédiaire. 

Supposons les prix suivant : 

- Prix ETH/USDT = 1980 (1 ETH = 1980 USDT)
- Prix ETH/BTC = 0.05 (1 ETH = 0.05 BTC)
- Prix BTC/USDT = 40000 (1 BTC = 40000 USDT)
- Frais de transaction = 0.075% par transaction

**1) Calcul du TCI ETH/USDT via BTC**
$$TCI_{ETH/USDT} = \frac{ETH}{BTC}.\frac{BTC}{USDT} = 0.05 \times 40000 = 2000$$

Nous avons une opportunité car $TCI_{ETH/USDT} \gt TR_{ETH/USDT}$

**2) Achat d'ETH**
- Achat de 1 ETH pour 1980 USDT
- Frais = 0,198 USDT ***SEB : est-ce que tu peux détailler le calcul stp ? Je fais $\frac{0.075 \times 1980}{100} = 1.485$ donc je dois me tromper quelque part.***
- Montant net en ETH = 0.999 ETH

**3) Vente 0.999 ETH pour BTC**
- Vente de 0.04995 BTC pour 0.999 ETH
- Frais -> 0,1998 USDT (0.0000049995 BTC)
- Montant net en BTC -> 0.0499900005 BTC

**4) Vente de 0.0499900005 BTC en USDT**
- Vente de 0.0499900005 BTC pour 1999.60002 USDT
- Frais -> 0.19996 USDT
- Montant net en USDT = 1999.40006

**5) Calcul du bénéfice**
$$B = 1999,40006 -  1980 = 19,40006$$
Soit une augmentation de `0.98%`

## Récupération des cours en temps réel

Récupérer toutes les x-secondes / milli secondes depuis un broker.

***SEB: comme la connexion au flux de données est indépendante des la logique métier, je pense qu'il est mieux de traiter ce point après ce qu'il y a ci-dessous.***