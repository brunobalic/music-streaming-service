<template>
  <div>
    <template v-if="playlistsList.length > 0">
      <div class="d-flex">
        <h5 class="text-white">Playlists</h5>
      </div>
      <ol class="list-group list-group-flush">
        <li v-for="pl in playlistsList"
            v-bind:key="pl.id"
            class="list-group-item ps-0">
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
            <div class="col-md d-flex flex-column justify-content-start">
              <router-link v-bind:to="{ name: 'u-playlist', params: { id: pl.id } }">
                <span class="h5">{{ pl.name }}</span>
              </router-link>
              <p class="mt-3 mb-1">
                <span class="fst-italic">Created: </span>
                <span class="fw-bold">{{ dateFormatted(pl.publish_date) }}</span>
              </p>
              <p class="mt-1 mb-1">
                <span class="fst-italic">Nb. of tracks: </span>
                <span class="fw-bold">{{ pl.number_of_tracks }}</span>
              </p>
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

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'SearchPlaylistsList',
  props: {
    playlistsList: {
      type: Array,
      required: true
    }
  },
  computed: {
    ...mapGetters({
      imgAlbumBaseUrl: 'imgAlbumBaseUrl',
      imgArtistBaseUrl: 'imgArtistBaseUrl',
      userData: 'userData'
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
      //color: #2d3f50;
      color: #7d9aaf;
      background-color: #2d3f50;
      border-radius: 5%;
      transition: .3s;
    }
    &:hover {
      svg {
        color: white;
        //color: #7d9aaf;
        background-color: #2d3f50;
      }
    }
  }
}

</style>