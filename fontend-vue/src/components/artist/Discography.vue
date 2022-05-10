<template>
  <div>
    <div class="container-md mt-4 ps-5 pe-5">
      <h5 class="ms-1 text-start text-white">Discography</h5>
      <ul class="nav nav-pills">
        <li v-for="el in albumTypes"
            v-bind:key="el.value"
            class="nav-item">
          <a class="nav-link h6 m-0 p-2"
             v-bind:class="selectedTab === el.value ? 'active' : ''"
             v-on:click="selectedTab = el.value">
            {{ el.name }}
          </a>
        </li>
      </ul>
      <hr class="mt-0 mb-3">
      <template v-for="el in albumTypes">
        <albums-list v-show="selectedTab === el.value"
                     v-bind="constructProps(el)"
                     v-bind:key="el.value">
        </albums-list>
      </template>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import AlbumsList from '@/components/artist/lists/AlbumsList'

const { mapGetters } = createNamespacedHelpers('artist')

export default {
  name: 'Discography',
  components: {
    AlbumsList
  },
  data() {
    return {
      albumTypes: {
        all: {
          value: '_all',
          name: 'All'
        },
        album: {
          value: 'album',
          name: 'Album'
        },
        single: {
          value: 'single',
          name: 'Single'
        },
        ep: {
          value: 'ep',
          name: 'EP'
        },
        lp: {
          value: 'lp',
          name: 'LP'
        },
        live: {
          value: 'live',
          name: 'Live'
        }
      },
      selectedTab: '_all' // show all
    }
  },
  computed: {
    ...mapGetters({
      discographyList: 'allAlbums',
      imgAlbumBaseUrl: 'albumImgBaseUrl'
    })
  },
  methods: {
    constructProps(albumType) {
      console.log('---- constructing props')
      if (albumType.value === this.albumTypes.all.value) {
        return {
          albumsList: this.discographyList,
          albumType: albumType.value,
          listTitle: albumType.name
        }
      }
      return {
        albumsList: this.discographyList.filter(el => el.collection_type === albumType.value),
        albumType: albumType.value,
        listTitle: albumType.name
      }
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