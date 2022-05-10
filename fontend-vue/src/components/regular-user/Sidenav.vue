<template>
  <div>
    <div class="sidenav" id="style-3">
      <img alt="Logo" src="../../assets/logo2_app.png" width="150px">
      <router-link v-bind:to="{ name: 'u-home' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-music" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <circle cx="6" cy="17" r="3"></circle>
          <circle cx="16" cy="17" r="3"></circle>
          <polyline points="9 17 9 4 19 4 19 17"></polyline>
          <line x1="9" y1="8" x2="19" y2="8"></line>
        </svg>
        <span>Home</span>
      </router-link>
<!--      <router-link v-bind:to="{ name: 'u-search' }">-->
      <router-link v-bind:to="{ name: 'u-search-advanced' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-search" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <circle cx="10" cy="10" r="7"></circle>
          <line x1="21" y1="21" x2="15" y2="15"></line>
        </svg>
        <span>Search</span>
      </router-link>
      <router-link v-bind:to="{ name: 'u-discover' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-compass" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <polyline points="8 16 10 10 16 8 14 14 8 16"></polyline>
          <circle cx="12" cy="12" r="9"></circle>
          <line x1="12" y1="3" x2="12" y2="5"></line>
          <line x1="12" y1="19" x2="12" y2="21"></line>
          <line x1="3" y1="12" x2="5" y2="12"></line>
          <line x1="19" y1="12" x2="21" y2="12"></line>
        </svg>
        <span>Discover</span>
      </router-link>
      <router-link v-bind:to="{ name: 'u-fav-all' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-heart" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428m0 0a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
        </svg>
        <span>Favourites</span>
        <div id="fav_content">
          <router-link v-bind:to="{ name: 'u-fav-all', params: { tab: 'tracks' } }" class="fav_content">Tracks</router-link>
          <router-link v-bind:to="{ name: 'u-fav-all', params: { tab: 'artists' } }" class="fav_content">Artists</router-link>
          <router-link v-bind:to="{ name: 'u-fav-all', params: { tab: 'albums' } }" class="fav_content">Albums</router-link>
          <router-link v-bind:to="{ name: 'u-fav-all', params: { tab: 'playlists' } }" class="fav_content">Playlists</router-link>
        </div>
      </router-link>
      <a>
        <button class="logout-btn" v-on:click="logout">
<!--          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-logout" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">-->
<!--            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>-->
<!--            <path d="M14 8v-2a2 2 0 0 0 -2 -2h-7a2 2 0 0 0 -2 2v12a2 2 0 0 0 2 2h7a2 2 0 0 0 2 -2v-2"></path>-->
<!--            <path d="M7 12h14l-3 -3m0 6l3 -3"></path>-->
<!--          </svg>-->
          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-door-exit" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
            <path d="M13 12v.01"></path>
            <path d="M3 21h18"></path>
            <path d="M5 21v-16a2 2 0 0 1 2 -2h7.5m2.5 10.5v7.5"></path>
            <path d="M14 7h7m-3 -3l3 3l-3 3"></path>
          </svg>
          <span class="logout-text">Logout</span>
        </button>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Sidenav',
  methods: {
    logout() {
      this.$auth_user.logout({
        // returnTo: window.location.origin
        returnTo: 'http://localhost:8080/welcome'
      })
    }
  }
}
</script>

<style scoped lang="scss">

.sidenav {
  /*height: calc(100% - 85px); !* Full-height: remove this if you want "auto" height *!*/
  //height: 100%; /* Full-height: remove this if you want "auto" height */
  // privremeno rjesenje da mi da scroll bar na navbar
  // // mjenjam nakon dodavanja bootstrapa na -85 (visina footera)
  //height: -webkit-calc(100% - 115px);
  //height:    -moz-calc(100% - 115px);
  //height:         calc(100% - 115px);
  height: -webkit-calc(100% - 85px);
  height:    -moz-calc(100% - 85px);
  height:         calc(100% - 85px);

  width: 225px; /* Set the width of the sidebar */
  position: fixed; /* Fixed Sidebar (stay in place on scroll) */
  z-index: 1; /* Stay on top */
  top: 0; /* Stay at the top */
  left: 0;
  background-color: #20202b; /* Black */
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 20px;
  //padding-bottom: calc(85px + 10px); // iskucujem nakon dodavanja bootstrapa
  border-right: #505061;
  border-right-width: 1px;
  border-right-style: solid;
}

.sidenav img {
  padding-bottom: 10px;
  border-top: 0;
  border-left: 0;
  border-right: 0;
  border-bottom: solid silver 1px;
  border-image: linear-gradient(to right, #1ddca3, #1dc0dc);
  border-image-slice: 1;
}

/* The navigation menu links */
.sidenav a {
  padding: 12px 10px 12px 30px;
  text-decoration: none;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  font-weight: bold;
  font-size: 18px;
  color: #ffffff;
  display: block;
  transition: .25s;
  vertical-align: middle;
  text-align: left;
}

.sidenav {
  a {
    &.router-link-exact-active {
      color: #ffcb6b;
    }
  }
}

.sidenav {
  button {
    transition: .3s;
    color: silver;
    span {
      //color: white;
      font-weight: bold;
    }
    &:hover {
      color: #c63a5d;
    }
    &.logout-btn:hover {
      color: #1ddca3;
    }
  }
}

/* When you mouse over the navigation links, change their color */
.sidenav a:hover {
  color: #c63a5d;
}

.icon {
  margin-right: 12px;
  vertical-align: middle;
}

#fav_content {
  a {
    color: #8f8f9a;
    font-size: 1rem;
    font-weight: normal;
    padding: 5px 0px 5px 45px;
    &.router-link-exact-active {
      color: #ffcb6b;
    }
  }
  a:hover {
    color: #c63a5d;
  }
}

.sidenav {
  .logout-text {
    /* Fallback: Set a background color. */
    background-color: #c63a5d;

    /* Create the gradient. */
    background-image: linear-gradient(45deg, #1ddca3, #1dc0dc);

    /* Set the background size and repeat properties. */
    background-size: 100%;
    background-repeat: repeat;

    /* Use the text as a mask for the background. */
    /* This will show the gradient as a text color rather than element bg. */
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    //-moz-background-clip: text;
    -moz-text-fill-color: transparent;
  }
}

// SCROLLBAR
#style-3::-webkit-scrollbar-track {
  //-webkit-box-shadow: inset 0 0 4px rgb(143, 143, 154);
  background-color: rgba(255, 255, 255, 0);
}
#style-3::-webkit-scrollbar {
  width: 4px;
  background-color: #FFFFFF00;
}
#style-3::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: #8f8f9a;
}

</style>