De classes Edge, Interpreter, LinKernighan, NegativeGainException, Point & Token zijn afkomstig van de implementatie van het Lin Kernighan algoritme (https://github.com/RodolfoPichardo/LinKernighanTSP). Deze hebben we niet zelf geschreven.

De map 'data' is voor TSP files, deze worden gebruikt voor de routebepaling. De TSP files worden aangemaakt in de class 'CreateFile'.

In de class 'PostcodeAPI' bevinden zich 2 methodes: één om de longitude en latitude van een adres op te vragen, en één om een ArrayList met adres-informatie op te vragen (postcode, huisnr, straat, stad, provincie). 
Deze methodes maken allebei een API request en gebruiken allebei postcode en huisnr als input.

Verder hebben we nog de classes Inlog, JDBC, PanelRoute, Popup, Scherm & Tables.
