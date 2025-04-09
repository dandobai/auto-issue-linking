# Használati Útmutató

## Alapvető működés
A megoldás két fő Groovy scriptből áll, amelyek együttműködnek az issue linkek és az egyéni "Theme Selection" mező szinkronizálásában:

1. **`event_listener.groovy`:**
   - Figyeli a Jira issue módosítását (pl. Issue Updated esemény).
   - Amikor egy Epic issue módosul, ellenőrzi a "Theme Selection" mező értékét.
   - Ha a mező értéke tartalmaz egy Theme issue azonosítót, meghívja a link szinkronizációt végző scriptet.

2. **`link_sync.groovy`:**
   - Ellenőrzi, hogy létezik-e már a link az Epic és az általa kiválasztott Theme között.
   - Ha nem létezik még, létrehozza az issue linket.
   - Emellett, ha a link megsemmisül (például manuális törlés esetén), nullára állítja a "Theme Selection" mezőt az Epicben.
   
3. **`utils.groovy`:**
   - Tartalmaz általános segédfüggvényeket az issue lekérdezéshez, linkek létrehozásához és törléséhez.
   - Ezek a függvények újrafelhasználhatók a szervezet többi részében is.

## Interakciók Felhasználói Műveletekkel
- **Theme kiválasztása az Epic-ben:**
  - A felhasználó kiválaszt egy Theme-t az Elements Connect által biztosított egyéni mezőben.
  - Az `event_listener.groovy` script ezt észleli, majd a `link_sync.groovy` végrehajtja a szükséges link létrehozását.
  
- **Manuális link módosítása/törlése:**
  - Ha a felhasználó közvetlenül módosítja az issue linkeket (például egy meglévő kapcsolat törlésre kerül),
  - A rendszer automatikusan frissíti a "Theme Selection" mezőt az Epic-ben, így mindig az aktuális állapotot tükrözi.
  
## Ellenőrzés és Hibakeresés
- **Log üzenetek:**
  - Mindkét script logol, így a ScriptRunner (vagy a plugin által biztosított naplózási mechanizmus) segítségével ellenőrizheted, hogy mely műveletek kerültek végrehajtásra.
  - Például: "Kapcsolat létrehozva: EPIC-123 -> THEME-456" vagy "Theme mező törölve az Epicben: EPIC-123".
  
- **Tesztelési forgatókönyvek:**
  - Készíts egy teszt Epic issue-t, és állítsd be a "Theme Selection" mezőt.
  - Próbáld meg módosítani a linkeket manuálisan, majd ellenőrizd, hogy a mező helyesen frissül.
  - Ellenőrizd a különböző projektekben történő kapcsolódást is, mivel a Theme és az Epic nem kell, hogy azonos projektben legyenek.

Ez az útmutató segít eligazodni abban, hogy miként használhatod a generált megoldást a mindennapi működés során, és hogyan biztosítható a kétirányú szinkronizáció a felhasználói interakciók során.