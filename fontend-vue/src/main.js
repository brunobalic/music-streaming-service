import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { Vuelidate } from 'vuelidate'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'

// Import the Auth0 configuration
import { domain, clientId, audience } from "../auth_config_user.json";
import { domain as domainArtist, clientId as clientIdArtist, audience as audienceArtist } from "../auth_config_artist.json";

// Import the plugin here
import { Auth0Plugin } from "./auth/index_user";
import { Auth0Plugin_artist } from "./auth/index_artist";

// Install the authentication plugin here
Vue.use(Auth0Plugin, {
  domain,
  clientId,
  audience,
  onRedirectCallback: appState => {
    router.push(
      appState && appState.targetUrl
        ? appState.targetUrl
        : window.location.pathname
    );
  }
});

Vue.use(Auth0Plugin_artist, {
  domain: domainArtist,
  clientId: clientIdArtist,
  audience: audienceArtist,
  onRedirectCallback: appState => {
    router.push(
      appState && appState.targetUrl
        ? appState.targetUrl
        : window.location.pathname
    );
  }
});

Vue.use(Vuelidate)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
