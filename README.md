# Avancerad_JAVA24_Sheroq_Shahwan_slutprojekt

# Task Management Application

Detta är en uppgiftshanteringsapplikation byggd med **JavaFX** för frontend och **Spring Boot** för backend. Applikationen tillåter användare att hantera uppgifter genom att skapa, uppdatera, ta bort och lista uppgifter. Frontend och backend kommunicerar via REST-API.

---

## Funktionalitet

- **Skapa uppgifter**: Lägg till nya uppgifter med titel och beskrivning.
- **Visa uppgifter**: Se en lista över alla uppgifter som finns lagrade.
- **Uppdatera uppgifter**: Ändra information för en befintlig uppgift.
- **Ta bort uppgifter**: Radera en vald uppgift.

## Projektstruktur

Projektet använder sig av följande komponenter:

### Frontend
 - **JavaFX**: Används för att skapa ett dynamiskt användargränssnitt.
 - **Jackson**: För JSON-serialisering och deserialisering.
 - **Java 11+**: Programmeringsspråket som används.

 - `HelloController`: Hanterar användarinteraktion och kommunikation med 
 backend.
 - `Task`: Representerar en uppgift med fält som `id`, `title`, 
 `description` och `completed`.

### Backend
 - **Spring Boot**: Används för att bygga RESTful-tjänster.
 - **Java 11+**: Programmeringsspråket som används.

 - `TaskController`: Tillhandahåller REST-API-slutpunkter för CRUD- 
 operationer.
 - `Task`: Modellklass för att representera en uppgift i backend.

- **Kommunikation**:
 - Frontend kommunicerar med backend via HTTP-anrop och JSON-data.

### Verktyg och Byggsystem
- **Maven**: För att hantera projektets beroenden och byggprocess.

---

## Systemarkitektur

Applikationen är uppdelad i två moduler:
1. **Frontend**: Hanterar användargränssnitt och HTTP-kommunikation med 
   backend.
   - **MVC (Model-View-Controller)**: Frontend använder MVC-mönstret för 
     att separera logik och vy.
2. **Backend**: Tillhandahåller REST-API-slutpunkter för CRUD-operationer.
   - **RESTful API**: Backend följer REST-principer för att möjliggöra 
     enkel och skalbar kommunikation.
3. **Objektorienterad design**: Använder klasser och objekt för att 
   hantera uppgifter och deras tillstånd.

---

## Krav

- Java Development Kit (JDK) 11 eller högre.
- Maven för att hantera beroenden.
- En IDE som stödjer Java och Spring Boot (t.ex. IntelliJ IDEA, Eclipse, eller VS Code).
  
---

## Frontend

### Struktur och Koddetaljer

#### `HelloController.java`
Denna klass fungerar som huvudkontrollern för frontend.

- **Fält och komponenter**:
  - `TableView<Task> taskTable`: En tabell för att visa uppgifter.
  - `TextField titleField, descriptionField`: Textfält för att ange titel och beskrivning för en uppgift.
  - `Button addButton, deleteButton, updateButton`: Knappar för att lägga till, ta bort och uppdatera uppgifter.

- **Metoder**:
  - **`addButton(ActionEvent event)`**  
    Lägger till en ny uppgift via en POST-förfrågan.
    ```java
    Task task = new Task(0, title, description, false);
    String json = mapper.writeValueAsString(task);
    HttpURLConnection connection = createConnection("POST", BASE_URL);
    os.write(json.getBytes());
    ```

  - **`deleteButton(ActionEvent event)`**  
    Tar bort en vald uppgift via en DELETE-förfrågan.
    ```java
    HttpURLConnection connection = createConnection("DELETE", BASE_URL + "/" + selectedTask.getId());
    ```

  - **`updateButton(ActionEvent event)`**  
    Uppdaterar informationen om en vald uppgift via en PUT-förfrågan.
    ```java
    String updatedJson = String.format("{\"title\":\"%s\",\"description\":\"%s\",\"completed\":%b}", titleField.getText(), 
    descriptionField.getText(), selectedTask.isCompleted());
    HttpURLConnection connection = createConnection("PUT", BASE_URL + "/" + selectedTask.getId());
    ```

  - **`refreshTable()`**  
    Uppdaterar uppgiftslistan genom en GET-förfrågan och deserialiserar JSON-svaret till Java-objekt:
    ```java
    List<Task> tasks = new ObjectMapper().readValue(connection.getInputStream(), new TypeReference<List<Task>>() {});
    ```

#### `Task.java`
Denna klass representerar en uppgift.

- **Fält**:
  - `int id`: Unikt ID för uppgiften.
  - `String title`: Titel på uppgiften.
  - `String description`: Beskrivning av uppgiften.
  - `boolean completed`: Status om uppgiften är färdig eller ej.

- **Exempel på Getter och Setter**:
  ```java
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

## Så här kör du programmet

### Backend
1. Gå till backend-mappen.
2. Kör kommandot:
   ```bash
   mvn spring-boot:run
  
