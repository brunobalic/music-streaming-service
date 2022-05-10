<template>
  <div class="user-body">
    <template v-if="albumData !== null">
      <div class="container-md mt-4 ps-5 pe-5">
        <div class="row g-0">
          <div class="col-md-4 m-4"
               style="max-width: 220px">
            <img style="max-width: 220px; border-radius: 2.5%"
                 v-bind:src="albumImgBaseUrl + albumData.filename"
                 alt="Album Cover">
          </div>
          <div class="col-md m-4 d-flex flex-column text-start">
            <h4 class="mb-1">
              <span class="text-white">{{ albumData.name }}</span>
            </h4>
            <p class="mt-1 mb-1">
              <small class="fst-italic">by: </small>
              <router-link v-bind:to="{ name:'u-artist', params: { id: albumData.artist_id } }"
                           class="mt-1 mb-1">
                <span class="h6">{{ albumData.artistObj.first_name }}</span>
              </router-link>
            </p>
            <p class="mt-1 mb-1">
              <span class="fst-italic">Record type: </span>
              <span class="fw-bold text-capitalize">{{ albumData.collection_type }}</span>
            </p>
            <p class="mt-1 mb-1">
              <span class="fst-italic">Released: </span>
              <span class="fw-bold">{{ dateFormatted }}</span>
            </p>
            <p class="mt-1 mb-1">
              <span class="fst-italic">Nb. of tracks: </span>
              <span class="fw-bold">{{ albumData.number_of_tracks }}</span>
            </p>
            <p class="mt-1 mb-auto">
              <span class="fst-italic">Duration: </span>
              <span class="fw-bold">{{ durationFormatted }}</span>
            </p>
            <div class="d-flex">
              <button class="me-2 btn-play" v-on:click="playCurrentAlbum()" style="color: white">
                <svg class="" width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                     stroke="currentColor" fill="none" stroke-linecap="round"
                     stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                  <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                  <path d="M7 4v16l13 -8z"></path>
                </svg>
              </button>
              <div class="vr me-2 mt-1"></div>
              <template v-if="!isAlbumFavourite">
                <button id="btn-album-fav"
                        class="favAlbum"
                        v-on:click="addAlbumToFavourite">
                  <svg class="" fill="none"
                       width="24" height="24" stroke-width="1" viewBox="0 0 24 24"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428m0 0a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                  </svg>
<!--                  <span>Add To Favourites</span>-->
                </button>
              </template>
              <template v-else>
                <button id="btn-album-unfav"
                        class="unfavAlbum"
                        v-on:click="removeAlbumFromFavourite">
                  <svg class="" fill="none"
                       width="24" height="24" stroke-width="1" viewBox="0 0 24 24"
                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                       xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                    <path d="M19.5 13.572l-7.5 7.428l-7.5 -7.428a5 5 0 1 1 7.5 -6.566a5 5 0 1 1 7.5 6.572"></path>
                    <path d="M12 7l-2 4l4 3l-2 4v3"></path>
                  </svg>
<!--                  <span>Remove From Favourites</span>-->
                </button>
              </template>
            </div>
          </div>
        </div>
        <tracks-list v-bind:tracks-list="albumData.tracks"></tracks-list>
        <hr>
      </div>
    </template>
  </div>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'
