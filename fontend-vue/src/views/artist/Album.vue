<template>
  <div class="artist-body">
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
import { createNamespacedHelpers } from 'vuex'
import TracksList from '@/components/artist/lists/TracksList'

const { mapGetters } = createNamespacedHelpers('artist')

export default {
  name: 'Album',
  components: {TracksList},
  data() {
    return {
      albumId: this.$route.params.id
    }
  },
  computed: {
    ...mapGetters({
      albumImgBaseUrl: 'albumImgBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl',
      getAlbumById: 'albumById'
    }),
    albumData() {
      return this.getAlbumById({
        id: this.albumId
      })
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
  methods: {
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

</style>