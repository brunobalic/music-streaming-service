<template>
  <div>
    <loading-screen-albums-list v-if="loading" v-bind:repeat="4"></loading-screen-albums-list>
    <template v-if="artistAlbums !== null">
      <albums-list v-bind:album-type="albumTypeNames.album"
                   v-bind:albums-list="albumsAlbums">
      </albums-list>
      <albums-list v-bind:album-type="albumTypeNames.ep"
                   v-bind:albums-list="albumsEPs">
      </albums-list>
      <albums-list v-bind:album-type="albumTypeNames.single"
                   v-bind:albums-list="albumsSingles">
      </albums-list>
    </template>
  </div>
</template>

<script>
import BackendService from '@/services/regular-user/BackendService'
import AlbumsList from './lists/AlbumsList'
import LoadingScreenAlbumsList from '@/components/loadingScreens/LoadingScreenAlbumsList'

export default {
  name: 'DiscographyTab',
  components: {
    AlbumsList,
    LoadingScreenAlbumsList
  },
  props: {
    artistId: String
  },
  data() {
    return {
      artistAlbums: null,
      albumTypeNames: {
        album: 'album',
        ep: 'ep',
        single: 'single'
      },
      loading: false
    }
  },
  computed: {
    albumsAlbums: function () {
      return this.artistAlbums.filter(al => al.collection_type === 'album')
    },
    albumsEPs: function () {
      return this.artistAlbums.filter(al => al.collection_type === 'ep')
    },
    albumsSingles: function () {
      return this.artistAlbums.filter(al => al.collection_type === 'single')
    }
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.loading = true
    let result = (await BackendService.getAllAlbumsFromArtist(this.artistId, token)).data
    result.sort((a, b) => {
      const dateA = a.official_release_date
      const dateB = b.official_release_date
      return (dateA > dateB ? -1 : (dateB > dateA ? 1 : 0))
    })
    this.artistAlbums = result
    this.loading = false
  }
}
</script>

<style lang="scss" scoped>

</style>