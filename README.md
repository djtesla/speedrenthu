Ez az alkalmazás a speedrent.hu gépkölcsönző adatainak - gépeinek, bérleti díjainak és rendeléseinek - tárolását és karbantartását valósítja meg.

A különböző gépek külöböző árakon bérelhetőek a kölcsönzési idő függvényében. Az adattárolást az entitásoknak megfelelően három tábla biztosítja: machines, price-categories
és orders.

A Machine és PriceCategory ill. a  PriceCategory és Order entitások között egyaránt OneToMany kapcsolat van. A kapcsolati attribútumoknak köszönhetően, a táblákat átívelően 
kérdezhetőek le az adatok (pl. rendelések gépek szerint).

Az alkalmazás erőforrásai http metódusokkal rest végpontokon keresztül érhetőek el.




