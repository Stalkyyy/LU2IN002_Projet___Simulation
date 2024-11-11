# LU2IN002-Projet-Java

Notre projet tente de simuler les attaques que peuvent subir les poules de la part des renards et des rats. 
En effet, c’est un problème très récurrent. De plus, les rats attaquent les oeufs des poules. 
Ces trois là sont donc les agents de notre projet. 

Il y a tout un système d’énergie, de reproduction, de probabilité… afin de faire une simulation intéressante d’après nous.

Les 3 ressources sont : 
   → L’herbe qui pousse sans l’aide des agents
   → Les oeufs qui seront pondus par les poules et écloeront à un moment.
   → La viande, elle sera déposé sur le terrain si une poule meurt d’elle-même.

______________________________________________________________________________________________________________________________________________

Sur le terrain, l'herbe sera représentée par "  __  ".
Elle gagnera une quantité par itération.
Les poules, quand elles seront sur une case d'herbe, gagneront autant de points d'énergie que de quantité d'herbe présente sous leurs pieds.
Après cela, l'herbe prendra <temps_repousse_herbe> itérations avant d'être de nouveau mangeable par la poule.

Si l'énergie d'un agent tombe à 0, il meurt. La particularité des poules est qu'elle laisse tomber de la viande (leur corps) sur le terrain, qui sera donc considéré comme une ressource pour les renards.
Si le renard tombe dessus, il gagnera <5 * quantitéViande> points d'énergie.

Ce n'est pas le seul moyen pour que le renard puisse se nourrir. En effet, si une poule est à proximité, il y a confrontation.
S'il arrive à le tuer, il gagnera ses points d'énergie.

La poule pondera des oeufs au hasard, suivant une probabilité donnée. Les rats, dès qu'ils seront dessus, attaqueront l'oeuf.
Si une poule est à proximité, il y a confrontation, il a une certaine chance de mourir. S'il survit, il gagne <QuantitéOeufs> points d'énergie.
S'il n'y a pas de poule, il mange les oeufs.

Les oeufs placés sur la carte ont une certaine chance d'éclore. Si cela arrive, une nouvelle poule est crée.
Les renards et les rats peuvent faire des enfants. Mais ces derniers n'auront que la moitié de l'énergie du parent !
_______________________________________________________________________________________________________________________________________________

Note : 15.25/20
