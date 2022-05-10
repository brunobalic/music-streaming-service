<template>
  <div class="user-body">

    <select-collection-modal ref="modalName3"
                             @event-selectedColl="displaySelectedCollection"></select-collection-modal>
    <create-collection-modal ref="modalName4"
                             @event-selectNewlyCreatedColl="displaySelectedCollection"></create-collection-modal>

    <div class="container-md">

      <div class="py-5">
        <h3>Upload New Music</h3>
      </div>

      <div class="row justify-content-center">
        <div class="col-md-7 col-lg-8" v-bind:class="[isLoading ? 'disabled' : '']">
          <form class="needs-validation" novalidate="" style="padding-bottom: 20px">
            <div class="row g-3">

              <div class="col-12 mb-3 input-group">
                <span id="title-desc" class="input-group-text"
                      style="background-color: #e0e0e0; color: #23232d;">Title</span>
                <input v-model="title" aria-describedby="title-desc" class="form-control input-style"
                       placeholder="Enter Title here..."
                       required="" type="text">
              </div>

              <hr class="mt-2 mb-4">

              <div class="col-12" style="padding-bottom: 20px">
                <label class="form-label" for="coll-new2">Collection</label><br>

                <div class="row">
                  <div class="col-2">
                    <button id="coll-new2" class="btn btn-light btn-lg"
                            style="background-color: #e0e0e0; font-size: 1rem; float: left; width: 100%; padding-bottom: 6px; padding-top: 6px"
                            type="button"
                            v-on:click="$refs.modalName3.openModal()">Select
                    </button>
                  </div>
                  <div class="col-10">
                    <!--<input type="text" class="form-control" v-bind:value="[selectedCollectionData===undefined ? '' : (selectedCollectionData.name + ' - ' + selectedCollectionData.type + ' - ' + selectedCollectionData.date)]" placeholder="Track will be added to selected collection" readonly>-->
                    <input class="form-control" placeholder="Track will be added to selected collection" readonly
                           type="text" v-bind:value="selectedCollection_Value()">
                  </div>
                </div>

                <div class="row" style="padding-top: 20px; justify-content: center">
                  <div class="col-6">
                    <button id="coll-new4" class="btn btn-light btn-lg"
                            style="background-color: #e0e0e0; font-size: 1rem; float: left; width: 100%" type="button"
                            v-on:click="$refs.modalName4.openModal()">Or create new
                      Collection
                    </button>
                  </div>
                </div>

              </div>

              <hr class="mt-2 mb-4">

              <div class="col-12" style="padding-bottom: 20px">
                <label class="form-label" for="file1">Select Audio File</label>
                <input id="file1"
                       class="form-control"
                       required type="file"
                       v-on:change="handleFileUpload($event)">
                <div class="invalid-feedback">
                  Valid file is required!
                </div>
              </div>

            </div>

            <hr class="mt-2 mb-4">

            <div v-if="successMsg !== ''" class="col-12 mb-4">
              <span class="text-success">{{ successMsg }}</span>
            </div>
            <div v-if="errorMsg !== ''" class="col-12 mb-4">
              <span class="text-warning">{{ errorMsg }}</span>
            </div>

            <div v-if="isLoading" class="mb-4">
              <div class="spinner-border" role="status">
                <span class="visually-hidden">Uploading...</span>
              </div>
            </div>

            <button class="w-50 btn btn-light btn-lg" style="background-color: #e0e0e0" type="button"
                    v-on:click="submitFileUpload">
              <span>Upload</span>
            </button>

          </form>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
import _ from 'lodash'
import {createNamespacedHelpers} from 'vuex'
import SelectCollectionModal from '../../components/artist/SelectCollectionModal'
import CreateCollectionModal from '../../components/artist/CreateCollectionModal'
import BackendService from '../../services/artist/BackendService'

const {mapState} = createNamespacedHelpers('artist')

