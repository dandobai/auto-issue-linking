# Telepítési és Konfigurációs Útmutató

## Előfeltételek
- **Jira Data Center** telepítve, amely támogatja a Data Center környezetet.
- **ScriptRunner** vagy egy egyéni Jira plugin keret, amely lehetővé teszi a Groovy script futtatását.
- Hozzáférés a Jira rendszergazdai beállításaihoz az eseményfigyelő és a post function konfigurálásához.

## Telepítési lépések

1. **Repository klónozása:**
   - Klónozd a repositoryt, amely tartalmazza a teljes kódbázist:
     ```bash
     git clone https://github.com/a_te_repo_url.git
     ```

2. **Fájlstruktúra bemutatása:**
   - A repository a következő struktúrával rendelkezik:
     ```
     repo-root/
       ├── docs/
       │    ├── requirements.md
       │    ├── setup.md
       │    └── usage.md
       ├── src/
       │    ├── link_sync.groovy
       │    ├── event_listener.groovy
       │    ├── config.yaml
       │    └── utils.groovy
       ├── README.md
       ├── requirements.txt
       └── setup.sh
     ```

3. **ScriptRunner konfigurálása:**
   - Jelentkezz be Jira rendszergazdaként.
   - Telepítsd a ScriptRunner plugint (amennyiben még nincs telepítve).
   - Menj a **Script Listeners** (Eseményfigyelők) részhez.
   - Hozz létre egy új **Listener**-t, és add meg az `event_listener.groovy` fájl tartalmát.
   - Győződj meg róla, hogy a megfelelő esemény (pl. "Issue Updated" vagy "Issue Linked") figyelés alatt van.

4. **Link szinkronizáció beállítása:**
   - Ha szükséges, a `link_sync.groovy` scriptet is konfiguráld úgy, hogy annak paramétereit (például az issue azonosítókat) dinamikusan olvassa a konfigurációs fájlból.
   - Ellenőrizd, hogy a `config.yaml` fájlban megfelelően van-e megadva az egyéni mező neve és a Jira instance URL-je.

5. **Tesztelés:**
   - Hozz létre vagy módosíts egy Epic issue-t, és válassz ki egy Theme-t a "Theme Selection" mezőből.
   - Ellenőrizd, hogy a script létrehozza-e az issue linket az Epic és a Theme között.
   - Próbáld ki a manuális link létrehozását vagy törlését, és figyeld, hogy a custom field állapota megfelelően frissül-e.

6. **Telepítési script (opcionális):**
   - A `setup.sh` script segíthet bizonyos telepítési lépések automatizálásában (például szükséges könyvtárak létrehozásában).  
   - Futtatás:
     ```bash
     sh setup.sh
     ```

Ez az útmutató lépésről-lépésre bevezeti a megoldás telepítésébe és konfigurálásába, hogy a rendszeredre feltelepített Jira instance megfelelően szinkronizálja a Theme-Epic kapcsolatokat.
