<template>
  <div>
    <div class="sidenav" id="style-3">
      <img alt="Logo" src="../../assets/logo2_app.png" width="150px">
      <router-link v-bind:to="{ name: 'a-library' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-music" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <circle cx="6" cy="17" r="3"></circle>
          <circle cx="16" cy="17" r="3"></circle>
          <polyline points="9 17 9 4 19 4 19 17"></polyline>
          <line x1="9" y1="8" x2="19" y2="8"></line>
        </svg>
        <span>Library</span>
      </router-link>
      <router-link v-bind:to="{ name: 'a-stats' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-chart-infographic" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <circle cx="7" cy="7" r="4"></circle>
          <path d="M7 3v4h4"></path>
          <line x1="9" y1="17" x2="9" y2="21"></line>
          <line x1="17" y1="14" x2="17" y2="21"></line>
          <line x1="13" y1="13" x2="13" y2="21"></line>
          <line x1="21" y1="12" x2="21" y2="21"></line>
        </svg>
        <span>Stats</span>
      </router-link>
      <router-link v-bind:to="{ name: 'a-upload' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-upload" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <path d="M4 17v2a2 2 0 0 0 2 2h12a2 2 0 0 0 2 -2v-2"></path>
          <polyline points="7 9 12 4 17 9"></polyline>
          <line x1="12" y1="4" x2="12" y2="16"></line>
        </svg>
        <span>Upload</span>
      </router-link>
      <router-link v-bind:to="{ name: 'a-profile' }">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-user" width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
          <circle cx="12" cy="7" r="4"></circle>
          <path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2"></path>
        </svg>
        <span>Profile</span>
      </router-link>
      <a>
        <button class="logout-btn" v-on:click="logout">
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
      this.$auth_artist.logout({
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

  //font-family: myFont2;
  //font-weight: normal; // ili 300 ??
  //font-weight: 500;

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
      //font-weight: bold;
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
    //font-weight: 500;
    font-weight: bold;

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