export default {
  name: 'UploadMedia',
  components: {
    SelectCollectionModal,
    CreateCollectionModal
  },
  data() {
    return {
      isLoading: false,
      errorMsg: '',
      successMsg: '',
      file: '',
      title: '',
      selectedCollectionData: undefined
    }
  },
  created() {
    this.test1 = _.debounce(this.test1, 5000)
    this.removeSuccessMsg = _.debounce(this.removeSuccessMsg, 5000)
  },
  computed: {
    ...mapState({
      a_al: state => state.artistAlbums
    })
  },
  methods: {
    test1: function () {
      console.log("this is debounced")
    },
    triggerDebounce() {
      console.log("triggered")
      this.test1()
    },
    //
    displaySelectedCollection(data) {
      console.log('displaySelectedCollection - ', data)
      this.selectedCollectionData = data
    },
    selectedCollection_Value() {
      const tmp = this.selectedCollectionData
      return (tmp === undefined ? '' : (tmp.name + ' · ' + tmp.type + ' · ' + tmp.date))
    },
    handleFileUpload(event) {
      this.file = event.target.files[0]
    },
    isFormDataValid() {
      if (this.title === '' || this.title.length > 32) {
        this.errorMsg = 'Please enter valid title (up to 32 characters)!'
        return false
      } else if (this.selectedCollectionData === undefined) {
        this.errorMsg = 'Collection is not selected!'
        return false
      } else if (this.file === '') {
        this.errorMsg = 'Audio File is not selected!'
        return false
      }
      //
      return true
    },


    // po nekom primjeru iz vue dokumentacije , nesto sa Watchers
    // limitacija ovoga je sto ne mogu prosljedit paramtere ???
    // removeSuccessMsg: _.debounce(
    //     function () {
    //       this.successMsg = ''
    //     },
    //     5000
    // ),
    // displaySuccessMsg() {
    //   this.successMsg = 'Track Successfully uploaded!'
    //   this.removeSuccessMsg() // ovo je error zbog webstorm-a, inace sve je pravilno, tocno ovako je i u vuejs sluzbenoj dokumentaciji
    // },

    removeSuccessMsg: function () {
      this.successMsg = ''
    },
    displaySuccessMsg() {
      this.successMsg = 'Track Successfully uploaded!'
      this.removeSuccessMsg()
    },
    async submitFileUpload() {
      // In case second consecutive upload is performed
      // success message from previous submit needs to be removed
      this.successMsg = ''

      // Remove prevoius error message
      this.errorMsg = ''

      if (!this.isFormDataValid()) {
        return
      }
      this.isLoading = true

      let trackData = {
        title: this.title,
        collectionId: this.selectedCollectionData.id
      }

      let formData = new FormData()
      formData.append('data', new Blob([JSON.stringify(trackData)], {type: 'application/json'}))
      formData.append('file', this.file)

      let token = await this.$auth_artist.getTokenSilently()
      BackendService.uploadTrack(formData, token)
          .then(res => {
            console.log('upload track - uspjesan', res)
            this.title = ''
            this.selectedCollectionData = undefined
            // deselecting file
            const file = document.getElementById('file1')
            file.value = file.defaultValue
            this.errorMsg = ''
            this.displaySuccessMsg()
            this.isLoading = false
          })
          .catch(error => {
            // console.log("upload track - error", error)
            console.log(error.response.status, error.response.data)
            // console.log(error.response.headers)
            // console.log(error.response.data)
            // console.log(error.response.data.message)

            if (error.response.data.message) {
              this.errorMsg = error.response.data.message
            } else {
              this.errorMsg = 'Unknown error occurred!'
            }
            this.isLoading = false
          })
    }
  }
}
</script>

<style lang="scss" scoped>

// custom spinner
//.my-spinner1 {
//  display: inline-block;
//  transform: translateZ(1px);
//}
//.my-spinner1 > div {
//  display: inline-block;
//  width: 32px;
//  height: 32px;
//  margin: 8px;
//  border-radius: 50%;
//  background: #fff;
//  animation: lds-circle 2.4s cubic-bezier(0, 0.2, 0.8, 1) infinite;
//}
//@keyframes lds-circle {
//  0%, 100% {
//    animation-timing-function: cubic-bezier(0.5, 0, 1, 0.5);
//  }
//  0% {
//    transform: rotateY(0deg);
//  }
//  50% {
//    transform: rotateY(1800deg);
//    animation-timing-function: cubic-bezier(0, 0.5, 0.5, 1);
//  }
//  100% {
//    transform: rotateY(3600deg);
//  }
//}

.disabled {
  pointer-events: none;
  opacity: 0.5;
  transition: 0.5s;
}


.input-style {
  color: #e0e0e0;
  background-color: #23232d;
  border-color: #8f8f9a;

  &:focus {
    color: #e0e0e0;
    background-color: #23232d;
  }
}

.container {
  color: #e0e0e0;

  .input-group-text {
    background-color: rgb(35, 35, 45);
    color: #e0e0e0;
  }

  .form-control {
    //background-color: transparent;
    background-color: rgb(35, 35, 45);
    border-color: #8f8f9a;
    color: #e0e0e0;
  }

}

::-webkit-calendar-picker-indicator {
  filter: invert(1);
}

</style>