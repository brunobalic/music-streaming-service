<template>
  <div>
    <template v-if="artistsList.length > 0">
      <div class="d-flex">
        <h5 class="text-white">Artists</h5>
      </div>
      <div class="row justify-content-start">
        <div v-for="artist in artistsList"
             v-bind:key="artist.id"
             class="col-md-2" style="min-width: calc(140px)">
          <router-link v-bind:to="{ name: 'u-artist', params: { id: artist.id } }">
            <img class="artist-img-link" alt="Image not found" style="max-width: 140px; border-radius: 50%"
                 v-bind:src="imgArtistBaseUrl + artist.pictureFilename" @error="setAltImgArtist">
          </router-link>
          <h5 class="mt-2">
            <router-link v-bind:to="{ name: 'u-artist', params: { id: artist.id } }">
              <span>{{ artist.first_name }}</span>
            </router-link>
          </h5>
        </div>
      </div>
      <slot></slot>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'SearchArtistsList',
  props: {
    artistsList: {
      type: Array,
      required: true
    }
  },
  computed: {
    ...mapGetters({
      imgArtistBaseUrl: 'imgArtistBaseUrl',
      userData: 'userData'
    })
  },
  methods: {
    setAltImgArtist(event) {
      event.target.src = require('@/assets/artist_logo.png')
    }
  }
}
</script>

<style lang="scss" scoped>

a {
  color: white;
  text-decoration: none;
  transition: .2s ease-in-out;
  &:hover {
    color: #c63a5d;
  }
  img {
    transition: .2s ease-in-out;
    &:hover {
      opacity: .75;
    }
  }
}

</style>