import BackendService from '@/services/regular-user/BackendService'
import TracksList from '@/components/regular-user/lists/TracksList'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: "Album",
  components: {
    TracksList
  },
  data() {
    return {
      albumId: this.$route.params.id,
      albumData: null,
    }
  },
  computed: {
    ...mapGetters({
      albumImgBaseUrl: 'imgAlbumBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl',
      userData: 'userData'
    }),
    isAlbumFavourite() {
      return this.userData.favouriteAlbums.find(element => element.album.toString() === this.albumId.toString())
    },
    dateFormatted() {
      const date = new Date(this.albumData.official_release_date)
      return date.toLocaleString('default', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    },
    durationFormatted() {
      let totalDurationSec = 0
      this.albumData.tracks.forEach(track => {
        totalDurationSec += track.duration_sec
      })
      const hour = Math.floor(totalDurationSec / 3600)
      let remainingSec = totalDurationSec % 3600
      const min = Math.floor((remainingSec / 60) + 1)
      const sec = remainingSec % 60
      //return `${hour}h, ${min}min, ${sec}sec`
      return `${hour === 0 ? '' : hour + ':'}${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`
    }
  },
  beforeCreate() {
    console.log("before create album component")
  },
  created() {
    console.log("created album component")
  },
  beforeMount() {
    console.log("before mount album component")
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.albumData = (await BackendService.getAlbumData(this.albumId, token)).data
  },
  filters: {
  },
  methods: {
    // todo ovu metodu ubaciti u poseban file jer se koristi u vise komponenti
    createPlayerQueueList() {
      let queueList = []
      const fromSong = 0
      this.albumData.tracks.slice(fromSong).forEach(track => {
        const obj = {
          trackId: track.id,
          title: track.title,
          duration: track.duration_sec,
          artist: track.artistObj.first_name + " " + track.artistObj.last_name,
          audioUrl: this.audioResourceBaseUrl + track.id,
          imageUrl: this.albumImgBaseUrl + track.albumData.filename
        }
        queueList.push(obj)
      })
      return queueList
    },
    playCurrentAlbum() {
      this.$root.$emit('pl-set-queue-and-play', this.createPlayerQueueList())
    },
    async addAlbumToFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')
      element.classList.add('noHover')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.addAlbumToFav(this.albumId, token)
      await this.$store.dispatch('user/getUserData', {
        accessToken: token
      })
      element.disabled = false
      element.classList.remove('opacity-75')
      element.classList.remove('noHover')
    },
    async removeAlbumFromFavourite(event) {
      let element = document.getElementById(event.currentTarget.id)
      element.disabled = true
      element.classList.add('opacity-75')
      element.classList.add('noHover')

      let token = await this.$auth_user.getTokenSilently()
      await BackendService.removeAlbumFromFav(this.albumId, token)
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

.btn-play {
  background-color: white;
  //border: 1px solid white;
  //padding: 7px 15px 7px 15px;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
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

.favAlbum {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  border: #2d3f50 solid 1px;
  transition: .3s;
  svg {
    //color: #2d3f50;
    color: #8f8f9a;
    transition: .3s;
  }
  span {
    color: #8f8f9a;
    transition: .3s;
  }
  &:not(.noHover):hover {
    svg {
      opacity: .75;
      //color: #c63a5d;
      //fill: #c63a5d;
    }
    span {
      color: white;
    }
  }
}

.unfavAlbum {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  border: #2d3f50 solid 1px;
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
  &:not(.noHover):hover {
    svg {
      opacity: .75;
      //color: #c63a5d;
      //fill: #c63a5d;
    }
    span {
      color: white;
    }
  }
}

//.track-name {
//  a {
//    &:hover {
//      cursor: pointer;
//    }
//  }
//  .tmp1 {
//    transition: .3s ease;
//    //display: none;
//    position: absolute; // oba elementa na istom mjestu
//    //z-index: -1;
//    opacity: 0;
//  }
//  .move {
//    transition: .3s ease;
//    position: absolute; // oba elementa na istom mjestu
//    a {
//      &:hover {
//        cursor: pointer;
//      }
//    }
//  }
//  &:hover {
//    .tmp1 {
//      //transition: 1.3s;
//      //display: inline-flex;
//      //transition-delay: 0.1s;
//      //float: left;
//      opacity: 100%;
//    }
//    .move {
//      //transition: .3s ease;
//      transform: translateX(25px);
//    }
//  }
//}
//.list-group {
//  .list-group-item {
//    color: white;
//    background-color: transparent;
//    border-color: rgb(46, 43, 60);
//    text-align: left;
//    padding-top: 10px;
//    padding-bottom: 10px;
//    .list-index {
//      flex: 0 0 auto;
//      width: 50px;
//    }
//    .list-fav {
//      flex: 0 0 auto;
//      width: 55px;
//    }
//    .fav svg {
//      color: #8f8f9a;
//      //height: 80%;
//      height: 1.3rem;
//      width: auto;
//      transition: .2s;
//    }
//    .fav svg:hover {
//      color: #c63a5d;
//      fill: #c63a5d;
//    }
//    .unFav svg {
//      color: #c63a5d;
//      fill: #c63a5d;
//      height: 1.3rem;
//      width: auto;
//      transition: .2s;
//    }
//    .unFav svg:hover {
//      fill: none;
//      color: #8f8f9a;
//    }
//  }
//}
//.fav-main-content {
//  transition: .3s;
//  svg {
//    color: #2d3f50;
//    transition: .3s;
//  }
//  span {
//    color: #8f8f9a;
//    transition: .3s;
//  }
//  &:hover {
//    svg {
//      color: #c63a5d;
//      fill: #c63a5d;
//    }
//    span {
//      color: white;
//    }
//  }
//}
//.unFav-main-content {
//  transition: .3s;
//  svg {
//    color: #c63a5d;
//    fill: #c63a5d;
//    transition: .3s;
//  }
//  span {
//    color: #8f8f9a;
//    transition: .3s;
//  }
//  &:hover {
//    svg {
//      fill: none;
//      color: #2d3f50;
//    }
//    span {
//      color: white;
//    }
//  }
//}
//.play-album {
//  transition: .3s;
//  background-color: transparent;
//  border: 1px solid white;
//  padding: 7px 15px 7px 15px;
//  border-radius: 28px;
//  svg {
//    transition: .3s;
//    color: white;
//    //fill: transparent;
//    fill: white;
//  }
//  span {
//    transition: .3s;
//    color: #8f8f9a;
//  }
//  &:hover {
//    background-color: white;
//    svg {
//      transition: .3s;
//      color: #2d3f50;
//      fill: #2d3f50;
//    }
//    span {
//      //color: white;
//    }
//  }
//}
//button {
//  border: none;
//  background: transparent;
//  padding: 0;
//}

</style>