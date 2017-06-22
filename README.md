# HITS_PageRank_Implementation

Implementation of HITS and PageRank algorithms on a dataset using the keyword 'California'.

# Premiere étape :

Lecture du fichier grace à la fonction readfile() et création du graph orienté dans la variable "dataset"

# Deuxième étape :

Implémentation de l'algorithme HITS qui donne l' hub score et l'authority score de chaque sommet

# Troisième étape :

Implémentation de l'algorithme page Rank qui retourne le score de chaque page 


# Quatrième étape : Déduction

On remarque avec l'algorithme page rank sur le fichier gr0.California2.txt que les pages 0 et 4 se démarquent avec un score plus important que les autres.
Cela s'explique notamment parce que la page 4 est référencée par la 3 qui a un très bon score
La page 0 est référencée elle meme par 4 ce qui explique aussi son bon score.


Matrice d'adjacence de l'exemple :

0 0 1 1 1 0 0 <br/>
0 0 0 0 0 0 0 <br/>
1 0 0 1 0 0 0 <br/>
0 0 0 1 0 0 0 <br/>
1 1 0 0 0 0 0 <br/>
0 0 0 0 0 0 0 <br/>
1 0 0 0 0 0 0 <br/>


# Source :

Pseudo code HITS : https://en.wikipedia.org/wiki/HITS_algorithm 
Page rank : http://pr.efactory.de/e-pagerank-algorithm.shtml
