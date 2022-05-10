<template>
  <div class="user-body">

    <loading-screen-artist-profile v-if="!isLoaded"></loading-screen-artist-profile>

    <template v-if="artistData !== null">
      <div class="container-md mt-4 ps-5 pe-5">
        <div class="row g-0">
          <div class="col-md-4 m-4"
               style="max-width: 200px">
            <img style="max-width: 200px; border-radius: 50%"
                 v-bind:src="artistImgBaseUrl + artistData.pictureFilename"
                 @error="setAltImgArtist" alt="Artist Picture">
          </div>
          <div class="col-md m-4 d-flex flex-column text-start">
            <h3>
              <span class="text-white" style="color: white">{{ artistData.first_name }} {{ artistData.last_name }}</span>
            </h3>
            <p>
              <span class="text-white fst-italic">{{ artistData.description }}</span>
            </p>
            <p class="mb-auto">
              <small class="text-muted">Additional info</small>
            </p>
            <div class="mt-3">
              <div v-if="!isArtistFavourite">
                <button id="btn-artist-fav"
                        class="favArtist"
                        v-on:click="addArtistToFavourite">
                  <svg class="icon icon-tabler icon-tabler-heart" fill="none"
                       width="32" height="32" stroke-width="1.5" viewBox="0 0 32 32"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428m0 0a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                  </svg>
                  <span>Add to Favourites</span>
                </button>
              </div>
              <div v-else>
                <button id="btn-artist-unfav"
                        class="unFavArtist"
                        v-on:click="removeArtistFromFavourite">
                  <svg class="icon icon-tabler icon-tabler-heart-broken" fill="none"
                       width="32" height="32" stroke-width="1.5" viewBox="0 0 32 32"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                    <path d="M12 7l-2 4l4 3l-2 4v3"></path>
                  </svg>
                  <span>Remove from Favourites</span>
                </button>
              </div>
            </div>
          </div>
        </div>
        <ul class="nav nav-pills">
          <li class="nav-item">
            <button class="nav-link h6 m-0 p-2"
                    v-bind:class="[currentTabComponentName === componentNames.TopTracksTab ? 'active' : '']"
                    v-on:click="currentTabComponentName = componentNames.TopTracksTab">
              Top Tracks</button>
          </li>
          <li class="nav-item">
            <button class="nav-link h6 m-0 p-2"
                    v-bind:class="[currentTabComponentName === componentNames.DiscographyTab ? 'active' : '']"
                    v-on:click="currentTabComponentName = componentNames.DiscographyTab">
              Discography</button>
          </li>
        </ul>
        <hr class="mt-0 mb-3">

        <keep-alive>
          <component v-bind:is="currentTabComponentName"
                     v-bind="propsToSend">
          </component>
        </keep-alive>

      </div>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '../../services/regular-user/BackendService'
import TopTracksTab from '../../components/regular-user/lists/TopTracksTab'
import DiscographyTab from '../../components/regular-user/DiscographyTab'
import LoadingScreenArtistProfile from '../../components/loadingScreens/LoadingScreenArtistProfile'

const { mapState } = createNamespacedHelpers('user')

export default {
  name: 'Artist',
  components: {
    TopTracksTab,
    DiscographyTab,
    LoadingScreenArtistProfile
  },
  data() {
    return {
      artistId: this.$route.params.id,
      artistData: null,

      componentNames: {
        TopTracksTab: TopTracksTab.name,
        DiscographyTab: DiscographyTab.name
      },
      currentTabComponentName: TopTracksTab.name,
      //currentTabComponentName: DiscographyTab.name,

      isLoaded: false
    }
  },
  computed: {
    ...mapState({
      artistImgBaseUrl: 'baseUrl_imgArtist',
      albumImgBaseUrl: 'baseUrl_imgAlbum',
      userData: 'userData'
    }),
    isArtistFavourite() {
      return this.userData.favouriteArtists.find(element => {
        return element.artist.toString() === this.artistId.toString()
      })
    },
    propsToSend() {
      if (this.currentTabComponentName === TopTracksTab.name) {
        return {
          artistId: this.artistId.toString()
        }
      } else if (this.currentTabComponentName === DiscographyTab.name) {
        return {
          artistId: this.artistId.toString()
        }
      }
      else return null
    }
  },
  async mounted() {
    this.isLoaded = false
    let token = await this.$auth_user.getTokenSilently()
    this.artistData = (await BackendService.getArtistData(this.artistId, token)).data
    this.isLoaded = true
  },
  methods: {
    dateFormatted(dateString) {
      const date = new Date(dateString)
      return date.toLocaleString('default', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    },
    setAltImgArtist(event) {
      event.target.src = require('@/assets/artist_logo.png')
    },
    async addArtistToFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.addArtistToFav(this.artistId, token)
          .then(res => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
            element.disabled = false
            element.classList.remove('opacity-75')
          })
          .catch(error => console.log(error))
    },
    async removeArtistFromFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.removeArtistFromFav(this.artistId, token)
          .then(res => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
            element.disabled = false
            element.classList.remove('opacity-75')
          })
          .catch(error => console.log(error))
    }
  }
}
</script>

<style lang="scss" scoped>

p {
  font-size: .9rem;
  color: #8f8f9a;
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

.favArtist {
  transition: .3s;
  svg {
    color: #2d3f50;
    transition: .3s;
  }
  span {
    color: #8f8f9a;
    transition: .3s;
  }
  &:hover {
    svg {
      color: #c63a5d;
      fill: #c63a5d;
    }
    span {
      color: white;
    }
  }
}

.unFavArtist {
  transition: .3s;
  svg {
    color: #c63a5d;
    fill: #c63a5d;
    transition: .3s;
  }
  span {
    color: #8f8f9a;
    transition: .3s;
  }
  &:hover {
    svg {
      fill: none;
      color: #2d3f50;
    }
    span {
      color: white;
    }
  }
}

</style>