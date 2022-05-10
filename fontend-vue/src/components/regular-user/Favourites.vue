<template>
  <div>
    <div class="container-md mt-4 ps-5 pe-5">
      <h5 class="ms-1 text-start text-white">Favourites</h5>
      <ul class="nav nav-pills">
        <li class="nav-item">
          <a class="nav-link h6 m-0 p-2"
             v-bind:class="currentTabComponentName === componentsNames.favTracks ? 'active' : ''"
             v-on:click="currentTabComponentName = componentsNames.favTracks">
            Tracks
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link h6 m-0 p-2"
             v-bind:class="currentTabComponentName === componentsNames.favAlbums ? 'active' : ''"
             v-on:click="currentTabComponentName = componentsNames.favAlbums">
            Albums
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link h6 m-0 p-2"
             v-bind:class="currentTabComponentName === componentsNames.favArtists ? 'active' : ''"
             v-on:click="currentTabComponentName = componentsNames.favArtists">
            Artists
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link h6 m-0 p-2"
             v-bind:class="currentTabComponentName === componentsNames.favPlaylists ? 'active' : ''"
             v-on:click="currentTabComponentName = componentsNames.favPlaylists">
            Playlists
          </a>
        </li>
      </ul>
      <hr class="mt-0 mb-3">

      <keep-alive>
        <component v-bind:is="currentTabComponentName"></component>
      </keep-alive>

    </div>
  </div>
</template>

<script>
import ListFavAlbums from './lists/FavAlbumsList'
import ListFavTracks from './lists/FavTracksList'
import ListFavArtists from './lists/FavArtistsList'
import ListFavPlaylists from './lists/FavPlaylistsList'

export default {
  name: 'ListFavAll',
  components: {
    ListFavTracks,
    ListFavAlbums,
    ListFavArtists,
    ListFavPlaylists
  },
  data() {
    return {
      routeParamTabName: this.$route.params.tab,
      componentsNames: {
        favTracks: ListFavTracks.name,
        favAlbums: ListFavAlbums.name,
        favArtists: ListFavArtists.name,
        favPlaylists: ListFavPlaylists.name
      },
      currentTabComponentName: ListFavTracks.name, // default tab
    }
  },
  mounted() {
    if (this.routeParamTabName) {
      if (this.routeParamTabName === 'tracks') this.currentTabComponentName = this.componentsNames.favTracks
      if (this.routeParamTabName === 'albums') this.currentTabComponentName = this.componentsNames.favAlbums
      if (this.routeParamTabName === 'artists') this.currentTabComponentName = this.componentsNames.favArtists
      if (this.routeParamTabName === 'playlists') this.currentTabComponentName = this.componentsNames.favPlaylists
    }
  }
}
</script>

<style lang="scss" scoped>

.nav-pills {
  .nav-link {
    color: silver;
    //border-radius: 0;
    //border-top: solid #2d3f50 1px;
    &:hover {
      color: white;
    }
    &.active {
      color: white;
      background: transparent;
      border-radius: 0;
      border-bottom: solid #c63a5d .2rem;
      transition: 0s; // da maknem od bootstrap-a
    }
  }
}

</style>