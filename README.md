# WeatherApp API
A simple RESTful API built with Spring Boot to retrieve current weather data and forecasts for cities filtered by zip code. It is powered by external weather services. 

### Technologies Used
This project is a Java application utilizing the following key technologies:
- Framework: Spring Boot 3.x
- Build Tool: Maven (Inferred from standard Java/Spring setup)
- Architecture: RESTful Web Service
- Dependencies: Spring Web, likely uses a JSON processing library (Gson) and an HTTP client to communicate with the external weather API.
- Caffeine (caching library)
- Testing: JUnit, Mockito

### Getting Started
These instructions will get a copy of the project up and running on your local machine for development and testing.

#### Prerequisites
You will need the following software installed:
- Java Development Kit (JDK) 25
- Maven
- API Key from the external weather service (OpenWeatherMap provided) to configure the WeatherService.
- Git

### Installation and Setup
Clone the repository:
```shell
git clone https://github.com/viniciusrocsantiago/java-weatherapi-test.git
cd weatherapp
```

API Documentation
- Swagger: http://localhost:8080/swagger-ui/index.htm

Configure API Key:
- API Key is provided for [OpenWeatherMap](https://home.openweathermap.org/). No need registration required.

Build the project:
```shell
mvn clean install
```

Run the application:
```shell
java -jar target/weatherapp.jar
# The API will start on http://localhost:8080
```

API Endpoints (Usage)

The API provides endpoints to retrieve weather data. All endpoints are prefixed with /weather.

| HTTP Method | Path                              | Description                                               |
|:------------|:----------------------------------|:----------------------------------------------------------|
| GET         | /weather/forecast/{zipCode}       | Get current weather forecast data for a specific zip code |
| GET         | /weather/city/{cityName}          | Get the weather forecast for a specific city              |


