<template>
  <transition name="fade">
    <div v-if="show">
      <div id="a-modal1" aria-labelledby="a-modal1" aria-modal="true" class="modal fade show" role="dialog"
           tabindex="-1">
        <div class="modal-dialog modal-dialog-scrollable" style="max-width: 700px">
          <div class="modal-content h-75" style="transform: translateX(112px);">
            <div class="modal-header">
              <h5 id="a-modal1-title" class="modal-title">Create New Collection</h5>
              <button aria-label="Close" class="btn-close" data-bs-dismiss="modal" type="button"></button>
            </div>

            <div id="style-3" class="modal-body">

              <div v-if="loading">
                <div class="spinner-border" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
              </div>

              <div v-else class="container">
                <div class="row justify-content-center">
                  <div class="col-12">
                    <div class="row g-3">

                      <div class="col-12">
                        <p style="color: #e0e0e0; float: left; margin-bottom: 0">Note: Fields marked with (*) are
                          optional.</p>
                      </div>

                      <div class="col-12 mb-3 input-group">
                        <span id="title-desc" class="input-group-text"
                              style="background-color: #e0e0e0; color: #23232d;">Title</span>
                        <input v-model="collectionName" aria-describedby="title-desc" class="form-control input-style"
                               maxlength="64" placeholder="Enter Title here..." required type="text">
                      </div>

                      <!--                      <label class="form-label" for="desc-desc">Description</label>-->
                      <div class="col-12 mb-3 input-group">
                        <span id="desc-desc" class="input-group-text"
                              style="background-color: #e0e0e0; color: #23232d;">*Description</span>
                        <textarea id="desc-textarea" v-model="collectionDescription"
                                  aria-describedby="desc-desc" class="form-control input-style"
                                  maxlength="255" name="desc" rows="4"
                                  placeholder="Tell us more about it..."></textarea>
                      </div>

                      <div class="col-12 mb-3 input-group">
                        <span id="type-desc" class="input-group-text"
                              style="background-color: #e0e0e0; color: #23232d;">Type</span>
                        <select id="type" v-model="selectedCollectionType" aria-describedby="type-desc"
                                aria-label="Default select example" class="form-select input-style">
                          <option disabled>--Choose here--</option>
                          <option v-for="type in collectionTypes" v-bind:key="type" v-bind:value="type">{{ type }}
                          </option>
                        </select>
                      </div>

                      <div class="col-12 mb-3 input-group">
                        <span id="releaseDate-desc" class="input-group-text"
                              style="background-color: #e0e0e0; color: #23232d;">Official Release Date</span>
                        <input v-model="releaseDate" aria-describedby="releaseDate-desc" aria-label="Username"
                               class="form-control input-style" placeholder="Enter date here" type="date">
                      </div>

                      <div class="col-12 mb-3 input-group">
                        <div class="input-group-text" style="background-color: #e0e0e0">
                          <input v-model="publishDateCheckbox" aria-label="Checkbox for following text input"
                                 class="form-check-input mt-0"
                                 type="checkbox" value="" v-on:change="helper">
                        </div>
                        <span id="publish-desc" class="input-group-text"
                              style="background-color: #e0e0e0; color: #23232d;">*Make public at:</span>
                        <input v-model="publishDate" aria-describedby="publish-desc" aria-label="Username"
                               class="form-control input-style" placeholder="Enter date here" type="date"
                               v-bind:disabled="!publishDateCheckbox">
                        <input v-model="publishTime" aria-describedby="publish-desc" aria-label="Username"
                               class="form-control input-style" placeholder="Enter time here" type="time"
                               v-bind:disabled="!publishDateCheckbox">
                      </div>

                      <div class="col-12 mb-3 input-group">
                        <input class="form-control input-style" required type="file"
                               v-on:change="handleArtworkUpload($event)">
                      </div>

                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer" style="justify-content: space-around">
              <button class="btn btn-secondary" data-bs-dismiss="modal" v-bind:disabled="loading"
                      v-on:click="closeModal">Dismiss
              </button>
              <button class="btn btn-light" v-bind:disabled="loading" v-on:click="createCollection">Create</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import BackendService from '../../services/artist/BackendService'

