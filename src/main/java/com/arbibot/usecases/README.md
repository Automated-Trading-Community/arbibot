# Abitrage triangulaire méthode 

## Récupération des cours en temps réel

Récupérer toutes les x-secondes / milli secondes depuis un broker. 

## Calcul des Opportunités d'arbitrage
### Calcul du taux impliqué

Le `taux de chage impliqué` est le taux de change (valeur asset1 / valeur asset2) dérivé indirectement à partir des taux de change des autres devises (autre devises du triangle). Pour résumé, le `taux de change impliqué` représente le taux de change entre deux devises en passant par une troisième. 

**Taux de change impliqué**

$$TI_{AC} = \frac{V_{A}}{V_{B}}.\frac{V_{B}}{V_{C}}$$

- Avec A, B, C les devises
- `TI` le taux de change Impliqué
- $V_{i}$ la valeur / prix de la devise i

### Opportunité et conditions d'arbitrage

En prenant A, B et C trois devises dont les paires de trading `A/B`, `B/C` et `C/A` existent :  

- Si le taux impliqué `A/C` est supérieur au taux réel `A/C` alors il y a une opportunité d'achat de A en B et de vente en C.

-  Si le taux impliqué `A/C` est inférieur au taux réel `A/C` alors il y a une opportunité d'achat de A en C et de vente en B.

## déclenchement de l'arbitrage
### Définitions des frais

Dans la plupart des exchanges les frais sont exprimé dans la devise acheté (J'achète de la devise A je paye un pourcentage de cette devise). 

On définit $F_{A}$ les frais payé avec la devise A : 

$$F_{A} = QTT_{A}.\frac{P}{100}$$

Avec : 

- $QTT_{A}$ le montant de devise A engagé

On définit $F_{Ausd}$ les frais payé pour l'achat / vente de la devise A en usd : 

$$F_{Ausd} = F_{A}.TR_{Ausd}$$

Avec : 

- $TR_{Ausd}$ le taux de change réel de la paire A/usd


### Calcul des bénéfices 

On définit le bénéfice comme suit : 

$$B = QTT_{A}.(TI-TR)-F_{usd} $$

avec : 

- $QTT_{A}$ la quantité engagé
- $TI$ le taux de change impliqué
- $TR$ le taux réel
- $F_{Ausd}$ les frais (en usd ici)

Si B est supérieur à 1 alors une opportunité est présente. 

## Cas concret
Nous souhaitons arbitrer sur ETH/USDT avec BTC comme intermédiaire. 

Supposons les prix suivant : 

- Prix ETH/USDT=1980 (1 ETH = 1980 USDT)
- Prix ETH/BTC= 0.05 (1 ETH = 0.05 BTC)
- Prix BTC/USDT = 40000
- Prix BTC/USDT=40000 (1 BTC = 40000 USDT)
- Frais de transaction = 0.075% par transaction

### Calcul du Taux de change Impliqué ETH/USDT via BTC

$$TI_{ETHUSDT} = \frac{ETH}{BTC}.\frac{BTC}{USDT}$$
$$TI_{ETHUSDT} = 0.05 . 40000$$
$$TI_{ETHUSDT} = 2000$$

Nous avons une opportunité car $TI_{ETHUSDT} \gt TR_{ETHUSDT}$

**Achat d'ETH**

- Achat de 1 ETH pour 1980 USDT
- Frais -> 0,198 USDT
- Montant net en ETH -> 0.999 ETH

**Vente 0.999 ETH pour BTC**

- Vente de 0.04995 BTC pour 0.999 ETH
- Frais -> 0,1998 USDT (0.0000049995 BTC)
- Montant net en BTC -> 0.0499900005 BTC

**Vente de 0.0499900005 BTC en USDT**

- Vente de 0.0499900005 BTC pour 1999.60002 USDT
- Frais -> 0.19996 USDT
- Montant net en USDT = 1999.40006

**Calcul du bénéfice**

$$B = 1999,40006 -  1980 = 19,40006$$

Soit une augmentation de `0.98%`