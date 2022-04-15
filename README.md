# Music streaming service

## *About*
This is my music streaming web application that I work on to sharpen my programming skills and learn new technologies along the way.

:arrow_right: **[See how it looks](#screenshots)** :arrow_left:

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

<p align="middle">
	<img src="/showcase/architecture-diagram.png" width="50%" /> 
</p>

## Description

The application has two types of users:
- **Regular users**
  - Can search for music (songs, albums, artists, playlists), add content to his personal favourites list, create playlists, discover most popular songs and most importantly listen to music.

- **Professional artists**
  - Can upload their own music and put them in different collections (albums, singles, etc.) and monitor their performance.

### Screenshots

*Some screenshots from regular user account*

<p align="middle">
	<img src="/showcase/screenshots/landing_page.jpg" width="49%" /> 
</p>
<p align="middle">
	<img src="/showcase/screenshots/regular-user/artist_page1.jpg" width="49%" /> 
	<img src="/showcase/screenshots/regular-user/album_page1.jpg" width="49%" />
</p>
<p align="middle">
	<img src="/showcase/screenshots/regular-user/artist_page2.jpg" width="49%" /> 
	<img src="/showcase/screenshots/regular-user/album_page2.jpg" width="49%" />
</p>
<p align="middle">
	<img src="/showcase/screenshots/regular-user/search_page1.jpg" width="49%" />
	<img src="/showcase/screenshots/regular-user/discover_page1.jpg" width="49%" /> 
</p>
<p align="middle">
	<img src="/showcase/screenshots/regular-user/user_home1.jpg" width="33%" />
	<img src="/showcase/screenshots/regular-user/user_favs1.jpg" width="33%" /> 
	<img src="/showcase/screenshots/regular-user/user_favs2.jpg" width="33%" /> 
</p>
