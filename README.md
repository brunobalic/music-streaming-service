# Music streaming service

## *About*
This is my music streaming web application that I work on to sharpen my programming skills and learn new technologies along the way.

## Technologies used

- *Frontend*
	- **[Vue.js](https://github.com/vuejs/vue "Vue.js")**
	  - Vuex
	  - Vue-router
- *Backend*
	- **Java** & **[Spring Boot](https://github.com/spring-projects/spring-boot)**
- *Authentication*
	- Auth0 IAM

## Architecture

The system is designed as a **microservice architecture**.

On the frontend side there is a Vue.js application that communicates with the gateway via REST API.
Before Vue app can request any data from the gateway, user needs to authenticate and obtain JWT token.
<br>
Now when Vue app makes requests to the gateway, with the included token, gateway validates the token and proceeds with request.
Gateway then forwards requests to other microservices.


## Description

The application has two types of users:
- **Regular users**
  - Can search for music (songs, albums, artists, playlists), add content to his personal favourites list, create playlists, discover most popular songs and most importantly listen to music.

- **Professional artists**
  - Can upload their own music and put them in different collections (albums, singles, etc.) and monitor their performance.
