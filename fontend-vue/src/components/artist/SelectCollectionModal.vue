<template>
  <transition name="fade">
    <!-- Scrollable modal -->
    <div v-if="show">
      <div id="a-modal1" aria-labelledby="a-modal1" aria-modal="true" class="modal fade show" role="dialog"
           tabindex="-1">
        <div class="modal-dialog modal-dialog-scrollable">

          <div class="modal-content h-75" style="transform: translateX(112px);">
            <div class="modal-header">
              <h5 id="a-modal1-title" class="modal-title">Select Collection</h5>
              <button aria-label="Close" class="btn-close" data-bs-dismiss="modal" type="button"></button>
            </div>
            <div id="style-3" class="modal-body">
              <ul>
                <li v-for="item in artistCollections" v-bind:key="item.id" class="list-group-item">
                  <div class="container">
                    <div class="row">
                      <div class="col-4">
                        <img alt="album artwork" style="max-width: inherit; border-radius: 5px"
                             v-bind:src="'http://localhost:8104/api/media/image/' + item.filename">
                      </div>
                      <div class="col-8">
                        <span class="item-title">{{ item.name }}</span>
                        <br>
                        <span class="item-type">{{ item.collection_type }}</span>
                        <br>
                        <span class="item-date">{{ item.official_release_date }}</span>
                        <br>
                        <button class="btn-sm btn-light rounded-pill" v-on:click="selectCollection(item)">Select
                        </button>
                      </div>
                    </div>
                  </div>
                  <hr class="my-hr1">
                </li>
              </ul>
            </div>
            <div class="modal-footer">
              <button class="btn btn-secondary" data-bs-dismiss="modal" type="button" v-on:click="closeModal">Dismiss
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'

const {mapState} = createNamespacedHelpers('artist')

export default {
  name: 'SelectCollectionModal',
  data() {
    return {
      show: false
    }
  },
  computed: {
    ...mapState({
      artistCollections: state => state.artistAlbums
    })
  },
  mounted() {
    console.log(this.artistCollections, '**********')
  },
  methods: {
    openModal() {
      this.show = true
    },
    closeModal() {
      this.show = false
    },
    selectCollection(collectionData) {
      /** @namespace collectionData.id **/
      /** @namespace collectionData.name **/
      /** @namespace collectionData.collection_type **/
      /** @namespace collectionData.official_release_date **/

      console.log(collectionData, 'this is selected collection ID')
      const data = {
        id: collectionData.id,
        name: collectionData.name,
        type: collectionData.collection_type,
        date: collectionData.official_release_date
      }
      this.$emit('event-selectedColl', data)
      this.closeModal()
    }
  }
}
</script>

<style lang="scss" scoped>

#a-modal1 {
  display: block;
}

.my-hr1 {
  color: #2d3f50;
  opacity: 100%;
  margin-bottom: 0;
}

.modal-content {
  background-color: #181818;
  border: 1px solid #2d3f50;
  border-radius: 10px;

  .modal-header {
    border-color: #2d3f50;

    h5 {
      color: #8f8f9a;
    }

    button {
      color: #8f8f9a;
    }
  }

  .modal-body {
    color: white;

    ul {
      padding: 0;
      margin: 0;
    }

    .list-group-item {
      border-left: 0;
      border-top: 0;
      border-right: 0;
      background-color: transparent;
      border-bottom: 1px #2d3f50;
    }

    .icon {
      color: #8f8f9a;
    }

    .item-title {
      font-size: 1.1rem;
      color: white;
      font-weight: bold;
    }

    .item-type {
      color: white;
      font-style: italic;
      font-weight: normal;
    }

    .item-date {
      color: white;
      font-style: italic;
      font-weight: normal;
    }
  }

  .modal-footer {
    border-color: #2d3f50;

    button {
      border-radius: 18px;
      color: black;
      background-color: #e0e0e0;
    }
  }
}

// SCROLLBAR
#style-3::-webkit-scrollbar-track {
  //-webkit-box-shadow: inset 0 0 4px rgb(143, 143, 154);
  background-color: rgba(255, 255, 255, 0);
}

#style-3::-webkit-scrollbar {
  width: 4px;
  background-color: #FFFFFF00;
}

#style-3::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: #8f8f9a;
}

</style>