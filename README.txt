Requirements to launch program:
Maven: 3.5.3
Java JDK: 1.8.0_171
Preferred IDE: IntelliJ IDEA 2018

After downloading all Maven dependencies, write the following phrase in terminal:
mvn clean install spring-boot:run

Application is available in the following URL address:
http://localhost:8888/

The following URL address contains all information regarding methods used in the application:
http://localhost:8888/swagger-ui.html#/

In the aforementioned URL address you can find, all methods including JSON structure filled with example data. In some cases e.g. searching for reservation, some parts of JSON structure can be omitted like dateTo or/and dateFrom (just like mentioned in the documentation).

Example:
URL:
http://localhost:8888/reservations/room

JSON:
{
  "dateFrom": "2019-08-05 12:00:00",
  "rooms": {
    "roomName": "Large Room"
  }
}

The aforementioned example will return all Large Room's reservation which started from 2019-08-05 12:00:00.