<template>
  <div class="user-body">
    <add-to-playlist-modal ref="modalName2"></add-to-playlist-modal>
    <template v-if="playlistData">
      <div class="container-md mt-4 ps-5 pe-5">
        <div class="row">
          <div class="col-md-3 m-4 text-start" style="max-width: 200px">
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-playlist" width="180" height="180" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <circle cx="14" cy="17" r="3"></circle>
              <path d="M17 17v-13h4"></path>
              <path d="M13 5h-10"></path>
              <line x1="3" y1="9" x2="13" y2="9"></line>
              <path d="M9 13h-6"></path>
            </svg>
          </div>
          <div class="col-md m-4 ms-1 mb-5 d-flex flex-column text-start">
            <h5 class="m-1">
              <span class="text-white">{{ playlistData.name }}</span>
            </h5>
            <p class="mt-1 mb-1">
              <span class="fst-italic">Created at: </span>
              <span class="fw-bold">{{ dateFormatted }}</span>
            </p>
            <p class="mt-1 mb-auto">
              <span class="fst-italic">Nb of Tracks: </span>
              <span class="fw-bold">{{ playlistData.number_of_tracks }}</span>
            </p>
            <div class="d-flex flex-row pl-btns">
              <button class="play"
                      v-on:click="playCurrentPlaylist()">
                <svg class=""
                     width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                     stroke="currentColor" fill="none" stroke-linecap="round"
                     stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                  <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                  <path d="M7 4v16l13 -8z"></path>
                </svg>
              </button>
              <div class="vr ms-2 me-2 mt-1 mb-1"></div>
              <button v-if="!isPlaylistFavourite()"
                      v-bind:id="'btn-fav-' + playlistId"
                      v-on:click="addPlaylistToFavourite($event)"
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
                      v-bind:id="'btn-unfav-' + playlistId"
                      v-on:click="removePlaylistFromFavourite($event)"
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
              <button v-if="userOwnsThisPlaylist"
                      v-on:click="deletePlaylist"
                      class="ms-auto btn-delete ps-2 pe-2">
                {{ btnLblDeletePlaylist }}
              </button>
            </div>
          </div>
        </div>

        <ol class="list-group list-group-flush">
          <li class="list-group-item top-row noHover">
            <div class="row">
              <div class="col-md-1 ps-3 pe-1 part1">
                <span>#</span>
              </div>
              <div class="col-md-1 ps-1 pe-1 part2">
                <span></span>
              </div>
              <div class="col-md ps-1 ps-1 part3">
                <span>Track</span>
              </div>
              <div class="col-md-1 ps-1 pe-3 text-end part4">
                <span>Duration</span>
              </div>
            </div>
          </li>
          <template v-for="(track, index) in playlistData.tracks">
            <li v-if="track.trackObj"
                v-bind:key="track.track"
                class="list-group-item">
              <div class="row">
                <div class="col-md-1 ps-3 pe-1 part1">
                  <span>{{ index + 1 }}</span>
                </div>
                <div class="col-md-1 ps-1 pe-1 part2">
                  <button v-if="!isTrackFavorite(track.track)"
                          v-bind:id="'btn-fav-' + track.track"
                          v-on:click="addTrackToFavourite(track.track, $event)"
                          class="btn-track fav">
                    <svg class=""
                         width="20" height="20" stroke-width="1" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                         xmlns="http://www.w3.org/2000/svg">
                      <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                      <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428m0 0a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                    </svg>
                  </button>
                  <button v-else
                          v-bind:id="'btn-unfav-' + track.id"
                          v-on:click="removeTrackFromFavourite(track.track, $event)"
                          class="btn-track unfav">
                    <svg class=""
                         width="20" height="20" stroke-width="1" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                         xmlns="http://www.w3.org/2000/svg">
                      <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                      <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                      <path d="M12 7l-2 4l4 3l-2 4v3"></path>
                    </svg>
                  </button>
                </div>
                <div class="col-md-3 ps-1 ps-1 part3">
                  <a v-on:click="playFromSelectedTrack(index)">
                    {{ track.trackObj.title }}
                  </a>
                </div>
                <div class="col-md ps-1 ps-1 part3">
                  <router-link v-bind:to="{ name: 'u-artist', params: { id: track.trackObj.artistObj.id} }">
                    <span class="artist-link">{{ track.trackObj.artistObj.first_name }}</span>
                  </router-link>
                  <span> · </span>
                  <router-link v-bind:to="{ name: 'u-album', params: { id: track.trackObj.albumData.id} }">
                    <span class="fst-italic album-link">{{ track.trackObj.albumData.name }}</span>
                  </router-link>
                </div>
                <div class="col-md-1 ps-1 pe-3 text-end part4">
                  <span>{{ timeFormatted(track.trackObj.duration_sec) }}</span>
                </div>
              </div>
            </li>
          </template>
        </ol>
        <div v-if="playlistData.tracks.length === 0">
          <p class="m-2 text-start fst-italic text-muted">There are no songs in this playlist.</p>
        </div>
        <hr class="mt-1">
      </div>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '../../services/regular-user/BackendService'
