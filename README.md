IMF est une API Java avec:

Spring: security, web, data rest, data jpa
Lombok, modelMapper, rest-assured...
Swagger
Dockerfile et rancher-pipeline (build)
Un peu de tests d'integ et de composants

Il n'y a pas de couche service, notez qu'il faudrait remédier à cela pour que cela soit digne de IMF!

Il y a les routes suivantes:
-auth-controller : se connecter cédric/password (admin) et slim/password (spy) (il n'y a que 2 rôles)
-spy-controller : l'admin peut créer/supprimer des spys. (cédric est donc seul et s'il se supprime de la base, c'est terminé!)
-mission-controller : l'admin peut poster une mission pour un spy, le spy consulte la liste de ses missions.

Un déploiement est dispo!
https://saagie.blueforest.org/swagger-ui.html

Note sur swagger pour se connecter:
-Appeler /auth/signin (auth-controller) et copier la réponse
-Cliquer sur 'authorize' tout en haut
-Saisir la chaine "Bearer " + token de réponse de auth-controller

:)
