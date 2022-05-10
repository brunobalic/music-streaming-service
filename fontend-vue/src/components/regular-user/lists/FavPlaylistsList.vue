<template>
  <div>
    <ol class="list-group list-group-flush">
      <li v-for="(pl) in favPlaylists"
          v-bind:key="pl.id"
          class="list-group-item">
        <div class="row">
          <div class="col-md-3 part1">
            <router-link v-bind:to="{ name: 'u-playlist', params: { id: pl.id } }">
              <svg class="" fill="none" stroke="currentColor"
                   stroke-linecap="round" stroke-linejoin="round"
                   stroke-width="2" viewBox="0 0 24 24" width="120" height="120"
                   xmlns="http://www.w3.org/2000/svg">
                <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                <circle cx="14" cy="17" r="3"></circle>
                <path d="M17 17v-13h4"></path>
                <path d="M13 5h-10"></path>
                <line x1="3" x2="13" y1="9" y2="9"></line>
                <path d="M9 13h-6"></path>
              </svg>
            </router-link>
          </div>
          <div class="col-md d-flex flex-column">
            <router-link v-bind:to="{ name: 'u-playlist', params: { id: pl.id } }">
              <span class="h6">{{ pl.name }}</span>
            </router-link>
            <p class="mt-1 mb-1">
              <span class="fst-italic">Created: </span>
              <span class="fw-bold">{{ dateFormatted(pl.publish_date) }}</span>
            </p>
            <p class="mb-auto">
              <span class="fst-italic">Nb. of tracks: </span>
              <span class="fw-bold">{{ pl.number_of_tracks }}</span>
            </p>
            <div class="d-flex flex-row plist-btns">
              <button class="play"
                      v-on:click="playSelectedPlaylist(pl.id)">
                <svg class=""
                     width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                     stroke="currentColor" fill="none" stroke-linecap="round"
                     stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                  <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                  <path d="M7 4v16l13 -8z"></path>
                </svg>
              </button>
              <div class="vr ms-2 me-2 mt-1 mb-1"></div>
              <button v-if="!isPlaylistFavourite(pl.id)"
                      v-bind:id="'btn-fav-pl-' + pl.id"
                      v-on:click="addPlaylistToFavourites(pl.id, $event)"
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
                      v-bind:id="'btn-unfav-pl-' + pl.id"
                      v-on:click="removePlaylistFromFavourites(pl.id, $event)"
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
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'ListFavPlaylists',
  data() {
    return {
      favPlaylists: []
    }
  },
  computed: {
    ...mapGetters({
      imgAlbumBaseUrl: 'imgAlbumBaseUrl',
      imgArtistBaseUrl: 'imgArtistBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl',
      userData: 'userData'
    })
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.userData.favouritePlaylists.forEach(element => {
      BackendService.getPlaylistData(element.playlist, token)
          .then(response => {
            this.favPlaylists.push(response.data)
          })
          .catch(error => console.log(error))
    })
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
    isPlaylistFavourite(playlistId) {
      return this.userData.favouritePlaylists.find(element => {
        return element.playlist.toString() === playlistId.toString()
      })
    },
    createPlayerQueueList(trackDto) {
      return {
        trackId: trackDto.id,
        title: trackDto.title,
        duration: trackDto.duration_sec,
        artist: trackDto.artistObj.first_name + " " + trackDto.artistObj.last_name,
        audioUrl: this.audioResourceBaseUrl + trackDto.id,
        imageUrl: "http://localhost:8104/api/media/image/" + trackDto.albumData.filename
      }
    },
    async playSelectedPlaylist(playlistId) {
      let token = await this.$auth_user.getTokenSilently()
      let plData = (await BackendService.getPlaylistData(playlistId, token)).data
      plData.tracks.forEach(track => {
        BackendService.getTrack(track.track, token)
            .then(res => {
              track.trackObj = res.data
              this.$root.$emit('pl-add-to-end-of-queue', this.createPlayerQueueList(res.data))
            })
      })
    },
    async addPlaylistToFavourites(playlistId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.addPlaylistToFav(playlistId, token)
      await this.$store.dispatch('user/getUserData', {
        accessToken: token
      })
      element.disabled = false
      element.classList.remove('opacity-75')
    },
    async removePlaylistFromFavourites(playlistId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.removePlaylistFromFav(playlistId, token)
      await this.$store.dispatch('user/getUserData', {
        accessToken: token
      })
      element.disabled = false
      element.classList.remove('opacity-75')
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
  max-width: 140px;
  a {
    svg {
      color: #2d3f50;
      border-radius: 5%;
      transition: .3s;
    }
    &:hover {
      svg {
        //color: white;
        color: #7d9aaf;
        background-color: #2d3f50;
      }
    }
  }
}

.plist-btns {
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