import AddToPlaylistModal from '../../components/regular-user/AddToPlaylistModal'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: "Playlist",
  components: {
    AddToPlaylistModal
  },
  data() {
    return {
      playlistId: this.$route.params.id,
      playlistData: null,
      playlistTracksData: null,
      btnLblDeletePlaylist: 'Delete this Playlist',
      deleteBtnClickCount: 0
    }
  },
  computed: {
    ...mapGetters({
      userData: 'userData',
      usersPlaylists: 'usersPlaylists',
      imgAlbumBaseUrl: 'imgAlbumBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl'
    }),
    dateFormatted() {
      const date = new Date(String(this.playlistData.publish_date))
      return date.toLocaleString('default', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    },
    userOwnsThisPlaylist() {
      return this.usersPlaylists.find(el => el.id.toString() === this.playlistId.toString())
    }
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    BackendService.getPlaylistData(this.playlistId, token)
        .then(res => {
          this.playlistData = res.data
          this.playlistData.tracks.forEach(track => {
            BackendService.getTrack(track.track, token)
                .then(res => {
                  track.trackObj = res.data
                })
                .catch(error => {
                  console.log(error)
                })
          })
        })
        .catch(error => {
          console.log('error', error)
          this.$router.push({ name: 'user-base' })
        })
  },
  methods: {
    timeFormatted(timeSec) {
      let min = Math.floor(timeSec / 60)
      let sec = timeSec % 60
      return (min + ':' + String(sec).padStart(2, '0'))
    },
    createPlayerQueueList() {
      let queueList = []
      this.playlistData.tracks.forEach(track => {
        const t = track.trackObj
        queueList.push({
          trackId: t.id,
          title: t.title,
          duration: t.duration_sec,
          artist: t.artistObj.first_name + " " + t.artistObj.last_name,
          audioUrl: this.audioResourceBaseUrl + t.id,
          imageUrl: this.imgAlbumBaseUrl + t.albumData.filename
        })
      })
      return queueList
    },
    playCurrentPlaylist() {
      this.$root.$emit('pl-set-queue-and-play', this.createPlayerQueueList())
    },
    playFromSelectedTrack(index) {
      this.$root.$emit('pl-set-queue-and-play-from-index', this.createPlayerQueueList(), index)
    },
    async deletePlaylist() {
      if (this.deleteBtnClickCount === 0) {
        this.btnLblDeletePlaylist = 'Confirm Delete'
        this.deleteBtnClickCount++
        setTimeout(() => {
          this.btnLblDeletePlaylist = 'Delete This Playlist'
          this.deleteBtnClickCount = 0
        }, 5000)
      } else {
        let token = await this.$auth_user.getTokenSilently()
        BackendService.deletePlaylist(this.playlistId, token)
            .then(res => {
              console.log(res)
              this.$store.dispatch('user/getUsersPlaylists', {
                accessToken: token
              })
              this.$router.push({ name: 'user-base' })
            })
            .catch(error => {
              console.log(error)
            })
      }
    },
    isPlaylistFavourite() {
      return this.userData.favouritePlaylists.find(element => element.playlist.toString() === this.playlistId.toString())
    },
    isTrackFavorite(trackId) {
      return this.userData.favouriteTracks.find(element => element.track === trackId)
    },
    async addPlaylistToFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.addPlaylistToFav(this.playlistId, token)
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
    async removePlaylistFromFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true // disable button while request is processing
      element.classList.add('opacity-75')

      let token = await this.$auth_user.getTokenSilently()
      BackendService.removePlaylistFromFav(this.playlistId, token)
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
    async addTrackToFavourite(trackId) {
      let token = await this.$auth_user.getTokenSilently()
      BackendService.addTrackToFav(trackId, token)
          .then(res => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
          })
          .catch(error => console.log(error))
    },
    async removeTrackFromFavourite(trackId) {
      let token = await this.$auth_user.getTokenSilently()
      BackendService.removeTrackFromFav(trackId, token)
          .then(res => {
            console.log(res)
            this.$store.dispatch('user/getUserData', {
              accessToken: token
            })
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

.pl-btns {
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
    &.btn-delete {
      width: auto;
      color: silver;
      background: #2d3f50;
      border-radius: 24px;
      &:hover {
        background: firebrick;
      }
    }
  }
}

.btn-track {
  &.fav {
    svg {
      transition: .5s;
      color: #8f8f9a;
      //fill: none; // ne radi s transition -> koristi transparent
      fill: transparent;
    }
    &:not(.noHover):hover {
      svg {
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
    &:not(.noHover):hover {
      svg {
        opacity: .75;
      }
    }
  }
}

.list-group-item {
  transition: .15s ease-in-out;
  &:not(.noHover):hover {
    background-color: #2d3f50;
  }
  &.top-row {
    color: #8f8f9a;
  }
  color: white;
  .part1 {
    color: #8f8f9a;
    max-width: 3rem;
  }
  .part2 {
    max-width: 3rem;
  }
  .part3 {
    a {
      color: white;
      text-decoration: none;
      cursor: pointer;
      .artist-link {
        color: #8f8f9a;
        font-weight: 500;
      }
      .album-link {
        color: #8f8f9a;
        font-size: .9rem;
      }
    }
  }
  .part4 {
    color: #8f8f9a;
    max-width: 4rem;
  }
}

</style>