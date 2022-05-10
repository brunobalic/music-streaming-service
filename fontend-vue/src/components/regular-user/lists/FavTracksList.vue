<template>
  <div>
    <tracks-list v-bind:tracks-list="favTracks"
                 v-bind:main-play-button="true"
                 v-bind:track-info="true">
    </tracks-list>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '../../../services/regular-user/BackendService'
import TracksList from '@/components/regular-user/lists/TracksList'

const { mapState } = createNamespacedHelpers('user')

export default {
  name: 'ListFavTracks',
  components: {
    TracksList
  },
  data() {
    return {
      favTracks: []
    }
  },
  computed: {
    ...mapState({
        imgAlbumBaseUrl: state => state.baseUrl_imgAlbum,
        imgArtistBaseUrl: state => state.baseUrl_imgArtist,
        userData: state => state.userData
    })
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.userData.favouriteTracks.forEach(element => {
      BackendService.getTrack(element.track, token)
          .then(response => {
            this.favTracks.push(response.data)
          })
          .catch(error => console.log(error))
    })
  }
}
</script>

<style lang="scss" scoped>

</style>