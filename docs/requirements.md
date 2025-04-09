# Feladatkövetelmények

## Áttekintés
A projekt célja egy olyan logika kialakítása, amely az Atlassian Data Center környezetben futó Jira rendszerben kezeli a két issue típus (Theme és Epic) közötti hierarchikus kapcsolatot. Különösen az **Elements Connect** által kezelt egyéni mező és az issue linkek közötti szinkronizációra kell fókuszálni.

## Funkcionális követelmények
- **Hierarchikus kapcsolatok:**
  - **Theme** az _Epic_ szülője, míg az **Epic** a _Theme_ gyermekét jelöli.
  - Az **Epic** típusú issue tartalmaz egy **Elements Connect** által kezelt egyéni mezőt ("Theme Selection"), ahol **Theme** típusú issue-k közül lehet választani.
  
- **Bidirectional sync (kétirányú szinkronizáció):**
  - **Ha a mezőben kiválasztanak egy Theme-t:**
    - Automatikusan létrejön a megfelelő issue link az Epic és a Theme között.
  - **Ha manuálisan létrejön vagy törlődik az issue link:**
    - A **Theme Selection** egyéni mező automatikusan frissül (vagyis beállítódik, illetve törlődik).
    
- **Egyéb követelmények:**
  - A megoldásnak támogatnia kell a törlést is: ha egy Theme törlésre kerül a kapcsolatokból, annak tükröződnie kell az egyéni mezőben.
  - A Theme és az Epic nem feltétlenül kell, hogy azonos projektben legyenek.
  - A kapcsolat 1 Theme : N Epics arányban áll elő.
  - Minden felhasználói interakció (mező módosítása, link létrehozása/törlése) alatt összhangban kell tartani a custom field és az issue link aktuális állapotát.

## Technológiai követelmények
- **Nyelv:** Groovy (ScriptRunner vagy Jira plugin keretben történő futtatásra)
- **Komponensek:**
  - `link_sync.groovy`: Felelős a logika egyirányú (módosított mező → link, illetve módosított link → mező) szinkronizációjáért.
  - `event_listener.groovy`: Jira események figyelése, mely triggereli a link szinkronizációt a felhasználói műveletek alapján.
  - `utils.groovy`: Általános segédfüggvények, például issue lekérdezés és linkek kezelése.
  - `config.yaml`: Configurációs fájl, ahol a Jira instance URL-je, API token és egyéni mező neve van beállítva.
  
Ez a dokumentáció áttekintést nyújt arról, hogy milyen elvárásoknak kell megfelelnie a megoldásnak a feladatkiírásban.