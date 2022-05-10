<template>
  <div class="user-body">
    <div class="container-md mt-4 ps-5 pe-5">

      <div class="m-3 form__group">
        <input v-model="searchQuery.q"
               id="search-input"
               class="form__field"
               placeholder="Search here..."
               name="search-input"
               required>
        <label for="search-input" class="form__label text-start">Search here</label>
      </div>

      <div>
        <ul class="nav nav-pills ms-3 me-3">
          <template v-for="(type, index) in contentTypes">
            <li class="nav-item"
                v-bind:key="index">
              <a class="nav-link h6 m-0 p-2"
                 v-bind:class="searchQuery.t === type.value ? 'active' : ''"
                 v-on:click="searchQuery.t = type.value">
                {{ type.displayName }}
              </a>
            </li>
          </template>
        </ul>
        <hr class="m-3 mt-0">
      </div>

      <div v-if="isLoading"
           class="spinner-border text-light"
           role="status">
        <span class="visually-hidden">Loading...</span>
      </div>

      <template v-if="searchResults">
        <template v-for="(type, index) in contentTypes">
          <template v-if="type.componentName">

            <keep-alive v-bind:key="index">
              <component v-if="searchQuery.t === type.value || searchQuery.t === undefined"
                         v-bind:is="type.componentName"
                         v-bind="generateProps(type.componentName)"
                         class="m-3">
                <hr>
                <template v-if="isLoadingAdditional.status && isLoadingAdditional.contentType === type.value">
                  <div class="spinner-border text-light"
                       role="status">
                    <span class="visually-hidden">Loading...</span>
                  </div>
                </template>
                <template v-else>
                  <button v-on:click="searchMoreResults2(type.value)">
                    <span class="text-white">Search harder...</span>
                  </button>
                </template>

              </component>
            </keep-alive>

          </template>
        </template>
      </template>

    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'
import SearchAlbumsList from '@/components/regular-user/lists/SearchAlbumsList'
import SearchArtistsList from '@/components/regular-user/lists/SearchArtistsList'
import SearchPlaylistsList from '@/components/regular-user/lists/SearchPlaylistsList'
import SearchTracksList from '@/components/regular-user/lists/SearchTracksList'
import router from '@/router'

const { mapGetters } = createNamespacedHelpers('user')

const timeoutMilisec = 1000

