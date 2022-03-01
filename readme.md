Travail rendu par :
	- Marta Boshkovska 22012535
	- Sara Sale 22009614
	- Luc Guyard 21507439
	- Antoine Laroche 22003439

Pour lancer le jeu : 
compilation : javac -d bin src/game/*.java src/ia/*.java
execution : java -cp bin game.Main x y z

x : indique la profondeur du raisonnement minimax du joueur 1.
y : indique la profondeur du raisonnement negamax du joueur 2.
z : est un booleen qui indique si les raisonnement des 2 joueur utilise l'élagage AlphaBeta (1 = oui ; 0 = non).

(conseil : ne pas lancer des profondeurs   supérieurs à 6).