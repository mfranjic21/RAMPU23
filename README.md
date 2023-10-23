# Iznajmljivanje vozila

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Luka Pamer | lpamer20@foi.hr | 0016149215 | lpamer20 | G1.2
Matej Franjić | mfranjic21@foi.hr | 0016154467 | mfranjic21 | G1.2
Marko Saško | msasko1@foi.hr | 0016142095 | msasko1 | G1.2

## Opis domene
Ovim projektom bi radili na izradi mobilne aplikacije za iznajmljivanje vozila koja bi pokrila funkcionalnosti od prijavljivavnja korisnika sve do ispisa samo računa i ispisa istog.

## Specifikacija projekta

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login | Za pristup aplikaciji potrebno se ulogirati nakon toga korisnik ima pregled svih vozila i mogućnost iznajmljivanja. | Marko Saško
F02 | Pretraga vozila | Omogućiti korisnicima pretraživanje dostupnih vozila prema lokaciji, datumu i vremenskom periodu. | Marko Saško
F03 | Obavijesti i potvrde | Slanje obavijesti o rezervacijama, potvrdama, podsjetnicima i promocijama putem push obavijesti ili e-pošte. | Marko Saško
F04 | Upravljanje profilom | Mogućnost pristupa vlastitom profilu te izmjena podataka kao što su ime i prezime, datum rođenja, OIB, email, broj telefona. | Luka Pamer
F05 | Integracija s kartama i navigacijom | Povezati aplikaciju s uslugama kartografije kako bi korisnici mogli lako pronaći lokacije vozila. | Luka Pamer
F06 | Postavke | Mogućnost promjene aplikacijske teme, jezika kako bi se unaprijedilo korisničko iskustvo. | Luka Pamer
F07 | Ocjenjivanje i recenzije | Dopustiti korisnicima da ocjenjuju i ostavljaju recenzije vozila i iskustava s iznajmljivanjem. | Matej Franjić
F08 | Rezervacije | Dopustiti korisnicima da rezerviraju vozilo za određeni datum i vrijeme te omogućite im praćenje svojih rezervacija. | Matej Franjić
F09 | Podrška | Rubrika sa često postavljenim pitanjima u vezi računa i ostalih nejasnoća. | Matej Franjić

## Tehnologije i oprema
Tehnologjie koje ćemo koristiti su Github i git za verzioniranje, GitWiki za pisanje jednostavne dokumentacije te praćenje kroz GitHub projects. Za samo programiranje softvera ćemo koristiti programski jezik Kotlin kroz sučelje Android Studia (Apache 2.0 licenca). Bazu podataka bi koristili kroz SqLite i MySQL Workbench. Rješenje će biti namijenjeno isključivo za određene verzije Androida. Poslužiteljska strana će biti odrađeno preko lokalno podignutog express.js.

## Baza podataka i web server
Koristili bi bazu podataka putem SqLite-a.
