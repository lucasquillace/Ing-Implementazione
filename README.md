# Progetto di Ingegneria del Software: Implementazione del Design Pattern Flyweight
## Utilizzo del Pattern Flyweigth
Per implementare il pattern ho creato una tela su cui è possibile progettare circuiti inserendo e collegando le porte logiche principali. 
Ogni porta logica al suo interno ha la propria implementazione tramite transistor.

Il pattern Flyweight permette di gestire in maniera ottimale la memoria grafica: quando viene aggiunta una nuova porta logica al disegno, il sistema separa lo stato condiviso (l'immagine vera e propria caricata in memoria) dallo stato non condiviso (la posizione all'interno della tela); permettendo di risparmiare in termini di spazio.
In questo modo, anche in uno scenario con moltissime porte logiche, la memoria grafica non soffrirà.

## Architettura Generale

- **LogicGate** : È la classe Flyweight. Dichiara l'operazione che il flyweight concreto deve implementare per esprimere il suo stato estrinseco.
- **LogicGateFactory** : È la FlyweightFactory. Si occupa di gestire la condivisione dei flyweight; se l'oggetto non è ancora stato caricato in memoria, lo crea.
- **ConcreteLogicGate** : È il Flyweight concreto. Implementa l'interfaccia di Logicate e memorizza lo stato interno

## Funzionalità
- L'inserimento delle porte è tramite Drag & Drop. (Il trascinamento memorizza solo una stringa, poi delega alla Factory la creazione della porta vera e propria sulla tela)
- È possibile navigare su una tela infinita. (Il pannello si ingrandisce quando necessario)
- È possibile inserire dei collegamenti tra le porte, simulando dei fili elettrici
- È integrato un sistema di zoom: si può ingrandire il piano (con CTRL +) e rimpicciolirlo (con CTRL -), oppure farlo ritornare allo zoom iniziale (CTRL 0) 