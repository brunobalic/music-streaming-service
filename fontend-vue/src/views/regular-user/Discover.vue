<template>
  <div class="user-body">
    <div class="container-md mt-4 ps-5 pe-5">
      <tracks-list v-bind:tracks-list="trackList"
                   v-bind:main-play-button="true"
                   v-bind:track-info="true"
                   v-bind:list-title="'Top 100 Globally'"
      ></tracks-list>
    </div>
  </div>
</template>

<script>
import BackendService from '@/services/regular-user/BackendService'
import TracksList from '@/components/regular-user/lists/TracksList'

export default {
  name: "Discover",
  components: {
    TracksList
  },
  data() {
    return {
      loading: false,
      trackList: []
    }
  },
  async mounted() {
    this.loading = true
    let token = await this.$auth_user.getTokenSilently()
    BackendService.getTop100Global(token)
        .then(res => {
          this.trackList = res.data
          this.loading = false
        })
  }
}
</script>

<style scoped>

</style>