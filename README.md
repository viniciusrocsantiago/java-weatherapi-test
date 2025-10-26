# WeatherApp API
A simple RESTful API built with Spring Boot to retrieve current weather data and forecasts for cities, powered by external weather services. This project is ideal for developers needing quick and reliable weather data integration.

### Technologies Used
This project is a modern Java application utilizing the following key technologies:
- Language: Java
- Framework: Spring Boot 3.x
- Build Tool: Maven (Inferred from standard Java/Spring setup)
- Architecture: RESTful Web Service
- Dependencies: Spring Web, likely uses a JSON processing library (e.g., GSON) and an HTTP client to communicate with the external weather API.
- Testing: JUnit, Mockito

### Getting Started
These instructions will get a copy of the project up and running on your local machine for development and testing.

#### Prerequisites
You will need the following software installed:
- Java Development Kit (JDK) 25
- Maven
- An API Key from your chosen external weather service (e.g., OpenWeatherMap, WeatherAPI, etc.) to configure the WeatherService.
- Git

### Installation and Setup
Clone the repository:

Bash
```shell
git clone https://github.com/viniciusrocsantiago/java-weatherapi-test.git
cd weatherapp
```

Configure API Key:
- It needs to register in [OpenWeatherMap](https://home.openweathermap.org/)

Build the project:
Bash
```shell
mvn clean install
```

Run the application:
Bash
```shell
java -jar target/weatherapp.jar
# The API will typically start on http://localhost:8080
```

API Endpoints (Usage)The API provides several endpoints to retrieve weather data. All endpoints are prefixed with /weather.

| HTTP Method |                Path                | Description                                                                                   |
|:------------|:----------------------------------:|:----------------------------------------------------------------------------------------------|
| GET         |        /weather/city/{city}        | Get current weather data for a specific city                                                  |
| GET         |      /weather/forecast/{city}      | Get the weather forecast for a specific city                                                  |
| GET         |            /weather/csv            | Process weather data from a CSV file and return the results. (Inferred to be a batch process) |
| GET         | /weather/geo?latitude=&longitude=  | Get temperature by geographic coordinates (latitude and longitude)                            |