export default {
  name: 'SearchAdvanced',
  components: {
    SearchTracksList,
    SearchArtistsList,
    SearchAlbumsList,
    SearchPlaylistsList
  },
  data() {
    return {
      // redosljed je bitan, po ovo redosljedu su poredane komponente u view-u
      componentsNames: [
        SearchTracksList.name,
        SearchArtistsList.name,
        SearchAlbumsList.name,
        SearchPlaylistsList.name
      ],
      contentTypes: [{
        value: undefined,
        displayName: 'All',
        componentName: undefined
      }, {
        value: 'track',
        displayName: 'Tracks',
        componentName: SearchTracksList.name
      }, {
        value: 'artist',
        displayName: 'Artists',
        componentName: SearchArtistsList.name
      }, {
        value: 'album',
        displayName: 'Albums',
        componentName: SearchAlbumsList.name
      }, {
        value: 'playlist',
        displayName: 'Playlists',
        componentName: SearchPlaylistsList.name
      }],

      searchQuery: {
        q: undefined,
        t: undefined
      },
      searchResults: null,
      isLoading: false,
      isLoadingAdditional: {
        status: false,
        contentType: undefined
      },
      debounceTimeout: null,
    }
  },
  computed: {
    ...mapGetters({
      userData: 'userData',
      lastSearchData: 'lastSearchData'
    }),
    routeQueryParams() {
      return {
        q: this.$route.query.q,
        t: this.$route.query.t
      }
    },
    areQueryParamsChanged() {
      return this.$route.query.q !== this.searchQuery.q
          || this.$route.query.t !== this.searchQuery.t;
    }
  },
  watch: {
    //'searchQuery.q': function (newValue, oldValue) {
    'searchQuery.q': function (newValue) {
      //console.log('old value', oldValue)
      //console.log('new value', newValue)
      if (newValue === undefined) {
        return
      }

      newValue = newValue.trim()
      if (newValue === '') {
        this.searchQuery.q = undefined
        return
      }

      if (this.debounceTimeout) {
        console.log('clear timeout', this.debounceTimeout)
        clearTimeout(this.debounceTimeout)
      }

      this.debounceTimeout = setTimeout(this.searchContent, timeoutMilisec)
      console.log('set timeout', this.debounceTimeout)
    },
    //'searchQuery.t': function (newValue, oldValue) {
    'searchQuery.t': function () {
      //console.log('old value', oldValue)
      //console.log('new value', newValue)
      this.$store.dispatch('user/setLastSearchData', {
        searchQuery: {
          q: this.searchQuery.q,
          t: this.searchQuery.t
        },
        searchResults: {
          trackDtos: this.searchResults ? this.searchResults.trackDtos : [],
          artistDtos: this.searchResults ? this.searchResults.artistDtos : [],
          albumDtos: this.searchResults ? this.searchResults.albumDtos : [],
          playlistDtos: this.searchResults ? this.searchResults.playlistDtos : []
        }
      })

      // update route url
      if (this.areQueryParamsChanged) {
        router.replace({
          name: 'u-search-advanced',
          query: this.searchQuery
        })
      }
    }
  },
  async mounted() {
    // provjeri jesu li query params u url-u
    // oni imaju prioritet
    // npr. ako je neki user kliknu na neki vanjski link
    // koji ga vodi na trazilicu s odredenjim parametrima
    // onda mu to i zelimo prikazati...
    if (this.$route.query.q) {
      // !!!
      // u slucaju kada se user vraca s back buttonom preko history-a
      // imat ce query parametre
      // pa provjerimo dali su ti parametri isti kao i iz last search-a
      // ako su isti onda mozemo prikazati prethodne spremljene podatke
      // * provjeravam samo parametar 'q' jer jedino po njemu pretrazujem
      // * parametar 't' mi samo sluzi kao filter za tip podatka...
      if (this.lastSearchData) {
        if (this.$route.query.q === this.lastSearchData.searchQuery.q) {
          this.searchResults = this.lastSearchData.searchResults
          this.searchQuery = this.lastSearchData.searchQuery
          return
        }
      }

      // ako 'q' nije isti kao i spremljeni 'q'
      // onda posatvljam 'q' pa ce se pretrazivanje automatski zapoceti
      this.searchQuery.q = this.$route.query.q
      // 'this.$route.query.t' nema potrebe provjeravati jer nije nuzan
      // pa i ako ga nema biti ce undefined, tocno sto mi i odgovara...
      this.searchQuery.t = this.$route.query.t

      return
    }

    // -> odlucia sam ako nema query parametara da nista ne prikazujem
    //    to se gleda kao da je novi search
    // // ako nema query parametara onda
    // if (this.lastSearchData) {
    //   this.searchResults = this.lastSearchData.searchResults
    //   this.searchQuery = this.lastSearchData.searchQuery
    //   return
    // }
  },
  beforeDestroy() {
    console.log('before destroy')
  },
  methods: {
    generateProps(componentName) {
      if (componentName === SearchTracksList.name) {
        return {
          tracksList: this.searchResults.trackDtos
        }
      }
      if (componentName === SearchArtistsList.name) {
        return {
          artistsList: this.searchResults.artistDtos
        }
      }
      if (componentName === SearchAlbumsList.name) {
        return {
          albumsList: this.searchResults.albumDtos
        }
      }
      if (componentName === SearchPlaylistsList.name) {
        return {
          playlistsList: this.searchResults.playlistDtos
        }
      }
    },
    async searchContent() {

      console.log('----inside search')

      if (this.lastSearchData && this.lastSearchData.searchQuery.q === this.searchQuery.q) {
        // ako je isto q nema potreba ponovo pretrazivat jer te podatke vec imam

        console.log('----input', this.searchQuery.q)
        console.log('----vuex', this.lastSearchData.searchQuery.q)

        return
      }

      console.log('----1')

      this.isLoading = true

      let token = await this.$auth_user.getTokenSilently()
      let response = await BackendService.searchAllContent(this.searchQuery.q, token)
      this.searchResults = response.data

      this.isLoading = false

      // building object to save to vuex store
      // ovako nevalja jer mi referencira isto objekt... trebao bi koristi lodash deep clone
      // let searchData = {}
      // searchData.searchQuery = this.searchQuery
      // searchData.searchResults = this.searchResults
      // await this.$store.dispatch('user/setLastSearchData', searchData)
      await this.$store.dispatch('user/setLastSearchData', {
        searchQuery: {
          q: this.searchQuery.q,
          t: this.searchQuery.t
        },
        searchResults: {
          trackDtos: this.searchResults.trackDtos,
          artistDtos: this.searchResults.artistDtos,
          albumDtos: this.searchResults.albumDtos,
          playlistDtos: this.searchResults.playlistDtos
        }
      })

      // update route url
      if (this.areQueryParamsChanged) {
        await router.replace({
          name: 'u-search-advanced',
          query: this.searchQuery
        })
      }
    },
    async searchMoreResults() {
      let token = await this.$auth_user.getTokenSilently()
      let response = await BackendService
          .searchSpecificContent(
              this.searchQuery.q,
              this.searchQuery.t,
              0,
              15,
              token)

      if (this.searchQuery.t === 'track')
        this.searchResults.trackDtos = response.data
      if (this.searchQuery.t === 'artist')
        this.searchResults.artistDtos = response.data
      if (this.searchQuery.t === 'album')
        this.searchResults.albumDtos = response.data
      if (this.searchQuery.t === 'playlist')
        this.searchResults.playlistDtos = response.data

      let searchData = {}
      searchData.searchResults = this.searchResults
      searchData.searchQuery = this.searchQuery
      await this.$store.dispatch('user/setLastSearchData', searchData)

    },
    async searchMoreResults2(contentType) {
      this.isLoadingAdditional.status = true
      this.isLoadingAdditional.contentType = contentType

      let token = await this.$auth_user.getTokenSilently()
      let response = await BackendService
          .searchSpecificContent(
              this.searchQuery.q,
              contentType,
              0,
              15,
              token)

      if (contentType === 'track')
        this.searchResults.trackDtos = response.data
      if (contentType === 'artist')
        this.searchResults.artistDtos = response.data
      if (contentType === 'album')
        this.searchResults.albumDtos = response.data
      if (contentType === 'playlist')
        this.searchResults.playlistDtos = response.data

      this.isLoadingAdditional.status = false
      this.isLoadingAdditional.contentType = undefined

      // let searchData = {}
      // searchData.searchResults = this.searchResults
      // searchData.searchQuery = this.searchQuery
      // await this.$store.dispatch('user/setLastSearchData', searchData)
      await this.$store.dispatch('user/setLastSearchData', {
        searchQuery: {
          q: this.searchQuery.q,
          t: this.searchQuery.t
        },
        searchResults: {
          trackDtos: this.searchResults.trackDtos,
          artistDtos: this.searchResults.artistDtos,
          albumDtos: this.searchResults.albumDtos,
          playlistDtos: this.searchResults.playlistDtos
        }
      })

    }
  }
}
</script>

