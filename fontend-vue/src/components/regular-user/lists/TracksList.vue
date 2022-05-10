<template>
  <div>
    <template v-if="tracksList.length > 0">
      <template v-if="mainPlayButton">
        <div class="d-flex align-items-center ms-3">
  <!--        <h5 class="ms-3 me-3 mt-1 mb-1 text-white">Tracks</h5>-->
          <h4 v-if="listTitle" class="ms-1 me-3 mt-1 mb-1 fw-bold">
            {{ listTitle }}
          </h4>
          <button class="btn-play" v-on:click="playTracklist()" style="color: white">
            <svg class="me-1" width="22" height="22" viewBox="0 0 24 24" stroke-width="1"
                 stroke="currentColor" fill="none" stroke-linecap="round"
                 stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <path d="M7 4v16l13 -8z"></path>
            </svg>
            <small>Play</small>
          </button>
        </div>
      </template>
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
        <li v-for="(track, index) in tracksList"
            v-bind:key="track.id"
            class="list-group-item">
          <div class="row">
            <div class="col-md-1 ps-3 pe-1 part1">
              <span>{{ index + 1 }}</span>
            </div>
            <div class="col-md-1 ps-1 pe-1 part2">
              <button v-if="!isTrackFavorite(track.id)"
                      v-bind:id="'btn-fav-' + track.id"
                      v-on:click="addTrackToFavourite(track.id, $event)"
                      class="fav">
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
                      v-on:click="removeTrackFromFavourite(track.id, $event)"
                      class="unfav">
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
            <div class="col-md ps-1 pe-1 part3">
              <a v-on:click="playFromSelectedTrack(index)">
                {{ track.title }}
              </a>
            </div>
            <div class="col-md ps-1 pe-1 part3">
              <template v-if="trackInfo">
                <router-link v-bind:to="{ name: 'u-artist', params: { id: track.artistObj.id} }">
                  <span class="artist-link">{{ track.artistObj.first_name }}</span>
                </router-link>
                <span> · </span>
                <router-link v-bind:to="{ name: 'u-album', params: { id: track.albumData.id} }">
                  <span class="fst-italic album-link">{{ track.albumData.name }}</span>
                </router-link>
              </template>
            </div>
            <div class="col-md-1 ps-1 pe-3 text-end part4">
              <span>{{ timeFormatted(track.duration_sec) }}</span>
            </div>
          </div>
        </li>
      </ol>
    </template>
    <template v-else>
      <div class="d-flex flex-column ms-3 text-start">
        <h5 class="ms-3 me-3 mt-1 mb-1 text-white">Tracks</h5>
        <p class="ms-3 text-muted fst-italic">There are no songs in this collection yet.</p>
      </div>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '../../../services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'TracksList',
  props: {
    //tracksList: Array,
    tracksList: {
      type: Array,
      required: true
    },
    mainPlayButton: {
      type: Boolean,
      default: false
    },
    listTitle: {
      type: String,
      required: false
    },
    trackInfo: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    ...mapGetters({
      userData: 'userData',
      albumImgBaseUrl: 'imgAlbumBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl'
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
      const fromSong = 0
      this.tracksList.slice(fromSong).forEach(track => {
        const obj = {
          trackId: track.id,
          title: track.title,
          duration: track.duration_sec,
          artist: track.artistObj.first_name + " " + track.artistObj.last_name,
          audioUrl: this.audioResourceBaseUrl + track.id,
          //imageUrl: this.albumImgBaseUrl + // nemam ovo
        }
        queueList.push(obj)
      })
      return queueList
    },
    playTracklist() {
      this.$root.$emit('pl-set-queue-and-play', this.createPlayerQueueList())
    },
    playFromSelectedTrack(trackIndex) {
      console.log('will play tracks from this index', trackIndex)
      this.$root.$emit('pl-set-queue-and-play-from-index', this.createPlayerQueueList(), trackIndex)
    },
    isTrackFavorite(trackId) {
      return this.userData.favouriteTracks.find(element => {
        return element.track.toString() === trackId.toString()
      })
    },
    async addTrackToFavourite(trackId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')
      element.classList.add('noHover')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.addTrackToFav(trackId, token)
      await this.$store.dispatch('user/getUserData', {
        accessToken: token
      })
      element.disabled = false
      element.classList.remove('opacity-75')
      element.classList.remove('noHover')
    },
    async removeTrackFromFavourite(trackId, event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')
      element.classList.add('noHover')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.removeTrackFromFav(trackId, token)
      await this.$store.dispatch('user/getUserData', {
        accessToken: token
      })
      element.disabled = false
      element.classList.remove('opacity-75')
      element.classList.remove('noHover')
    }
  }
}
</script>

<style lang="scss" scoped>

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

button {
  &.btn-play {
    background-color: white;
    border: 1px solid white;
    padding: 7px 15px 7px 15px;
    border-radius: 28px;
    transition: .3s;
    svg {
      color: #2d3f50;
      fill: #2d3f50;
    }
    small {
      color: #2d3f50;
    }
    &:hover {
      background-color: #c63a5d;
      border-color: #c63a5d;
      svg {
        color: white;
        fill: white;
      }
      small {
        color: white;
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

</style>