<h1>speedrent.hu</h1>

Ez az alkalmazás a speedrent.hu **gépkölcsönző** adatainak - **gépeinek, bérleti díjainak és rendeléseinek** - tárolását és karbantartását valósítja meg.

A különböző gépek különböző árakon bérelhetőek a kölcsönzési idő függvényében. A cél egy olyan restful backend alkalmazás meegvalósítása volt, amin keresztül az adatbevitel könnyen, a lekérdezés pedig sokrétűen történhet, különös tekintettel a bevételekre.

Az alkalmazás három rétegű webes alalmazás, az SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val történt, ami MariaDb adatbázisra csatlakozik.

Az adattárolást az entitásoknak megfelelően három tábla biztosítja: machines, price-categories és orders.<br/><br/>

*Nyilvántartott adatok:*

**Machine**<br/>
Egy gépnek van neve és szegmense (`BUILDING, CLEANING, GARDENING`).

**PriceCategory**<br/>
Egy árkategóriának van időtartama (`THREE_HOURS, ONE_DAY, WEEKEND`) és összege.

**Order**<br/>
Egy bérlésnek/rendelésnek van dátuma, helyszine és státusza (`WIP, COMPLETED, CANCELED`).<br/><br/>

*Kapcsolatok:* 

A machine és a priceCategory között ill. a priceCategory és az order között egyaránt egy a többhöz kapcsolat van.<br/><br/>

Az alkalmazás erőforrásai http metódusokkal rest végpontokon keresztül érhetőek el. Minden entitást létre lehet hozni, listázni, törölni és az attribútumait módosítani.<br/><br/>

*Kiemelt metódusok:*

A bérlések összértékét szegmensenként az `/api/speedrenthu/orders/revenue_by_segment` végponton lehet lekérdezni.

A rendelések értékét gépenként az `/api/speedrenthu/orders/machine/{id}/revenue` végponton lehet lekérdezni.<br/><br/>

*Az alkalmazás további komponensei:*

- [x] Integrációs tesztek
- [x] Flyway scriptek
- [x] Hibakezelés, validáció
- [x] Swagger felület
- [x] HTTP fájlok a teszteléshez
- [x] Dockerfile.




