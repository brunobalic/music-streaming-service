<template>
  <div>
    <template v-if="albumsList.length > 0">
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
              <router-link v-bind:to="{ name: 'a-album', params: { id: album.id } }">
                <span class="h5">{{ album.name }}</span>
              </router-link>
              <p v-if="albumType === '_all'" class="mt-1 mb-1">
                <span class="fst-italic">Record type: </span>
                <span class="fw-bold">{{ album.collection_type }}</span>
              </p>
              <p class="mt-1 mb-1">
                <span class="fst-italic">Released: </span>
                <span class="fw-bold">{{ dateFormatted(album.official_release_date) }}</span>
              </p>
              <p class="mt-1 mb-auto">
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
              </div>
            </div>
          </div>
        </li>
      </ol>
      <hr>
    </template>
    <template v-else>
      <p class="text-start text-muted fst-italic">
        There are no albums in this category.
      </p>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'

const { mapGetters } = createNamespacedHelpers('artist')

export default {
  name: 'A-AlbumsList',
  props: {
    albumsList: {
      type: Array,
      required: true
    },
    albumType: {
      type: String,
      required: true
    },
    listTitle: {
      type: String,
      required: false
    }
  },
  computed: {
    ...mapGetters({
      albumImgBaseUrl: 'albumImgBaseUrl',
      audioResourceBaseUrl: 'audioResourceBaseUrl'
    })
  },
  mounted() {
    this.albumsList.sort((el1, el2) => {
      if (el1.official_release_date < el2.official_release_date) return 1
      else return -1
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
  max-width: 196px;
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