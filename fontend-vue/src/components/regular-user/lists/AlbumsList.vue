<template>
  <div>
    <template v-if="albumsList.length > 0">
      <h6 class="m-0 ps-3 text-start text-white">{{ heading }}</h6>
      <ol class="list-group list-group-flush">
        <li v-for="(album, index) in albumsList"
            v-bind:key="album.id"
            class="list-group-item">
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
              <template v-if="showArtistName">
                <router-link v-bind:to="{ name: 'u-artist', params: { id: album.artistObj.id } }">
                  <span class="h6 artist-link">{{ album.artistObj.first_name }}</span>
                </router-link>
              </template>
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
                        v-on:click="playSelectedAlbum(index)">
                  <svg class=""
                       width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                       stroke="currentColor" fill="none" stroke-linecap="round"
                       stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M7 4v16l13 -8z"></path>
                  </svg>
                </button>
                <div class="vr ms-2 me-2 mt-1 mb-1 text-info"></div>
                <button v-if="!isAlbumFavourite(album.id)"
                        v-bind:id="'btn-fav-' + albumType + '-' + album.id"
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
                        v-bind:id="'btn-unfav-' + albumType + '-' + album.id"
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
      <hr>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'AlbumsList',
  props: {
    albumsList: Array,
    albumType: {
      type: String,
      required: false,
      default: ''
    },
    showArtistName: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  computed: {
    ...mapGetters({
      albumImgBaseUrl: 'imgAlbumBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl',
      userData: 'userData'
    }),
    heading: function () {
      if (this.albumType === 'album') return 'Albums'
      if (this.albumType === 'ep') return 'EPs'
      if (this.albumType === 'single') return 'Singles'
      return this.albumType
    }
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
    createPlayerQueueList(index) {
      let queueList = []
      this.albumsList[index].tracks.forEach(track => {
        queueList.push({
          trackId: track.id,
          title: track.title,
          duration: track.duration_sec,
          artist: track.artistObj.first_name + " " + track.artistObj.last_name,
          audioUrl: this.audioResourceBaseUrl + track.id,
          //imageUrl: this.albumImgBaseUrl + track.album_id + '.jpg'
        })
      })
      return queueList
    },
    playSelectedAlbum(index) {
      this.$root.$emit('pl-set-queue-and-play', this.createPlayerQueueList(index))
    },
    async addAlbumToFavourite(albumId, event) {

      // VAZNO !!!
      // event.target         -> ovo je element na koji se kliknulo
      // event.currentTarget  -> ovo je element nad kojim je trigeran event
      // * Note: The value of event.currentTarget is ONLY available
      //         while the event is being handled.
      // * https://developer.mozilla.org/en-US/docs/Web/API/Event/currentTarget

      // U mom slucaju imao sam button koji je v-on:click
      // pozivao metodu, unutar te metode pokusao sam procitat
      // id tog elementa tj. button-a
      // medutim nisam mogao, nije ga bilo
      // -> sto se zapravo dogadalo
      // kada bi kliknuo na button, zapravo bi kliknuo na svg (konkretnije na svg path)
      // i event.target je bio svg path, a posto taj svg-om nije imao svoj id
      // nisam odmah skuzio da ne hvata button
      // ...tribalo mi je jako dugo vremena to to skuzim...
      // e sad kad sam to skuzio kliknuo sam na button ali na rub button-a
      // gdje nije bilo svg i onda je event.target bio button element
      // * u ovom trenutku znam uzrok problema
      // -> rjesenje
      // koristiti event.currentTarget, to je element nad kojim je trigeran event

      // console.log('event', event)
      // console.log('target', event.target)
      // console.log('current target', event.currentTarget)
      // console.log('current target ID', event.currentTarget.id)

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
    },
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
      border: solid #2d3f50 1px;
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
      border: solid #2d3f50 1px;
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