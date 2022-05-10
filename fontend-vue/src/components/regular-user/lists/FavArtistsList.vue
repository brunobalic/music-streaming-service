<template>
  <div>
    <div class="row justify-content-center">
      <div v-for="artist in favArtists"
           v-bind:key="artist.id"
           class="col-md-2 p-2" style="min-width: calc(140px + 24px)">
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
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'ListFavArtists',
  data() {
    return {
      favArtists: []
    }
  },
  computed: {
    ...mapGetters({
      imgArtistBaseUrl: 'imgArtistBaseUrl',
      userData: 'userData'
    })
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.userData.favouriteArtists.forEach(element => {
      BackendService.getArtistData(element.artist, token)
          .then(response => {
            this.favArtists.push(response.data)
          })
          .catch(error => console.log(error))
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