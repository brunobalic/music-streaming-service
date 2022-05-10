<template>
  <div>
    <template v-if="albumsList.length > 0">
      <div class="d-flex">
        <h5 class="text-white">Albums</h5>
      </div>
      <ol class="list-group list-group-flush">
        <li v-for="album in albumsList"
            v-bind:key="album.id"
            class="list-group-item ps-0">
          <div class="row">
            <div class="col-md-3 part1">
              <router-link v-bind:to="{ name: 'u-album', params: { id: album.id } }">
                <img class="album-img" alt="Album cover"
                     style="max-width: 100%; border-radius: 5%"
                     v-bind:src="albumImgBaseUrl + album.filename">
              </router-link>
            </div>
            <div class="col-md d-flex flex-column">
              <router-link v-bind:to="{ name: 'u-album', params: { id: album.id } }">
                <span class="h5">{{ album.name }}</span>
              </router-link>
              <router-link v-bind:to="{ name: 'u-artist', params: { id: album.artistObj.id } }">
                <span class="h6 artist-link">{{ album.artistObj.first_name }}</span>
              </router-link>
              <p class="mt-1 mb-1">
                <span class="fst-italic">Released: </span>
                <span class="fw-bold">{{ dateFormatted(album.official_release_date) }}</span>
              </p>
              <p class="mb-auto">
                <span class="fst-italic">Nb. of tracks: </span>
                <span class="fw-bold">{{ album.number_of_tracks }}</span>
              </p>
              <div class="d-flex flex-row album-btns">
                <button class="play"
                        v-on:click="playSelectedAlbum(album.id)">
                  <svg class=""
                       width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                       stroke="currentColor" fill="none" stroke-linecap="round"
                       stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M7 4v16l13 -8z"></path>
                  </svg>
                </button>
                <div class="vr ms-2 me-2 mt-1 mb-1"></div>
                <button v-if="!isAlbumFavourite(album.id)"
                        v-bind:id="'btn-fav-album-' + album.id"
                        v-on:click="addAlbumToFavourite(album.id, $event)"
                        class="fav">
                  <svg class=""
                       width="24" height="24" stroke-width="1" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428m0 0a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                  </svg>
                </button>
                <button v-else
                        v-bind:id="'btn-unfav-album-' + album.id"
                        v-on:click="removeAlbumFromFavourite(album.id, $event)"
                        class="unfav">
                  <svg class=""
                       width="24" height="24" stroke-width="1" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                    <path d="M12 7l-2 4l4 3l-2 4v3"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </li>
      </ol>
      <slot></slot>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'SearchAlbumsList',
  props: {
    albumsList: {
      type: Array,
      required: true
    }
  },
  computed: {
    ...mapGetters({
      albumImgBaseUrl: 'imgAlbumBaseUrl',
      userData: 'userData'
    }),
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
    isAlbumFavourite(albumId) {
      return this.userData.favouriteAlbums.find(element => {
        return element.album.toString() === albumId.toString()
      })
    },
    async addAlbumToFavourite(albumId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.addAlbumToFav(albumId, token)
          .then(res => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
            element.classList.remove('opacity-75')
            element.disabled = false
          })
          .catch(error => console.log(error))
    },
    async removeAlbumFromFavourite(albumId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.removeAlbumFromFav(albumId, token)
          .then((res) => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
            element.classList.remove('opacity-75')
            element.disabled = false
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

a {
  color: white;
  text-decoration: none;
  transition: .2s ease-in-out;
  &:hover {
    color: #c63a5d;
  }
}

.part1 {
  max-width: 175px;
}

.album-img {
  transition: .3s ease-in-out;
  &:hover {
    opacity: 0.75;
  }
}

.album-link span {
  transition: .2s;
  &:hover {
    color: #c63a5d;
  }
}

.artist-link {
  color: silver;
  transition: .2s;
  &:hover {
    color: #c63a5d;
  }
}

.album-btns {
  button {
    color: white;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    transition: .3s;
    &.play {
      background: white;
      svg {
        transition: .5s;
        color: #2d3f50;
        fill: #2d3f50;
      }
      &:hover {
        background-color: #c63a5d;
        svg {
          color: white;
          fill: white;
        }
      }
    }
    &.fav {
      svg {
        transition: .5s;
        color: #8f8f9a;
        //fill: none; // ne radi s transition -> koristi transparent
        fill: transparent;
      }
      &:hover {
        svg {
          //color: #c63a5d;
          //fill: #c63a5d;
          opacity: .75;
        }
      }
    }
    &.unfav {
      svg {
        transition: .5s;
        color: #c63a5d;
        fill: #c63a5d;
      }
      &:hover {
        svg {
          //color: #8f8f9a;
          //fill: none; // ne radi s transition -> koristi transparent
          //fill: transparent;
          opacity: .75;
        }
      }
    }
  }
}

</style>