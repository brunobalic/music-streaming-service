<template>
  <div class="user-body">
    <div v-if="loaded" class="container-md mt-4 ps-5 pe-5">
      <h5 class="ms-1 text-start text-white">Statistics</h5>

      <!-- total po danu u zadnjih 30 dana -->
      <listens-per-day-chart v-bind:stats="stats"></listens-per-day-chart>

      <!-- najslusanije pjesme u zadnjih 30 dana -->
<!--      <top-tracks-chart v-bind:stats="stats"></top-tracks-chart>-->

      <!-- individualni prikaz po pojedinacnj pjesmi ? -->

    </div>
  </div>
</template>

<script>
import BackendService from '@/services/artist/BackendService'
import ListensPerDayChart from '@/components/artist/charts/ListensPerDayChart'
// import TopTracksChart from '@/components/artist/charts/TopTracksChart'

export default {
  name: "Statistics",
  components: {
    ListensPerDayChart,
    // TopTracksChart
  },
  data() {
    return {
      loaded: false,
      stats: undefined
    }
  },
  async mounted() {
    let token = await this.$auth_artist.getTokenSilently()
    this.stats = (await BackendService.getPlayStats(token)).data
    this.loaded = true
  }
}
</script>

<style scoped>

</style>