export default {
  name: 'CreateCollectionModal',
  data() {
    return {
      show: false,
      loading: false,
      // form data
      collectionName: '',
      collectionDescription: '',
      collectionTypes: [
        'Album',
        'Single',
        'EP',
        'LP',
        'Live Performance'
      ],
      selectedCollectionType: undefined,
      releaseDate: undefined,
      publishDateCheckbox: false,
      publishDate: undefined,
      publishTime: undefined,

      artworkFile: undefined
    }
  },
  methods: {
    openModal() {
      this.show = true
      console.log('open modal')
    },
    closeModal() {
      this.show = false
    },
    handleArtworkUpload(event) {
      this.artworkFile = event.target.files[0]
    },
    isFormDataValid() {
      if (this.collectionName > 64 || this.collectionName === '') {
        return false
      }
      if (this.selectedCollectionType === undefined) {
        return false
      }
      if (this.releaseDate === undefined) {
        return false
      }
      if (this.artworkFile === undefined) {
        return false
      }
      //
      return true
    },
    async createCollection() {
      if (!this.isFormDataValid()) {
        return
      }

      this.loading = true

      console.log('unutar createCollection')

      let formData = new FormData()
      formData.append('key-data', this.formatFormDataObjectAsBlob())
      formData.append('key-file', this.artworkFile)
      let token = await this.$auth_artist.getTokenSilently()
      BackendService.createCollection(formData, token)
          .then(res => {
            console.log(res.data, '    createCollection')

            // emitting event so select newly created collection
            const createdColl = {
              id: res.data.id,
              name: res.data.name,
              type: res.data.collection_type,
              date: res.data.official_release_date
            }
            this.$emit('event-selectNewlyCreatedColl', createdColl)

            this.$store.dispatch('artist/getArtistAlbums', {
              accessToken: token
            })
            this.loading = false
            this.removeInputs()
            this.closeModal()
          })
          .catch(error => {
            console.log(error.response)
            this.loading = false
          })
    },
    removeInputs() {
      this.collectionName = ''
      this.collectionDescription = ''
      this.selectedCollectionType = undefined
      this.releaseDate = undefined
      this.publishDateCheckbox = false
      this.publishDate = undefined
      this.publishTime = undefined
      this.artworkFile = undefined
    },
    formatFormDataObjectAsBlob() {
      const obj = {
        'name': this.collectionName,
        'collectionType': this.selectedCollectionType,
        'description': this.collectionDescription,
        'releaseDate': this.releaseDate
      }
      if (this.publishDateCheckbox) {
        obj.publishDate = this.publishDate
        //obj.publishTime = this.publishTime // za sada cu zanemarit vrijeme -> treba napraviti konverziju u datetime...
        obj.publishTime = 0
      }

      const json = JSON.stringify(obj)
      return new Blob([json], {
        type: 'application/json'
      })
    },
    helper() {
      if (this.publishDateCheckbox === false) {
        this.publishDate = undefined
        this.publishTime = undefined
      }
    }
  }
}
</script>

<style lang="scss" scoped>

$spinner-animation-speed: .5s;

.input-style {
  color: #e0e0e0;
  background-color: #23232d;
  border-color: #8f8f9a;
  &:focus {
    color: #e0e0e0;
    background-color: #23232d;
  }
}

.form-control {
  &:disabled {
    background-color: #23232d;
  }
}

// invert color of date and time picker icon
::-webkit-calendar-picker-indicator {
  filter: invert(1);
}

#a-modal1 {
  display: block;
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
    }
    .create {
      color: black;
      background-color: #1dc0dc;
    }
    .dismiss {
      background-color: #c63a5d;
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