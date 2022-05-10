<template>
  <div>
    <template v-if="artistTopTracks !== null">
      <tracks-list v-bind:tracks-list="artistTopTracks" v-bind:main-play-button="true"></tracks-list>
    </template>
  </div>
</template>

<script>
import BackendService from '@/services/regular-user/BackendService'
import TracksList from './TracksList'

export default {
  name: 'TopTracksTab',
  components: {
    TracksList
  },
  props: {
    artistId: String
  },
  data() {
    return {
      artistTopTracks: []
    }
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    BackendService.getTop50TracksFromArtist(this.artistId, token)
        .then(res => {
          this.artistTopTracks = res.data
        })
  }
}
</script>

<style lang="scss" scoped>

</style>