<style lang="scss" scoped>

a {
  color: white;
  text-decoration: none;
  transition: .2s ease-in-out;
  &:hover {
    color: #c63a5d;
  }
  .see-more {
    color: #8f8f9a;
    font-size: 0.9rem;
    font-weight: 500;
    //transition: .2s ease-in-out;
    &:hover {
      color: #c63a5d;
    }
  }
}

.nav-pills {
  .nav-link {
    color: silver;
    //border-radius: 0;
    //border-top: solid #2d3f50 1px;
    &:hover {
      color: white;
    }
    &.active {
      color: white;
      background: transparent;
      border-radius: 0;
      border-bottom: solid #c63a5d .2rem;
      transition: 0s; // da maknem od bootstrap-a
    }
  }
}

////////

/* START - SEARCH INPUT FIELD */
$primary: #c63a5d;
//$secondary: #2d3f50;
$secondary: rgba(0, 0, 0, 0);
$white: #fff;
$gray: #9b9b9b;
.form__group {
  padding-top: 35px;
}

.form__field {
  font-family: inherit;
  //width: 70%;
  width: 100%;
  border: 0;
  border-bottom: 2px solid $gray;
  outline: 0;
  //font-size: 1.3rem;
  font-size: 1rem;
  color: $white;
  padding: 7px 0;
  background: transparent;
  transition: border-color 0.2s;

  &::placeholder {
    color: transparent;
  }

  &:placeholder-shown ~ .form__label {
    //font-size: 1.3rem;
    font-size: 1rem;
    cursor: text;
    top: 20px;
  }

}

.form__label {
  //position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: $gray;
}

.form__field:focus {
  ~ .form__label {
    //position: absolute;
    top: 0;
    display: block;
    transition: 0.2s;
    font-size: 1rem;
    //color: $primary;
    color: white;
    font-weight:700;
  }
  padding-bottom: 6px;
  font-weight: 700;
  border-width: 3px;
  border-image: linear-gradient(to right, $primary,$secondary);
  border-image-slice: 1;
}
/* reset input */
.form__field{
  &:required,&:invalid { box-shadow:none; }
}
/* demo */
body {
  font-family: 'Poppins', sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  font-size: 1.5rem;
  background-color:#222222;
}
/* END - SEARCH INPUT FIELD */

</style>